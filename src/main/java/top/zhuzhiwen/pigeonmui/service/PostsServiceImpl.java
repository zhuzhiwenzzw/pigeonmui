package top.zhuzhiwen.pigeonmui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import top.zhuzhiwen.pigeonmui.dao.PostsRepository;
import top.zhuzhiwen.pigeonmui.model.DTO.UserPostDto;
import top.zhuzhiwen.pigeonmui.model.Posts;

import java.util.List;

@Service
public class PostsServiceImpl implements PostsService {

    @Autowired
    private PostsRepository PostsRepository;

    @Override
    public void add(Posts posts) {
        PostsRepository.save(posts);
    }

    @Override
    public void delete(int pid) {
        PostsRepository.deleteById(pid);
    }

    @Override
    public void update(int pid,String content) {
        Posts posts = PostsRepository.findByPid(pid);
        posts.setContent(content);
        PostsRepository.save(posts);
    }

    @Override
    public List<Posts> list() {
        return PostsRepository.findAll();

    }

    @Override
    public Posts findbypid(int pid) {
        return PostsRepository.findByPid(pid);
    }

    @Override
    public List<String> listUserAndPost() {
        return PostsRepository.listUserAndPost();
    }

    @Override
    public List<String> randUserPost() {
        return PostsRepository.randUserPost();
    }

    @Override
    public Page<UserPostDto> findAllorderby(Pageable pageable) {
        return PostsRepository.findAllorderby(pageable);
    }

    @Override
    public Page<UserPostDto> findAllorderbyAttend(int id, Pageable pageable) {
        return PostsRepository.findAllorderbyAttend(id,pageable);
    }

    @Override
    public UserPostDto findbyPid(int pid) {
        return PostsRepository.findbyPid(pid);
    }

    @Override
    public int likeHe(int id) {
        return PostsRepository.likeHe(id);
    }
}
