package controllers;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import javax.imageio.ImageIO;
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
                } else if (e.getActionCommand().equals("SHARE")) {
                    shareOnTwitter(themeKey, createdText);
                }
            }
        });
        String themeText;
        switch (themeKey) {
            case "oracle":
                themeText = "神託";
                break;
            case "propose":
                themeText = "プロポーズ";
                break;
            case "begging":
                themeText = "命乞い";
                break;
            default:
                themeText = "???";
        }

        // Geminiにリクエスト(非同期)
        new Thread(() -> {
            String prompt = String.format(
                "# 指示\n" +
                "ロールプレイをします。他プレイヤーが作成した心打たれる文章をジャッチするのが、あなたの役目です。\n" +
                "以下の「入力データ」「ルール」をもとに文章を評価し、「出力形式」に従って結果を返してください。\n" +
                "\n" +
                "# 入力データ\n" +
                "- テーマ：(例：神託、プロポーズ、命乞い)\n" +
                "- 文章：(プレイヤーが作成した文章)\n" +
                "\n" +
                "# 判定ルール\n" +
                "## ステップ1：文法チェック\n" +
                "文章が日本語として到底理解不能、または意味不明な単語の羅列である場合は、0点としてください。\n" +
                "\n" +
                "## ステップ2：点数の算出\n" +
                "以下の基準によって、**0〜100点**で点数を出してください。\n" +
                "- テーマに沿っているか？\n" +
                "- 文章として面白いか？（意外性、シュールさ、切実さ）\n" +
                "- 同じ文章に対しては常に同じ点数になるよう、厳格に評価する\n" +
                "\n" +
                "## ステップ3：コメント生成\n" +
                "あなたは「そのテーマを信奉する信者」または「評価者」になりきってください。\n" +
                "送られた文章に対し、30〜40字程度の短いコメントを書いてください。\n" +
                "- 分析的視点(4割)と感情的視点(6割)を混ぜて、少し砕けた口調で話してください\n" +
                "- 0点の場合は、辛辣にツッコミを入れてください\n" +
                "\n" +
                "# 出力形式\n" +
                "点数とコメントをjson形式で返してください。\n" +
                "\n" +
                "# 今回の入力\n" +
                "テーマ：%s\n" +
                "文章：%s\n",
                themeText, createdText
            );
            var response = mainCtrl.connectGemini.connect(prompt);

            SwingUtilities.invokeLater(() -> {
                if (response != null) {
                    view.updateLabel(1, "" + response.get("score"));
                    view.updateLabel(2, "" + response.get("message"));
                } else {
                    view.updateLabel(2, "通信エラーが発生しました。");
                }
                view.showXButton();
                captureJudgeScene();
            });
        }).start();
    }

    // スクショ撮影
    private void captureJudgeScene() {
        BufferedImage image = new BufferedImage(view.getWidth(), view.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        view.paintAll(g);
        g.dispose();
        try {
            ImageIO.write(image, "png", new File("res/judge_scene.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Twitter共有機能
    private void shareOnTwitter(String themeKey, String createdText) {
        SwingUtilities.invokeLater(() -> view.showLoading());
        new Thread(() -> {
            try {
                File merged = buildMergedImage();
                String imageUrl = uploadToCatbox(merged, "image/png");
                if (imageUrl == null || imageUrl.isBlank()) {
                    return;
                }
                openTweetComposerWithUrl(imageUrl, themeKey, createdText);
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                SwingUtilities.invokeLater(() -> view.hideLoading());
            }
        }).start();
    }

    // ゲーム画面と結果画面を結合
    private File buildMergedImage() throws IOException {
        BufferedImage left = ImageIO.read(new File("res/game_scene.png"));
        BufferedImage right = ImageIO.read(new File("res/judge_scene.png"));
        int width = left.getWidth() + right.getWidth();
        int height = Math.max(left.getHeight(), right.getHeight());
        BufferedImage merged = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = merged.createGraphics();
        g.drawImage(left, 0, 0, null);
        g.drawImage(right, left.getWidth(), 0, null);
        g.dispose();
        File out = new File("res/share_scene.png");
        ImageIO.write(merged, "png", out);
        return out;
    }

    // Catboxに画像アップロード
    private String uploadToCatbox(File file, String contentType) throws IOException {
        String boundary = "----CodexBoundary" + System.currentTimeMillis();
        HttpURLConnection conn = (HttpURLConnection) new URL("https://catbox.moe/user/api.php").openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        String lineBreak = "\r\n";
        try (OutputStream out = conn.getOutputStream()) {
            out.write(("--" + boundary + lineBreak).getBytes(StandardCharsets.UTF_8));
            out.write("Content-Disposition: form-data; name=\"reqtype\"".getBytes(StandardCharsets.UTF_8));
            out.write((lineBreak + lineBreak).getBytes(StandardCharsets.UTF_8));
            out.write("fileupload".getBytes(StandardCharsets.UTF_8));
            out.write(lineBreak.getBytes(StandardCharsets.UTF_8));

            out.write(("--" + boundary + lineBreak).getBytes(StandardCharsets.UTF_8));
            out.write(("Content-Disposition: form-data; name=\"fileToUpload\"; filename=\"" + file.getName() + "\"").getBytes(StandardCharsets.UTF_8));
            out.write(lineBreak.getBytes(StandardCharsets.UTF_8));
            out.write(("Content-Type: " + contentType).getBytes(StandardCharsets.UTF_8));
            out.write((lineBreak + lineBreak).getBytes(StandardCharsets.UTF_8));
            out.write(Files.readAllBytes(file.toPath()));
            out.write(lineBreak.getBytes(StandardCharsets.UTF_8));
            out.write(("--" + boundary + "--" + lineBreak).getBytes(StandardCharsets.UTF_8));
        }

        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            return null;
        }
        return new String(conn.getInputStream().readAllBytes(), StandardCharsets.UTF_8).trim();
    }

    // TwitterのURLを構築して開く
    private void openTweetComposerWithUrl(String urlToShare, String themeKey, String createdText) throws IOException {
        if (!Desktop.isDesktopSupported()) {
            return;
        }

        String timeText;
        switch (themeKey) {
            case "oracle":
                timeText = "聖なるお告げ";
                break;
            case "propose":
                timeText = "プロポーズ中";
                break;
            case "begging":
                timeText = "命乞い中";
                break;
            default:
                timeText = "???";
        }

        String personText;
        switch (themeKey) {
            case "oracle":
                personText = "神";
                break;
            case "propose":
                personText = "俺";
                break;
            case "begging":
                personText = "俺";
                break;
            default:
                personText = "???";
        }

        String text = String.format(
            "#ぼっちAIコミュレーター\n\n" +
            "【%s】\n" +
            "%s「%s」\n\n" +
            "★☆衝撃のAI審査結果は...こちらでした！☆★\n" +
            "%s",
            timeText, personText, createdText, urlToShare
        );       
        String url = "https://twitter.com/intent/tweet?text=" + URLEncoder.encode(text, StandardCharsets.UTF_8);
        Desktop.getDesktop().browse(URI.create(url));
    }
    
}
