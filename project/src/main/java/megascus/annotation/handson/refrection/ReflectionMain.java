package megascus.annotation.handson.refrection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class ReflectionMain {

    public static void main(String[] args) throws ReflectiveOperationException {
        Class<?> clazz = Class.forName("megascus.annotation.handson.refrection.TestTarget");
        Constructor<?> constructor = clazz.getConstructor();
        Object instance = constructor.newInstance();
        instance = clazz.newInstance();

        Method staticMethod = instance.getClass().getMethod("staticMethod1", String.class);
        System.out.println(staticMethod.invoke(null, "文字列"));

        Method instanceMethod = instance.getClass().getMethod("method1", String[].class);
        //可変長引数はJavaでは配列として扱う
        System.out.println(instanceMethod.invoke(instance, (Object) new String[]{"文字列1", "文字列2"}));

        try {
            instanceMethod.invoke(null, (Object) new String[]{"文字列1", "文字列2"});
        } catch (NullPointerException ex) {
            System.out.println("null");
        }
    }
}
