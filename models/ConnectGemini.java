package models;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ConnectGemini {
    private String key=System.getenv("GEMINI_API_KEY");
    public String connect(String prompt) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://generativelanguage.googleapis.com/v1beta/models/gemini-3-flash-preview:generateContent"))
            .header("x-goog-api-key", key)
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString("""
                {
                    "contents": [{
                        "parts": [{ "text": "%s" }]
                    }],
                    "generationConfig": {
                        "responseMimeType": "application/json",
                        "responseJsonSchema": {
                            "type": "object",
                            "properties": {
                                "点数": { "type": "integer", "minimum": 0, "maximum": 100 },
                                "コメント": { "type": "string" }
                            },
                            "required": ["点数", "コメント"],
                            "additionalProperties": false
                        }
                    }
                }
                """.formatted(prompt)))
            .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responce_text = response.body();
            return parseJson(responce_text);
        } catch(IOException | InterruptedException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String parseJson(String json) {
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
        return sb.toString();
    }
}
