package com.apinizer.example.api.openapi;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service("contactService")
public class ContactService {

    private static final AtomicLong counter = new AtomicLong();

    private static List<Contact> contacts;

    static{
        contacts = populateDummyContacts();
    }

    public List<Contact> findAllContacts() {
        return contacts;
    }

    public Contact findById(long id) {
        for (Contact contact : contacts) {
            if (contact.getId() == id) {
                return contact;
            }
        }
        return null;
    }

    public Contact findByName(String name) {
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                return contact;
            }
        }
        return null;
    }

    public void saveContact(Contact contact) {
        contact.setId(counter.incrementAndGet());
        contacts.add(contact);
    }

    public void updateContact(Contact contact) {
        int index = contacts.indexOf(contact);
        contacts.set(index, contact);
    }

    public void deleteContactById(long id) {

        for (Iterator<Contact> iterator = contacts.iterator(); iterator.hasNext(); ) {
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
        contacts.clear();
    }

    private static List<Contact> populateDummyContacts() {
        List<Contact> contacts = new ArrayList<Contact>();
        contacts.add(new Contact(counter.incrementAndGet(), "halil", "0555 555 55 55", "a@b.com", "adres1", "adres2", new Date(), 6800, "not1"));
        contacts.add(new Contact(counter.incrementAndGet(), "ertugrul", "0555 555 55 55", "a@b.com", "adres1", "adres2", new Date(), 6800, "not1"));
        contacts.add(new Contact(counter.incrementAndGet(), "oguz", "0555 555 55 55", "a@b.com", "adres1", "adres2", new Date(), 6800, "not1"));
        return contacts;
    }

}
