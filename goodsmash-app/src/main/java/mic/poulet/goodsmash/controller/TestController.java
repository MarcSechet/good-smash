package mic.poulet.goodsmash.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import mic.poulet.goodsmash.spec.api.TestsApi;

@Controller
@RequestMapping("${api-smash.base-path:}")
public class TestController implements TestsApi {

	@Override
	public ResponseEntity<Void> test() {
		return ResponseEntity.ok().build();
	}
}
