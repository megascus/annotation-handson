# 1.リフレクションを使用したプログラミング

アノテーションを勉強する前の前提条件としてリフレクションを使用したプログラミングが行える必要があります。
まずは、リフレクションを使用したプログラミングがどのようなものかを体験します。

## リフレクションとは

リフレクション (reflection) は、プログラムの実行中にプログラム自身の情報を操作することを言います。
プログラム自身の情報は一般的にはメタデータと呼ばれます。
プログラムのメタデータにアクセスすることで動的にクラスを作成したりすることも出来ます。

## クラスの準備

リフレクションを使用したプログラムから使うように以下のクラスを作成してください。


megascus.annotation.handson.refrection.TestTarget.java

```java:TestTarget.java
package megascus.annotation.handson.refrection;

public class TestTarget {

    public TestTarget() {
    }

    public static String staticMethod1(String arg) {
        return arg;
    }

    public String method1(String... args) { // 可変長引数
        return args.length > 0 ? args[0] : "";
    }
}

```

## リフレクションを使用せずにプログラミングを行ってみる。

まずは、リフレクションをまったく使わないバージョンのプログラムを作成します。
以下のクラスを作成してください。

megascus.annotation.handson.refrection.Main.java

```java:Main.java
package megascus.annotation.handson.refrection;

public class Main {

    public static void main(String[] args) {
        
        TestTarget instance = new TestTarget(); //クラスを指定してインスタンスを作成

        System.out.println(TestTarget.staticMethod1("文字列")); //スタティックメソッドを実行

        System.out.println(instance.method1("文字列1", "文字列2")); //インスタンスメソッドを実行
    }
}
```

何も面白みのないプログラムです。
実行は作成したMain.javaファイルを右クリック→「ファイルの実行」から行うことが出来ます。

## リフレクションを使用して書き直す。

### クラスの作成

まず、以下のクラスを作成してください。

megascus.annotation.handson.refrection.ReflectionMain.java

```java:ReflectionMain.java
package megascus.annotation.handson.refrection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class ReflectionMain {

    public static void main(String[] args) throws ReflectiveOperationException {
       //これから内容を追加していく場所
    }
}

```

### クラスを指定してインスタンスを作成

Main.javaで書いてあった以下の記載を書き直します。
```java:Main.java
        TestTarget instance = new TestTarget(); //クラスを指定してインスタンスを作成
```

リフレクションを使うと以下のように書き直すことが出来ます。 ReflectionMainのmainメソッド内に追記してください。

```java:ReflectionMain.java
        Class<?> clazz = Class.forName("megascus.annotation.handson.refrection.TestTarget"); //クラスのメタデータを取得する
        Constructor<?> constructor = clazz.getConstructor(); //クラスのメタデータからコンストラクタを取得する
        Object instance = constructor.newInstance(); //コンストラクタを実行してインスタンスを作成する
        instance = clazz.newInstance(); // デフォルトコンストラクタが存在する場合はコンストラクタクラスを経由せずにインスタンスを作成できる。
```

今回はConstructorクラスのオブジェクトを作成してからインスタンスを作っていますが、クラスにデフォルトコンストラクタがある場合は以下のようにConstructorクラスのオブジェクトの生成は省略することが出来ます。

```java:ReflectionMain.java
        Class<?> clazz = Class.forName("megascus.annotation.handson.refrection.TestTarget"); //クラスのメタデータを取得する
        Object instance = clazz.newInstance(); // デフォルトコンストラクタが存在する場合はコンストラクタクラスを経由せずにインスタンスを作成できる。
```

## スタティックメソッドを実行

Main.javaで書いてあった以下の記載を書き直します。
```java:Main.java
        System.out.println(TestTarget.staticMethod1("文字列")); //スタティックメソッドを実行
```

リフレクションを使うと以下のように書き直すことが出来ます。 ReflectionMainのmainメソッド内に追記してください。

```java:ReflectionMain.java
        Method staticMethod = instance.getClass().getMethod("staticMethod1", String.class);
        System.out.println(staticMethod.invoke(null, "文字列")); //スタティックメソッドの実行時は第一引数にnullを入れる
```

## インスタンスメソッドを実行

Main.javaで書いてあった以下の記載を書き直します。
```java:Main.java
        System.out.println(instance.method1("文字列1", "文字列2")); //インスタンスメソッドを実行
```

リフレクションを使うと以下のように書き直すことが出来ます。 ReflectionMainのmainメソッド内に追記してください。

```java:ReflectionMain.java
        Method instanceMethod = instance.getClass().getMethod("method1", String[].class);
        //可変長引数はJavaでは配列として扱う
        System.out.println(instanceMethod.invoke(instance, (Object) new String[]{"文字列1", "文字列2"}));
```

ここまで来たら一度実行してみてください。Main.javaとReflectinMain.javaで同じ結果が得られると思います。



また、スタティックメソッド実行時と違ってインスタンスメソッド実行時にはMethod#invoke()メソッドの第一引数に実行するオブジェクトを入れてあげる必要があります。
以下のように入れない場合はNullPointerExceptionが発生します。

```java:ReflectionMain.java
        try {
            instanceMethod.invoke(null, (Object) new String[]{"文字列1", "文字列2"}); //インスタンスメソッドの第一引数にnullを入れるとNullPointerExceptionが発生する
        } catch (NullPointerException ex) {
            System.out.println("null");
        }

```

## リフレクションの危険性

いままで、リフレクションを使ってきましたが、面倒くさいものだと思います。面倒くさいだけではなく、極力使わないほうが良い危険なものでもあります。

TestTarget.javaの中身を削除してみてください。Main.javaではTestTarget.javaが見つからないというコンパイルエラーが発生しますが、ReflectionMain.javaではコンパイルエラーは発生しません。
そのままコンパイルが行え、実行時にやっとReflectiveOperationExceptionが発生します。

リフレクションは必要が無い場合は極力使用しないほうが静的言語の恩恵を受けやすいです。

ただし、アノテーションを扱うためにはリフレクションが必要です。
