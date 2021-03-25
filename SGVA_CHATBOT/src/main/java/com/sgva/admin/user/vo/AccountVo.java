package com.sgva.admin.user.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountVo {
	private String memberId;
	private String memberPw;
	private String memberName;
	private String memberNickName;
	private String memberEmail;
	private String memberPhone;
	private String regDate;
	private String regId;
}