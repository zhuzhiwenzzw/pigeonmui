package top.zhuzhiwen.pigeonmui.model;

import javax.persistence.*;

/**
 * @author CoolWen
 * @version 2018-10-31 6:50
 */
@Entity
@Table(name = "t_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "user_id")
    @TableGenerator(name = "user_id", initialValue = 0, allocationSize = 1, table = "seq_table")
    private int id;
    private String username;
    private String nickname;
    private String password;
    private Boolean status;
    private String phone;
    private Boolean authentication;
    private int orgid;
//    @Column(name="information", columnDefinition="String default 暂无",nullable=false)
    private String information;
    private String imgSrc;

    @Column(columnDefinition = "text")
    private String introduction;
    private String bgImg;
    private String licenseImg;
    private String serviceTel;
    private Boolean apply;
    private String qqNumber;
    private String mailnum;

    public User() {
    }

    public User(int id, String username, String nickname, String password, Boolean status, String phone, Boolean authentication, int orgid, String information) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.status = status;
        this.phone = phone;
        this.authentication = authentication;
        this.orgid = orgid;
        this.information = information;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Boolean authentication) {
        this.authentication = authentication;
    }

    public int getOrgid() {
        return orgid;
    }

    public void setOrgid(int orgid) {
        this.orgid = orgid;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getBgImg() {
        return bgImg;
    }

    public void setBgImg(String bgImg) {
        this.bgImg = bgImg;
    }

    public String getLicenseImg() {
        return licenseImg;
    }

    public void setLicenseImg(String licenseImg) {
        this.licenseImg = licenseImg;
    }

    public String getServiceTel() {
        return serviceTel;
    }

    public void setServiceTel(String serviceTel) {
        this.serviceTel = serviceTel;
    }

    public Boolean getApply() {
        return apply;
    }

    public void setApply(Boolean apply) {
        this.apply = apply;
    }

    public String getQqNumber() {
        return qqNumber;
    }

    public void setQqNumber(String qqNumber) {
        this.qqNumber = qqNumber;
    }

    public String getMailnum() {
        return mailnum;
    }

    public void setMailnum(String mailnum) {
        this.mailnum = mailnum;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", phone='" + phone + '\'' +
                ", authentication=" + authentication +
                ", orgid=" + orgid +
                ", information='" + information + '\'' +
                ", imgSrc='" + imgSrc + '\'' +
                '}';
    }
}
