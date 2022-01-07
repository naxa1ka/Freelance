package Contact;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class Contact implements Serializable {
    protected String phoneNumber;
    protected final LocalDateTime timeCreated;
    protected LocalDateTime timeLastEdit;

    public Contact(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        timeCreated = timeLastEdit = LocalDateTime.now();
    }

    abstract public String canEdit();

    abstract public String getter(String field);

    abstract public String info();

    abstract public void setter(String field, String value);
}
