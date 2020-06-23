package sample.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.Task;
import sample.model.User;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseHandler extends Configs {

    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":"
                + dbPort + "/"
                + dbName;

        Class.forName("com.mysql.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }

    //Add new user to the user table
    public void signUpUser(User user) {

        String insert = "INSERT INTO " + Const.USERS_TABLE+"("+Const.USERS_FIRSTNAME
                +","+Const.USERS_LASTNAME+","+Const.USERS_USERNAME+","
                +Const.USERS_PASSWORD+","+Const.USERS_LOCATION+","
                +Const.USERS_GENDER+")"+"VALUES(?,?,?,?,?,?)";

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

            //select all from users where username="paulo" and password="password"

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

        int counter = 0;
        String userName = user.getUserName();
        String password = user.getPassword();
        String firstName = "";
        String lastName = "";
        String location = "";
        String gender = "";
        Integer userID = -1;

        try {
            while(resultSet.next()) {
                counter++;

                userID = resultSet.getInt("userid");
                firstName = resultSet.getString("firstname");
                lastName = resultSet.getString("lastname");
                location = resultSet.getString("location");
                gender = resultSet.getString("gender");

            }

            if (counter ==1) {

                returnUser = new User(firstName, lastName, userName, password,
                        location, gender, true, userID);

                return returnUser;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
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
                Const.TASKS_USERID + "=" + user.getUserID();

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

            //preparedStatement.setString(1, user.getUserName());
            //preparedStatement.setString(2, user.getPassword());

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
            while(resultSet.next()) {
                counter++;

                taskName = resultSet.getString("task");
                taskDescription = resultSet.getString("description");
                dateCreated = resultSet.getTimestamp("datecreated");
                taskID = resultSet.getInt("taskid");

                Task task = new Task(dateCreated, taskDescription, taskName, taskID);

                taskList.add(task);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return taskList;

    }

    //Update

    //Delete
    public void deleteTask(Task task) {
        String insert = "DELETE FROM " + Const.TASKS_TABLE+" WHERE "+
                Const.TASKS_ID + "=" + task.getTaskID();

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
