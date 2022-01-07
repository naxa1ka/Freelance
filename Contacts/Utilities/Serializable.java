package Utilities;

import Contact.Contact;

import java.io.*;
import java.util.ArrayList;

public class Serializable {
    public static void load(ArrayList<Contact> contacts, String fileName) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(fileName);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(contacts);
        objectOutputStream.close();
    }
    public static void unload(ArrayList<Contact> contacts, String [] fileName) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(fileName[0]);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        contacts = (ArrayList<Contact>) objectInputStream.readObject();
    }
}

