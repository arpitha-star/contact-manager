import java.util.regex.Pattern;

public class ContactValidator {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^[0-9]{10}$|^[0-9]{3}-[0-9]{3}-[0-9]{4}$");

    public static void validateContact(String name, String phone, String email)
            throws ValidationException {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Name cannot be empty");
        }
        if (name.trim().length() < 2) {
            throw new ValidationException("Name must be at least 2 characters");
        }
        if (phone == null || !PHONE_PATTERN.matcher(phone).matches()) {
            throw new ValidationException("Invalid phone format (use: 1234567890 or 123-456-7890)");
        }
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new ValidationException("Invalid email format");
        }
    }
}
