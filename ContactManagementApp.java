import java.util.List;
import java.util.Scanner;

public class ContactManagementApp {
    private ContactManager manager;
    private Scanner scanner;

    public ContactManagementApp() {
        manager = new ContactManager();
        scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("=== Contact Management System ===");
        boolean running = true;

        while (running) {
            displayMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addContact();
                    break;
                case "2":
                    viewAllContacts();
                    break;
                case "3":
                    searchContact();
                    break;
                case "4":
                    updateContact();
                    break;
                case "5":
                    deleteContact();
                    break;
                case "6":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
            System.out.println();
        }
    }

    private void displayMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Add Contact");
        System.out.println("2. View All Contacts");
        System.out.println("3. Search Contact");
        System.out.println("4. Update Contact");
        System.out.println("5. Delete Contact");
        System.out.println("6. Exit");
        System.out.print("Choose an option: ");
    }

    private void addContact() {
        System.out.println("\n--- Add New Contact ---");
        try {
            System.out.print("Name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Phone (10 digits or XXX-XXX-XXXX): ");
            String phone = scanner.nextLine().trim();

            System.out.print("Email: ");
            String email = scanner.nextLine().trim();

            System.out.print("Address: ");
            String address = scanner.nextLine().trim();

            String id = manager.createContact(name, phone, email, address);
            System.out.println("✓ Contact added successfully! ID: " + id);
        } catch (ValidationException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private void viewAllContacts() {
        List<Contact> contacts = manager.getAllContacts();
        System.out.println("\n--- All Contacts (" + contacts.size() + ") ---");

        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
        } else {
            for (Contact contact : contacts) {
                System.out.println(contact);
            }
        }
    }

    private void searchContact() {
        System.out.println("\n--- Search Contact ---");
        System.out.println("1. Search by Name");
        System.out.println("2. Search by Phone");
        System.out.print("Choose search type: ");

        String choice = scanner.nextLine().trim();
        List<Contact> results = null;

        if (choice.equals("1")) {
            System.out.print("Enter name to search: ");
            String name = scanner.nextLine().trim();
            results = manager.searchByName(name);
        } else if (choice.equals("2")) {
            System.out.print("Enter phone to search: ");
            String phone = scanner.nextLine().trim();
            results = manager.searchByPhone(phone);
        } else {
            System.out.println("Invalid choice.");
            return;
        }
        System.out.println("\n--- Search Results (" + results.size() + ") ---");
        if (results.isEmpty()) {
            System.out.println("No contacts found.");
        } else {
            for (Contact contact : results) {
                System.out.println(contact);
            }
        }
    }

    private void updateContact() {
        System.out.println("\n--- Update Contact ---");
        System.out.print("Enter Contact ID: ");
        String id = scanner.nextLine().trim();

        Contact existing = manager.getContact(id);
        if (existing == null) {
            System.out.println("✗ Contact not found.");
            return;
        }

        System.out.println("Current: " + existing);
        System.out.println("Enter new details (press Enter to keep current value):");

        try {
            System.out.print("Name [" + existing.getName() + "]: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) name = existing.getName();

            System.out.print("Phone [" + existing.getPhone() + "]: ");
            String phone = scanner.nextLine().trim();
            if (phone.isEmpty()) phone = existing.getPhone();

            System.out.print("Email [" + existing.getEmail() + "]: ");
            String email = scanner.nextLine().trim();
            if (email.isEmpty()) email = existing.getEmail();

            System.out.print("Address [" + existing.getAddress() + "]: ");
            String address = scanner.nextLine().trim();
            if (address.isEmpty()) address = existing.getAddress();
            if (manager.updateContact(id, name, phone, email, address)) {
                System.out.println("✓ Contact updated successfully!");
            } else {
                System.out.println("✗ Update failed.");
            }
        } catch (ValidationException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private void deleteContact() {
        System.out.println("\n--- Delete Contact ---");
        System.out.print("Enter Contact ID: ");
        String id = scanner.nextLine().trim();

        Contact contact = manager.getContact(id);
        if (contact == null) {
            System.out.println("✗ Contact not found.");
            return;
        }

        System.out.println("Contact: " + contact);
        System.out.print("Are you sure you want to delete? (yes/no): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("yes")) {
            if (manager.deleteContact(id)) {
                System.out.println("✓ Contact deleted successfully!");
            } else {
                System.out.println("✗ Delete failed.");
            }
        } else {
            System.out.println("Delete cancelled.");
        }
    }

    public static void main(String[] args) {
        ContactManagementApp app = new ContactManagementApp();
        app.run();
    }




    }

