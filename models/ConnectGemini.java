package models;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ConnectGemini {
    private String key=System.getenv("GEMINI_API_KEY");
    public Map<String, Object> connect(String prompt) {
        HttpClient client = HttpClient.newHttpClient();
        String body = String.format("{\n" +
            "  \"contents\": [{\n" +
            "    \"parts\": [{ \"text\": \"%s\" }]\n" +
            "  }],\n" +
            "  \"generationConfig\": {\n" +
            "    \"responseMimeType\": \"application/json\",\n" +
            "    \"responseJsonSchema\": {\n" +
            "      \"type\": \"object\",\n" +
            "      \"properties\": {\n" +
            "        \"点数\": { \"type\": \"integer\", \"minimum\": 0, \"maximum\": 100 },\n" +
            "        \"コメント\": { \"type\": \"string\" }\n" +
            "      },\n" +
            "      \"required\": [\"点数\", \"コメント\"],\n" +
            "      \"additionalProperties\": false\n" +
            "    }\n" +
            "  }\n" +
            "}\n", prompt);
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://generativelanguage.googleapis.com/v1beta/models/gemini-3-flash-preview:generateContent"))
            .header("x-goog-api-key", key)
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responce_text = response.body();
            return parseJson(responce_text);
        } catch(IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, Object> parseJson(String json) {
        JsonObject root = JsonParser.parseString(json).getAsJsonObject();
        if (root.has("error")) {
            System.err.println("Geminiとの通信に失敗しました。エラーコード:\n" + json);
            return null;
        }

        JsonObject cand = root.getAsJsonArray("candidates").get(0).getAsJsonObject();
        JsonObject content = cand.getAsJsonObject("content");
        JsonArray parts = content.getAsJsonArray("parts");
        StringBuilder sb = new StringBuilder();
        for (JsonElement e : parts) {
            JsonObject part = e.getAsJsonObject();
            if (part.has("text")) sb.append(part.get("text").getAsString());
        }

        // ここから：Geminiが返した本文(JSON文字列)をパースして Map に詰める
        JsonObject body = JsonParser.parseString(sb.toString()).getAsJsonObject();

        Map<String, Object> result = new HashMap<>();
        result.put("score", body.get("点数").getAsInt());
        result.put("message", body.get("コメント").getAsString());
        return result;
    }
}
