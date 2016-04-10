package megascus.annotation.handson.beanvalidation;

import megascus.annotation.handson.example.Book;
import java.lang.reflect.Field;
import java.text.MessageFormat;

/**
 *
 * @author 2568
 */
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
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            NotNull notNull = field.getAnnotation(NotNull.class);
            if(notNull != null) {
                try {
                    field.setAccessible(true);
                    if(field.get(obj) == null) {
                        throw new NullPointerException(MessageFormat.format(notNull.message(),field.getName()));
                    }
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    // setAccessible(true)を呼び出しているため発生しない
                }
            }
        }
    }
}
