package top.zhuzhiwen.pigeonmui.model.DTO;

import java.util.Date;

/**
 * @author 朱治汶
 * @version 1.0
 * @className UserPostDto
 * @description TODO
 * @date 2021/5/5 21:08
 **/
public class UserAttentionDto {
    private int uid;
    private String username;
    private String nickname;
    private String imgSrc;
    private String information;
    private int newinfo;

    public UserAttentionDto(int uid, String username, String nickname, String imgSrc, String information, int newinfo) {
        this.uid = uid;
        this.username = username;
        this.nickname = nickname;
        this.imgSrc = imgSrc;
        this.information = information;
        this.newinfo = newinfo;
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

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public int getNewinfo() {
        return newinfo;
    }

    public void setNewinfo(int newinfo) {
        this.newinfo = newinfo;
    }
}
