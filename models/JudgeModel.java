package models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JudgeModel {
    public static class JudgeResult {
        public final Integer score;
        public final String comment;

        public JudgeResult(Integer score, String comment) {
            this.score = score;
            this.comment = comment;
        }
    }

    private static final Pattern SCORE_PATTERN = Pattern.compile("(\\d+)");

    public JudgeResult parseJudgeResult(String aiResponse) {
        if (aiResponse == null || aiResponse.trim().isEmpty()) {
            return new JudgeResult(null, null);
        }

        String trimmed = aiResponse.trim();
        if (trimmed.startsWith("{")) {
            JudgeResult parsed = parseJson(trimmed);
            if (parsed != null) {
                return parsed;
            }
        }

        return parseLines(trimmed);
    }

    public String formatJudgeResult(String aiResponse) {
        JudgeResult result = parseJudgeResult(aiResponse);
        String scoreText = result.score != null ? result.score + "点" : "N/A";
        String commentText = result.comment != null ? result.comment : "";
        return "点数: " + scoreText + "\nコメント: " + commentText;
    }

    private JudgeResult parseJson(String json) {
        try {
            JsonElement root = JsonParser.parseString(json);
            if (!root.isJsonObject()) {
                return null;
            }
            JsonObject obj = root.getAsJsonObject();
            Integer score = obj.has("点数") ? obj.get("点数").getAsInt() : null;
            String comment = obj.has("コメント") ? obj.get("コメント").getAsString() : null;
            if (score == null && comment == null) {
                return null;
            }
            return new JudgeResult(score, comment);
        } catch (RuntimeException e) {
            return null;
        }
    }

    private JudgeResult parseLines(String text) {
        Integer score = null;
        String comment = null;
        String[] lines = text.split("\\R");
        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.startsWith("点数") || trimmed.toLowerCase().startsWith("score")) {
                Matcher matcher = SCORE_PATTERN.matcher(trimmed);
                if (matcher.find()) {
                    score = Integer.parseInt(matcher.group(1));
                }
            } else if (trimmed.startsWith("コメント") || trimmed.toLowerCase().startsWith("comment")) {
                comment = stripLabel(trimmed);
            }
        }
        return new JudgeResult(score, comment);
    }

    private String stripLabel(String line) {
        int colonIndex = line.indexOf(':');
        if (colonIndex == -1) {
            colonIndex = line.indexOf('：');
        }
        if (colonIndex >= 0 && colonIndex + 1 < line.length()) {
            return line.substring(colonIndex + 1).trim();
        }
        if (line.startsWith("コメント")) {
            return line.substring("コメント".length()).trim();
        }
        if (line.toLowerCase().startsWith("comment")) {
            return line.substring("comment".length()).trim();
        }
        return line.trim();
    }
}
