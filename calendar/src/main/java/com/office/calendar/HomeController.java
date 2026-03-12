package com.office.calendar;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {

	@GetMapping({"", "/"})
	public String home() {
//		System.out.println("[HomeController] home()");
		log.info("home()");
		
		return "home";
		
	}
	
}
