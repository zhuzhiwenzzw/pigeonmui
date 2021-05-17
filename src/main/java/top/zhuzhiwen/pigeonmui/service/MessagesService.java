package top.zhuzhiwen.pigeonmui.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import top.zhuzhiwen.pigeonmui.model.Messages;

import java.util.List;

public interface MessagesService {


    Page<Messages> findByUidAndAid(int id, int uid, Pageable pageable);

    void add(Messages messages);

}
