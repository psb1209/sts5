package com.office.calendar.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
	
	public final static int USER_ID_ALREADY_EXIST    = 0;
    public final static int USER_SIGNUP_SUCCESS     = 1;
    public final static int USER_SIGNUP_FAIL        = -1;

    public final static int MODIFY_SUCCESS     = 1;
    public final static int MODIFY_FAIL        = 0;

    public final static int NEW_PASSWORD_CREATION_SUCCESS     = 1;
    public final static int NEW_PASSWORD_CREATION_FAIL        = 0;
	
	private final MemberDao memberDao;
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public MemberService(MemberDao memberDao, PasswordEncoder passwordEncoder) {
		this.memberDao=memberDao;
		this.passwordEncoder = passwordEncoder;
	}

	public int signupConfirm(MemberDto memberDto) {
		System.out.println("[MemberService] signupConfirm()");
		
		boolean isMember=memberDao.isMember(memberDto.getId());
		System.out.println("[MemberService] isMember==>" + isMember);
		
		
		if(!isMember) {
			String encodeedPW=passwordEncoder.encode(memberDto.getPw()); //비밀번호 암호화
			memberDto.setPw(encodeedPW);//암호화한 비밀번호 다시 저장
			
			int result=memberDao.insertMember(memberDto);
			
			if(result>0) {
				return USER_SIGNUP_SUCCESS;
			}else {
				return USER_SIGNUP_FAIL;
			}
			
		}else {
			return USER_ID_ALREADY_EXIST; //아이디 중복체크에 걸림
			
		}
		
		

	}

	public void signinConfirm(MemberDto memberDto) {
		System.out.println("[MemberService] signinConfirm()");
		
		
		MemberDto dto=memberDao.selectMemberByID(memberDto.getId()); 
		if(dto != null && passwordEncoder.matches(memberDto.getPw(), dto.getPw()) ) {
			 //평문 암호화된 비밀번호=비밀번호
			System.out.println("LOGIN_SUCCESS");
		}else {
			System.out.println("LOGIN_FAIL");
		}
		
	}
	
}
