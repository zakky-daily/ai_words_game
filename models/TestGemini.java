package models;

public class TestGemini { 
    public static void main(String[] args) {
        ConnectGemini a = new ConnectGemini();

        // 信託風ゲーム
        /*
        a.connect("今から「信託風ゲーム」の定義をします……");
        */

        // 俳句評価
        String response = a.connect(
            "以下の俳句の評価をしてください。点数(最大100点)と、コメントを書いてください。\n\n"
            + "「ランドセル　暴れる文豪　つめこんで」"
        );
        System.out.println(response);
    }
}