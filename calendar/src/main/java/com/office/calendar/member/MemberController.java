package com.office.calendar.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {
	
//	@Autowired
//	MemberService memberService;
	
	private final MemberService memberService;
	
	@Autowired //생략가능
	public MemberController(MemberService memberService) {
		this.memberService=memberService;
	}
	
	//회원가입 양식
	@GetMapping("/member/signup")
	//@RequestMapping(value="member/signup, method=RequestMethod.GET")->get이면 메소드 생략가능(default값이 get)
	public String signup() {
		System.out.println("[MemberController] signup()");
		
		String nextPage="member/signup_form";
		
		return nextPage;
				
	}
	
	//회원 가입 확인
	@PostMapping("/member/signup_confirm")
	//@RequestMapping(value="member/signup_confirm, method=RequestMethod.POST")
	public String signupConfirm(MemberDto memberDto, Model model) {
		System.out.println("[MemberController] signupConfirm()");
		
		String nextPage="member/signup_result";
		
		int result = memberService.signupConfirm(memberDto);
		model.addAttribute("result",result);
		
		
		return nextPage;
	}
	
	//회원 로그인 양식
	@GetMapping("/member/signin")
	public String signin() {
		System.out.println("[MemberController] signin()");
		
		String nextPage="member/signin_form";
		
		return nextPage;

	}
	
	//회원 로그인 확인
	@PostMapping("/member/signin_confirm")
	public String  signinConfirm() {
		System.out.println("[MemberController] signinConfirm()");
		
		String nextPage="";
		
		return nextPage;

	}
	
	
	
	
	
	
	
	
	
	
}
