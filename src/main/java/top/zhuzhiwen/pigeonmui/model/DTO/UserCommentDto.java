package top.zhuzhiwen.pigeonmui.model.DTO;

import java.util.Date;

/**
 * @author 朱治汶
 * @version 1.0
 * @className UserCommentDto
 * @description TODO
 * @date 2021/5/11 13:44
 **/
public class UserCommentDto {

    private int id;
    private int uid;//评论者id
    private int cid;
    private String unickname;
    private String cnickname;
    private Date time;//时间
    private String content;//内容


    public UserCommentDto(int uid, int cid, String unickname, String cnickname, Date time, String content) {
        this.uid = uid;
        this.cid = cid;
        this.unickname = unickname;
        this.cnickname = cnickname;
        this.time = time;
        this.content = content;
    }

    public UserCommentDto(int id, int uid, int cid, String unickname, String cnickname, Date time, String content) {
        this.id = id;
        this.uid = uid;
        this.cid = cid;
        this.unickname = unickname;
        this.cnickname = cnickname;
        this.time = time;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getUnickname() {
        return unickname;
    }

    public void setUnickname(String unickname) {
        this.unickname = unickname;
    }

    public String getCnickname() {
        return cnickname;
    }

    public void setCnickname(String cnickname) {
        this.cnickname = cnickname;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
