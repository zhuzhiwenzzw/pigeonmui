package top.zhuzhiwen.pigeonmui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zhuzhiwen.pigeonmui.dao.ResourceRepository;
import top.zhuzhiwen.pigeonmui.model.Resource;

import java.util.List;

/**
 * @author CoolWen
 * @version 2018-11-02 6:36
 */
@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Override
    public void add(Resource res) {

        resourceRepository.save(res);
    }

    @Override
    public void update(Resource res) {
        resourceRepository.save(res);

    }

    @Override
    public void delete(int id) {
        Resource res = this.load(id);
        resourceRepository.delete(res);
    }

    @Override
    public Resource load(int id) {
        return resourceRepository.findById(id).get();
    }

    @Override
    public List<Resource> listResource() {
        return resourceRepository.findAll();
    }
}
