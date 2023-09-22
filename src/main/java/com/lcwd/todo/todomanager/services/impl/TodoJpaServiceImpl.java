package com.lcwd.todo.todomanager.services.impl;

import com.lcwd.todo.todomanager.dao.TodoRepository;
import com.lcwd.todo.todomanager.exceptions.ResourceNotFoundException;
import com.lcwd.todo.todomanager.models.Todo;
import com.lcwd.todo.todomanager.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public class TodoJpaServiceImpl implements TodoService
{
    @Autowired
    private TodoRepository todoRepository;
    @Override
    public Todo getTodo(int id) throws ParseException {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo not found with given ID", HttpStatus.NOT_FOUND));
        return todo;
    }

    @Override
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    @Override
    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    @Override
    public Todo updateTodo(int id, Todo newTodo)
    {
        Todo todo1 = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo not found with given ID", HttpStatus.NOT_FOUND));
        todo1.setTitle(todo1.getTitle());
        todo1.setContent(todo1.getContent());
        todo1.setStatus(todo1.getStatus());
        todo1.setToDoDate(todo1.getToDoDate());
        return todoRepository.save(todo1);
    }

    @Override
    public void deleteTodo(int id) {
        Todo todo1 = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo not found with given ID", HttpStatus.NOT_FOUND));
        todoRepository.delete(todo1);
    }

    @Override
    public void deleteMultiple(int[] ids) {

    }
}
