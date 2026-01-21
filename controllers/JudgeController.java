package controllers;
import java.awt.event.*;
import javax.swing.SwingUtilities;
import views.*;

public class JudgeController{
    private MainController mainCtrl;
    private JudgeScene view;
    private MainFrame mainFrame;

    public JudgeController(MainController mc, String themeKey, String createdText){
        this.mainCtrl = mc;
        this.mainFrame = mc.mainFrame;
        this.view = mainFrame.startJudge();
        view.setEndButtonListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(e.getActionCommand().equals("END")){
                    mainCtrl.startTitle();
                }
            }
        });
        String themeText = switch (themeKey) {
            case "oracle" -> "神託";
            case "propose" -> "プロポーズ";
            case "begging" -> "命乞い";
            default -> "???";
        };
        new Thread(() -> {
            // ここはバックグラウンドスレッド（画面をフリーズさせない）
            var response = mainCtrl.connectGemini.connect("""
                # 指示
                ロールプレイをします。他プレイヤーが作成した心打たれる文章をジャッチするのが、あなたの役目です。
                以下の「入力データ」「ルール」をもとに文章を評価し、「出力形式」に従って結果を返してください。

                # 入力データ
                - テーマ：(例：神託、プロポーズ、命乞い)
                - 文章：(プレイヤーが作成した文章)

                # 判定ルール
                ## ステップ1：文法チェック
                文章が日本語として到底理解不能、または意味不明な単語の羅列である場合は、0点としてください。

            ## ステップ2：点数の算出
            以下の基準によって、**0〜100点**で点数を出してください。
            - テーマに沿っているか？
            - 文章として面白いか？（意外性、シュールさ、切実さ）
            - 同じ文章に対しては常に同じ点数になるよう、厳格に評価する

            ## ステップ3：コメント生成
            あなたは「そのテーマを信奉する信者」または「評価者」になりきってください。
            送られた文章に対し、30〜40字程度の短いコメントを書いてください。
            - 分析的視点(4割)と感情的視点(6割)を混ぜて、少し砕けた口調で話してください
            - 0点の場合は、辛辣にツッコミを入れてください

                # 出力形式
                点数とコメントをjson形式で返してください。

                # 今回の入力
                テーマ：%s
                文章：%s
                """.formatted(themeText, createdText)
            );

            // 3. 通信が終わったら、画面更新処理をEDTに依頼する
            SwingUtilities.invokeLater(() -> {
                // ここは再びEDT（安全に画面を更新できる）
                if (response != null) {
                    view.updateLabel(1, "" + response.get("score"));
                    view.updateLabel(2, "" + response.get("message"));
                } else {
                    // エラーハンドリング（API接続失敗時など）
                    view.updateLabel(2, "通信エラーが発生しました。");
                }
            });

        }).start();
    }
    
}