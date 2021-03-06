/*
This Class interacts with the SQL database.
It is the bridge between the app and the data that is used in it.
 */
package sample.database;

import sample.model.Task;
import sample.model.User;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseHandler extends Configs {

    //Connection to the database used by the app
    Connection dbConnection;

    //Connects to the database and returns the said connection variable
    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":"
                + dbPort + "/"
                + dbName;

        Class.forName("com.mysql.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }

    //Adds new user to the user table in the database
    public void signUpUser(User user) {

        //SQL language code that will be inserted into the connection to
        //execute the required command in the database
        String insert = "INSERT INTO " + Const.USERS_TABLE+"("+Const.USERS_FIRSTNAME
                +","+Const.USERS_LASTNAME+","+Const.USERS_USERNAME+","
                +Const.USERS_PASSWORD+","+Const.USERS_LOCATION+","
                +Const.USERS_GENDER+")"+"VALUES(?,?,?,?,?,?)";

        //Sets up the values that need to be inserted into the User table of the
        //database and executes the above SQL command.
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getLocation());
            preparedStatement.setString(6, user.getGender());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Get existing user from user table
    public User getUser(User user) {

        User returnUser;

        ResultSet resultSet = null;

        if(!user.getUserName().equals("") || !user.getPassword().equals("")) {
            String query = "SELECT * FROM " + Const.USERS_TABLE + " WHERE " +
                    Const.USERS_USERNAME + "=?" + " AND " + Const.USERS_PASSWORD
                    + "=?";

            //select all from users where username="username" and password="password"

            try {
                PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

                preparedStatement.setString(1, user.getUserName());
                preparedStatement.setString(2, user.getPassword());

                resultSet = preparedStatement.executeQuery();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            System.out.print("Please Enter Your Credentials!");
        }

        return getUserFromResultSet(user, resultSet);

    }

    //Check if user login is already in use
    public boolean usernameTaken(User user) {

        ResultSet resultSet = null;

        String query = "SELECT * FROM " + Const.USERS_TABLE + " WHERE " +
                Const.USERS_USERNAME + "=?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

            preparedStatement.setString(1, user.getUserName());

            resultSet = preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        User returnedUser = getUserFromResultSet(user, resultSet);

        if(returnedUser.isInDatabase()) {
            return true;
        } else {
            return false;
        }
    }

    //Write new task into the tasks table
    public void addTask(Task task, User user) {

        String insert = "INSERT INTO " + Const.TASKS_TABLE+"("+Const.TASKS_USERID
                +","+Const.TASKS_TASK+","+Const.TASKS_DATE+","
                +Const.TASKS_DESCRIPTION+")"+" VALUES(?,?,?,?)";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);

            preparedStatement.setInt(1, user.getUserID());
            preparedStatement.setString(2, task.getTask());
            preparedStatement.setTimestamp(3, task.getDatecreated());
            preparedStatement.setString(4, task.getDescription());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Read
    public ArrayList<Task> getUserTasks(User user) {

        ArrayList<Task> taskList = new ArrayList<Task>();

        ResultSet resultSet = null;

        //Implement the extraction of tasks from database
        String query = "SELECT * FROM " + Const.TASKS_TABLE + " WHERE " +
                Const.TASKS_USERID + "=?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

            preparedStatement.setInt(1, user.getUserID());

            resultSet = preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        int counter = 0;
        String taskName;
        String taskDescription;
        Timestamp dateCreated;
        Integer taskID;

        try {
            //Retrieves every task that the selected user has.
            while(resultSet.next()) {
                counter++;

                //Get important info on each task
                taskName = resultSet.getString("task");
                taskDescription = resultSet.getString("description");
                dateCreated = resultSet.getTimestamp("datecreated");
                taskID = resultSet.getInt("taskid");

                //Create new Task Object for each extracted task
                Task task = new Task(dateCreated, taskDescription, taskName, taskID);

                //Store the above Task Object in the list of user tasks
                taskList.add(task);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Return the list of user tasks
        return taskList;

    }

    //Update task
    public void updateTask(Task task) {
        String insert = "UPDATE " + Const.TASKS_TABLE +
                " SET " + Const.TASKS_TASK + "=?, " + Const.TASKS_DESCRIPTION + "=?" +
                " WHERE " + Const.TASKS_ID + "=?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);

            preparedStatement.setString(1, task.getTask());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setInt(3, task.getTaskID());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Deletes the specified task
    public void deleteTask(Task task) {
        String insert = "DELETE FROM " + Const.TASKS_TABLE+" WHERE "+
                Const.TASKS_ID + "=?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);

            preparedStatement.setInt(1, task.getTaskID());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Helping function:
    //Returns User Object based on user and result set returned from database.
    private User getUserFromResultSet(User user, ResultSet resultSet) {

        int counter = 0;
        String userName = user.getUserName();
        String password = user.getPassword();
        String firstName = "";
        String lastName = "";
        String location = "";
        String gender = "";
        Integer userID = -1;

        try {
            //Get all the information about the user from the database.
            while(resultSet.next()) {
                counter++;

                userID = resultSet.getInt("userid");
                firstName = resultSet.getString("firstname");
                lastName = resultSet.getString("lastname");
                location = resultSet.getString("location");
                gender = resultSet.getString("gender");

            }

            //If the user exists, set up the User Object and return it to the
            //caller.
            if (counter ==1) {

                User returnUser = new User(firstName, lastName, userName, password,
                        location, gender, true, userID);

                return returnUser;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Returns the user that was set in the parameters of the method.
        return user;
    }

}
