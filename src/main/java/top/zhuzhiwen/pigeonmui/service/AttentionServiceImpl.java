package top.zhuzhiwen.pigeonmui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zhuzhiwen.pigeonmui.dao.AttentionRepository;
import top.zhuzhiwen.pigeonmui.model.Attention;

import java.util.List;

/**
 * @author 朱治汶
 * @version 1.0
 * @className AttentionServiceImpl
 * @description TODO
 * @date 2021/5/7 22:16
 **/
@Service
public class AttentionServiceImpl implements AttentionService {

    @Autowired
    AttentionRepository attentionRepository;

    @Override
    public void add(Attention attention) {
        attentionRepository.save(attention);
    }

    @Override
    public void delete(int pid) {
    }

    @Override
    public List<Attention> list() {
        return null;
    }

    @Override
    public Attention findByUA(int id, int uid) {
        return attentionRepository.findByUA(id,uid);
    }

    @Override
    public void delete(int id, int uid) {
        attentionRepository.delete(id,uid);
    }

    @Override
    public void updateDisplay(int id, int uid) {
        attentionRepository.updateDisplay(id,uid);
    }

    @Override
    public void updateDisplayTrue(int id, int uid) {
        attentionRepository.updateDisplayTrue(id,uid);
    }
}
