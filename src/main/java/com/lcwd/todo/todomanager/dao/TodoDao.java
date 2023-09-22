package com.lcwd.todo.todomanager.dao;

import com.lcwd.todo.todomanager.helper.Helper;
import com.lcwd.todo.todomanager.models.Todo;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Repository
public class TodoDao
{
    Logger logger = LoggerFactory.getLogger(Todo.class);

    private JdbcTemplate template;

    public TodoDao(@Autowired JdbcTemplate template) {
        this.template = template;

        // create table if does not exists

        String cretateTable = "create table IF NOT EXISTS todos(id int primary key, title varchar(100) not null, content varchar(4500), status varchar(10) not null, addedDate datetime, todoDate datetime) ";
        int update = template.update(cretateTable);
        logger.info("TODO TABLE CREATED {}",update);
    }

    public JdbcTemplate getTemplate() {
        return template;
    }

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }



    // save todo in database

    public Todo saveTodo(Todo todo)
    {
        String insertQuerry = "insert into todos(id, title, content, status, addedDate, todoDate) values(?,?,?,?,?,?)";
        int rows = template.update(insertQuerry, todo.getId(), todo.getTitle(), todo.getContent(), todo.getStatus(), todo.getAddedDate(), todo.getToDoDate());

        logger.info("JDBC OPERATION: {} inserted", rows);

        return todo;
    }

    
    // get single todo from database
    public Todo getTodo(int id) throws ParseException {
        String query = "select * from todos WHERE id = ?";
        Todo todo = template.queryForObject(query, new TodoRowMapper(), id);

     //       Map<String, Object> todoData = template.queryForMap(query, id);
    //        Todo todo = new Todo();
    //        todo.setId(((int)todoData.get("id")));
    //        todo.setTitle(((String)todoData.get("title")));
    //        todo.setContent(((String)todoData.get("content")));
    //        todo.setStatus(((String)todoData.get("status")));
    //        todo.setAddedDate(Helper.parseDate((LocalDateTime) todoData.get("addedDate")));
    //        todo.setToDoDate(Helper.parseDate((LocalDateTime) todoData.get("todoDate")));

        return todo;

    }
    // get all todo from database
    public List<Todo> getAllTodos()
    {
        String query = "select * from todos";
        List<Todo> todo = template.query(query, new TodoRowMapper());
        return todo;

     /*   List<Map<String, Object>> maps = template.queryForList(query);
        List<Todo> todos = maps.stream().map((map) -> {
            Todo todo = new Todo();

            todo.setId(((int) map.get("id")));
            todo.setTitle(((String) map.get("title")));
            todo.setContent(((String) map.get("content")));
            todo.setStatus(((String) map.get("status")));
            try {
                todo.setAddedDate(Helper.parseDate((LocalDateTime) map.get("addedDate")));
                todo.setToDoDate(Helper.parseDate((LocalDateTime) map.get("todoDate")));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            return todo;

        }).collect(Collectors.toList());
        return todos; */
    }

    // update todo
       public Todo updateTodo(int id, Todo newTodo){
        String query = "update todos set title=?, content=?, status=?, addedDate=?, todoDate=? WHERE id=?";
           int update = template.update(query, newTodo.getTitle(), newTodo.getContent(), newTodo.getStatus(), newTodo.getAddedDate(), newTodo.getToDoDate(), id);
           logger.info("UPDATED {}", update);
           newTodo.setId(id);
           return newTodo;
       }


    // delete todo from database
    public void deleteTodo(int id)
    {
        String query = "delete from todos WHERE id=?";
        int update = template.update(query, id);
        logger.info("DELETED {}", update);
    }

    public void deleteMultiple(int ids[])
    {
        String query = "delete from todos WHERE id=?";
        int[] ints = template.batchUpdate(query, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                int id = ids[i];
                ps.setInt(1, id);

            }

            @Override
            public int getBatchSize() {
                return ids.length;
            }
        });

        for(int i:ints)
        {
            logger.info("DELETED {} : ",i);
        }
    }
}
