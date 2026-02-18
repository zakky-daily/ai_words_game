package models;

import java.io.InputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GameModel {
    public static final int CARD_WIDTH = 80;
    public static final int DEFAULT_CARD_COUNT = 6;
    public static final int RANDOM_CARD_COUNT = 9;
    public static final int TOTAL_CARD_COUNT = 15;

    private static final List<String> THEME_KEYS =
        Collections.unmodifiableList(Arrays.asList("oracle", "propose", "begging"));

    private final Random random = new Random();
    private Map<String, List<String>> cardMap;

    public int pickRandomThemeId() {
        return random.nextInt(THEME_KEYS.size());
    }

    public String getThemeKey(int themeId) {
        if (themeId < 0 || themeId >= THEME_KEYS.size()) {
            return THEME_KEYS.get(0);
        }
        return THEME_KEYS.get(themeId);
    }

    public ArrayList<String> getRandomCards(String themeKey) {
        return getCards(themeKey, "random", RANDOM_CARD_COUNT, true);
    }

    public ArrayList<String> getDefaultCards(String themeKey) {
        return getCards(themeKey, "common", DEFAULT_CARD_COUNT, false);
    }

    public ArrayList<String> buildCardSet(String themeKey) {
        ArrayList<String> defaults = getDefaultCards(themeKey);
        ArrayList<String> randoms = getRandomCards(themeKey);

        ArrayList<String> result = new ArrayList<>(TOTAL_CARD_COUNT);
        result.addAll(defaults);
        result.addAll(randoms);

        if (result.size() < TOTAL_CARD_COUNT) {
            ArrayList<String> filler = new ArrayList<>(defaults);
            filler.addAll(randoms);
            int i = 0;
            while (result.size() < TOTAL_CARD_COUNT && i < filler.size()) {
                result.add(filler.get(i));
                i++;
            }
        }

        if (result.size() > TOTAL_CARD_COUNT) {
            return new ArrayList<>(result.subList(0, TOTAL_CARD_COUNT));
        }
        return result;
    }

    private ArrayList<String> getCards(String themeKey, String suffix, int count, boolean shuffle) {
        if (themeKey == null) {
            return new ArrayList<>();
        }
        Map<String, List<String>> map = loadCardMap();
        List<String> source = map.get(themeKey + "_" + suffix);
        if (source == null || source.isEmpty()) {
            return new ArrayList<>();
        }

        ArrayList<String> result = new ArrayList<>();
        if (shuffle) {
            ArrayList<String> pool = new ArrayList<>(source);
            Collections.shuffle(pool, random);
            int take = Math.min(count, pool.size());
            result.addAll(pool.subList(0, take));
        } else {
            int take = Math.min(count, source.size());
            result.addAll(source.subList(0, take));
        }
        return result;
    }

    private Map<String, List<String>> loadCardMap() {
        if (cardMap != null) {
            return cardMap;
        }
        try {
            //String json = Files.readString(Path.of("models", "card_list.json"));
            java.io.InputStream is = getClass().getClassLoader().getResourceAsStream("models/card_list.json");
            String json = new String(is.readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);
            Type type = new TypeToken<Map<String, List<String>>>() {}.getType();
            cardMap = new Gson().fromJson(json, type);
            return cardMap;
        } catch (IOException e) {
            System.err.println("Failed to read card_list.json");
            cardMap = Collections.emptyMap();
            return cardMap;
        }
    }

}
