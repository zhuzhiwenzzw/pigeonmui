package top.zhuzhiwen.pigeonmui.model;

import javax.persistence.*;

/**
 * @author 朱治汶
 * @version 1.0
 * @className Attention
 * @description TODO
 * @date 2021/5/7 22:06
 **/

@Entity
@Table(name = "enterpriseinfo")
public class EnterpriseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "enterprise_id")
    @TableGenerator(name = "enterprise_id", initialValue = 0,allocationSize = 1, table = "seq_table")
    private int id;//自增id
    private int uid;
    @Column(columnDefinition = "text")
    private String introduction;
    private String bgImg;
    private String licenseImg;
    private String serviceTel;

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

    @Override
    public String toString() {
        return "EnterpriseInfo{" +
                "id=" + id +
                ", uid=" + uid +
                ", introduction='" + introduction + '\'' +
                ", bgImg='" + bgImg + '\'' +
                ", licenseImg='" + licenseImg + '\'' +
                ", serviceTel='" + serviceTel + '\'' +
                '}';
    }
}
