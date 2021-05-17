package top.zhuzhiwen.pigeonmui.index.controller;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import top.zhuzhiwen.pigeonmui.kit.ShiroKit;
import top.zhuzhiwen.pigeonmui.model.User;
import top.zhuzhiwen.pigeonmui.service.UserService;
import top.zhuzhiwen.pigeonmui.utils.FileUploadUtil;
import top.zhuzhiwen.pigeonmui.utils.GetServerRealPathUnit;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author 朱治汶
 * @version 1.0
 * @className EnterpriseInfoController
 * @description TODO
 * @date 2021/5/7 22:56
 **/

@Controller
@RequestMapping("/index")
public class EnterpriseInfoController {

    protected static final Logger logger = LoggerFactory.getLogger(EnterpriseInfoController.class);

    @Autowired
    UserService userService;


    @PostMapping("/addEnterpriseInfo")
    public ModelAndView addEnterpriseInfo(@RequestParam("attachs") MultipartFile[] attachs,
                                          @RequestParam("attachimg") MultipartFile[] attachimg,
                                          User user,
                                          HttpSession session){
        logger.debug("enterpriseInfo");
        logger.debug("attachs："+attachs);
        // 从seesion拿到student的内容
        User user2 = (User) session.getAttribute("user");

        ModelAndView modelAndView = new ModelAndView();
        if (ShiroKit.isEmpty(user2)) {
            return new ModelAndView("login");
        }

        User user1 = userService.findById(user2.getId());

        //储存图片，并将图片路径储存到数据库
        String realpath = GetServerRealPathUnit.getPath("static/upload/");
//       logger.debug("realPath:" + realpath);
        //图片路径数组,字符串类型,以 , 为间隔
        String imgs = "";
        int jishu = 0;
        for (MultipartFile attach : attachs) {
            if (attach.isEmpty()) {
                if (!imgs.equals("")){
                    imgs = imgs.substring(0,imgs.length() - 1);
                }
                continue;
            }
            jishu += 1;
            //图片验证重命名
            String picName = FileUploadUtil.picRename(attach.getContentType());
            String path = realpath + "/" + picName;
//            logger.debug(path);
            File f = new File(path);
//            user.setImg(picName);
            imgs  += picName + "|";
//            posts.setImgSrc(picName);
            try {
                FileUtils.copyInputStreamToFile(attach.getInputStream(), f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        logger.debug("imgs："+imgs);

        for (MultipartFile attach : attachimg) {
            if (attach.isEmpty()) {
//                user1.setBgImg(userService.findById(user1.getId()).getBgImg());
                continue;
            }
            //图片验证重命名
            String picName = FileUploadUtil.picRename(attach.getContentType());
            String path = realpath + "/" + picName;
//            logger.debug(path);
            File f = new File(path);
//            user.setImg(picName);
            user1.setBgImg(picName);
            try {
                FileUtils.copyInputStreamToFile(attach.getInputStream(), f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        logger.debug("imgsrc:"+imgs);
        if (jishu!=0){
            user1.setLicenseImg(imgs);
        }
//        else {
//            user1.setLicenseImg(userService.findById(id).getLicenseImg());
//        }

        user1.setIntroduction(user.getIntroduction());
        user1.setServiceTel(user.getServiceTel());
        logger.debug("getAuthentication:"+user1.getAuthentication());
        if (!user1.getAuthentication()){
            logger.debug("进入判断！！");
            user1.setApply(true);
            user1.setAuthentication(false);
        }
        userService.update(user1);
        session.setAttribute("user",user1);

        modelAndView.setViewName("redirect:/index/userpg");
        return modelAndView;
    }


    //审核列表页
    @GetMapping("/renzhengList")
    public ModelAndView renzhengList(){
        ModelAndView modelAndView = new ModelAndView();
        List<User> usersApply = userService.findByApply();
        modelAndView.addObject("userApplys",usersApply);

        List<User> usersPass = userService.findByPass();
        modelAndView.addObject("userPasss",usersPass);

        modelAndView.setViewName("index/renzhengList");
        return modelAndView;
    }

    //用户详细信息页
    @GetMapping("/renzhengDetail/{uid}")
    public ModelAndView renzhenguser(@PathVariable int uid){
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findById(uid);
        logger.debug("user:"+user);
        modelAndView.addObject("user",user);
        modelAndView.setViewName("index/renzhengDetail");
        return modelAndView;
    }


    //认证成功
    @GetMapping("/renzhengTrue/{uid}")
    public ModelAndView renzhengTrue(@PathVariable int uid){
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findById(uid);
        user.setAuthentication(true);
        user.setApply(false);
        userService.update(user);
        modelAndView.setViewName("redirect:/index/renzhengDetail/"+uid);
        return modelAndView;
    }

    //认证失败
    @GetMapping("/renzhengFail/{uid}")
    public ModelAndView renzhengFail(@PathVariable int uid){
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findById(uid);
        user.setAuthentication(false);
        user.setApply(false);
        userService.update(user);
        modelAndView.setViewName("redirect:/index/renzhengList");
        return modelAndView;
    }
}
