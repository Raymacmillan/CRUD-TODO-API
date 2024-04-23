package com.todo.todo;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

@Repository
public class TodoRepository {
    List<Todo> todo = new ArrayList<>(Arrays.asList((new Todo(0, "Study Japanese", "2 August 2023", false))));

    public TodoRepository() {
        createTable();
    }

    String url = "jdbc:sqlite:/home/raymk/Documents/todo/src/main/java/com/todo/todo/todoDatabase.db";

    public void createTable() {
        try{
            Connection connection = DriverManager.getConnection(url);
            String createTableSQL = "CREATE TABLE IF NOT EXISTS tasks (id INTEGER PRIMARY KEY,todo_item TEXT NOT NULL,creation_date TEXT NOT NULL,is_completed BOOLEAN NOT NULL DEFAULT FALSE)";
            try(PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL)){
                preparedStatement.execute();
            }
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public boolean insertData(String todoItem, String creationDate, boolean isCompleted) {
        try{
            Connection connection = DriverManager.getConnection(url);
            String insertDataSQL = "INSERT INTO tasks (todo_item,creation_date,is_completed) VALUES(?, ?, ?)";
            try(PreparedStatement preparedStatement = connection.prepareStatement(insertDataSQL)){
                preparedStatement.setString(1, todoItem);
                preparedStatement.setString(2, creationDate);
                preparedStatement.setBoolean(3, isCompleted);
                preparedStatement.execute();
                connection.close();
                return true;
            }
        }catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Todo> TodoDB(int ID) {
        List<Todo> todo = new ArrayList<>();
        try{
            Connection connection = DriverManager.getConnection(url);
            String queryDataSQL = ID == -1 ? "SELECT * FROM tasks" : "SELECT * FROM tasks WHERE id=" + ID;
            try(PreparedStatement preparedStatement = connection.prepareStatement(queryDataSQL)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                
                while(resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String todoItem = resultSet.getString("todo_item");
                    String creationDate = resultSet.getString("creation_date");
                    boolean isCompleted = resultSet.getBoolean("is_completed");
                    todo.add(new Todo(id, todoItem, creationDate, isCompleted));
                }
                connection.close();
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return todo;
    }

    public JSONObject addTodo(Todo newTodo) {
        boolean result = insertData(newTodo.TodoList(),newTodo.CreatedDate(),newTodo.TaskComplete());
        System.out.print(result);
        JSONObject newTodoObject = new JSONObject();

        if(result) {
            newTodoObject.put("message", "successful");
            return newTodoObject;
        }
        newTodoObject.put("message", "unsuccessful");
        return newTodoObject;
    }

    public JSONArray getUsers() {
        return ToJSONArray(TodoDB(-1));
    }

    public JSONObject getUserByID(int id) {
        List<Todo> todo = TodoDB(id);
        return todo.get(0).ToJSON();
    }


    public JSONObject deleteTodoByID(int id) {
        JSONObject message = new JSONObject();
        try{
            Connection connection = DriverManager.getConnection(url);
            String deleteTodoSQL = id == -1 ? "DELETE FROM tasks" : "DELETE FROM tasks WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteTodoSQL);
            if(id != -1) {
                preparedStatement.setInt(1, id);
            }
            preparedStatement.execute();
            message.put("message", "deleted");
        }catch(SQLException e) {
            e.printStackTrace();
            message.put("message", "Error occured");
        }
        return message;
    }


    public JSONObject updateTask(int id, String todoItem, String creatioDate, boolean isCompleted) {
        JSONObject taskUpdated = new JSONObject();

        try(Connection connection = DriverManager.getConnection(url)) {
            String baseStatement = "UPDATE tasks SET ";
            StringBuilder setClauses = new StringBuilder();

            if(todoItem != null) {
                setClauses.append("todo_item = ?, ");
            }
            if(creatioDate != null) {
                setClauses.append("creation_date = ?, ");
            }

            if(isCompleted == true || isCompleted == false) {

                setClauses.append("is_completed=?, ");
            }
            
            if(setClauses.length() > 0) {
                setClauses.setLength(setClauses.length() - 2);
            }
            baseStatement += setClauses + " WHERE id=?";
            System.out.print("Base statement" + baseStatement);

            

            try(PreparedStatement preparedStatement = connection.prepareStatement(baseStatement)) {
                int parameterIndex = 1;
                
                if(todoItem != null) {
                    preparedStatement.setString(parameterIndex++, todoItem);
                }
                if(creatioDate != null) {
                    preparedStatement.setString(parameterIndex++, creatioDate);
                }
                if(isCompleted == true || isCompleted == false) {
                    preparedStatement.setBoolean(parameterIndex++, isCompleted);
                }
                preparedStatement.setInt(parameterIndex, id);
                preparedStatement.execute();
                taskUpdated.put("Status", "updated");
            }
            connection.close();

        }catch(SQLException e) {
            e.printStackTrace();
            taskUpdated.put("Status", "Failed to updated");
        }
        return taskUpdated;
    }

    public JSONArray ToJSONArray(List<Todo> todos) {
        JSONArray TodoArray = new JSONArray();
        for(Todo todo : todos) {
            TodoArray.add(todo.ToJSON());
        }
        return TodoArray;
    }

}
