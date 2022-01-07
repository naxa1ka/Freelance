
import Contact.Contact;
import Utilities.Actions;
import Menu.Menu;

import Utilities.Serializable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner in = new Scanner(System.in);
        ArrayList<Contact> contacts = new ArrayList<>();
        /*
        for (String i: args){
            System.out.println(i);
        }
        */

        //Serializable.unload(contacts, args);
        String choose;
        do {
            System.out.print("[menu] Enter action (add, list, search, count, exit): ");
            choose = in.nextLine();
            switch (choose){
                case "add":
                    Menu.add(contacts);
                    break;
                case "list":
                    Menu.list(contacts);
                    break;
                case "count":
                    Actions.count(contacts);
                    break;
                case "search":
                    Menu.search(contacts);
                    break;
            }
        } while (!choose.equals("exit"));
    }
}
