package top.zhuzhiwen.pigeonmui.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.zhuzhiwen.pigeonmui.model.Role;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleServiceImplTest {

    @Autowired
    private RoleService roleService;

    @Test
    public void add() {
        Role r = new Role();
        r.setName("球星");
        r.setSn("Star");
        roleService.add(r);
        r = new Role();
        r.setName("球迷");
        r.setSn("Fans");
        roleService.add(r);
        r = new Role();
        r.setName("球探");
        r.setSn("spy");
        roleService.add(r);
    }

    @Test
    public void testAddUserRole() {
        roleService.addUserRole(1, 2);
        roleService.addUserRole(2, 1);
        roleService.addUserRole(3, 2);
        roleService.addUserRole(3, 3);
    }

    @Test
    public void testAddRoleRes() {
        roleService.addRoleResource(1, 1);
        roleService.addRoleResource(2, 2);
        roleService.addRoleResource(2, 3);
        roleService.addRoleResource(2, 4);

        roleService.addRoleResource(3, 5);
        roleService.addRoleResource(3, 6);
        roleService.addRoleResource(3, 7);
    }
}