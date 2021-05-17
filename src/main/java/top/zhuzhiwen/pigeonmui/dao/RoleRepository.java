package top.zhuzhiwen.pigeonmui.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import top.zhuzhiwen.pigeonmui.dao.basedao.BaseRepository;
import top.zhuzhiwen.pigeonmui.model.Role;

/**
 * @author CoolWen
 * @version 2018-10-31 8:25
 */
public interface RoleRepository extends BaseRepository<Role, Integer>,  JpaSpecificationExecutor<Role> {
}
