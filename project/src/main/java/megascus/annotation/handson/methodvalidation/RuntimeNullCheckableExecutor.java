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
                return;
            }
        } catch (NoSuchMethodException | SecurityException ex) {
            //never happen.
        }
        runtimeNullCheckable.execute(arg);
    }

}
