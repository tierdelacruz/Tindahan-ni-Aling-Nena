import java.io.Serializable;

class User implements Serializable {
  private static final long serialVersionUID = 1L;
  String name , role;

  User(String name, String role) {
    this.name = name;
    this.role = role;
  }
}

