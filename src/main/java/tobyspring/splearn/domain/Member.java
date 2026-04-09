package tobyspring.splearn.domain;

import static java.util.Objects.*;
import static org.springframework.util.Assert.*;

import java.util.Objects;
import java.util.regex.Pattern;

import lombok.Getter;

@Getter
public class Member {
	private Email email;

	private String nickname;

	private String passwordHash;

	private MemberStatus status;

	private Member() {
	}

	public static Member create(MemberCreateRequest createRequest, PasswordEncoder passwordEncoder) {
		Member member = new Member();

		member.email = new Email(createRequest.email());
		member.nickname = requireNonNull(createRequest.nickname());
		member.passwordHash = requireNonNull(passwordEncoder.encode(createRequest.password()));
		member.status = MemberStatus.PENDING;

		return member;
	}

	public void activate() {
		state(status == MemberStatus.PENDING, "PENDING 상태가 아닙니다");

		status = MemberStatus.ACTIVE;
	}

	public void deactivate() {
		state(status == MemberStatus.ACTIVE, "ACTIVE 상태가 아닙니다");

		status = MemberStatus.DEACTIVATED;
	}

	public Object verifyPassword(String password, PasswordEncoder passwordEncoder) {
		return passwordEncoder.matches(password, passwordHash);
	}

	public void changeNickname(String nickname) {
		this.nickname = requireNonNull(nickname);
	}

	public void changePassword(String password, PasswordEncoder passwordEncoder) {
		passwordHash = passwordEncoder.encode(requireNonNull(password));
	}

	public boolean isActive() {
		return status == MemberStatus.ACTIVE;
	}
}