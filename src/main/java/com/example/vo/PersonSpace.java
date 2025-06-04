package com.example.vo;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
//该类用于接受可下载文件列表、以及对其进行操作
public class PersonSpace {
    private Integer fileid;
    private String uploadname;
    private String filename;
    private Date timeUp;
    private Integer downTimes;
    private String status;

    public PersonSpace() {
    }

    public PersonSpace(Integer fileid,String uploadname, String filename, Date timeUp, Integer downTimes, String status) {
        this.fileid = fileid;
        this.uploadname = uploadname;
        this.filename = filename;
        this.timeUp = timeUp;
        this.downTimes = downTimes;
        this.status = status;
    }

    public Integer getFileid() {
        return fileid;
    }

    public void setFileid(Integer fileid) {
        this.fileid = fileid;
    }

    public String getUploadname() {
        return uploadname;
    }

    public void setUploadname(String uploadname) {
        this.uploadname = uploadname;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Date getTimeUp() {
        return timeUp;
    }

    public void setTimeUp(Date timeUp) {
        this.timeUp = timeUp;
    }

    public Integer getDownTimes() {
        return downTimes;
    }

    public void setDownTimes(Integer downTimes) {
        this.downTimes = downTimes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PersonSpace{" +
                "fileid=" + fileid +
                ", uploadname='" + uploadname + '\'' +
                ", filename='" + filename + '\'' +
                ", timeUp=" + timeUp +
                ", downTimes=" + downTimes +
                ", status='" + status + '\'' +
                '}';
    }
}
