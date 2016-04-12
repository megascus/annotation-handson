package megascus.annotation.handson.pattern;

import megascus.annotation.handson.example.Executable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import megascus.annotation.handson.example.ExecutableImpl;

public class ProxyMain {

    public static void main(String... args) {
        Executable e1 = new ExecutableImpl();
        e1.execute();

        Executable e2 = new ExecutableProxy(e1);
        e2.execute();
        
        Executable e3 = (Executable) Proxy.newProxyInstance(ProxyMain.class.getClassLoader(), new Class<?>[]{Executable.class}, new ExecutableInvocationHandler(e1));
        e3.execute();

    }

}

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
