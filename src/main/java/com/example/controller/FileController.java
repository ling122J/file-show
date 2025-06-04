package com.example.controller;

import com.example.service.FileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.*;

@Controller
public class FileController {
    @Autowired
    FileService fileService;

    //文件上传
    @RequestMapping("uploadFile")
    @ResponseBody
    public Map<String,Object> uploadFile(MultipartFile[] file, HttpServletRequest req) throws IOException {
       return fileService.uploadFile(file,req);
    }
    //展示下载列表
    @RequestMapping("showDownload")
    @ResponseBody
    public Map<String, Object> showDownload(HttpServletRequest req, @RequestParam(defaultValue = "1") Integer pageIndex,@RequestParam(defaultValue = "10") Integer pageSize) {
        return fileService.showDownload(req,pageIndex,pageSize);
    }
    //冻结、解冻状态切换
    @RequestMapping("ToggleFreeze/lingabc{fileid}")
    @ResponseBody
    public Map<String,Object> toggleFreeze(@PathVariable("fileid") Integer fileid, HttpServletRequest req) {
        return fileService.toggleFreeze(fileid,req);
    }
    //检查用户下载权限
    @RequestMapping("CheckDownPermission/lingabc{fileid}")
    @ResponseBody
    public Map<String, Object> CheckDownPermission(@PathVariable Integer fileid,HttpServletRequest req,HttpServletResponse res)  {
        return fileService.CheckDownPermission(fileid,req,res);
    }
    //文件下载
    @RequestMapping("Download/lingabc{fileid}")
    public String DownLoad(@PathVariable Integer fileid, HttpServletRequest req, HttpServletResponse res) throws IOException {
        fileService.DownLoad(fileid,req,res);
        return "space";
    }
    //文件上传记录
    @RequestMapping("showRecord")
    @ResponseBody
    public Map<String, Object> showRecord(HttpServletRequest req) {
        return fileService.showRecord(req);
    }
}
