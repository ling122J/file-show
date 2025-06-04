package com.example.service.Impl;

import com.example.dao.IFile;
import com.example.dao.IUser;
import com.example.service.FileService;
import com.example.vo.PersonSpace;
import com.example.vo.UploadFile;
import com.example.vo.User;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    IUser userDao;
    @Autowired
    IFile fileDao;

    @Override
    public Integer checkLoginUser(HttpServletRequest req) {
        User user = (User)req.getSession().getAttribute("user");
        if (user == null) {
            return  null;
        }
        return user.getUserid();
    }

    @Override
    public Map<String, Object> uploadFile(MultipartFile[] file, HttpServletRequest req) throws IOException {
        Integer userid = checkLoginUser(req);
        Map<String, Object> result = new HashMap<>();
        User user = userDao.selById(userid); // 通过ID得到用户信息
        /*不允许上传表单为空,前端作输入的相应处理*/
        String[] frnames;// 上传文件的真实文件名、用户空间(物理存放地址)
        // 获取真实文件名
        String s = "";
        for (MultipartFile m : file) {
            s += m.getOriginalFilename() + ",";
        }
        frnames = s.split(",");
        // 获取文件名后缀
        String[] suffix = new String[frnames.length];
        for (int i = 0; i < frnames.length; i++) {
            suffix[i] = frnames[i].substring(frnames[i].lastIndexOf("."));
        }
        // 获取用户空间
        String[] fsnames = new String[frnames.length];
        String webRoot = req.getServletContext().getRealPath("/");
        for (int i = 0; i < frnames.length; i++) {
            String uuid = UUID.randomUUID().toString();
            fsnames[i] = webRoot + "files\\" + user.getUsername() + "\\" + uuid + suffix[i];
            FileUtils.copyInputStreamToFile(file[i].getInputStream(), new File(fsnames[i]));
        }
        result.put("code", 0);
        result.put("msg", "success");
        result.put("count", frnames.length);
        // fid自增,插入uid,frname,fsname,timeUp,downCount,status
        List<UploadFile> list = new ArrayList<>();
        for (int i = 0; i < frnames.length; i++) {
            list.add(new UploadFile(null, userid, frnames[i], fsnames[i], new Date(), 0, "解冻"));
        }
        fileDao.addFiles(list);
        return result;
    }

    @Override
    public Map<String, Object> showDownload(HttpServletRequest req,Integer pageIndex,Integer pageSize) {
        Integer pageStart = (pageIndex - 1) * pageSize;
        List<UploadFile> fileList = fileDao.selByPage(pageStart,pageSize);
        int total = fileDao.countFiles();
        List<PersonSpace> show = new ArrayList<>();
        for (UploadFile file : fileList) {
            String name = fileDao.selUsername(file.getUid());
            show.add(new PersonSpace(file.getFid(),name,file.getFrname(),file.getTimeUp(),
                    file.getDownCount(),file.getStatus()));
        }
        Map<String, Object> data = new HashMap<>();
        data.put("code", 0);
        data.put("msg", "");
        data.put("count", total);
        data.put("data", show);
        return data;
    }

    @Override
    public Map<String, Object> toggleFreeze(Integer fileid, HttpServletRequest req) {
        Map<String, Object> result = new HashMap<>();
        Integer userid = checkLoginUser(req);
        UploadFile file = fileDao.selByFid(fileid);
        if (userid != file.getUid()) {
            result.put("success", false);   //非上传者自己上传的文件,设置冻结权限为false
            result.put("message", "无权限操作该文件");
            result.put("disableButton", true);
            return result;
        }
        if ("解冻".equals(file.getStatus())) {
            file.setStatus("冻结");
        } else {
            file.setStatus("解冻");
        }
        fileDao.update(file);
        result.put("success", true);
        result.put("message", "文件状态已更新为" + file.getStatus());
        result.put("newStatus", file.getStatus());
        return result;
    }

    @Override
    public Map<String, Object> CheckDownPermission(Integer fileid, HttpServletRequest req, HttpServletResponse res) {
        Integer userid = checkLoginUser(req);
        Map<String,Object> result = new HashMap<>();
        String username = fileDao.selUsername(userid);
        UploadFile uploadFile = fileDao.selByFid(fileid);
        if ("冻结".equals(uploadFile.getStatus())) {  //文件冻结状态下,其他用户下载非法
            if (userid != uploadFile.getUid()) {  //文件所属不是当前用户
                result.put("success", false);
                result.put("message", "文件所属不是当前用户,无下载权限");
                result.put("count", "0");
                return result;
            }
        }
        result.put("success",true);
        result.put("message",username + "有下载权限!");
        result.put("count","1");
        return result;
    }

    @Override
    public void DownLoad(Integer fileid, HttpServletRequest req, HttpServletResponse res) throws IOException {
        UploadFile uploadFile = fileDao.selByFid(fileid);
        File path = new File(uploadFile.getFsname());
        ServletOutputStream os = res.getOutputStream();
        res.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(uploadFile.getFrname(),"UTF-8"));
        byte[] bytes = FileUtils.readFileToByteArray(path);
        os.write(bytes);
        os.flush();
        os.close();
        uploadFile.setDownCount(uploadFile.getDownCount() + 1); //下载次数 + 1
        fileDao.update(uploadFile);
    }

    @Override
    public Map<String, Object> showRecord(HttpServletRequest req) {
        Integer userid = checkLoginUser(req);
        User user = userDao.selById(userid); // 通过ID得到用户信息
        List<UploadFile> fileList = fileDao.selByUid(userid);
        List<PersonSpace> show = new ArrayList<>();
        for (UploadFile file : fileList) {
            show.add(new PersonSpace(file.getFid(),user.getUsername(),file.getFrname(),
                    file.getTimeUp(),file.getDownCount(),file.getStatus()));
        }
        Map<String, Object> data = new HashMap<>();
        data.put("code", 0);
        data.put("msg", "");
        data.put("count", show.size());
        data.put("data", show);
        return data;
    }
}
