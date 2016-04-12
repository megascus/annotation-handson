# 4.2.アノテーションを使ってみる 引数にアノテーションをつけてみる

次はメソッドの引数にアノテーションをつけてみます。

まず、@NotNullアノテーションをメソッドの引数に適用できるように修正します。

@NotNullアノテーションの@Targetを以下のように修正してください。

```
@Target(value={ElementType.FIELD}) //アノテーションを使用できる種別
```

↓

```
@Target(value={ElementType.FIELD, ElementType.PARAMETER}) //アノテーションを使用できる種別
```

以下の二つのクラスを作成します。

megascus.annotation.handson.methodvalidation.RuntimeNullCheckable.java

```java:RuntimeNullCheckable.java
package megascus.annotation.handson.methodvalidation;

public interface RuntimeNullCheckable {
    
    public void execute(Object arg);
}

```

megascus.annotation.handson.methodvalidation.RuntimeNullCheckableExecutor.java

```java:RuntimeNullCheckableExecutor.java
package megascus.annotation.handson.methodvalidation;

import megascus.annotation.handson.beanvalidation.NotNull;

public class RuntimeNullCheckableExecutor {
    
    public static void main(String... args) {
        RuntimeNullCheckable runtimeNullCheckable1 = new RuntimeNullCheckable() {
            @Override
            public void execute(@NotNull Object arg) {
                System.out.println(arg.toString()); //argがnullの場合はエラーになるのでnullかどうかチェックしてnullの場合は実行したくない
            }
        };
        new RuntimeNullCheckableExecutor().execute(runtimeNullCheckable1, null);
        
        RuntimeNullCheckable runtimeNullCheckable2 = new RuntimeNullCheckable() {
            @Override
            public void execute(Object arg) {
                System.out.println(arg); //argがnullの場合もエラーにならないので、実行してもよい
            }
        };
        new RuntimeNullCheckableExecutor().execute(runtimeNullCheckable2, null);
    }

    public void execute(RuntimeNullCheckable runtimeNullCheckable, Object arg) {
        try {
            NotNull notNull = runtimeNullCheckable.getClass().getMethod("execute", Object.class).getParameters()[0].getAnnotation(NotNull.class);
            if(notNull != null && arg == null) {
                return; //実行しない
            }
        } catch (NoSuchMethodException | SecurityException ex) {
            //never happen.
        }
        runtimeNullCheckable.execute(arg);
    }

}

```

今回、二つのRuntimeNullCheckableの実装クラスを作っていますが、実務ではFactoryパターンで隠蔽されます。
二つのクラスでは片方では@NotNullアノテーションがついており、もう片方では@NotNullアノテーションがついていません。
処理系では@NotNullアノテーションがついているかどうかを動的に判定をして、処理を切り替えています。

また、今回はexecuteメソッド内でアノテーションがついているかどうかを判定していますが、実務ではProxyパターンが使用されます。
Proxyパターンを使った形で書き直すと以下のようになります。

megascus.annotation.handson.methodvalidation.RuntimeNullCheckableExecutorProxy.java

```java:RuntimeNullCheckableExecutorProxy.java
package megascus.annotation.handson.methodvalidation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import megascus.annotation.handson.beanvalidation.NotNull;

public class RuntimeNullCheckableExecutorProxy {

    public static void main(String... args) {
        RuntimeNullCheckable runtimeNullCheckable1 = getInstance(new RuntimeNullCheckable() {
            @Override
            public void execute(@NotNull Object arg) {
                System.out.println(arg.toString()); //argがnullの場合はエラーになるのでnullかどうかチェックしてnullの場合は実行したくない
            }
        });
        runtimeNullCheckable1.execute(null);

        RuntimeNullCheckable runtimeNullCheckable2 = getInstance(new RuntimeNullCheckable() {
            @Override
            public void execute(Object arg) {
                System.out.println(arg); //argがnullの場合もエラーにならないので、実行してもよい
            }
        });
        runtimeNullCheckable2.execute(null);
    }

    public void execute(RuntimeNullCheckable runtimeNullCheckable, Object arg) {

    }

    static RuntimeNullCheckable getInstance(RuntimeNullCheckable o) {
        return (RuntimeNullCheckable) Proxy.newProxyInstance(RuntimeNullCheckableExecutorProxy.class.getClassLoader(), new Class[]{RuntimeNullCheckable.class}, new RuntimeNullCheckableInvocationHandler(o));
    }

}

class RuntimeNullCheckableInvocationHandler implements InvocationHandler {

    RuntimeNullCheckable e;

    RuntimeNullCheckableInvocationHandler(RuntimeNullCheckable e) {
        this.e = e;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            int i = 0;
            for (Parameter param : e.getClass().getMethod(method.getName(), method.getParameterTypes()).getParameters()) { //実装クラスのメソッドのパラメーターを取得する(引数のMethodに入ってるのは呼び出されたインターフェースのメソッド)
                NotNull notNull = param.getAnnotation(NotNull.class);
                if (notNull != null && args[i++] == null) {
                    return null;
                }
            }
        } catch (SecurityException ex) {
            //never happen.
        }
        return method.invoke(e, args);
    }
}
```