package com.example.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.*;

public interface FileService {
    //文件上传
    public Map<String,Object> uploadFile(MultipartFile[] file, HttpServletRequest req) throws IOException;

    //展示下载列表
    public Map<String, Object> showDownload(HttpServletRequest req,Integer pageIndex,Integer pageSize);

    //冻结、解冻状态切换
    public Map<String,Object> toggleFreeze(@PathVariable("fileid") Integer fileid,HttpServletRequest req);

    //检查用户下载权限
    public Map<String, Object> CheckDownPermission(@PathVariable Integer fileid, HttpServletRequest req, HttpServletResponse res);

    //文件下载
    public void DownLoad(@PathVariable Integer fileid, HttpServletRequest req, HttpServletResponse res) throws IOException;

    //文件上传记录
    public Map<String, Object> showRecord(HttpServletRequest req);
    //拿到session的用户信息
    public Integer checkLoginUser(HttpServletRequest req);

}
