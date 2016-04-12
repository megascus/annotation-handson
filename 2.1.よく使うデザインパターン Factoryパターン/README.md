# 2.1.よく使うデザインパターン Factoryパターン

アノテーションとは直接は関係ないですが、アノテーションを使用したプログラミングを行うときによく使用するデザインパターンがあるので、そちらを説明します。

## Factoryパターンとは

Factoryパターンは通常Javaのオブジェクトの生成はコンストラクタで行いますが、コンストラクタで行う場合、生成されるオブジェクトの属するクラスがひとつに限定されてしまうという問題があります。
それを複数のクラス(サブクラスもしくはインターフェースの実装クラス)に切り替えられるようにしようというのがFactoryパターンです。

以下のクラスを作成してください。


megascus.annotation.handson.pattern.CalendarFactory.java

```java:TestTarget.java
package megascus.annotation.handson.pattern;

import java.util.Locale;

public class CalendarFactory {
    
    public Calendar getInstance(Locale locale) {
        if(locale.getCountry().equals("ja")) {
            return new JapaneseCalendar();
        }
        return new GregorianCalendar();
    }
    
}

interface Calendar {
    String getName();
}

class GregorianCalendar implements Calendar {
    @Override
    public String getName() {
        return "グレゴリオ暦";
    }
}

class JapaneseCalendar implements Calendar {
     @Override
    public String getName() {
        return "日本暦";
    }   
}
```

Javaの組み込みクラスにはFactoryパターンを使用した有名なものがいくつかあり、java.util.Calendarクラスもそのひとつです。
このコードはjava.util.CalendarからFactoryパターンを使用している部分を切り取ったものです。java.util.Calendarではロケール(地域情報)を受け取った上で複数の実装クラスを返しています。

(参考)Java日付処理メモ(Hishidama's Java Date Memo)
http://www.ne.jp/asahi/hishidama/home/tech/java/date.html#h3_Cal_getInstance