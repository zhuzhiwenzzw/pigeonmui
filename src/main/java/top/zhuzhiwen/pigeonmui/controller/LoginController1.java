package top.zhuzhiwen.pigeonmui.controller;

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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import top.zhuzhiwen.pigeonmui.kit.ShiroKit;
import top.zhuzhiwen.pigeonmui.model.User;
import top.zhuzhiwen.pigeonmui.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController1 {

    protected static final Logger logger = LoggerFactory.getLogger(LoginController1.class);

    @Autowired
    private UserService userService;


    @RequestMapping(value = {"/login1"}, method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = {"/login1"}, method = RequestMethod.POST)
    public String login(String username, String password, Model model) {
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
            return "redirect:/admin/res/list";
        } else {
            return "login";
        }
    }

    @GetMapping(value = "/logout1")
    public ModelAndView loginOut(HttpSession session, SessionStatus sessionStatus) {
        ModelAndView model = new ModelAndView();
        SecurityUtils.getSubject().logout();
//        session.invalidate();
//        sessionStatus.setComplete();
        model.setViewName("redirect:/login1");
        return model;
    }
}

