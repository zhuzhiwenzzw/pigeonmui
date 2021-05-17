package top.zhuzhiwen.pigeonmui.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import top.zhuzhiwen.pigeonmui.model.Attention;
import top.zhuzhiwen.pigeonmui.model.Comment;
import top.zhuzhiwen.pigeonmui.model.DTO.UserCommentDto;

import java.util.List;

public interface CommentService {

    Page<UserCommentDto> findByPid(int pid, Pageable pageable);

    Comment add(Comment comment);

    int countByPid(int pid);
}
