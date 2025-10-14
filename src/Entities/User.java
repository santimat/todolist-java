package Entities;

public class User {
  private String username;
  private String password;
  private String salt;

  public User(String username, String password, String salt) {
    this.username = username;
    this.password = password;
    this.salt = salt;
  }

  public String toCSV() {
    return this.username + "," + this.password + "," + this.salt;
  }

  public static User fromCSV(String[] user) throws ArrayIndexOutOfBoundsException {
    if (user.length != 3) {
      throw new ArrayIndexOutOfBoundsException("Users fields are incomplete");
    }

    String username = user[0];
    String password = user[1];
    String salt = user[2];
    return new User(username, password, salt);
  }

  public String getUsername() {
    return this.username;
  }
}
