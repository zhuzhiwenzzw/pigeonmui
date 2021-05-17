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
import top.zhuzhiwen.pigeonmui.model.Comment;
import top.zhuzhiwen.pigeonmui.model.DTO.UserCommentDto;
import top.zhuzhiwen.pigeonmui.model.DTO.UserPostDto;
import top.zhuzhiwen.pigeonmui.model.Posts;
import top.zhuzhiwen.pigeonmui.model.User;
import top.zhuzhiwen.pigeonmui.service.CommentService;
import top.zhuzhiwen.pigeonmui.service.PostsService;
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
 * @className PostsController
 * @description TODO
 * @date 2021/5/5 15:53
 **/
@Controller
@RequestMapping("/index")
public class PostsController {
    protected static final Logger logger = LoggerFactory.getLogger(PostsController.class);

    @Autowired
    PostsService postsService;

    @Autowired
    CommentService commentService;

//    @GetMapping("/postList")
//    public ModelAndView postList(@RequestParam(defaultValue = "0", required = true, value = "pageNum") Integer pageNum,
//                                 HttpSession session){
//        Pageable pageable = PageRequest.of(pageNum, 12);
//        Page<UserPostDto> page = postsService.findAllorderby(pageable);
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("page",page);
//        modelAndView.setViewName("index/new");
//        return modelAndView;
//    }

    @PostMapping("/addPost")
    public ModelAndView addPost(@RequestParam("attachs") MultipartFile[] attachs,
                                Posts posts,
                                HttpSession session){
        logger.debug("进入addpost");
        logger.debug("attachs："+attachs);
        // 从seesion拿到student的内容
        User user = (User) session.getAttribute("user");
        ModelAndView modelAndView = new ModelAndView();
        if (ShiroKit.isEmpty(user)) {
            return new ModelAndView("login");
        }
        //储存图片，并将图片路径储存到数据库
        String realpath = GetServerRealPathUnit.getPath("static/upload/");
//       logger.debug("realPath:" + realpath);
        //图片路径数组,字符串类型,以 , 为间隔
        String imgs = "";
        for (MultipartFile attach : attachs) {
            if (attach.isEmpty()) {
                if (!imgs.equals("")){
                    imgs = imgs.substring(0,imgs.length() - 1);
                }
                continue;
            }
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
        logger.debug("imgsrc:"+imgs);
        posts.setTime(new Date());
        posts.setImgSrc(imgs);
        posts.setUid(user.getId());
        postsService.add(posts);

        modelAndView.setViewName("redirect:/index/new");
        return modelAndView;
    }


    //进入帖子详情页
    @GetMapping("/detailPost/{pid}")
    public ModelAndView detailPost(@RequestParam(defaultValue = "0", required = true, value = "pageNum") Integer pageNum,@PathVariable int pid,HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        User user = (User) session.getAttribute("user");

        UserPostDto userPostDto = postsService.findbyPid(pid);
        logger.debug("post:"+userPostDto);
        modelAndView.addObject("post",userPostDto);

        Pageable pageable = PageRequest.of(pageNum, 15);
        Page<UserCommentDto> commentPage = commentService.findByPid(pid,pageable);
        logger.debug("dto:"+commentPage);
        modelAndView.addObject("commentPage",commentPage);
        modelAndView.setViewName("index/comment");
        return modelAndView;
    }



    //添加评论
    @PostMapping("/addComment")
    public ModelAndView addComment(Comment comment,HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        User user = (User) session.getAttribute("user");
        comment.setUid(user.getId());
        comment.setTime(new Date());
        commentService.add(comment);
        modelAndView.setViewName("redirect:/index/detailPost/" + comment.getPid());
        return modelAndView;
    }

    //添加评论
    @PostMapping("/addComment/{pid}")
    public ModelAndView addComment1(Comment comment,HttpSession session,@PathVariable int pid){
        ModelAndView modelAndView = new ModelAndView();
        User user = (User) session.getAttribute("user");
        comment.setUid(user.getId());
        comment.setTime(new Date());
        comment.setPid(pid);
        commentService.add(comment);
        logger.debug("sdafs");
        modelAndView.setViewName("redirect:/index/detailPost/" + comment.getPid());
        logger.debug("sdafs");
        return modelAndView;
    }


    //点赞
    @GetMapping("/addgiveLike/{pid}")
    @ResponseBody
    public int addgiveLike(@PathVariable int pid,HttpSession session){
        Posts posts = postsService.findbypid(pid);
        posts.setGiveLike(posts.getGiveLike()+1);
        postsService.add(posts);
        return posts.getGiveLike();
    }
}
