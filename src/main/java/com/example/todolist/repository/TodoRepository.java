package com.example.todolist.repository;

import com.example.todolist.model.TodoItem;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class TodoRepository {
    private final List<TodoItem> todos = new ArrayList<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    public List<TodoItem> findAll() {
        return new ArrayList<>(todos);
    }

    public Optional<TodoItem> findById(int id) {
        return todos.stream()
                .filter(todo -> todo.getId() != null && todo.getId() == id)
                .findFirst();
    }

    public TodoItem save(TodoItem todo) {
        int id = nextId.getAndIncrement();
        todo.setId(id);
        if (todo.getStatus() == null) {
            todo.setStatus(false);
        }
        todos.add(todo);
        return todo;
    }

    public Optional<TodoItem> update(int id, TodoItem updated) {
        return findById(id).map(existing -> {
            existing.setTitle(updated.getTitle());
            existing.setDescription(updated.getDescription());
            existing.setStatus(updated.getStatus());
            return existing;
        });
    }

    public boolean delete(int id) {
        return todos.removeIf(todo -> todo.getId() != null && todo.getId() == id);
    }
}
