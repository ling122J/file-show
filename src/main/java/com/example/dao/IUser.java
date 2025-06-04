package com.example.dao;

import com.example.vo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface IUser {

    @Select("select * from users")
    public List<User> selAllUser();

    @Select("select * from users where userid = #{userid}")
    public User selById(@Param("userid") Integer userid);

    @Select("<script>"+
            "select * from users where userid in"+
            "<foreach collection='LoginUserList' item='item' open='(' close=')' separator=','>"+
            "#{item}"+
            "</foreach>"+
            "</script>"
    )
    public List<User> selBylistId(List<Integer> LoginUserList);

    @Select("select * from users where username = #{username}")
    public User selByName(@Param("username") String username);

    @Select("select * from users where username = #{username} and password = #{password}")
    public User selByName_pwd(@Param("username") String username,@Param("password") String password);

    @Insert("insert into users(userid,username,password) value (#{userid},#{username},#{password})")
    public void adduser(User user);


}