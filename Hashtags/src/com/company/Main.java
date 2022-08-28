package com.company;

import com.company.HashTag.HashTagWord;
import com.company.HashTag.MaxHashTagSettings;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class Main {
    private static final String WORDS_FILE_NAME = "files/words.txt";
    private static final String TEXT_FILE_NAME = "files/text.txt";
    private static final String OUTPUT_FILE_NAME = "files/output.txt";

    private static final String PATTERN_SPACE = " ";
    private static final String HASH_TAG = "#";

    /*
    Написать программу для расстановки хэштегов в тексте.

     На вход в программу передается файл с текстом, файл со словами,
     для которых нужно проставить хэштег
     (в порядке убывания приоритета и отметкой об обязательности хэштега),
     максимальное количество хэштегов в тексте (в абсолютном или относительном формате).

     Программа должна изменить исходный текст,
     добавив туда все обязательные хэштеги (даже, если таких слов нет в тексте)
     и все остальные хэштеги в приоритетном порядке (только, если такие слова встречаются в тексте),
     но не превысить при этом общее допустимое количество хэштегов.
     При этом хэштеги должны быть как можно лучше распределены по тексту.
     */


    public static void main(String[] args) {
        //текст который мы обрабатываем
        Text text = new Text(TEXT_FILE_NAME);
        //cчитываем все слова учитывая \n, чтобы при формировании текста учитывались переносы на строчки
        WordsList allWordsListText = new WordsList(text.readAllFileWithEscapeSymbol());
        //подситываем общее количество слов в тексте
        int amountWordsInText = text.getAmountWordsInText();

        //текст с  хэштегами
        Text hashTagText = new Text(WORDS_FILE_NAME);
        //считываем все слова хэштеги
        WordsList allWordsListHashTags = new WordsList(hashTagText.readAllFile());

        //формируем настройку заполнения хэштегов в тексте
        //в абсолютном или относительном формате
        MaxHashTagSettings maxHashTagSettings = initMaxHashTagSettings(allWordsListHashTags, amountWordsInText);
        //формируем список хэш слов с учетом его обязательного присутствия/не присутствия в тексте
        //а так же приоритет
        //внутри функции мы сортируем все хэштеги получая вначале слова
        //с флагом обязательности и с высшим приоритетом, после идут также обязательные слова
        //но с меньшим приоритетом, далее уже идут необязательные слова, также учитывается приоритет
        List<HashTagWord> hashTagWords = initHashWordList(allWordsListHashTags);

        //высчитываем количество обязательных слов не существующих в тексте
        //данное значение нам нужно чтобы как можно грамотнее распределить
        //обязательные хэштеги по всему тексту
        int amountObligatoryWordsNotExistingInText = amountObligatoryWordsNotExistInText(allWordsListText, hashTagWords);
        //класс, который инкапсулирует в себе логику распределения слов в тексте
        //он получает количество распределяемых слов и общее количество в тексте
        //и каждый раз когда мы будем вызывать функцию getNextPosition()
        //он будет выдывать наиболее выгодную позицию для очередного слова
        TextDistributor textDistributor = new TextDistributor(amountObligatoryWordsNotExistingInText, amountWordsInText);


        int counter = 0;
        while (maxHashTagSettings.haveWords()) { //пока не закончится количество доступных хештегов
            //чтобы не выйти за границы списка хештегов
            if (counter > hashTagWords.size() - 1) break;

            //так как мы все хэштеги отсортировали как
            //нам требует занятие, мы их можем просто брать след за следом и работать с ними
            HashTagWord hashTagWord = hashTagWords.get(counter);
            //список всех слов доступных в тексте с которым мы работаем
            List<String> allWordsListTextWords = allWordsListText.getWords();

            //проверяем существует ли данный хэштег в нашем тексте
            //игнорируя регистры и некоторые символы
            //Если слово сущесвует меняем заменяем все слова в тексте
            //на слова с хэштегами
            //если данного слова не существует в тексте проверяем обязательно ли оно
            //и если оно обязательно вставляем ее в позицию которую выдает наш
            //распределитель слов по тексту, описанный выше

            if (allWordsListText.isExistWordInList(hashTagWord.getWord())) {
                if (isNeedToDistribute(hashTagWord, allWordsListTextWords, maxHashTagSettings)) {
                    distributedReplaceWordsHashTags(hashTagWord, allWordsListTextWords, maxHashTagSettings);
                } else {
                    replaceWordsHashTags(hashTagWord, allWordsListTextWords, maxHashTagSettings);
                }
            } else if (hashTagWord.isObligatory()) {
                distributedInsertHashTagInText(textDistributor, hashTagWord, allWordsListTextWords);
            }

            //переходим к следующему хэштегу
            counter++;
        }

        //записываем получившийся результат в выходной файл
        writeResult(OUTPUT_FILE_NAME, allWordsListText.getWords());
    }

    //записываем результат работы в выходный файл
    //можно в принципе удалять содержимое исходного файла
    //и записывать то что у нас получилось
    //но для наглядности воспользуемся этим методом
    private static void writeResult(String fileName, List<String> stringList) {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            for (String string : stringList) { //проходимся по всем словам которые мы модифицировали

                if (!string.contains("\n")) { //если слово содержит каретку перехода на след строку пробел не добавляем
                    string += PATTERN_SPACE;
                }

                writer.write(string); //записываем строчку в файл
            }
            writer.flush(); // заканчиваем запись
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //распределение хэштега
    //так как вся логика распределения по файлу уже инкапсулирована в классе TextDistributor
    //тогда нам достаточно взять следующую позицию куда мы будет вставлять хэштег
    //взять слово добавить к нему хэштег и поместить в наш список слов - будущий выходной файл
    private static void distributedInsertHashTagInText(TextDistributor textDistributor, HashTagWord hashTagWord, List<String> stringList) {
        int insertIndex = textDistributor.getNextPosition(); //берем очередную позицию для вставляемого слова
        stringList.add(insertIndex, HASH_TAG + hashTagWord.getWord()); //добавляем слово в список
    }

    //заменяем все слова во входном тексте хэштегом
    //для этого мы проходимся по всем словам в тексте
    //удаляем лишние символы и сравниваем его в одинаковом регистре
    //если слова совпадают - заменяем слово в тексте словом с хэштегом
    private static void replaceWordsHashTags(HashTagWord hashTagWord, List<String> stringList, MaxHashTagSettings maxHashTagSettings) {
        for (int i = 0; i < stringList.size(); i++) {
            String word = stringList.get(i); //берем очередное слово в списке всех слов

            if (isEqualsWords(hashTagWord, word)) {
                //сравниваем слова и если они совпадают
                //то заменяем наше слово на слово с хэштегом
                if (!tryReplaceWordToHashTag(stringList, maxHashTagSettings, i)){
                    break;
                }
            }
        }
    }

    //заменяем наше слово на слово с хэштегом
    private static boolean tryReplaceWordToHashTag(List<String> stringList, MaxHashTagSettings maxHashTagSettings, int i) {
        if (!maxHashTagSettings.haveWords()) return false;

        maxHashTagSettings.decreaseAmountsWords();
        stringList.set(i, HASH_TAG + stringList.get(i));

        return true;
    }

    //проверяем не нужно ли распределить нам существующие хэштеги
    private static boolean isNeedToDistribute(HashTagWord hashTagWord, List<String> stringList, MaxHashTagSettings maxHashTagSettings) {
        int hashTagWordsInList = amountHashTagWordsInList(hashTagWord, stringList);
        return (hashTagWordsInList > maxHashTagSettings.getMaxWords());
    }

    //распределенная замена
    private static void distributedReplaceWordsHashTags(HashTagWord hashTagWord, List<String> stringList, MaxHashTagSettings maxHashTagSettings) {
        List<Integer> indexList = getAllIndexHashTagWordsInList(hashTagWord, stringList);

        //maxWords < amountHashTagWordsInList
        int maxWords = maxHashTagSettings.getMaxWords();
        int amountHashTagWordsInList = amountHashTagWordsInList(hashTagWord, stringList);

        int step = amountHashTagWordsInList / maxWords;

        for (int i = 0; i < indexList.size(); i += step) {
            Integer index = indexList.get(i);

            if (!tryReplaceWordToHashTag(stringList, maxHashTagSettings, index)){
                break;
            }
        }
    }

    //количество хэштегов в тексте
    private static int amountHashTagWordsInList(HashTagWord hashTagWord, List<String> stringList) {
        return getAllIndexHashTagWordsInList(hashTagWord, stringList).size();
    }

    //все индексы хэштегов в тексте
    private static List<Integer> getAllIndexHashTagWordsInList(HashTagWord hashTagWord, List<String> stringList) {
        List<Integer> indexList = new ArrayList<>();

        for (int i = 0; i < stringList.size(); i++) {
            String word = stringList.get(i);
            if (isEqualsWords(hashTagWord, word)) {
                indexList.add(i);
            }
        }

        return indexList;
    }

    //равны ли два слова
    private static boolean isEqualsWords(HashTagWord hashTagWord, String word) {
        word = formatWord(word);
        return word.toUpperCase(Locale.ROOT)
                .equals(hashTagWord.getWord().toUpperCase(Locale.ROOT));
    }

    //форматируем слово - удаляем все ненужное для сравнения
    private static String formatWord(String word) {
        return word.replaceAll("[.,\n]", "");
    }

    //инициализируем список наших слов-хэштегов
    //для этого проходимся по всем строчкам из файла с хэштегами
    //смотрим обязательно ли слово и его приоритет
    //и формируем наш список используя класс который инкапсулирует всю логику хэштег-слова
    //далее мы сортируем эти слова чтобы получить нужный нам приоритет
    //мы сортируем все хэштеги получая вначале слова
    //с флагом обязательности и с высшим приоритетом, после идут также обязательные слова
    //но с меньшим приоритетом, далее уже идут необязательные слова, также учитывается приоритет
    private static List<HashTagWord> initHashWordList(WordsList wordsList) {
        int priorityCounter = 1; //приоритет слов

        List<HashTagWord> hashTagWords = new ArrayList<>(); //список хэштегов

        while (wordsList.hasNext()) { //пока не кончились слова
            boolean isObligatory = wordsList.next().equals("1"); // обязательно ли слово
            String word = wordsList.next(); //само слово
            hashTagWords.add(new HashTagWord(word, isObligatory, priorityCounter)); //добавляем в список

            priorityCounter++; //добавляем приоритет
        }

        Collections.sort(hashTagWords); //сортируем

        return hashTagWords;
    }

    //формируем настройку заполнения хэштегов в тексте
    //в абсолютном или относительном формате
    //считываем первую строчку смотрим rel/abs
    //если это abs - просто записываем значение из строчки
    //если это rel - высчитываем количество слов также основываясь на общем количестве слов в тексте
    private static MaxHashTagSettings initMaxHashTagSettings(WordsList wordsList, int amountWordInText) {
        String settingsEnum = wordsList.next().toUpperCase(Locale.ROOT); //считываем REL/ABS
        int settingsValue = Integer.parseInt(wordsList.next()); //считываем либо проценты либо количество

        MaxHashTagSettings maxHashTagSettings;

        if (settingsEnum.equals("REL")) {
            //чтобы высчитать относительное надо знать количество слов в тексте
            maxHashTagSettings = new MaxHashTagSettings(amountWordInText, settingsValue);
        } else if (settingsEnum.equals("ABS")) {
            maxHashTagSettings = new MaxHashTagSettings(settingsValue);
        } else {
            throw new IllegalArgumentException("Unexpected value");
        }

        return maxHashTagSettings;
    }

    //подсчет количества обязательных слов не существующих в тексте
    //используется для лучшего распределения хэштегов в тексте
    private static int amountObligatoryWordsNotExistInText(WordsList allWordsInText, List<HashTagWord> hashtagWords) {
        int counter = 0; //счетчик обязательных слов не существующих в тексте

        for (HashTagWord hashtagWord : hashtagWords) {
            if (!hashtagWord.isObligatory()) continue; //если слово не обязательно выходим

            String word = hashtagWord.getWord(); //берем очередное слово

            //смотрим существует ли данное слово в тексте
            if (!allWordsInText.isExistWordInList(word)) {
                counter++;
            }
        }

        return counter;
    }


}
