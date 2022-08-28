package com.company.HashTag;

//максимальное количество хэштегов в тексте (в абсолютном или относительном формате)
//класс тоже сделан для инкапсуляции всей логики
public class MaxHashTagSettings {
    private int maxWords;

    //конструктор для абсолютного формата
    public MaxHashTagSettings(int maxWords) {
        this.maxWords = maxWords;
    }

    //конструктор для относительного формата
    public MaxHashTagSettings(int allWords, int relativePercent) {
        int percent = relativePercent / 100;
        maxWords = allWords * percent;
    }

    //проверяем осталось ли у нас место для хэштегов
    public boolean haveWords() {
        return maxWords > 0;
    }

    public void decreaseAmountsWords(){
        maxWords--;
    }

    public int getMaxWords(){
        return maxWords;
    }
}


