# simple-twitter-netpro2020 (SimpleTwitterClient)
大学授業のミニプロジェクト作品として作成したものです。

# 使い方
- [Twitter4J](http://twitter4j.org/ja/)をダウンロードし，クラスパスを通してください。なお，このミニプロジェクト開発の際はバージョン4.0.7を使用しました。

- [Twitter applicationの作成およびアクセストークン等の準備](https://apps.twitter.com/)をし，`src/`に`twitter4j.properties`を以下の内容で作成してください。xxxx...の部分はそれぞれ対応する内容に書き換えてください。

```properties:twitter4j.properties
debug=true
oauth.consumerKey=xxxxxxxxxxxxxxxxxxxxx
oauth.consumerSecret=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
oauth.accessToken=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
oauth.accessTokenSecret=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```
- `SimpleTwitterMain.java`を実行してください。
- `SimpleTwitterGUI.java`を実行して，ウインドウが表示されたらはじめに`SimpleTwitterMain.java`を実行しているコンピュータのアドレスを入力してください。（同一なら`localhost`）
- 接続に成功すると，タイムラインの確認やツイート操作ができます。
