package ar.com.pichidev.homestock;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class HomestockApplicationTests {

	@Test
	void contextLoads() {
        assertDoesNotThrow(() -> HomestockApplication.main(new String[] {}));
	}

}
