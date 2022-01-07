package Utilities;

import Contact.Contact;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexicalAnalyzer {

    /**
     * Проверка корректности пола пользователя
     *
     * @param input проверяемая строка
     * @return исходная строка или сообщение о ошибке
     */
    public static String checkGender(String input) {
        Pattern genderPattern = Pattern.compile
                ("^[mMfF]$");
        Matcher genderMatcher = genderPattern.matcher(input);
        if (genderMatcher.matches()) {
            return input;
        } else {
            System.out.println("Bad gender!");
            return "[no data]";
        }
    }

    /**
     * Проверка корректности номера телефона
     *
     * @param input проверяемая строка
     * @return исходная строка или сообщение о ошибе
     */
    public static String checkPhoneNumber(String input) {
        String regex = "\\+?\\(?[A-Za-z0-9]{1,}\\)?";
        String regexP = "\\+?\\([A-Za-z0-9]{1,}\\)";
        String regex1 = "(\\+?\\([A-Za-z0-9]{1,}\\))([\\s\\-][A-Za-z)-9]{2,})*";
        String regex2 = "(\\+?[A-Za-z)-9]{1,})([\\s\\-]\\([A-Za-z0-9]{2,}\\))([\\s\\-][A-Za-z0-9]{2,})*";
        String regex3 = "(\\+?[A-Za-z0-9]{1,})([\\s\\-][A-Za-z0-9]{2,})*";
        boolean itMatches;
        itMatches = input.matches(regex) || input.matches(regex1) || input.matches(regex2) || input.matches(regexP) || input.matches(regex3);
        //Pattern phoneNumberPattern = Pattern.compile
        //        ("^[+]?(\\([a-zA-Z0-9]{1,3}\\)[\\s-]?[a-zA-Z0-9]{2,3}|[a-zA-Z0-9]{1,3}[\\s-]?\\(?[a-zA-Z0-9]{2,3}\\)?|\\(?[a-zA-Z0-9]+\\)?$)[\\s-]?(([a-zA-Z0-9]{2,3}[\\s-]?){1,3})?$");
        //Matcher phoneNumberMatcher = phoneNumberPattern.matcher(input);
        if (itMatches) {
            return input;
        } else {
            System.out.println("Wrong number format!");
            return "[no number]";
        }
    }

    /**
     * Проверка корректности даты дня рождения
     *
     * @param birthDay проверяемая строка
     * @return исходная строка или сообщение о ошибке
     */
    public static String checkBirthDay(String birthDay) {
        Pattern birthDayPattern = Pattern.compile
                ("^\\d{4}-\\d{2}-\\d{2}$");
        Matcher birthDayMatcher = birthDayPattern.matcher(birthDay);
        if (birthDayMatcher.matches()) {
            return birthDay;
        } else {
            System.out.println("Bad birth date!");
            return "[no data]";
        }
    }

    /**
     * Поиск подстроки в строке
     *
     * @param subString подстрока, наличие которой нужно проверить
     * @param contact   контакт, в котором ищется подстрока
     * @return логическое значение, найдена ли подстрока
     */
    public static boolean find(String subString, Contact contact) {
        Pattern searchPattern = Pattern.compile(subString, Pattern.CASE_INSENSITIVE);
        Matcher searchMatcher = searchPattern.matcher(contact.toString());
        return searchMatcher.find();
    }

    /**
     * Подсчет количества найденных совпадений подстроки
     *
     * @param subString подстрока, наличие которой нужно проверить
     * @param contacts  контакты, в котором ищется подстрока
     * @return количество найденных совпадений подстроки
     */
    public static int findCounter(String subString, ArrayList<Contact> contacts) {
        int counter = 0;
        Pattern searchPattern = Pattern.compile(subString, Pattern.CASE_INSENSITIVE);
        for (Contact contact : contacts) {
            Matcher searchMatcher = searchPattern.matcher(contact.info());
            if (searchMatcher.find()) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * Проверка, является ли данное число целым
     *
     * @param input строка для проверки
     * @return логическое выражение является или нет
     */
    public static boolean isNumber(String input) {
        return (input.matches("\\d+"));
    }
}

