import controllers.*;

class Main {
    public static void main(String[] args) {

        if (System.getenv("GEMINI_API_KEY") == null) {
            System.err.println("シェルで環境変数 GEMINI_API_KEY を設定してから起動してください。例：export GEMINI_API_KEY=【値】");
            System.exit(1);
        }
        
        new MainController();
    }
}