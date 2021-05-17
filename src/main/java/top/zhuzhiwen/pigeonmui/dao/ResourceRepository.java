package top.zhuzhiwen.pigeonmui.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import top.zhuzhiwen.pigeonmui.dao.basedao.BaseRepository;
import top.zhuzhiwen.pigeonmui.model.Resource;

import java.util.List;

/**
 * @author CoolWen
 * @version 2018-10-31 8:25
 */
public interface ResourceRepository extends BaseRepository<Resource, Integer>, JpaSpecificationExecutor<Resource> {
    @Query("select res from Role role,Resource res,RoleResource rr where " +
            "role.id=rr.roleId and res.id=rr.resId and role.id=?1")
    List<Resource> listRoleResource(int roleId);
}
