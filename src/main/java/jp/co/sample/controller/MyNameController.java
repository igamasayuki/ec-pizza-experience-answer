package jp.co.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/myname")
public class MyNameController {
	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("name", "山田太郎");
		
		model.addAttribute("hobby", "プログラミング");
		
		model.addAttribute("school", "〇〇大学");
		
		return "myName";
	}
}

