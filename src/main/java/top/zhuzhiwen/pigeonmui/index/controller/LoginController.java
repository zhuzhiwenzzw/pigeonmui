package top.zhuzhiwen.pigeonmui.index.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import top.zhuzhiwen.pigeonmui.kit.ShiroKit;
import top.zhuzhiwen.pigeonmui.model.User;
import top.zhuzhiwen.pigeonmui.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class LoginController {

    protected static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;


    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public ModelAndView login(String username, String password, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        String emsg = null;
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            emsg = "用户名错误";
        } catch (IncorrectCredentialsException e) {
            emsg = "密码错误";
        } catch (ExcessiveAttemptsException e) {
            // TODO: handle exception
            emsg = "登录失败多次，账户锁定1小时!";
        } catch (AuthenticationException e) {
            emsg = e.getMessage();
            //    logger.info("登录信息MSG:" + msg);
        }
        model.addAttribute("emsg", emsg);
        logger.debug("登陆错误信息:" + emsg);
        if (ShiroKit.isEmpty(emsg)) {
            User user = userService.findByUsername(username);
            session.setAttribute("user", user);
            modelAndView.setViewName("redirect:/index/userpg");
//            return "redirect:/index/test4";
            return modelAndView;
        } else {
//            return "/login";
            modelAndView.setViewName("login");
            return modelAndView;
        }
    }

    @GetMapping(value = "/register")
    public ModelAndView register() {
        return new ModelAndView("reg");
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.POST)
    public ModelAndView register(@RequestParam("username") String username,
                                 @RequestParam("pass") String pass,
                                 @RequestParam("password") String password,
//                                 @RequestParam("stu_xuehao") String stu_xuehao,
//                                 @RequestParam("stu_isinschool") boolean stu_isinschool,
//                                 @RequestParam("class_id") String class_id,
                                 @RequestParam("tel") String tel,
                                 @RequestParam("nickname") String nickname) {
        ModelAndView model = new ModelAndView();
//        try {
        if (pass.equals(password)) {
//            Student student1 = studentService.findByUname(username);
            User user1 = userService.findByUsername(username);
            if (user1 != null) {
                model.addObject("msg1", "用户名已存在");
                model.setViewName("reg");
                return model;
            }
            Pattern p = Pattern.compile("^[1](([3|5|8][\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\d]{8}$");
            Matcher m = p.matcher(tel);
//            Pattern p1 = Pattern.compile("^$|^\\d{10}$");
//            Matcher m1 = p1.matcher(stu_xuehao);
//            if (!m.matches()) {
            if (!m.matches()) {
                model.addObject("telmsg", "请输入11位数字正确的电话号码");
                model.setViewName("reg");
                return model;
//            }
//            if (!m1.matches()) {
//                model.addObject("xuehaomsg", "请输入正确的学号");
//                model.setViewName("register");
//                return model;
//            if (username == "123"){
//                return model;
            } else {
                User user = new User();
                user.setUsername(username);
                user.setNickname(nickname);
                user.setPassword(password);
                user.setPhone(tel);
//                if (stu_isinschool && stu_xuehao.equals("")) {
//                    model.addObject("xuehaomsg", "在校学生须填写学号!");
//                    model.setViewName("register");
//                    return model;
//                }
//                if (!stu_xuehao.equals("")) {
//                    User user2 = studentService.findByStuXuehao(stu_xuehao);
//                    if (user2 != null) {
//                        model.addObject("xuehaomsg", "学号已经被使用!");
//                        model.setViewName("register");
//                        return model;
//                    }
//                    user.setStuXuehao(stu_xuehao);
//                }
                User u = userService.findByPhone(tel);
                if (u != null) {
                    model.addObject("telmsg", "手机号已被使用!");
                    model.setViewName("reg");
                    return model;
                }
//                logger.debug(">>>>>>>>>>>>>>>>>>>>>>>" + u);
//                logger.debug("Result {0}.", u);
//                user.setStuMobile(tel);
                user.setStatus(false);
                user.setAuthentication(false);
                user.setApply(false);
                user.setInformation("他很懒，什么也没有留下...");
                user.setImgSrc("原始头像.jpg");
                userService.add(user);
//                logger.debug(student.toString());
                logger.debug("注册用户信息message: {}", user.toString());
                model.addObject("msg2", "注册成功!!!,请登录。");
                model.setViewName("login");
            }
        } else {
            model.setViewName("reg");
            model.addObject("msg3", "两次输入密码不同");
        }
        return model;
    }

    @GetMapping(value = "/logout")
    public ModelAndView loginOut(HttpSession session, SessionStatus sessionStatus) {
        ModelAndView model = new ModelAndView();
        SecurityUtils.getSubject().logout();
//        session.invalidate();
//        sessionStatus.setComplete();
        model.setViewName("redirect:/index/userpg");
        return model;
    }
}

