package top.zhuzhiwen.pigeonmui.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;
import top.zhuzhiwen.pigeonmui.dao.basedao.BaseRepository;
import top.zhuzhiwen.pigeonmui.model.UserRole;

/**
 * @author CoolWen
 * @version 2018-10-31 8:25
 */

public interface UserRoleRepository extends BaseRepository<UserRole, Integer>, JpaSpecificationExecutor<UserRole> {

    UserRole findByUserIdAndRoleId(int uid, int roleId);

    //    @Modifying
//    @Query("delete from UserRole where userId=?1")
    @Transactional
    public void deleteByUserId(int uid);
    //    @Modifying
}
