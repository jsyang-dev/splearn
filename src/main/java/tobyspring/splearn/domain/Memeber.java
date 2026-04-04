package tobyspring.splearn.domain;

public class Memeber {
	private String email;

	private String nickname;

	private String passwordHash;

	private MemberStatus status;

	public Memeber(String email, String nickname, String passwordHash) {
		this.email = email;
		this.nickname = nickname;
		this.passwordHash = passwordHash;
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
}