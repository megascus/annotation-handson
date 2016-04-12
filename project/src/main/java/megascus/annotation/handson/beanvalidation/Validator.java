package megascus.annotation.handson.beanvalidation;

import megascus.annotation.handson.example.Book;
import java.lang.reflect.Field;
import java.text.MessageFormat;

public class Validator {
    
    public static void main(String... args) {
        try {
            validate(new Book());
        }catch(NullPointerException ex) {
            ex.printStackTrace();
        }
        
        Book book = new Book();
        book.setId(1L);
        book.setIsbn("isdn");
        validate(book);
    }
    static void validate(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields(); // クラスについているフィールドの一覧を取得する
        for (Field field : fields) {
            NotNull notNull = field.getAnnotation(NotNull.class); // フィールドにNotNullアノテーションがついていたら
            if(notNull != null) {
                try {
                    field.setAccessible(true); // privateフィールドにアクセスするために必須
                    if(field.get(obj) == null) { // NotNullがついているフィールドの値がnullだったらExceptionを発生させる
                        throw new NullPointerException(MessageFormat.format(notNull.message(),field.getName()));
                    }
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    // setAccessible(true)を呼び出しているため発生しない
                }
            }
        }
    }
}