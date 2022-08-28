package com.company.Grid.GridStatus;

//не мешаем кашу с мухами
//поэтому обработку статусов выносим в отдельную систему
public class GridStatusMessageHandler {
    public void message(GridStatus gridStatus, char letter){
        switch (gridStatus) {
            case CORRECT:
                System.out.println("Вы успешно поставили символ: " + letter);
                break;
            case LETTER_NOT_IN_ALPHABET:
                System.out.println("Данного символа [" + letter + "] не существует в алфавите!");
                break;
            case FIELD_IS_BUSY:
                System.out.println("Данная ячейка уже занята!");
                break;
            case INDEX_NOT_CORRECT:
                System.out.println("Неккоректный индекс!");
                break;
            case NO_NEIGHBORS:
                System.out.println("Вы должны ставить символ так, чтобы были соседи!");
                break;
            case WORD_CURRENTLY_IS_EXIST:
                System.out.println("Данное слово на поле уже существует!");
                break;
            case WORD_IS_COMPOSED:
                System.out.println("Вы составили слово!");
                break;
        }
    }
}
