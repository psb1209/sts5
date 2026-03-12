package com.office.calendar.planner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.office.calendar.member.jpa.MemberRepository;
import com.office.calendar.planner.jpa.PlannerEntity;
import com.office.calendar.planner.jpa.PlannerRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PlannerService {

	private final PlannerRepository plannerRepository;
	private final MemberRepository memberRepository;
	
	public PlannerService(
			PlannerRepository plannerRepository,
			MemberRepository memberRepository) {
		this.plannerRepository = plannerRepository;
		this.memberRepository = memberRepository;
	}

	// 일정 등록 하기
	public Map<String, Object> writePlan(PlannerDto plannerDto) {
		log.info("writePlan()");
		
		Map<String, Object> resultMap = new HashMap<>();
		
		int result = 0;
		PlannerEntity savedPlannerEntity  = 
				plannerRepository.save(plannerDto.toEntity());
		if (savedPlannerEntity != null) {
			log.info("INSERT INTO PLAN SUCCESS!!");
			result = 1;
			
		} else {
			log.info("INSERT INTO PLAN FAIl!!");
			
		}
		
		resultMap.put("result", result);
		
		return resultMap;
		
	}

	// 일정들 가져오기
	public Map<String, Object> getPlans(Map<String, Object> reqData) {
		log.info("getPlans()");
		
		Map<String, Object> resultMap = new HashMap<>();
		
		List<PlannerEntity> plannerEntities =
				plannerRepository.findByPlanYearAndPlanMonthAndPlanOwnerId(
									Integer.valueOf(String.valueOf(reqData.get("year"))),
									Integer.valueOf(String.valueOf(reqData.get("month"))),
									String.valueOf(reqData.get("owner_id"))
				);
		
		
		List<PlannerDto> plannerDtos = plannerEntities.stream()
				.map(PlannerEntity::toDto)
				.collect(Collectors.toList());
		
		
		resultMap.put("plans", plannerDtos);
		
		return resultMap;
		
	}
	
}
