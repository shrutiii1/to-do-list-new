package com.example.todolist.controller;

import com.example.todolist.model.TodoItem;
import com.example.todolist.repository.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TodoController {
    private final TodoRepository repository;

    public TodoController(TodoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/todos")
    public List<TodoItem> getAllTodos() {
        return repository.findAll();
    }

    @GetMapping("/todo/{id}")
    public ResponseEntity<TodoItem> getTodoById(@PathVariable int id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/todos")
    public ResponseEntity<TodoItem> createTodo(@RequestBody TodoItem todo) {
        if (todo.getTitle() == null || todo.getTitle().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        TodoItem saved = repository.save(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<TodoItem> updateTodo(@PathVariable int id, @RequestBody TodoItem todo) {
        return repository.update(id, todo)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/todo/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable int id) {
        if (repository.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
