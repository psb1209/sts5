package com.office.calendar;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j //로그 어노테이션
public class HomeController {
	
	@GetMapping({"/",""})
	public String home() {
		//System.out.println("[HomeController] home()");
		
		log.info("home()");
		
		return "home";
	}

}
