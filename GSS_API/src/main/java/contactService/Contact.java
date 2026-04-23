package contactService;

/**
 * Contact class for Java 1.8 compatibility.
 *
 * Stores personal information of a single contact including:
 *  - First Name
 *  - Last Name
 *  - Address
 *  - Phone Number
 *
 * The object also stores a non-mutable ID assigned at creation, taken from an external service.
 * All fields are immutable and final.
 */
public final class Contact {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final String phone;
    private final String address;

    /**
     * Constructor for Contact.
     *
     * @param id the unique contact identifier
     * @param firstName the contact's first name
     * @param lastName the contact's last name
     * @param phone the contact's phone number
     * @param address the contact's address
     */
    public Contact(String id, String firstName, String lastName, String phone, String address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
    }

    /**
     * Returns the contact ID.
     *
     * @return the contact ID
     */
    public String getID() {
        return id;
    }

    /**
     * Returns the contact's first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the contact's last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the contact's phone number.
     *
     * @return the phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Returns the contact's address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Compares this contact with another object for equality.
     * Two contacts are equal if all their fields are equal.
     *
     * @param obj the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        // If comparator is null, default to false
        if (obj == null)
            return false;

        // Check if comparator is a Contact object
        if (getClass() != obj.getClass())
            return false;

        // Store comparator as Contact object for comparison operations
        Contact other = (Contact) obj;

        // ID equality check
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;

        // First name equality check
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;

        // Last name equality check
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;

        // Phone equality check
        if (phone == null) {
            if (other.phone != null)
                return false;
        } else if (!phone.equals(other.phone))
            return false;

        // Address equality check
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;

        return true;
    }

    /**
     * Returns a string representation of the contact.
     *
     * @return string in the format "Contact(id=..., firstName=..., lastName=..., phone=..., address=...)"
     */
    @Override
    public String toString() {
        return "Contact(id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", phone=" + phone + ", address=" + address + ")";
    }
}