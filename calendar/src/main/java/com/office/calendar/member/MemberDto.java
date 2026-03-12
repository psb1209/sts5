package com.office.calendar.member;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto implements Serializable {		// Serializable

	private static final long serialVersionUID = 1L;	// 직렬화 버전 ID
	
	private int no;				// 사용자 고유 번호
	private String id;			// 사용자 아이디
	private String pw;			// 사용자 비밀번호
	private String mail;		// 사용자 메일
	private String phone;		// 사용자 연락처
	private int authority_no;	// 사용자 권한
	private String reg_date;	// 사용자 정보 등록일
	private String mod_date;	// 사용자 정보 수정일
	
}
