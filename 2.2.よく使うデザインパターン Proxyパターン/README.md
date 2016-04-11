# 2.2.よく使うデザインパターン Proxyパターン

## Proxyパターンとは

プロキシーパターンはとあるオブジェクトに処理を命令する(メソッドを呼び出す)のを他のオブジェクトに委譲するパターンです。
実務上は主にロギング処理のために使用されます。

以下のようなクラス(2つ)を用意してください。

megascus.annotation.handson.example.Executable.java

```java:Executable.java
package megascus.annotation.handson.example;

public interface Executable {
    void execute();
}
```

megascus.annotation.handson.example.ExecutableImpl.java

```java:ExecutableImpl.java
package megascus.annotation.handson.example;

public class ExecutableImpl implements Executable {

    @Override
    public void execute() {
        System.out.println("execute");
    }
}
```

これらを使用する以下のようなクラスを作成してください。

megascus.annotation.handson.pattern.ProxyMain.java

```java:ProxyMain.java
package megascus.annotation.handson.pattern;

import megascus.annotation.handson.example.Executable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import megascus.annotation.handson.example.ExecutableImpl;

public class ProxyMain {

    public static void main(String... args) {
        Executable e1 = new ExecutableImpl();
        e1.execute(); //ビジネスロジック呼び出し。
    }

}
```

これは一般的なJavaのプログラミングでビジネスロジックを呼び出しているものを模したものです。
Executable#execute()が複数個所から呼び出されており、それぞれの実行前と実行後にログを出力したい場合にどうすればよいでしょうか。
ひとつはExecutableImplの内部で直接ログ出力処理を書いてしまうことです。




(参考)ProxyパターンとProxyクラスと黒魔術
http://d.hatena.ne.jp/Nagise/20111218/1324182111