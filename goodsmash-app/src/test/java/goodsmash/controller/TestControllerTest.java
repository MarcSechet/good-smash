package goodsmash.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import mic.poulet.goodsmash.controller.TestController;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class TestControllerTest {

	@InjectMocks
	private TestController testController;

	@Test
	public void testServerIsUp() {

		ResponseEntity<Void> response = testController.test();

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}
