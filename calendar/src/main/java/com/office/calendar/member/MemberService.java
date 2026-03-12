package com.office.calendar.member;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.office.calendar.member.jpa.MemberEntity;
import com.office.calendar.member.jpa.MemberRepository;
import com.office.calendar.member.mapper.MemberMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberService {

	public final static int USER_ID_ALREADY_EXIST    = 0;
    public final static int USER_SIGNUP_SUCCESS     = 1;
    public final static int USER_SIGNUP_FAIL        = -1;

    public final static int MODIFY_SUCCESS     = 1;
    public final static int MODIFY_FAIL        = 0;

    public final static int NEW_PASSWORD_CREATION_SUCCESS     = 1;
    public final static int NEW_PASSWORD_CREATION_FAIL        = 0;
	
	private final MemberDao memberDao;
	private final MemberMapper memberMapper;
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public MemberService(MemberDao memberDao, 
			MemberMapper memberMapper,
			MemberRepository memberRepository,
			PasswordEncoder passwordEncoder) {
		this.memberDao = memberDao;
		this.memberMapper = memberMapper;
		this.memberRepository = memberRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public int signupConfirm(MemberDto memberDto) {
		System.out.println("[MemberService] signupConfirm()");
		
//		boolean isMember = memberDao.isMember(memberDto.getId());
//		boolean isMember = memberMapper.isMember(memberDto.getId());
		boolean isMember = memberRepository.existsByMemId(memberDto.getId());
		
		if (!isMember) {
			memberDto.setPw(passwordEncoder.encode(memberDto.getPw()));
//			int result = memberDao.insertMember(memberDto);
			/*
			int result = memberMapper.insertMember(memberDto);
			
			if (result > 0) {
				return USER_SIGNUP_SUCCESS;
			} else {
				return USER_SIGNUP_FAIL;
			}
			*/
			
			MemberEntity memberEntity = MemberEntity.builder()
					.memId(memberDto.getId())
					.memPw(memberDto.getPw())
					.memMail(memberDto.getMail())
					.memPhone(memberDto.getPhone())
					.build();
			
			MemberEntity savedMemberEntity = memberRepository.save(memberEntity);
			if (savedMemberEntity != null && savedMemberEntity.getMemId() != null) {
				return USER_SIGNUP_SUCCESS;
			} else {
				return USER_SIGNUP_FAIL;
			}
			
		} else {
			return USER_ID_ALREADY_EXIST;
		}
		
	}

	public String signinConfirm(MemberDto memberDto) {
		System.out.println("[MemberService] signinConfirm()");
		
//		MemberDto dto = memberDao.selectMemberByID(memberDto.getId());	// MemberDto or null
		/*
		MemberDto dto = memberMapper.selectMemberByID(memberDto.getId());	// MemberDto or null
		if (dto != null && passwordEncoder.matches(memberDto.getPw(), dto.getPw())) {
			System.out.println("[MemberService] MEMBER SIGNIN SUCCESS");
			return dto.getId();
			
		} else {
			System.out.println("[MemberService] MEMBER SIGNIN FAIL");
			return null;
		}
		*/
		
		Optional<MemberEntity> optionalMember =
				memberRepository.findByMemId(memberDto.getId());
		
		if (optionalMember.isPresent() && 
				passwordEncoder.matches(memberDto.getPw(), optionalMember.get().getMemPw())) {
			log.info("MEMBER SIGNIN SUCCESS");
			return optionalMember.get().getMemId();
			
		} else {
			log.info("MEMBER SIGNIN FAIL");
			return null;
			
		}
		
	}

}
