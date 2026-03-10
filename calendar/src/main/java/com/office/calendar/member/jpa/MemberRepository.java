package com.office.calendar.member.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<MemberEntity, Integer>{
	
	//회원 아이디로 중복 체크(존재여부 exist+by+엔티티 필드명)
	public boolean existsByMemId(String memId);
	
	
	//회원 아이디로 회원 조회
	public Optional<MemberEntity> findBymemId(String memId);
}
