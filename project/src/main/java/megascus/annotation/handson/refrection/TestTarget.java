/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package megascus.annotation.handson.refrection;

/**
 *
 * @author 2568
 */
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
