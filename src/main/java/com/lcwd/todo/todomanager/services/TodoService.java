package com.lcwd.todo.todomanager.services;

import com.lcwd.todo.todomanager.models.Todo;

import java.text.ParseException;
import java.util.List;

public interface TodoService
{
    public Todo getTodo(int id) throws ParseException;

    public List<Todo> getAllTodos();

    public Todo createTodo(Todo todo);

    public Todo updateTodo(int id, Todo newTodo);

    public void deleteTodo(int id);

    public void deleteMultiple(int ids[]);
}
