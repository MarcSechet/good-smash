package goodsmash.cucumber.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

import lombok.Getter;
import lombok.Setter;

@Component
public class TestContext {
	@Getter
	@Setter
	private ResultActions result;

	private Map<String, Object> objects;

	public void init() {
		result = null;
		objects = new HashMap<>();
	}

	public Object get(String key) {
		return objects.get(key);
	}

	public void put(String key, Object value) {
		objects.put(key, value);
	}
}
