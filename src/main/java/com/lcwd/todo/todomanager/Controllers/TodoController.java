package com.lcwd.todo.todomanager.Controllers;

import com.lcwd.todo.todomanager.models.Todo;
import com.lcwd.todo.todomanager.services.TodoService;
import com.lcwd.todo.todomanager.services.impl.TodoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/todos")
public class TodoController
{

    Logger logger = LoggerFactory.getLogger(TodoController.class);

    @Autowired
    private TodoService todoService;

    Random random = new Random();

    //create
    @PostMapping
    public ResponseEntity<Todo> createTodoHandler(@RequestBody Todo todo)
    {
       // create todo
        int id = random.nextInt(9999999);
        todo.setId(id);

        /*
            // To create a NULL Pointer Exception
            String str = null;
            logger.info("{}", str.length());
        */

        // Create CurrentDate with System Default Current CurrentDate
        Date CurrentDate = new Date();
        logger.info("Current CurrentDate : {}", CurrentDate);
        logger.info("toDoDate : {}", todo.getToDoDate());
        todo.setAddedDate(CurrentDate);

        logger.info("Create Todo");

        // call service to create todo
        Todo todo1 = todoService.createTodo(todo);
        return new ResponseEntity<>(todo1, HttpStatus.CREATED);

    }

    // get all todo method
    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodoHandler()
    {
        List<Todo> allTodos = todoService.getAllTodos();
        return new ResponseEntity<>(allTodos, HttpStatus.OK);
    }

    // get single todo
    @GetMapping("/{todoId}")
    public ResponseEntity<Todo> getSingleTodoHandler(@PathVariable int todoId) throws ParseException
    {
        Todo todo = todoService.getTodo(todoId);
        return ResponseEntity.ok(todo );
    }

    // update todo
    @PutMapping("/{todoId}")
    public ResponseEntity<Todo> updateTodoHandler(@RequestBody Todo todoWithNewDetails, @PathVariable int todoId)
    {
        Todo todo = todoService.updateTodo(todoId, todoWithNewDetails);
        return ResponseEntity.ok(todo);
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<String> deleteTodo(@PathVariable int todoId)
    {
         todoService.deleteTodo(todoId);
         return ResponseEntity.ok("Todo Successfully deleted");
    }

    // Exception Handler
    @ExceptionHandler(value = {NullPointerException.class, NumberFormatException.class})
    public ResponseEntity <String> nullPointerExceptionHandler(Exception ex)
    {
        System.out.println(ex.getMessage());
        System.out.println("NULL Pointer Exception generated");
        return new ResponseEntity<>("NULL Pointer Exception generated" + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
