package megascus.annotation.handson.refrection;

public class TestTarget {

    public TestTarget() {
    }

    public static String staticMethod1(String arg) {
        return arg;
    }

    public String method1(String... args) {
        return args.length > 0 ? args[0] : "";
    }
}
