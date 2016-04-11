# 5.DI(おまけ)

もともと、今回の勉強会の目的は前回Spring Bootを取り扱ったときにDIやらなんやらでアノテーションが使われているけれどもよく判らないという声に押されてのものでした。
なので、最後にDIコンテナを作ってみようと思っていたのですが、
説明したかったことの大半が「きしだのはてな 作って理解するDIコンテナ」に書かれてしまったので、そちらを参照してください。
今回の内容を理解していれば以下の記事が理解できるはずです。
http://d.hatena.ne.jp/nowokay/20160406

なお、記事の中でいくつかライブラリの依存が出てきています。新しくプロジェクトを作成したらpom.xmlに以下の依存関係を追加してから始めてください。

```pom.xml
    <!-- ここから追加 -->
    <dependencies>
        <dependency>
            <groupId>javax.inject</groupId>
            <artifactId>javax.inject</artifactId>
            <version>1</version>
        </dependency>
        <dependency>
            <groupId>org.javassist</groupId>
            <artifactId>javassist</artifactId>
            <version>3.20.0-GA</version>
        </dependency>
    </dependencies>
    <!-- ここまで追加 -->
</project><!-- 最後の閉じタグのすぐ上に追加する -->
```
