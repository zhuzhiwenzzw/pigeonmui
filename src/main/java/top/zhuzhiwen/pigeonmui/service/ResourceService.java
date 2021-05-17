package top.zhuzhiwen.pigeonmui.service;

import top.zhuzhiwen.pigeonmui.model.Resource;

import java.util.List;

/**
 * @author CoolWen
 * @version 2018-11-02 6:34
 */
public interface ResourceService {
    public void add(Resource res);

    public void update(Resource res);

    public void delete(int id);

    public Resource load(int id);

    public List<Resource> listResource();
}
