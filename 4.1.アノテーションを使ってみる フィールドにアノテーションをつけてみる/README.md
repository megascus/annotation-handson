# 4.1.アノテーションを使ってみる フィールドにアノテーションをつけてみる

前の章で作成したアノテーションを実際に使ってみます。

## アノテーションを使ったクラスを作成する

まず、アノテーションを設定するクラスを作成します。
以下のクラスを作成してください。

megascus.annotation.handson.example.Book.java

```java:Book.java
package megascus.annotation.handson.example;

import java.io.Serializable;
import java.util.Objects;
import megascus.annotation.handson.beanvalidation.NotNull;

public class Book implements Serializable {

    @NotNull
    private Long id;
    
    @NotNull
    private String isbn;
    
    private String title;

    private Integer price;

    private String summary;

    //ここから下はエディタに自動で生成させる
    //右クリック→コードを挿入→取得メソッドおよび設定メソッド
    //右クリック→コードを挿入→toString()
    //右クリック→コードを挿入→equals()及びにhashCode()

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "Book{" + "id=" + id + ", isbn=" + isbn + ", title=" + title + ", price=" + price + ", summary=" + summary + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Book other = (Book) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
```
いくつかのフィールドに先ほど作成した@NotNullアノテーションがついていることが確認できると思います。

## アノテーションを扱った処理系を書いてみる

さて、以下のクラスを作成してください。

megascus.annotation.handson.beanvalidation.Validator.java

```java:Validator.java
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
```
実際にアノテーションを使って何かを操作するクラスです。
実はアノテーション自体は何も機能を持っていません。
アノテーションを処理する処理系(一般的にアノテーションプロセッサと呼ばれます)が動くことで何かしらの挙動が行われます。
どういった条件でどのような挙動となるかはドキュメントを読んで確認するのが一般的です。
