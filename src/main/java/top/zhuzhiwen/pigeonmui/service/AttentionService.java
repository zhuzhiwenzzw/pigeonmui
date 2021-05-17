package top.zhuzhiwen.pigeonmui.service;

import top.zhuzhiwen.pigeonmui.model.Attention;

import java.util.List;

public interface AttentionService {

    public void add(Attention attention);

    public void delete(int pid);

    public List<Attention> list();

    Attention findByUA(int id, int uid);

    void delete(int id, int uid);

    void updateDisplay(int id, int uid);

    void updateDisplayTrue(int id, int uid);
}
