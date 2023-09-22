package com.lcwd.todo.todomanager.dao;

import com.lcwd.todo.todomanager.helper.Helper;
import com.lcwd.todo.todomanager.models.Todo;
import org.springframework.expression.ParseException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class TodoRowMapper implements RowMapper<Todo> {
    @Override
    public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {

        Todo todo = new Todo();

        todo.setId(rs.getInt("id"));
        todo.setTitle(rs.getString("title"));
        todo.setContent(rs.getString("content"));
        todo.setStatus(rs.getString("status"));
        try {
            todo.setAddedDate(Helper.parseDate((LocalDateTime) rs.getObject("addedDate")));
        } catch (java.text.ParseException e) {
            throw new RuntimeException(e);
        }
        try {
            todo.setToDoDate(Helper.parseDate((LocalDateTime) rs.getObject("todoDate")));
        } catch (java.text.ParseException e) {
            throw new RuntimeException(e);
        }
        return todo;
    }
}
