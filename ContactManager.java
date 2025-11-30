import java.util.List;

public class ContactManager {
    private ContactRepository repository;

    public ContactManager() {
        repository = new ContactRepository();
    }

    public String createContact(String name, String phone, String email, String address)
            throws ValidationException {
        ContactValidator.validateContact(name, phone, email);

        String id = repository.generateId();
        Contact contact = new Contact(id, name, phone, email, address);
        repository.addContact(contact);
        return id;
    }

    public Contact getContact(String id) {
        return repository.getContact(id);
    }

    public List<Contact> getAllContacts() {
        return repository.getAllContacts();
    }

    public boolean updateContact(String id, String name, String phone,
                                 String email, String address) throws ValidationException {
        ContactValidator.validateContact(name, phone, email);
        Contact contact = new Contact(id, name, phone, email, address);
        return repository.updateContact(id, contact);
    }

    public boolean deleteContact(String id) {
        return repository.deleteContact(id);
    }

    public List<Contact> searchByName(String name) {
        return repository.searchByName(name);
    }

    public List<Contact> searchByPhone(String phone) {
        return repository.searchByPhone(phone);
    }

    public int getContactCount() {
        return repository.getAllContacts().size();
    }
}


