package com.office.calendar.planner;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/planner")
public class PlannerController {

	private final PlannerService plannerService;
	
	@Autowired
	public PlannerController(PlannerService plannerService) {
		this.plannerService = plannerService;
	}
	
	@GetMapping({"", "/"})
	public String home() {
		log.info("home()");
		
		String nextPage = "planner/home";
		
		return nextPage;
		
	}
	
	// 일정 등록
	@PostMapping("/plan")
	public ResponseEntity<Map<String, Object>> writePlan(
			PlannerDto plannerDto, 
			Principal principal) {
		log.info("writePlan()");
		
		plannerDto.setOwner_id(principal.getName());
		Map<String, Object> resultMap = plannerService.writePlan(plannerDto);
		
		return ResponseEntity.ok(resultMap);	// 200 ok
		
	}
	
	// 일정들 가져오기
	@GetMapping("/plans")
	public ResponseEntity<Map<String, Object>> getPlans(
			@RequestParam  Map<String, Object> reqData,
			Principal principal) {
		log.info("getPlans()");
		
		String signInedMemberID = principal.getName();
		reqData.put("owner_id", signInedMemberID);
		
		Map<String, Object> resultMap = plannerService.getPlans(reqData);
		
		return ResponseEntity.ok(resultMap);	// 200 ok
		
	}
	
	
	
	
}
