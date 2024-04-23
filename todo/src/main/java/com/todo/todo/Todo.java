package com.todo.todo;

import org.json.simple.JSONObject;

public class Todo {
    int id = 0;
    String todoItem = "";
    String creationDate = "";
    boolean isCompleted = false;

    public Todo(int id, String todoItem, String creationDate, boolean isCompleted) {
        this.id = id;
        this.todoItem = todoItem;
        this.creationDate = creationDate;
        this.isCompleted = isCompleted;
    }

    // getting todo information

    public int ID() {
        return id;
    }

    public String TodoList() {
        return todoItem;
    }

    public String CreatedDate() {
        return creationDate;
    }

    public boolean TaskComplete() {
        return isCompleted;
    }

    // setting new todo information

    public void setTodo(String newTodo) {
        this.todoItem = newTodo;
    }

    public void setCreationDate(String date) {
        this.creationDate = date;
    }

    public void setTaskComplete(boolean complete) {
        this.isCompleted = complete;
    }

    // converting to JSON
    public JSONObject ToJSON(){
        JSONObject TodoObject = new JSONObject();
        TodoObject.put("ID",this.id);
        TodoObject.put("TodoList", this.todoItem);
        TodoObject.put("DateCreated", this.creationDate);
        TodoObject.put("TaskStatus", this.isCompleted);

        return TodoObject;
    }
}
