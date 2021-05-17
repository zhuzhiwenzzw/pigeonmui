package top.zhuzhiwen.pigeonmui.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import top.zhuzhiwen.pigeonmui.dao.basedao.BaseRepository;
import top.zhuzhiwen.pigeonmui.model.RoleResource;

/**
 * @author CoolWen
 * @version 2018-11-02 10:15
 */
public interface RoleResourceRepository extends BaseRepository<RoleResource, Integer>, JpaSpecificationExecutor<RoleResource> {
    RoleResource findByRoleIdAndResId(int roleId, int resId);
}
