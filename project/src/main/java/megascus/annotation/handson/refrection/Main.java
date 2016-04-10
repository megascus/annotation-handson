package megascus.annotation.handson.refrection;

public class Main {

    public static void main(String[] args) {
        
        TestTarget instance = new TestTarget();

        System.out.println(TestTarget.staticMethod1("文字列"));

        System.out.println(instance.method1("文字列1", "文字列2"));
    }
}
