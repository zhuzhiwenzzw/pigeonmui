package top.zhuzhiwen.pigeonmui.service;

import top.zhuzhiwen.pigeonmui.model.DTO.UserAttentionDto;
import top.zhuzhiwen.pigeonmui.model.Resource;
import top.zhuzhiwen.pigeonmui.model.Role;
import top.zhuzhiwen.pigeonmui.model.User;

import java.util.List;

/**
 * @author CoolWen
 * @version 2018-10-31 10:40
 */

public interface UserService {

    public User add(User user);

    public User add(User user, List<Integer> rids);

    public void delete(int id);



    public User update(User user, List<Integer> rids);

    public User update(User user);

    public User load(int id);

    public User findByUsername(String username);

    public User login(String username, String password);

    public List<User> list();

    public List<User> listByRole(int roleId);

    public List<Resource> listAllResource(int uid);

    public List<String> listRoleSnByUser(int uid);

    public List<Role> listUserRole(int uid);

    User findByPhone(String tel);

    List<User> randomList();

    User findById(int id);

    List<User> findByApply();

    List<User> findByPass();

    List<UserAttentionDto> findByAttention(int id);

    List<User> findByAllAttention(int id);

    List<User> findByNickname(String nickname);

    List<User> findByAllAttentionMe(int id);
}
