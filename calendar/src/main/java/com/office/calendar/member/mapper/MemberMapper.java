package com.office.calendar.member.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.jdbc.core.RowMapper;

import com.office.calendar.member.MemberDto;


@Mapper //ioc컨테이너에 담는 역할
public interface MemberMapper {
	
//	@Select("SELECT COUNT(*) FROM USER_MEMBER WHERE id= #{id}")
	public boolean isMember(String id);
		
//	@Insert("insert into USER_MEMBER(id,pw,mail,phone) values(#{id},#{pw},#{mail},#{phone})")
	public int insertMember(MemberDto memberDto);
	
//	@Select("select from  USER_MEMBER where id=#{id}")
	public MemberDto selectMemberByID(String id);
	    

	   
	
	
	
}
