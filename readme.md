# ぼっちのためのAIコミュレーター

限られた言葉で面白い文章をつくる面白いゲーム

## Gemini APIに関して

本ゲームでは、プレイヤー自身でGeminiのAPIキーを設定することが必要です。APIキーの取得方法については`https://aistudio.google.com/`を参照してください。

## 実行方法(Mac/Linux)

**↓はシェル立ち上げ時に1回だけやる**

1. `export GEMINI_API_KEY=【APIキー】`

**↓は毎回やる**

2. `javac -d bin -cp ".;lib/*" Main.java controllers/*.java`

3. `java -cp "bin;lib/*" Main`

## 実行方法(Windows-Powershell)

**↓はシェル立ち上げ時に1回だけやる**

1. `$env:GEMINI_API_KEY="【APIキー】"`

2. `$env:CLASSPATH="bin;lib\*"`

3. `New-Item -ItemType Directory -Force bin | Out-Null`

**↓は毎回やる**

4. `javac -d bin (Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName }) ; java Main`

あとでかく
