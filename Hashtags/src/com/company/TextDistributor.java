package com.company;

//класс инкапсулирующий в себе логику распределения слов
//по тексту
public class TextDistributor {
    private final int amountWordToDistribute; //общее количество слов которое надо распределить
    private final int amountWordInText; //общее количество слов в тексте

    private int currentIndex; //текущий индекс распределения

    public TextDistributor(int amountWordToDistribute, int amountWordInText) {
        this.amountWordToDistribute = amountWordToDistribute;
        this.amountWordInText = amountWordInText;
    }

    //выдаем следующую позицию в тексте
    public int getNextPosition() {
        //если слово первое слово будет в самом начале
        if (currentIndex == 0) {
            currentIndex++;
            return 0;
        }

        //если слово последнее - будет в самом конце
        if (currentIndex == amountWordToDistribute - 1) {
            return amountWordInText - 1;
        }

        //так как предполагается что к функции
        //мы обратимся столько слов сколько у нас для распределения
        //то мы можем взять пропорцию и с помощью индекса высчитывать куда нам вставлять слово в тексте
        int index = (amountWordInText / amountWordToDistribute) * currentIndex;
        currentIndex++;

        return  index;
    }
}
