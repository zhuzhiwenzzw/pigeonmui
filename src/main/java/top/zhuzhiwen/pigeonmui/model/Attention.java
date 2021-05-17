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
@Table(name = "attention")
public class Attention {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "attention_id")
    @TableGenerator(name = "attention_id", initialValue = 0,allocationSize = 1, table = "seq_table")
    private int id;//自增id
    private int uid;
    private int aid;//被关注的用户id
    private Boolean isattent;
    private Boolean display;
    private int newinfo;

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

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }



    public Boolean getDisplay() {
        return display;
    }

    public void setDisplay(Boolean display) {
        this.display = display;
    }

    public Boolean getIsattent() {
        return isattent;
    }

    public void setIsattent(Boolean isattent) {
        this.isattent = isattent;
    }

    public int getNewinfo() {
        return newinfo;
    }

    public void setNewinfo(int newinfo) {
        this.newinfo = newinfo;
    }

    @Override
    public String toString() {
        return "Attention{" +
                "id=" + id +
                ", uid=" + uid +
                ", aid=" + aid +
                ", isattent=" + isattent +
                ", display=" + display +
                ", newinfo=" + newinfo +
                '}';
    }
}
