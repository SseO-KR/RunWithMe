package com.ssafy.gumid101.dto;

import java.io.Serializable;

import javax.annotation.RegEx;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import com.ssafy.gumid101.entity.UserEntity;
import com.ssafy.gumid101.user.Role;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 직렬화 기능을 가진 User클래스
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {

	private Long userSeq;


	@Pattern(regexp = "",message = "닉네임에 사용불가능한 단어가 있습니다.")
	@ApiParam(value = "사용자 닉네임")
	private String nickName;

	@ApiParam(value = "사용자 이메일")
	private String email;

	@ApiParam(value = "사용자 키")
	private Integer height;

	@ApiParam(value = "사용자 몸무게")
	private Integer weight;

	@ApiParam(value = "사용자 포인트")
	private Integer point;

	private String fcmToken;

	private String userState;

	private Role role;

	public static UserDto of(UserEntity user) {
		return new UserDtoBuilder().userSeq(user.getUserSeq()).nickName(user.getNickName()).email(user.getEmail())
				.height(user.getHeight()).weight(user.getWeight()).point(user.getPoint()).fcmToken(user.getFcmToken())
				.userState(user.getUserState()).role(user.getRole()).build();
	}
	

}