package com.office.calendar.member.jpa;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="USER_MEMBER")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberEntity {

	@Id
	@Column(name="NO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int memNo;
	
	@Column(name="ID", nullable = false, length = 20)
	private String memId;
	
	@Column(name="PW", nullable = false, length = 100)
	private String memPw;
	
	@Column(name="MAIL", nullable = false, length = 50)
	private String memMail;
	
	@Column(name="PHONE", nullable = false, length = 50)
	private String memPhone;
	
	@Column(name="AUTHORITY_NO")
	private byte memAuthorityNo;
	
	@Column(name="REG_DATE", updatable = false)
	private LocalDateTime memRegDate;
	
	@Column(name="MOD_DATE")
	private LocalDateTime memModDate;
	
	@PrePersist
	protected void onCreate() {
		this.memAuthorityNo = 1;
		this.memRegDate = LocalDateTime.now();
		this.memModDate = LocalDateTime.now();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.memModDate = LocalDateTime.now();
	}
	
}
