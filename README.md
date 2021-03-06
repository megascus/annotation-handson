# annotation-handson
https://kumamotojava.doorkeeper.jp/events/42272

最近のJavaではアノテーションを使ったプログラミングが行われています。
Java EEやSpring、JUnit等アノテーションを使用したフレームワークもたくさん開発されており、実際に利用されています。
しかしながら、アノテーションが何をしているのかについて判らないといった声も良く聞きます。

今回はアノテーションを使ったプログラミングを行うことで、アノテーションを使ってなにができるのかについてを学びたいと思います。
ハンズオン形式です。

#対象者
対象者としては以下の通りです。

- Javaの基本的な構文がわかる
- アノテーションを使ったプログラミングを行えるようになりたい

#事前準備
事前準備としてJDK 8のインストールをお願いします。
http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
※自分の環境を選択してダウンロードしてください。

また、今回は開発環境としてNetBeans 8.1を使用します。
インストールが済んでない方はインストールをお願いします。
以下のページよりJava EEを選択してダウンロードしてください。
https://netbeans.org/downloads/?pagelang=ja
(Tomcat/GlassFishのインストールダイアログが途中でますが、そちらはインストールしなくてもかまいません)

#アジェンダ
以下の通り行う予定です。

- プロジェクト作成(事前準備)
- リフレクションを使用したプログラミング
- よく使うデザインパターン
 - Factoryパターン
 - Proxyパターン 
- アノテーションの作成方法
- アノテーションを使ってみる
 - フィールドにアノテーションをつけてみる
 - 引数にアノテーションをつけてみる
- DI(おまけ)

それぞれの章はフォルダで分けられています。ソースコードについては別フォルダに用意されています。必要に応じてそちらを参照してください。

また、このハンズオンはオンラインで行われることを想定しています。
ネットワークに繋がってない環境では行えませんのでご注意ください。
