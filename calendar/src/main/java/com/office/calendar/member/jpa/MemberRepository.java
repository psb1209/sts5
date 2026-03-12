package com.office.calendar.member.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {

	// 회원ID로 중복 체크
	public boolean existsByMemId(String memId);
	
	// 회원ID로 회원 조회
	public Optional<MemberEntity> findByMemId(String memId);
	
}
