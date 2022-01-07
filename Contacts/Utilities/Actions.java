package Utilities;

import Contact.Contact;

import java.util.ArrayList;
import java.util.Scanner;

public class Actions {
    protected static Scanner in = new Scanner(System.in);

    public static void edit(Contact contact) {
        System.out.print("Select a field (" + contact.canEdit() + "): ");
        String choose = in.nextLine();
        System.out.print("Enter " + choose + ": ");
        String field = in.nextLine();
        contact.setter(choose, field);
        System.out.println("Saved");
        System.out.println(contact.toString());
    }

    /**
     * Выводит список из доступных контактов и краткую информацию о них
     *
     * @param contacts список контактов
     */
    public static void list(ArrayList<Contact> contacts) {
        int counter = 1;
        for (Contact contact : contacts) {
            System.out.print(counter++ + ". " + contact.info());
        }
        System.out.println("\n");
    }

    /**
     * Удаление контакта из списка по выбранному полю
     *
     * @param contacts    список, в котором происходит удаление
     * @param chooseField индекса списка, в котором происходит удаление
     */
    public static void remove(ArrayList<Contact> contacts, String chooseField) {
        contacts.remove(Integer.parseInt(chooseField) - 1);
        System.out.println("The record removed!");
    }

    /**
     * Вывод количества доступных записей
     *
     * @param contacts список из контактов
     */
    public static void count(ArrayList<Contact> contacts) {
        System.out.println("The Phone Book has " + contacts.size() + " records.\n");
    }

    /**
     * @param contacts
     */
    public static void search(ArrayList<Contact> contacts) {
        int counter = 1;
        System.out.print("Enter search query: ");
        String subString = in.nextLine();
        int findCounter = LexicalAnalyzer.findCounter(subString, contacts);
        if (findCounter == 0) {
            System.out.println("Not found");
        } else {
            System.out.println("Found " + findCounter + " results:");
        }
        for (Contact i : contacts) {
            if ((LexicalAnalyzer.find(subString, i))) {
                System.out.println(counter++ + ". " + i.info());
            }
        }
        System.out.println();
    }
}
