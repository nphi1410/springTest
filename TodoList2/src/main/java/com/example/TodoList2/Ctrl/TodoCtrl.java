/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TodoList2.Ctrl;

import com.example.TodoList2.Model.TodoDTO;
import com.example.TodoList2.Repo.TodoRepo;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author AD
 */
@RestController

public class TodoCtrl {

    @Autowired
    private TodoRepo todoRepo;

    @GetMapping("/todos")
    public ResponseEntity<?> getAllTodos() {
        List<TodoDTO> todos = todoRepo.findAll();
        if (todos.size() > 0) {
            return new ResponseEntity<List<TodoDTO>>(todos, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No todos available", HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/todos")
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todo) {
        try {
            todo.setCreateAt(new Date(System.currentTimeMillis()));
            todoRepo.save(todo);
            return new ResponseEntity<TodoDTO>(todo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<?> GetTodoById(@PathVariable("id") String id) {
        Optional<TodoDTO> todoOptional = todoRepo.findById(id);

        if (todoOptional.isPresent()) {
            return new ResponseEntity<>(todoOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Not found todo with id: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/todos/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable("id") String id, @RequestBody TodoDTO todo) {
        Optional<TodoDTO> todoOptional = todoRepo.findById(id);

        if (todoOptional.isPresent()) {
            TodoDTO savedTodo = todoOptional.get();

            if (todo.isCompleted() != savedTodo.isCompleted()) {
                savedTodo.setCompleted(todo.isCompleted());
            }
            if (todo.getTodo() != null) {
                savedTodo.setTodo(todo.getTodo());
            }
            if (todo.getDescription() != null) {
                savedTodo.setDescription(todo.getDescription());
            }
            savedTodo.setUpdateAt(new Date(System.currentTimeMillis()));

            todoRepo.save(savedTodo);
            return new ResponseEntity<TodoDTO>(savedTodo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Not found todo with id: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/todos/delete/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable("id") String id) {
        Optional<TodoDTO> todoOptional = todoRepo.findById(id);
        if (todoOptional.isPresent()) {
            todoRepo.deleteById(id);
            return new ResponseEntity<>("todo with id: '" + id + "' deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Not found todo with id: " + id, HttpStatus.NOT_FOUND);
        }
    }
}
