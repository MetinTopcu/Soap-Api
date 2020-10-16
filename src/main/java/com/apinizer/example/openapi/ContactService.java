package com.apinizer.example.openapi;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service("contactService")
public class ContactService {

    public static final String DD_MM_YYYY = "dd.MM.yyyy";
    public static final String SOME_NOTE_WITH_SPEACIAL_CHARACTERS = "some note with speacial Characters -> .:; ÇİÜ ÖŞĞ !'^ +%& /() =?_ @#$";
    private static final AtomicLong counter = new AtomicLong();
    private static List<Contact> contactList;

    static {
        contactList = populateDummyContacts();
    }

    private static List<Contact> populateDummyContacts() {
        List<Contact> contactList = new ArrayList<Contact>();

        try {
            contactList.add(new Contact(counter.incrementAndGet(), "Sam", "5551112233", "sam@contact.ct", "address area 1 of Sam", "address area 2", new SimpleDateFormat(DD_MM_YYYY).parse("01.01.1001"), 11111, SOME_NOTE_WITH_SPEACIAL_CHARACTERS));
            contactList.add(new Contact(counter.incrementAndGet(), "Tom", "5551112244", "tom@contact.ct", "address area 1 of Tom", "address area 2", new SimpleDateFormat(DD_MM_YYYY).parse("01.12.2099"), 222222, SOME_NOTE_WITH_SPEACIAL_CHARACTERS));
            contactList.add(new Contact(counter.incrementAndGet(), "Jerome", "5551112255", "jerome@contact.ct", "address area 1 of Jerome", "address area 2", new SimpleDateFormat(DD_MM_YYYY).parse("29.02.2020"), 333333, SOME_NOTE_WITH_SPEACIAL_CHARACTERS));
            contactList.add(new Contact(counter.incrementAndGet(), "Silvia", "5551112266", "silvia@contact.ct", "address area 1 of Silvia", "address area 2", new SimpleDateFormat(DD_MM_YYYY).parse("31.12.2020"), 44444444, SOME_NOTE_WITH_SPEACIAL_CHARACTERS));
            contactList.add(new Contact(counter.incrementAndGet(), "Alexandra", "5551112277", "alexandra@contact.ct", "address area 1 of Alexandra", "address area 2", new SimpleDateFormat(DD_MM_YYYY).parse("01.02.2003"), 55555555, SOME_NOTE_WITH_SPEACIAL_CHARACTERS));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return contactList;
    }

    public List<Contact> findAllContacts() {
        return contactList;
    }

    public Contact findById(long id) {
        for (Contact contact : contactList) {
            if (contact.getId() == id) {
                return contact;
            }
        }
        return null;
    }

    public Contact findByName(String name) {
        for (Contact contact : contactList) {
            if (contact.getName().equalsIgnoreCase(name)) {
                return contact;
            }
        }
        return null;
    }

    public void saveContact(Contact contact) {
        contact.setId(counter.incrementAndGet());
        contactList.add(contact);
    }

    public void updateContact(Contact contact) {
        int index = contactList.indexOf(contact);
        contactList.set(index, contact);
    }

    public void deleteContactById(long id) {

        for (Iterator<Contact> iterator = contactList.iterator(); iterator.hasNext(); ) {
            Contact contact = iterator.next();
            if (contact.getId() == id) {
                iterator.remove();
            }
        }
    }

    public boolean isContactExist(Contact contact) {
        return findByName(contact.getName()) != null;
    }

    public void deleteAllContacts() {
        contactList.clear();
    }
}
