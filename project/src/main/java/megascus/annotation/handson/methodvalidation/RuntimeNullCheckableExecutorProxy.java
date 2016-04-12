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
