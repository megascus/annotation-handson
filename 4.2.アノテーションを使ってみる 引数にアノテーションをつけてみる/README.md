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