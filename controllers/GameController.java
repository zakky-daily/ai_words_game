package controllers;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lib.CardInfo;
import views.MainFrame;

public class GameController {
    
    public GameController(MainFrame mainFrame) {
        mainFrame.startGame();
        mainFrame.setVisible(true);
    }

    public String GenerateSentence(ArrayList<CardInfo> cards) {
        cards.sort(Comparator.comparingInt(c -> c.lastp.x));
        String res = "";
        for (CardInfo c : cards) res += c.word;
        return res;
    }

    public ArrayList<String> DecideWords(String theme) {
        try {
            String json = Files.readString(Path.of( "models", "card_list.json"));
            Type type = new TypeToken<Map<String, List<String>>>() {}.getType();
            Map<String, List<String>> map = new Gson().fromJson(json, type);

            ArrayList<String> res = new ArrayList<>();
            res.addAll(map.get(theme + "_common"));

            ArrayList<String> pool = new ArrayList<>(map.get(theme + "_random"));
            Collections.shuffle(pool);
            res.addAll(pool.subList(0, Math.min(6, pool.size())));

            return res;
        } catch (IOException e) {
            System.err.println("card_list.jsonが読み込めませんでした");
            return null;
        }
    }
}
