package tobyspring.splearn.domain;

import static org.springframework.util.Assert.*;

import java.util.Objects;

public class Member {
	private String email;

	private String nickname;

	private String passwordHash;

	private MemberStatus status;

	public Member(String email, String nickname, String passwordHash) {
		this.email = Objects.requireNonNull(email);
		this.nickname = Objects.requireNonNull(nickname);
		this.passwordHash = Objects.requireNonNull(passwordHash);
		this.status = MemberStatus.PENDING;
	}

	public String getEmail() {
		return email;
	}

	public String getNickname() {
		return nickname;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public MemberStatus getStatus() {
		return status;
	}

	public void activate() {
		state(status == MemberStatus.PENDING, "PENDING 상태가 아닙니다");

		status = MemberStatus.ACTIVE;
	}

	public void deactivate() {
		state(status == MemberStatus.ACTIVE, "ACTIVE 상태가 아닙니다");

		status = MemberStatus.DEACTIVATED;
	}
}