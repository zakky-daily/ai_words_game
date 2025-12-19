package models;

public class TestGemini {
    public static void main(String[] args) {
        models.ConnectGemini a = new ConnectGemini();
        String responce = a.connect("以下の俳句の評価をしてください。点数(最大100点)と、コメントを書いてください。\n\n「ランドセル　暴れる文豪　つめこんで」");
        System.out.println(responce);
    }
}
