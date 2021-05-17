package top.zhuzhiwen.pigeonmui.index.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import top.zhuzhiwen.pigeonmui.model.Attention;
import top.zhuzhiwen.pigeonmui.model.DTO.UserPostDto;
import top.zhuzhiwen.pigeonmui.model.Messages;
import top.zhuzhiwen.pigeonmui.model.User;
import top.zhuzhiwen.pigeonmui.service.AttentionService;
import top.zhuzhiwen.pigeonmui.service.MessagesService;
import top.zhuzhiwen.pigeonmui.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @author 朱治汶
 * @version 1.0
 * @className NewsController
 * @description TODO
 * @date 2021/5/9 9:31
 **/

@Controller
@RequestMapping("/news")
public class NewsController {

    protected static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    AttentionService attentionService;

    @Autowired
    UserService userService;

    @Autowired
    MessagesService messagesService;

    //不在显示列表上显示
    @GetMapping("/close/{uid}")
    public ModelAndView cancelatten(HttpSession session, @PathVariable int uid) {
        ModelAndView modelAndView = new ModelAndView();
        // 从seesion拿到student的内容
        User u = (User) session.getAttribute("user");
        attentionService.updateDisplay(u.getId(),uid);
        modelAndView.setViewName("redirect:/index/news");
        return modelAndView;
    }

    //查找列表
    @GetMapping("/find")
    public ModelAndView find(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        List<User> users = userService.randomList();
        modelAndView.addObject("users", users);
        modelAndView.setViewName("index/find");
        return modelAndView;
    }

    //查找搜索
    @GetMapping("/search")
    @ResponseBody
    public List<User> search(String nickname,HttpSession session) {
        logger.debug("nickname:"+nickname);
        List<User> users = userService.findByNickname(nickname);
        logger.debug("users:"+users);
        return users;
    }

    //我的关注  列表
    @GetMapping("/attentionList")
    public ModelAndView attentionList(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        User user = (User) session.getAttribute("user");
        List<User> users = userService.findByAllAttention(user.getId());
        logger.debug("users:"+users);
        modelAndView.addObject("users",users);
        modelAndView.setViewName("index/newlooking");
        return modelAndView;
    }

    //关注我的  列表
    @GetMapping("/attentionme")
    public ModelAndView attentionme(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        User user = (User) session.getAttribute("user");
        List<User> users = userService.findByAllAttentionMe(user.getId());
        logger.debug("users:"+users);
        modelAndView.addObject("users",users);
        modelAndView.setViewName("index/attentionme");
        return modelAndView;
    }

    //进入聊天框
    @GetMapping("/attention/{uid}")
    public ModelAndView attention(@RequestParam(defaultValue = "0", required = true, value = "pageNum") Integer pageNum, HttpSession session, @PathVariable int uid) {

        ModelAndView modelAndView = new ModelAndView();
        User user = (User) session.getAttribute("user");
        modelAndView.addObject("user",user);

        Attention attention = attentionService.findByUA(user.getId(),uid);
        attention.setNewinfo(0);
        attentionService.add(attention);

//        attentionService.updateDisplayTrue(user.getId(),uid);
        //被关注人的信息
        User attentUser = userService.findById(uid);
        modelAndView.addObject("attentUser",attentUser);

        //最近的消息记录
        Pageable pageable = PageRequest.of(pageNum, 20);
        Page<Messages> messagesList = messagesService.findByUidAndAid(user.getId(),uid,pageable);
        modelAndView.addObject("messagesList",messagesList);

        modelAndView.setViewName("index/imchat");
        return modelAndView;
    }


    //进入聊天框
    @ResponseBody
    @GetMapping("/attentionjs/{uid}")
    public Page<Messages> attentionjs(@RequestParam(defaultValue = "0", required = true, value = "pageNum") Integer pageNum, HttpSession session, @PathVariable int uid) {
//        ModelAndView modelAndView = new ModelAndView();
        logger.debug("page:"+pageNum);
        User user = (User) session.getAttribute("user");
//        modelAndView.addObject("user",user);
//
//        attentionService.updateDisplayTrue(user.getId(),uid);
//        //被关注人的信息
//        User attentUser = userService.findById(uid);
//        modelAndView.addObject("attentUser",attentUser);

        //最近的消息记录
        Pageable pageable = PageRequest.of(pageNum, 20);
        Page<Messages> messagesList = messagesService.findByUidAndAid(user.getId(),uid,pageable);
        return messagesList;
    }


    @PostMapping("/messagesadd/{uid}")
    public ModelAndView messagesadd(HttpSession session, @PathVariable int uid,Messages messages ) {

        User u = (User) session.getAttribute("user");
        attentionService.updateDisplayTrue(u.getId(),uid);

        Attention attention = attentionService.findByUA(uid,u.getId());
        attention.setDisplay(true);
        attention.setNewinfo(attention.getNewinfo()+1);
        attentionService.add(attention);

        logger.debug("进入attentionadd");
        ModelAndView modelAndView = new ModelAndView();
        User user = (User) session.getAttribute("user");

        messages.setUid(user.getId());
        messages.setAid(uid);
        messages.setSendTime(new Date());
        messagesService.add(messages);

        modelAndView.setViewName("redirect:/news/attention/" + uid);
        return modelAndView;
    }

}
