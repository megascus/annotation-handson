package megascus.annotation.handson.beanvalidation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.FIELD, ElementType.PARAMETER}) //アノテーションを使用できる種別
@Retention(RetentionPolicy.RUNTIME) //実行時に有効(リフレクションを使って読み取ることができる)
@Documented //このアノテーションがついていることをJavaDocに表示する @RetentionがRetentionPolicy.RUNTIME の時はつけておく
//@java.lang.annotation.Inherited アノテーションのターゲットがクラスの場合にサブクラスに伝播させるか
public @interface NotNull {
    public String message() default "{0} の値がnullです。";
}
