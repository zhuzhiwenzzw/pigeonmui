package top.zhuzhiwen.pigeonmui.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "posts")
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "posts_pid")
    @TableGenerator(name = "posts_pid", initialValue = 0,allocationSize = 1, table = "seq_table")
    private int pid;
    private int uid;
    private Date time;
    private int oid;
    private int cid;
    private String content;
    private String imgSrc;
    private int giveLike;

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

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getGiveLike() {
        return giveLike;
    }

    public void setGiveLike(int giveLike) {
        this.giveLike = giveLike;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    @Override
    public String toString() {
        return "Posts{" +
                "pid=" + pid +
                ", uid=" + uid +
                ", time=" + time +
                ", oid=" + oid +
                ", cid=" + cid +
                ", content='" + content + '\'' +
                ", imgSrc='" + imgSrc + '\'' +
                ", giveLike=" + giveLike +
                '}';
    }
}
