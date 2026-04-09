package tobyspring.splearn.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {
	private Member member;
	private PasswordEncoder passwordEncoder;

	@BeforeEach
	void setUp() {
		passwordEncoder = new PasswordEncoder() {
			@Override
			public String encode(String password) {
				return password.toUpperCase();
			}

			@Override
			public boolean matches(String password, String passwordHash) {
				return encode(password).equals(passwordHash);
			}
		};

		member = Member.create(new MemberCreateRequest("test@splearn.app", "Test", "secret"), passwordEncoder);
	}

	@Test
	void createMember() {
		// then
		assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
	}

	@Test
	void activate() {
		// when
		member.activate();

		// then
		assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
	}

	@Test
	void activateFail() {
		// given
		member.activate();

		// when & then
		assertThatThrownBy(member::activate).isInstanceOf(IllegalStateException.class);
	}

	@Test
	void deactivate() {
		// given
		member.activate();

		// when
		member.deactivate();

		// then
		assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
	}

	@Test
	void deactivateFail() {
		// when & then
		assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);
	}

	@Test
	void deactivateFail2() {
		// given
		member.activate();
		member.deactivate();

		// when & then
		assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);
	}

	@Test
	void verifyPassword() {
		// when
		var result = member.verifyPassword("secret", passwordEncoder);

		// then
		assertThat(result).isEqualTo(true);
	}

	@Test
	void verifyPasswordFail() {
		// when
		var result = member.verifyPassword("secret2", passwordEncoder);

		// then
		assertThat(result).isEqualTo(false);
	}

	@Test
	void changeNickname() {
		// when
		member.changeNickname("Test2");

		// then
		assertThat(member.getNickname()).isEqualTo("Test2");
	}

	@Test
	void changePassword() {
		// when
		member.changePassword("secret2", passwordEncoder);

		// then
		assertThat(member.verifyPassword("secret2", passwordEncoder)).isEqualTo(true);
	}

	@Test
	void isActive() {
		// when
		member.activate();

		// then
		assertThat(member.isActive()).isTrue();
	}

	@Test
	void invalidEmail() {
		// when & then
		assertThatThrownBy(() -> Member.create(new MemberCreateRequest("invalid email", "Test", "secret"), passwordEncoder))
			.isInstanceOf(IllegalArgumentException.class);
	}
}
