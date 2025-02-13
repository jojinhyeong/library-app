package com.group.libraryapp.repository.user;

import com.group.libraryapp.dto.user.reponse.UserResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserJdbcRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean isUserNotExist(long id){
        String readSql ="select * from user where id = ?";
        return jdbcTemplate.query(readSql, (rs,rowNUm) -> 0, id).isEmpty();
    }

    public void updateUserName(String name, long id){
        String sql ="update user set name = ? where id =?";
        jdbcTemplate.update(sql, name, id);
    }

    public boolean isUserNotExist(String name){
        String readSql ="select * from user where id = ?";
        return jdbcTemplate.query(readSql, (rs,rowNUm) -> 0, name).isEmpty();
    }

    public void deleteUserName(String name){
        String sql ="delete from user where name=?";
        jdbcTemplate.update(sql,name);
    }

    public List<UserResponse> getUsers(){
        String sql ="select * from user";

        return jdbcTemplate.query(sql, (rs,rowNum) ->{
            long id = rs.getLong("id");
            String name = rs.getString("name");
            int age = rs.getInt("age");
            return new UserResponse(id, name,age);
        });
    }

    public void saveUser(String name, Integer age){
        String sql ="insert into user(name, age) values(?,?)";
        jdbcTemplate.update(sql, name,age);
    }
}
