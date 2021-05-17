package top.zhuzhiwen.pigeonmui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zhuzhiwen.pigeonmui.dao.ResourceRepository;
import top.zhuzhiwen.pigeonmui.dao.RoleRepository;
import top.zhuzhiwen.pigeonmui.dao.RoleResourceRepository;
import top.zhuzhiwen.pigeonmui.dao.UserRoleRepository;
import top.zhuzhiwen.pigeonmui.model.Resource;
import top.zhuzhiwen.pigeonmui.model.Role;
import top.zhuzhiwen.pigeonmui.model.RoleResource;
import top.zhuzhiwen.pigeonmui.model.UserRole;

import java.util.List;

/**
 * @author CoolWen
 * @version 2018-11-02 9:34
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private ResourceRepository resourceRepository;
    @Autowired
    private RoleResourceRepository roleResourceRepository;

    @Override
    public void add(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void delete(int id) {
        roleRepository.delete(this.load(id));
    }

    @Override
    public Role load(int id) {
        return roleRepository.findById(id).get();
    }

    @Override
    public void update(Role role) {
        roleRepository.save(role);
    }

    @Override
    public List<Role> listRole() {
        return roleRepository.findAll();
    }

    @Override
    public UserRole loadUserRole(int uid, int roleId) {
        return userRoleRepository.findByUserIdAndRoleId(uid, roleId);
    }

    @Override
    public void addUserRole(int uid, int roleId) {
        UserRole ur = null;
        ur = loadUserRole(uid, roleId);
        if (ur == null) {
            ur = new UserRole();
            ur.setRoleId(roleId);
            ur.setUserId(uid);
            userRoleRepository.save(ur);
        }
    }

    @Override
    public void deleteUserRole(int uid, int roleId) {
        UserRole ur = null;
        ur = loadUserRole(uid, roleId);
        if (ur != null) {
            userRoleRepository.delete(ur);
        }
    }

    @Override
    public void deleteUserRoles(int uid) {
        String hql = "delete UserRole ur where ur.userId=?1";
        userRoleRepository.updateByHql(hql, uid);
    }

    @Override
    public List<Resource> listRoleResource(int roleId) {
        return resourceRepository.listRoleResource(roleId);
    }

    @Override
    public void addRoleResource(int roleId, int resId) {
        RoleResource rr = null;
        rr = loadResourceRole(roleId, resId);
        if (rr == null) {
            rr = new RoleResource();
            rr.setResId(resId);
            rr.setRoleId(roleId);
            roleResourceRepository.save(rr);
        }
    }

    @Override
    public void deleteRoleResource(int roleId, int resId) {
        RoleResource rr = null;
        rr = loadResourceRole(roleId, resId);
        if (rr != null) {
            roleResourceRepository.delete(rr);
        }
    }

    @Override
    public RoleResource loadResourceRole(int roleId, int resId) {
        return roleResourceRepository.findByRoleIdAndResId(roleId,resId);
    }
}
