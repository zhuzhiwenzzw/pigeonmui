package top.zhuzhiwen.pigeonmui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import top.zhuzhiwen.pigeonmui.dao.MessagesRepository;
import top.zhuzhiwen.pigeonmui.model.Messages;

import java.util.List;

/**
 * @author 朱治汶
 * @version 1.0
 * @className MessagesServiceImpl
 * @description TODO
 * @date 2021/5/9 22:36
 **/
@Service
public class MessagesServiceImpl implements MessagesService {

    @Autowired
    MessagesRepository messagesRepository;


    @Override
    public Page<Messages> findByUidAndAid(int id, int uid, Pageable pageable) {
        return messagesRepository.findByUidAndAid(id,uid,pageable);
    }

    @Override
    public void add(Messages messages) {
        messagesRepository.save(messages);
    }
}
