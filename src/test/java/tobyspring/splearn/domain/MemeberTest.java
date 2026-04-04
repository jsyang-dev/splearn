package tobyspring.splearn.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MemeberTest {
	@Test
	void createMember() {
		var memeber = new Memeber("test@splearn.app", "Test", "secret");

		assertThat(memeber.getStatus()).isEqualTo(MemberStatus.PENDING);
	}
}