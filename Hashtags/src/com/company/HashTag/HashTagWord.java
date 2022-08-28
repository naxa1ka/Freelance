package com.company.HashTag;

public class HashTagWord implements Comparable<HashTagWord> {
    private final String word; //само слово
    private final boolean isObligatory; //обязательно ли слово
    private final int priority; //приоритет

    /**
     * приоритет и данный класс веден для удобной работы с входными данными
     * так как слова вставить мы должны вначале которые обязательные
     * а после идти по приоритету
     * и чтобы удобно работать со списком
     * мы добавляем эти два поля, и позже сортируем изначальные наши данные
     * и работает с этим
     *
     * приоритет - чем меньше число - тем больше приоритет
     */

    //конструктор
    public HashTagWord(String word, boolean isObligatory, int priority) {
        this.word = word;
        this.isObligatory = isObligatory;
        this.priority = priority;
    }

    //геттер для слова
    public String getWord() {
        return word;
    }

    //геттер для флага обязательности слова
    public boolean isObligatory() {
        return isObligatory;
    }


    //для дебага
    @Override
    public String toString() {
        return "HashTagWord{" +
                "word='" + word + '\'' +
                ", isObligatory=" + isObligatory +
                ", priority=" + priority +
                '}';
    }

    //ДЛЯ СОРТИРОВКИ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //переопределяем стратегию сортировки
    //самыми первыми словами будут которые обязательны с меньшим(по значению) числу(самый высокий приоритет - 1)
    //далее идут обязательные слова с большим(по значению) приоритету, допустим 2
    //после необязательные слова, также с учетом приоритета
    @Override
    public int compareTo(HashTagWord o) {
        if (isObligatory && !o.isObligatory){
          return -1;
        }

        if (!isObligatory && o.isObligatory){
            return 1;
        }

        return Integer.compare(priority, o.priority);
    }
}