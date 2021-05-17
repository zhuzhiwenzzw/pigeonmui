package top.zhuzhiwen.pigeonmui.index.controller;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import top.zhuzhiwen.pigeonmui.kit.ShiroKit;
import top.zhuzhiwen.pigeonmui.model.Attention;
import top.zhuzhiwen.pigeonmui.model.DTO.UserPostDto;
import top.zhuzhiwen.pigeonmui.model.Messages;
import top.zhuzhiwen.pigeonmui.model.User;
import top.zhuzhiwen.pigeonmui.service.*;
import top.zhuzhiwen.pigeonmui.utils.FileUploadUtil;
import top.zhuzhiwen.pigeonmui.utils.GetServerRealPathUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author 朱治汶
 * @version 1.0
 * @className UserInfoController
 * @description TODO
 * @date 2021/5/5 22:42
 **/
@Controller
@RequestMapping("/userInfo")
public class UserInfoController {

    protected static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AttentionService attentionService;

    @Autowired
    PostsService postsService;

    @Autowired
    MessagesService messagesService;

    @Autowired
    CommentService commentService;

//    @RequestMapping("/random")
//    public ModelAndView randomList() {
//        List<User> users = userService.randomList();
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("users", users);
//        modelAndView.setViewName("index/main");
//        return modelAndView;
//    }

    //修改个人信息
    @GetMapping("/change")
    public ModelAndView userInfo() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index/main");
        return modelAndView;
    }

    @GetMapping("new")
    @ResponseBody
    public Page newgz(@RequestParam(defaultValue = "0", required = true, value = "pageNum") Integer pageNum,
                              HttpSession session){
        logger.debug("进入js:"+pageNum);
        Pageable pageable = PageRequest.of(pageNum, 10);
        Page<UserPostDto> page = postsService.findAllorderby(pageable);
        for (UserPostDto userPostDto:page) {
            int num = commentService.countByPid(userPostDto.getPid());
            userPostDto.setCommentNum(num);
            logger.debug("num>>>>>>:"+num);
        }
        return page;
    }

    @GetMapping("newn")
    @ResponseBody
    public Page<UserPostDto> newgz1(@RequestParam(defaultValue = "0", required = true, value = "pageNum") Integer pageNum,
                                    HttpSession session){
        logger.debug("进入newn:"+pageNum);
        User user = (User) session.getAttribute("user");
        Pageable pageable = PageRequest.of(pageNum, 10);
        Page<UserPostDto> page1 = postsService.findAllorderbyAttend(user.getId(),pageable);
        for (UserPostDto userPostDto:page1) {
            int num = commentService.countByPid(userPostDto.getPid());
            userPostDto.setCommentNum(num);
            logger.debug("num>>>>>>:"+num);
        }
        return page1;
    }


    //查看用户信息
    @GetMapping("/ton/{jiekou}/{uid}")
    public ModelAndView userInfo(HttpSession session,@PathVariable int uid,@PathVariable String jiekou) {
        ModelAndView modelAndView = new ModelAndView();
        // 从seesion拿到student的内容
        User u = (User) session.getAttribute("user");
//        modelAndView.addObject("u",u);

        Boolean guanzhu = false;
        try {
            Attention attention = attentionService.findByUA(u.getId(),uid);
            logger.debug("attention"+attention);
            if (attention != null && attention.getIsattent() == true){
                guanzhu = true;
            }
        }catch (Exception e){}

        modelAndView.addObject("guanzhu",guanzhu);

        Boolean issame = false;
        if (u.getId() == uid){
            issame = true;
        }
        modelAndView.addObject("issame",issame);

        User user = userService.findById(uid);
        modelAndView.addObject("user",user);

        String yemian = "";
        if (jiekou.equals("new")){
            yemian = "/index/new";
        }
        if (jiekou.equals("shouye")){
            yemian = "/index/shouye";
        }
        if (jiekou.equals("attent")){
            yemian = "/news/attention/"+uid;
        }
        if (jiekou.equals("search")){
            yemian = "/news/find";
        }
        if (jiekou.equals("attentionme")){
            yemian = "/news/attentionme";
        }
        modelAndView.addObject("yemian",yemian);
        modelAndView.addObject("jiekou",jiekou);
        logger.debug(">>>>>>>>>:"+yemian+">>>:"+jiekou);

        modelAndView.setViewName("index/business");
        return modelAndView;
    }

