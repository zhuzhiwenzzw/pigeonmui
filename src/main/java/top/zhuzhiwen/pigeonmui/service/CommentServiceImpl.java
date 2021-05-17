package top.zhuzhiwen.pigeonmui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import top.zhuzhiwen.pigeonmui.dao.CommentRepository;
import top.zhuzhiwen.pigeonmui.model.Comment;
import top.zhuzhiwen.pigeonmui.model.DTO.UserCommentDto;

/**
 * @author 朱治汶
 * @version 1.0
 * @className CommentServiceImpl
 * @description TODO
 * @date 2021/5/11 10:56
 **/

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;
    @Override
    public Page<UserCommentDto> findByPid(int pid, Pageable pageable) {
        return commentRepository.findByPid(pid,pageable);
    }

    @Override
    public Comment add(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public int countByPid(int pid) {
        return commentRepository.countByPid(pid);
    }
}
