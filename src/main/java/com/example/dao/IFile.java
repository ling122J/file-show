package com.example.dao;

import com.example.vo.UploadFile;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFile {
    @Insert("insert into file(fid,uid,frname,fsname,timeUp,downCount,status)" +
            "value (#{fid},#{uid},#{frname},#{fsname},#{timeUp},#{downCount},#{status})")
    public void addFile(UploadFile uploadFile);

    @Insert("<script>"+
            "insert into file values"+
            "<trim suffixOverrides=','>"+
            "<foreach collection='list' item='item'>"+
            "(#{item.fid},#{item.uid},#{item.frname},#{item.fsname},#{item.timeUp},#{item.downCount},#{item.status}),"+
            "</foreach>"+
            "</trim>"+
            "</script>"
    )
    public void addFiles(List<UploadFile> list);
    @Select("select * from file limit #{pageSize} offset #{pageStart}")
    public List<UploadFile> selByPage(@Param("pageStart") Integer pageStart,@Param("pageSize") Integer pageSize);
    @Select("SELECT COUNT(*) FROM file")
    int countFiles();
    //查询所有解冻状态的文件
    @Select("select * from file order by downCount DESC")
    public List<UploadFile> selAllFile();
    //根据fid查询文件
    @Select("select * from file where fid = #{fileid}")
    public UploadFile selByFid(@Param("fileid") Integer fileid);
    //查询用户上传的文件列表
    @Select("select * from file where uid = #{userid}")
    public List<UploadFile> selByUid(@Param("userid") Integer userid);
    //查询上传文件的上传者姓名
    @Select("select username from users where userid = #{uid}")
    public String selUsername(@Param("uid") Integer uid);
    //更新文件下载次数
    @Update("<script>"+
            "update file"+
            "<trim prefix='set' suffixOverrides=','>"+
            "fid = #{fid},uid = #{uid},frname = #{frname},fsname = #{fsname}," +
            "timeUp = #{timeUp},downCount = #{downCount},status = #{status},"+
            "</trim>"+
            "where fid = #{fid}"+
            "</script>"
    )
    public void update(UploadFile uploadFile);
}
