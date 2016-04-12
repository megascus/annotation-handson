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
