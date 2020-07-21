/*
Task class:
Contains all the important info on the tasks that are stored
in the database for each user.
 */
package sample.model;

import java.sql.Timestamp;

public class Task {

    private Timestamp datecreated;
    private String description;
    private String task;

    private Integer taskID;

    //Default Constructor
    public Task() {
    }

    //Constructor used to create a new task that will be inserted into the app database.
    public Task(Timestamp datecreated, String description, String task) {
        this.datecreated = datecreated;
        this.description = description;
        this.task = task;
    }

    //Constructor used to get the task from the app's database
    public Task(Timestamp datecreated, String description, String task, Integer taskID) {
        this.datecreated = datecreated;
        this.description = description;
        this.task = task;
        this.taskID = taskID;
    }

    //Getters and setters
    public Timestamp getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Timestamp datecreated) {
        this.datecreated = datecreated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    //There is no setter method for the taskID, since it is unchangeable.
    public Integer getTaskID() { return taskID; }
}
