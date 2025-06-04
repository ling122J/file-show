package com.example.vo;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class User {
    private Integer userid;
    private String username;
    private String password;
    private List<String> contents;
    private Map<String,Boolean> filestatus;
    private List<UploadFile> upfiles;

    public List<UploadFile> getUpfiles() {
        return upfiles;
    }

    public void setUpfiles(List<UploadFile> upfiles) {
        this.upfiles = upfiles;
    }

    public User() {
    }

    public User(Integer userid, String username, String password,
                List<String> contents, Map<String, Boolean> filestatus) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.contents = contents;
        this.filestatus = filestatus;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getContents() {
        return contents;
    }

    public void setContents(List<String> contents) {
        this.contents = contents;
    }

    public Map<String, Boolean> getFilestatus() {
        return filestatus;
    }

    public void setFilestatus(Map<String, Boolean> filestatus) {
        this.filestatus = filestatus;
    }

    @Override
    public String toString() {
        return "User{" +
                "userid=" + userid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", contents=" + contents +
                ", filestatus=" + filestatus +
                '}';
    }
}
