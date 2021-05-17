package top.zhuzhiwen.pigeonmui.index.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import top.zhuzhiwen.pigeonmui.kit.ShiroKit;
import top.zhuzhiwen.pigeonmui.model.Attention;
import top.zhuzhiwen.pigeonmui.model.DTO.UserAttentionDto;
import top.zhuzhiwen.pigeonmui.model.DTO.UserPostDto;
import top.zhuzhiwen.pigeonmui.model.User;
import top.zhuzhiwen.pigeonmui.service.CommentService;
import top.zhuzhiwen.pigeonmui.service.PostsService;
import top.zhuzhiwen.pigeonmui.service.UserService;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/index/")
public class IndexController {

    protected static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @GetMapping("index")
    public String index(){
        return "redirect:/index/main";
    }

    @Value("${huanzi.buttom.switch.mode}")
    private String switchMode;

    @Value("${huanzi.buttom.list}")
    private String buttomList;

    @Autowired
    PostsService postsService;
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;

    //跳转主页面
    @ResponseBody
    @GetMapping("main")
    public ModelAndView main() {
        ModelAndView modelAndView = new ModelAndView("index/main");
//        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("switchMode",switchMode);
        ArrayList arrayList = new ArrayList<>();
        try {
            //从配置文件读取，有编码问题，我这里先不管了，直接赋值
            logger.debug("buttomList"+buttomList);
            buttomList = "[{\"text\":\"首页\",\"icon\":\"mui-icon-phone\",\"url\":\"/index/shouye\"},{\"text\":\"发现\",\"icon\":\"mui-icon-email\",\"url\":\"/index/new\"},{\"text\":\"消息\",\"icon\":\"mui-icon-chatbubble\",\"url\":\"/index/news\"},{\"text\":\"我的\",\"icon\":\"mui-icon-weixin\",\"url\":\"/index/userpg\"}]";
            arrayList = new ObjectMapper().readValue(buttomList, ArrayList.class);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        modelAndView.addObject("buttomList", arrayList);
        return modelAndView;
    }

    @GetMapping("shouye")
    public ModelAndView muiDialog(){
        List<User> users = userService.randomList();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("users", users);
        modelAndView.setViewName("index/shouye");
        return modelAndView;
    }

    @GetMapping("new")
    public ModelAndView newgz(@RequestParam(defaultValue = "0", required = true, value = "pageNum") Integer pageNum,
                              HttpSession session){
        ModelAndView modelAndView = new ModelAndView();

        User user = (User) session.getAttribute("user");
        if (ShiroKit.isEmpty(user)) {
            return new ModelAndView("login");
        }
        Pageable pageable = PageRequest.of(pageNum, 10);
        Page<UserPostDto> page = postsService.findAllorderby(pageable);
        for (UserPostDto userPostDto:page) {
            int num = commentService.countByPid(userPostDto.getPid());
            userPostDto.setCommentNum(num);
        }
        modelAndView.addObject("page",page);
        logger.debug("user:"+user.getId());

        Page<UserPostDto> page1 = postsService.findAllorderbyAttend(user.getId(),pageable);
        for (UserPostDto userPostDto:page1) {
            int num = commentService.countByPid(userPostDto.getPid());
            userPostDto.setCommentNum(num);
        }
        logger.debug("page1");
        modelAndView.addObject("page1",page1);
        modelAndView.setViewName("index/new");
        return modelAndView;
    }

    @GetMapping("news")
    public ModelAndView news(HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        User user = (User) session.getAttribute("user");
        if (ShiroKit.isEmpty(user)) {
            return new ModelAndView("login");
        }
        List<UserAttentionDto> users = userService.findByAttention(user.getId());
        modelAndView.addObject("users",users);
        logger.debug("news,users:"+users);
        modelAndView.setViewName("index/news");
        return modelAndView;
    }

    @GetMapping("userpg")
    public ModelAndView userpg( HttpSession session){
        // 从seesion拿到student的内容
        User user = (User) session.getAttribute("user");
        ModelAndView modelAndView = new ModelAndView();
        if (ShiroKit.isEmpty(user)) {
            return new ModelAndView("login");
        }
        User user1 = userService.findById(user.getId());
        modelAndView.addObject("user",user1);

        int likeHe = postsService.likeHe(user.getId());
        modelAndView.addObject("likeHe",likeHe);

        List<User> users = userService.findByAllAttentionMe(user.getId());
        modelAndView.addObject("fansNum",users.size());

        modelAndView.setViewName("index/user");
        logger.debug("进入页面:"+user);
        return modelAndView;
    }
}
