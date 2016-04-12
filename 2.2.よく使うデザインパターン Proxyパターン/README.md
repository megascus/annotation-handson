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

megascus.annotation.handson.example.ExecutableImpl.java

```java:ExecutableImpl.java
package megascus.annotation.handson.example;

public class ExecutableImpl implements Executable {

    @Override
    public void execute() {
        System.out.println("execute start");
        System.out.println("execute");
        System.out.println("execute end");
    }
}
```

これでもよいのですが、実際にやりたい処理と、それに付随する処理(ログ出力)が混ざってしまい、どこまでが本当にやりたい処理だったのかの区別が難しくなります。その場合にproxyパターンを使用します。以下のクラスを作成してください。

megascus.annotation.handson.pattern.ExecutableProxy

```java:ExecutableProxy.java
class ExecutableProxy implements Executable {

    private final Executable e;

    ExecutableProxy(Executable e) {
        this.e = e;
    }

    @Override
    public void execute() {
        System.out.println("execute start");
        e.execute();
        System.out.println("execute end");
    }

}
```

ProxyMainクラスのmainメソッドに以下を追加してください。

```java:ProxyMain.java
        Executable e2 = new ExecutableProxy(e1);
        e2.execute();
```

本当にやりたかった処理とログ出力の処理が明確に分割されました。

## java.lang.reflect.Proxy
JavaにはProxyパターンを実装するための専用のクラスが存在します。先ほどの例ではExecutableProxyクラスをExecutableインターフェースに対してのみ実装していましたが、複数のインターフェースに対して同じ処理を行いたい場合もあります。そのような場合に使用できます。
Proxyクラスを使用する場合はInvocationHandlerインターフェースを一緒に使います。
以下のクラスを追加してください。

megascus.annotation.handson.pattern.ExecutableInvocationHandler

```java:ExecutableInvocationHandler.java
class ExecutableInvocationHandler implements InvocationHandler {

    Executable e;

    ExecutableInvocationHandler(Executable e) {
        this.e = e;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("execute start");
        Object invoke = method.invoke(e, args);
        System.out.println("execute end");
        return invoke;
    }
}
```

以下のように使用します。ProxyMainクラスのmainメソッドに追加してください。
```java:ProxyMain.java
        Executable e3 = (Executable) Proxy.newProxyInstance(ProxyMain.class.getClassLoader(), new Class<?>[]{Executable.class}, new ExecutableInvocationHandler(e1));
        e3.execute();
```

今回の例ではほとんどありがたみがありませんが、メソッド数が複数個あるインターフェースの場合には非常に便利に使用できます。
また、Javaの標準機能(Proxyクラス)の範囲ではインターフェースを持っていないとProxyを動的に作成することは出来ません。
インターフェースを持っていないクラスに対してProxyを動的に作成したい場合は、[cglib](https://github.com/cglib/cglib)や[javaassist](http://jboss-javassist.github.io/javassist/)を使用する必要があります。

(参考)ProxyパターンとProxyクラスと黒魔術
http://d.hatena.ne.jp/Nagise/20111218/1324182111
