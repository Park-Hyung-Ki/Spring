package www.dream.com.restStudy;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;

import www.dream.com.bulletinBoard.model.PostVO;

@RunWith(SpringJUnit4ClassRunner.class) //
@WebAppConfiguration //
@ContextConfiguration({ "file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
public class SampleControllerTest {

	@Autowired
	private WebApplicationContext ctx;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}

	@Test
	public void test() {
		PostVO post = new PostVO();
		post.setTitle("Rest Controller Test");
		post.setContent("안녕!");
		String jsonOfPost = new Gson().toJson(post);

		try {
			ResultActions ra = mockMvc.perform(
					MockMvcRequestBuilders.post("/sample/registerPost/3")
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonOfPost));
					ra.andExpect(status().is(200));
		} catch(Exception e) {
			e.printStackTrace();

		}
	}

}
