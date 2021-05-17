package top.zhuzhiwen.pigeonmui.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import top.zhuzhiwen.pigeonmui.dao.basedao.BaseRepository;
import top.zhuzhiwen.pigeonmui.model.DTO.UserAttentionDto;
import top.zhuzhiwen.pigeonmui.model.Resource;
import top.zhuzhiwen.pigeonmui.model.Role;
import top.zhuzhiwen.pigeonmui.model.User;

import java.util.List;

/**
 * @author CoolWen
 * @version 2018-10-31 8:25
 */
public interface UserRepository extends BaseRepository<User, Integer>, JpaSpecificationExecutor<User> {
    //    public List<User> listUser();
//
    @Query("select u from User u where username=?1")
    public User findByUsername(String username);

    @Query("select res from User u,Resource res,UserRole ur,RoleResource rr where " +
            "u.id=ur.userId and ur.roleId=rr.roleId  and rr.resId=res.id and u.id=?1")
    public List<Resource> listAllResource(int uid);

    @Query("select u from User u,Role r,UserRole ur where u.id=ur.userId and r.id=ur.roleId and r.id=?1")
    public List<User> listByRole(int roleId);

    @Query("select r.sn from UserRole ur,Role r,User u where u.id=ur.userId and r.id=ur.roleId and u.id=?1")
    public List<String> listRoleSnByUser(int uid);

    @Query("select r from UserRole ur,Role r,User u where u.id=ur.userId and r.id=ur.roleId and u.id=?1")
    public List<Role> listUserRole(int uid);

    User findByPhone(String tel);

    @Query(value="select * from t_users order by rand() LIMIT 10",nativeQuery=true)
    List<User> randomList();

    @Query("select u from User u where u.apply=true")
    List<User> findByApply();

    @Query("select u from User u where u.authentication=true")
    List<User> findByPass();

//    @Query("select u from User u , Attention a where a.uid = ?1 and a.aid = u.id and a.display = true")
    @Query("select new top.zhuzhiwen.pigeonmui.model.DTO.UserAttentionDto(u.id,u.username,u.nickname,u.imgSrc,u.information,a.newinfo) from User u , Attention a where a.uid = ?1 and a.aid = u.id and a.display = true")
    List<UserAttentionDto> findByAttention(int id);

    @Query("select u from User u , Attention a where a.uid = ?1 and a.aid = u.id and a.isattent = true ")
    List<User> findByAllAttention(int id);

    @Query("select u from User u where u.nickname like %?1%")
    List<User> findByNickname(String nickname);

    @Query("select u from User u , Attention a where a.aid = ?1 and a.uid = u.id and a.isattent = true ")
    List<User> findByAllAttentionMe(int id);
}
