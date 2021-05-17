package top.zhuzhiwen.pigeonmui.model;

import javax.persistence.*;
import java.util.Date;

/**
 * @author 朱治汶
 * @version 1.0
 * @className Messages
 * @description TODO
 * @date 2021/5/9 22:22
 **/

@Entity
@Table(name = "messages")
public class Messages {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "messages_id")
    @TableGenerator(name = "messages_id", initialValue = 0,allocationSize = 1, table = "seq_table")
    private int id;//自增id
    private int uid;
    private int aid;//被关注的用户id
    @Column(columnDefinition = "text")
    private String postMessages;
    private Date sendTime;


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

    public String getPostMessages() {
        return postMessages;
    }

    public void setPostMessages(String postMessages) {
        this.postMessages = postMessages;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }


    @Override
    public String toString() {
        return "Messages{" +
                "id=" + id +
                ", uid=" + uid +
                ", aid=" + aid +
                ", postMessages='" + postMessages + '\'' +
                ", sendTime=" + sendTime +
                '}';
    }
}
