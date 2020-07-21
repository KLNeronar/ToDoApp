/*
User class:
Contains all the important info on the User that uses the app.
 */
package sample.model;

public class User {

    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String location;
    private String gender;
    private boolean inDatabase;
    private Integer userID;

    //Default constructor. Initiates a User Object with no personal info.
    //The User Object will be set up as not existing in the apps database and
    //not having a valid user ID.
    public User() {
        this.inDatabase = false;
        this.userID = -1;
    }

    //Sets up the new User. Used when the user tries to create an account.
    public User(String firstName, String lastName, String userName, String password,
                String location, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.location = location;
        this.gender = gender;
        this.inDatabase = false;
        this.userID = -1;
    }

    //Sets up the User that exists in the database. Fills out all the info available
    //about the user.
    public User(String firstName, String lastName, String userName, String password,
                String location, String gender, boolean inDatabase, Integer userID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.location = location;
        this.gender = gender;
        this.inDatabase = inDatabase;
        this.userID = userID;
    }

    //Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    //No setter for the following attributes.
    //They are unchangeable.
    public boolean isInDatabase() { return inDatabase; }

    //public void setInDatabase(boolean inDatabase) {
    //    this.inDatabase = inDatabase;
    //}

    public Integer getUserID() { return userID; }

    //public void setUserID(Integer userID) { this.userID = userID; }
}
