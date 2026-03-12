package com.office.calendar.member.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.office.calendar.member.MemberDto;

@Mapper
public interface MemberMapper {

//	@Select("SELECT COUNT(*) FROM user_member WHERE id = #{id}")
	public boolean isMember(String id); 

//	@Insert("INSERT INTO USER_MEMBER(id, pw, mail, phone) VALUES(#{id}, #{pw}, #{mail}, #{phone})")
	public int insertMember(MemberDto memberDto); 

//	@Select("SELECT * FROM USER_MEMBER WHERE id = #{id}")
	public MemberDto selectMemberByID(String id); 
	
}
