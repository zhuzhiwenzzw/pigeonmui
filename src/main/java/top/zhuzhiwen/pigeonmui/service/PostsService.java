package top.zhuzhiwen.pigeonmui.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import top.zhuzhiwen.pigeonmui.model.DTO.UserPostDto;
import top.zhuzhiwen.pigeonmui.model.Posts;
import java.util.List;

public interface PostsService {

    //新建帖子
    public void add(Posts posts);

    //删帖
    public void delete(int pid);

    //编辑帖子
    public void update(int pid,String comment);

    //帖子列表
    public List<Posts> list();

    public Posts findbypid(int pid);

    public List<String> listUserAndPost();

    List<String> randUserPost();

    //帖子列表
    Page<UserPostDto> findAllorderby(Pageable pageable);

    Page<UserPostDto> findAllorderbyAttend(int id, Pageable pageable);

    UserPostDto findbyPid(int pid);

    int likeHe(int id);
}
