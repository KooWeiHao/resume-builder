package resume.builder.api;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import resume.builder.core.ResumeBuilderApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = ResumeBuilderApplication.class)
class ResumeBuilderApplicationTests {

	@Test
	void contextLoads() {
		JSONObject a = new JSONObject();
		a.put("a","a");
		a.put("b","b");
		JSONArray ja = new JSONArray();
		ja.add(a);

		JSONObject json = new JSONObject();
		json.put("username", "username");
		json.put("enabled", "true");
		json.put("credentials", ja);
		System.out.println(json.toString());
	}

}
