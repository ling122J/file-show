package com.example.vo;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UploadFile {
    private Integer fid;
    private Integer uid;
    private String frname;
    private String fsname;
    private Date timeUp;
    private Integer downCount;
    private String status;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UploadFile() {
        super();
    }

    public UploadFile(Integer fid, Integer uid, String frname, String fsname,
                      Date timeUp, Integer downCount, String status) {
        this.fid = fid;
        this.uid = uid;
        this.frname = frname;
        this.fsname = fsname;
        this.timeUp = timeUp;
        this.downCount = downCount;
        this.status = status;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getFrname() {
        return frname;
    }

    public void setFrname(String frname) {
        this.frname = frname;
    }

    public String getFsname() {
        return fsname;
    }

    public void setFsname(String fsname) {
        this.fsname = fsname;
    }

    public Date getTimeUp() {
        return timeUp;
    }

    public void setTimeUp(Date timeUp) {
        this.timeUp = timeUp;
    }

    public Integer getDownCount() {
        return downCount;
    }

    public void setDownCount(Integer downCount) {
        this.downCount = downCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UplodFile{" +
                "fid=" + fid +
                ", uid=" + uid +
                ", frname='" + frname + '\'' +
                ", fsname='" + fsname + '\'' +
                ", timeUp=" + timeUp +
                ", downCount=" + downCount +
                ", status='" + status + '\'' +
                '}';
    }
}
