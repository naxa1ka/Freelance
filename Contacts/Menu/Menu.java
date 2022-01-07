package Menu;

import Contact.Contact;
import Contact.Organization;
import Contact.Person;
import Utilities.Actions;
import Utilities.LexicalAnalyzer;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    protected static Scanner in = new Scanner(System.in);

    public static void record(ArrayList<Contact> contacts, String chooseField) {
        System.out.print("[record] Enter action (edit, delete, menu): ");
        String choose = in.nextLine();
        switch (choose){
            case "edit":
                Actions.edit(contacts.get(Integer.parseInt(chooseField) - 1));
                record(contacts, chooseField);
                break;
            case "delete":
                Actions.remove(contacts, chooseField);
                record(contacts, chooseField);
                break;
            default:
                System.out.println();
                break;
        }
    }

    public static void list(ArrayList<Contact> contacts) {
        Actions.list(contacts);
        System.out.print("[list] Enter action ([number], back): ");
        String choose = in.nextLine();
        if (LexicalAnalyzer.isNumber(choose)) {
            System.out.println(contacts.get(Integer.parseInt(choose) - 1).toString());
            Menu.record(contacts, choose);
        } else {
            System.out.println("");
        }
    }

    public static void add(ArrayList<Contact> contacts) {
        String phoneNumber;
        System.out.print("Enter the type (person, organization): ");
        switch (in.nextLine()) {
            case "p":
            case "person":
                System.out.print("Enter the name: ");
                String firstName = in.nextLine();
                System.out.print("Enter the surname: ");
                String secondName = in.nextLine();
                System.out.print("Enter the birth date: ");
                String birthDay = in.nextLine();
                birthDay = LexicalAnalyzer.checkBirthDay(birthDay);
                System.out.print("Enter the gender (M, F): ");
                String gender = in.nextLine();
                gender = LexicalAnalyzer.checkGender(gender);
                System.out.print("Enter the number: ");
                phoneNumber = in.nextLine();
                phoneNumber = LexicalAnalyzer.checkPhoneNumber(phoneNumber);
                contacts.add(new Person(phoneNumber, firstName, secondName, birthDay, gender));
            break;
            case "o":
            case "org":
            case "organization":
                System.out.print("Enter the organization name: ");
                String name = in.nextLine();
                System.out.print("Enter the address: ");
                String address = in.nextLine();
                System.out.print("Enter the number: ");
                phoneNumber = in.nextLine();
                phoneNumber = LexicalAnalyzer.checkPhoneNumber(phoneNumber);
                contacts.add(new Organization(phoneNumber, name, address));
            break;
        }
        System.out.println("The record added.\n");
    }

    public static void search(ArrayList<Contact> contacts) {
        Actions.search(contacts);
        System.out.print("[search] Enter action ([number], back, again): ");
        String choose = in.nextLine();
        if (LexicalAnalyzer.isNumber(choose)) {
            System.out.println(contacts.get(Integer.parseInt(choose) - 1).toString());
            Menu.record(contacts, choose);
        } else if (choose.equals("again")) {
            search(contacts);
        } else {
            System.out.println("");
        }
    }
}
