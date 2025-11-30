import java.io.*;
import java.util.*;

public class ContactRepository {
    private Map<String, Contact> contacts;
    private static final String DATA_FILE = "contacts.dat";
    private int nextId;

    public ContactRepository() {
        contacts = new HashMap<>();
        nextId = 1;
        loadContacts();
    }

    public String generateId() {
        return "C" + String.format("%04d", nextId++);
    }

    public void addContact(Contact contact) {
        contacts.put(contact.getId(), contact);
        saveContacts();
    }

    public Contact getContact(String id) {
        return contacts.get(id);
    }

    public List<Contact> getAllContacts() {
        return new ArrayList<>(contacts.values());
    }

    public boolean updateContact(String id, Contact updatedContact) {
        if (contacts.containsKey(id)) {
            contacts.put(id, updatedContact);
            saveContacts();
            return true;
        }
        return false;
    }
    public boolean deleteContact(String id) {
        if (contacts.remove(id) != null) {
            saveContacts();
            return true;
        }
        return false;
    }

    public List<Contact> searchByName(String name) {
        List<Contact> results = new ArrayList<>();
        String lowerName = name.toLowerCase();
        for (Contact contact : contacts.values()) {
            if (contact.getName().toLowerCase().contains(lowerName)) {
                results.add(contact);
            }
        }
        return results;
    }

    public List<Contact> searchByPhone(String phone) {
        List<Contact> results = new ArrayList<>();
        for (Contact contact : contacts.values()) {
            if (contact.getPhone().contains(phone)) {
                results.add(contact);
            }
        }
        return results;
    }

    private void saveContacts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(DATA_FILE))) {
            oos.writeObject(contacts);
            oos.writeInt(nextId);
        } catch (IOException e) {
            System.err.println("Error saving contacts: " + e.getMessage());
        }
    }
    @SuppressWarnings("unchecked")
    private void loadContacts() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(DATA_FILE))) {
                contacts = (Map<String, Contact>) ois.readObject();
                nextId = ois.readInt();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading contacts: " + e.getMessage());
                contacts = new HashMap<>();
                nextId = 1;
            }
        }
    }
}



