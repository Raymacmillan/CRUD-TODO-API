package com.todo.todo;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.todo.todo.TodoRepository;

@RestController
@RequestMapping("/todo")
public class TodoController {
   
    private TodoRepository todoRepository;

    @Autowired
    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping("/task")
    public List<Todo> Todos() {
        return todoRepository.getUsers();
    }

    @GetMapping("/task/{id}")
    public JSONObject taskObjectByID(@PathVariable int id) {
        return todoRepository.getUserByID(id);
    }

    @PostMapping("/task")
    public JSONObject createTodo(@RequestParam int id, @RequestParam String todo_item, @RequestParam String creation_date, @RequestParam boolean is_complete) {
        return todoRepository.addTodo(new Todo(id, todo_item, creation_date, is_complete));
    }

    @DeleteMapping("/task")
    public JSONObject deleteTask() {
        return todoRepository.deleteTodoByID(-1);
    }

    @DeleteMapping("/task/{id}")
    public JSONObject deleteTaskByID(@PathVariable int id) {
        return todoRepository.deleteTodoByID(id);
    }

    @PatchMapping("/task/{id}")
    public JSONObject updateObjectTask(@PathVariable int id, @RequestParam(required = false) String todo_item, @RequestParam(required = false)  String creation_date, @RequestParam(required = false)  boolean is_complete) {
        System.out.print(is_complete + " "+ id);
        return todoRepository.updateTask(id, todo_item, creation_date, is_complete);
    }

}
