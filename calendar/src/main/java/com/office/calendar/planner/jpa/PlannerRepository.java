package com.office.calendar.planner.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlannerRepository extends JpaRepository<PlannerEntity, Integer> {

	List<PlannerEntity> findByPlanYearAndPlanMonthAndPlanOwnerId(int planYear, int planMonth, String planOwnerId);

    PlannerEntity findByPlanNo(int planNo);

    int deleteByPlanNo(int planNo);
    
}
