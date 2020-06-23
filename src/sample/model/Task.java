package sample.model;

import java.sql.Timestamp;

public class Task {

    private Timestamp datecreated;
    private String description;
    private String task;

    private Integer taskID;

    public Task() {
    }

    public Task(Timestamp datecreated, String description, String task) {
        this.datecreated = datecreated;
        this.description = description;
        this.task = task;
    }

    public Task(Timestamp datecreated, String description, String task, Integer taskID) {
        this.datecreated = datecreated;
        this.description = description;
        this.task = task;
        this.taskID = taskID;
    }

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

    public Integer getTaskID() { return taskID; }
}