//    //查看用户信息
//    @GetMapping("/shouye/{uid}")
//    public ModelAndView userInfoShouye(HttpSession session,@PathVariable int uid) {
//        logger.debug("shouye");
//        ModelAndView modelAndView = new ModelAndView();
//        // 从seesion拿到student的内容
//        User u = (User) session.getAttribute("user");
////        modelAndView.addObject("u",u);
//        if (ShiroKit.isEmpty(u)) {
//            return new ModelAndView("login");
//        }
//
//        Boolean guanzhu = false;
//        try {
//            Attention attention = attentionService.findByUA(u.getId(),uid);
//            logger.debug("attention"+attention);
//            if (attention != null && attention.getIsattent() == true){
//                guanzhu = true;
//            }
//        }catch (Exception e){}
//
//        modelAndView.addObject("guanzhu",guanzhu);
//
//        Boolean issame = false;
//        if (u.getId() == uid){
//            issame = true;
//        }
//        modelAndView.addObject("issame",issame);
//
//        User user = userService.findById(uid);
//        modelAndView.addObject("user",user);
//
//        modelAndView.setViewName("index/business2");
//        return modelAndView;
//    }

    //关注
    @GetMapping("/atten/{jiekou}/{uid}")
    public ModelAndView atten(HttpSession session,@PathVariable int uid,@PathVariable String jiekou){
        logger.debug("aaaaaaaaaaaa");
        ModelAndView modelAndView = new ModelAndView();
        // 从seesion拿到student的内容
        User u = (User) session.getAttribute("user");
        logger.debug("user:"+u);

//        Messages messages = new Messages();
//        messages.setUid(u.getId());
//        messages.setAid(uid);
//        messages.setSendTime(new Date());
//        messages.setPostMessages("我是"+u.getNickname()+"。");
//        messagesService.add(messages);

        try {
            Attention a = attentionService.findByUA(u.getId(),uid);
            if (a != null){
                a.setIsattent(true);
                a.setDisplay(true);
                attentionService.add(a);
            }else {
                Attention attention = new Attention();
                attention.setUid(u.getId());
                attention.setAid(uid);
                attention.setDisplay(true);
                attention.setIsattent(true);
                attention.setNewinfo(0);
                attentionService.add(attention);
                logger.debug("att:"+attention);
            }
        }catch (Exception e){}

        try {
            Attention a1 = attentionService.findByUA(uid,u.getId());
            if (a1 == null){
                Attention attention1 = new Attention();
                attention1.setUid(uid);
                attention1.setAid(u.getId());
                attention1.setDisplay(false);
                attention1.setIsattent(false);
                attention1.setNewinfo(0);
                attentionService.add(attention1);
            }
        }catch (Exception e){}


        modelAndView.setViewName("redirect:/userInfo/ton/"+jiekou+"/"+uid);
        return modelAndView;
    }
    //取消关注
    @GetMapping("/cancelatten/{jiekou}/{uid}")
    public ModelAndView cancelatten(HttpSession session,@PathVariable int uid,@PathVariable String jiekou) {
        ModelAndView modelAndView = new ModelAndView();
        // 从seesion拿到student的内容
        User u = (User) session.getAttribute("user");
        logger.debug("uid:"+uid+">>>u:"+u.getId());
//        attentionService.delete(u.getId(),uid);
        Attention attention = attentionService.findByUA(u.getId(),uid);
        attention.setIsattent(false);
        attentionService.add(attention);
        modelAndView.setViewName("redirect:/userInfo/ton/"+jiekou+"/"+uid);
        return modelAndView;
    }

    //修改个人信息
    @PostMapping("/change")
    public ModelAndView changeUserInfo(HttpSession session,User user1,
                                       @RequestParam("attachs") MultipartFile[] attachs) {
        logger.debug("进入了提交个人修改");
        ModelAndView modelAndView = new ModelAndView();
        User user = (User) session.getAttribute("user");
        user.setPhone(user1.getPhone());
        user.setNickname(user1.getNickname());
        user.setInformation(user1.getInformation());
        user.setQqNumber(user1.getQqNumber());
        user.setMailnum(user1.getMailnum());

        //储存图片，并将图片路径储存到数据库
        String realpath = GetServerRealPathUnit.getPath("static/upload/");
//       logger.debug("realPath:" + realpath);
        for (MultipartFile attach : attachs) {
            if (attach.isEmpty()) {
                user.setImgSrc(userService.findById(user.getId()).getImgSrc());
                continue;
            }
            //图片验证重命名
            String picName = FileUploadUtil.picRename(attach.getContentType());
            String path = realpath + "/" + picName;
            logger.debug(path);
            File f = new File(path);
//            user.setImg(picName);
            user.setImgSrc(picName);
            try {
                FileUtils.copyInputStreamToFile(attach.getInputStream(), f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //添加数据
        logger.debug("保存到数据库");
        logger.debug("user:"+user);
        userService.update(user);
        logger.debug("user:"+user);
        session.setAttribute("user", user);
        //返回页面
        modelAndView.setViewName("redirect:/index/userpg");
        return modelAndView;
    }


//    @PostMapping(value = "/{id}/update")
//    public String update(@PathVariable int id, Effect effect, @RequestParam("attachs") MultipartFile[] attachs, HttpServletRequest req){
//        effect.setId(id);
//        effect.setDic_datetime(new Date());
//        //储存图片，并将图片路径储存到数据库
//        String realpath = GetServerRealPathUnit.getPath("static/upload/");
////       logger.debug("realPath:" + realpath);
//        for (MultipartFile attach : attachs) {
//            if (attach.isEmpty()) {
//                effect.setEffect_imgurl(effectService.findById(id).getEffect_imgurl());
//                continue;
//            }
//            //图片验证重命名
//            String picName = FileUploadUtil.picRename(attach.getContentType());
//            String path = realpath + "/" + picName;
////            logger.debug(path);
//            File f = new File(path);
////            user.setImg(picName);
//            effect.setEffect_imgurl(picName);
//            try {
//                FileUtils.copyInputStreamToFile(attach.getInputStream(), f);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        //将信息储存到数据库
//        effectService.add(effect);
//        logger.debug(effectService.findById(id).toString());
//        logger.debug("修改成功");
//        return "redirect:/learning/list";
//    }
}
