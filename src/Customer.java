import java.io.Serializable;

class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    String id, name, contact;
    double balance;

    Customer(String id, String name, String contact) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.balance = 0.0;
    }
}
