package tobyspring.splearn.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MemberTest {
	@Test
	void createMember() {
		// when
		var member = new Member("test@splearn.app", "Test", "secret");

		// then
		assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
	}

	@Test
	void constructorNullCheck() {
		// when & then
		assertThatThrownBy(() -> new Member(null, "Test", "secret")).isInstanceOf(NullPointerException.class);
	}

	@Test
	void activate() {
		// given
		var member = new Member("test@splearn.app", "Test", "secret");

		// when
		member.activate();

		// then
		assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
	}

	@Test
	void activateFail() {
		// given
		var member = new Member("test@splearn.app", "Test", "secret");
		member.activate();

		// when & then
		assertThatThrownBy(member::activate).isInstanceOf(IllegalStateException.class);
	}

	@Test
	void deactivate() {
		// given
		var member = new Member("test@splearn.app", "Test", "secret");
		member.activate();

		// when
		member.deactivate();

		// then
		assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
	}

	@Test
	void deactivateFail() {
		// given
		var member = new Member("test@splearn.app", "Test", "secret");

		// when & then
		assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);
	}

	@Test
	void deactivateFail2() {
		// given
		var member = new Member("test@splearn.app", "Test", "secret");
		member.activate();
		member.deactivate();

		// when & then
		assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);
	}
}