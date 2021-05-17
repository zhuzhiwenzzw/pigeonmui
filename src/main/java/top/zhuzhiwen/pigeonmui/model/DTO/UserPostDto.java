package top.zhuzhiwen.pigeonmui.model.DTO;

import java.util.Date;

/**
 * @author 朱治汶
 * @version 1.0
 * @className UserPostDto
 * @description TODO
 * @date 2021/5/5 21:08
 **/
public class UserPostDto {
    private int pid;
    private int uid;
    private String username;
    private String nickname;
    private Date time;
    private int oid;
    private String content;
    private String imgSrc;
    private String uimgSrc;
    private Boolean authentication;
    private String information;
    private int giveLike;
    private int commentNum;

    public UserPostDto(int pid, int uid, String username, String nickname, Date time, int oid, String content, String imgSrc, String uimgSrc, Boolean authentication, String information, int giveLike, int commentNum) {
        this.pid = pid;
        this.uid = uid;
        this.username = username;
        this.nickname = nickname;
        this.time = time;
        this.oid = oid;
        this.content = content;
        this.imgSrc = imgSrc;
        this.uimgSrc = uimgSrc;
        this.authentication = authentication;
        this.information = information;
        this.giveLike = giveLike;
        this.commentNum = commentNum;
    }

    public UserPostDto(int pid, int uid, String username, String nickname, Date time, int oid, String content, String imgSrc, String uimgSrc, Boolean authentication, String information, int giveLike) {
        this.pid = pid;
        this.uid = uid;
        this.username = username;
        this.nickname = nickname;
        this.time = time;
        this.oid = oid;
        this.content = content;
        this.imgSrc = imgSrc;
        this.uimgSrc = uimgSrc;
        this.authentication = authentication;
        this.information = information;
        this.giveLike = giveLike;
    }

    public UserPostDto(int pid, int uid, String username, String nickname, Date time, int oid, String content, String imgSrc, String uimgSrc, Boolean authentication, String information) {
        this.pid = pid;
        this.uid = uid;
        this.username = username;
        this.nickname = nickname;
        this.time = time;
        this.oid = oid;
        this.content = content;
        this.imgSrc = imgSrc;
        this.uimgSrc = uimgSrc;
        this.authentication = authentication;
        this.information = information;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getUimgSrc() {
        return uimgSrc;
    }

    public void setUimgSrc(String uimgSrc) {
        this.uimgSrc = uimgSrc;
    }

    public Boolean getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Boolean authentication) {
        this.authentication = authentication;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public int getGiveLike() {
        return giveLike;
    }

    public void setGiveLike(int giveLike) {
        this.giveLike = giveLike;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    @Override
    public String toString() {
        return "UserPostDto{" +
                "pid=" + pid +
                ", uid=" + uid +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", time=" + time +
                ", oid=" + oid +
                ", content='" + content + '\'' +
                ", imgSrc='" + imgSrc + '\'' +
                ", uimgSrc='" + uimgSrc + '\'' +
                ", authentication=" + authentication +
                ", information='" + information + '\'' +
                ", giveLike=" + giveLike +
                '}';
    }
}
