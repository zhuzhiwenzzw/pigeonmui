package top.zhuzhiwen.pigeonmui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HelloController {

	@GetMapping("/")
	public String home() {
		return "redirect:/index/index";
	}


	@RequestMapping("/405")
	public String bao(){
		return "405";
	}
}
