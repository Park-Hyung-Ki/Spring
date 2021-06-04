package www.dream.com.framework.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import www.dream.com.framework.lengPosAnalyzer.PosAnalyzer;

public class TestPosAnalyzer {

	@Test
	public void test() {
		Post post = new Post();
		post.setTitle("질문 구조 그리고 밥");
		post.setContent("프로젝트의 전체구조 목표");
		
		Party writer = new Party();
		writer.setName("강아지");
		post.setWriter(writer);
		
		ContactPoint cp = new ContactPoint();
		cp.setInfo("서울 특별시 금천구 가산디지털");
		writer.addCP(cp);
		
		cp = new ContactPoint();
		cp.setInfo("pretty_gm@naver.com");
		writer.addCP(cp);
		
		Map<String, Integer> map = PosAnalyzer.getHashTags(post);
		for (String k : map.keySet()) {
			System.out.println(k + " : " + map.get(k));
		}
	}
	
	@Test
	public void testString() {
		Map<String, Integer> map = PosAnalyzer.getHashTags("안녕하세요!, 이상덕 ");
		for (String k : map.keySet()) {
			System.out.println(k + " : " + map.get(k));
		}
	}
	
	@Test
	public void testList() {
		List<String> data = new ArrayList<>();
		data.add("응애 애기");
		data.add("쾌걸 이순신");
		Map<String, Integer> map = PosAnalyzer.getHashTags(data);
		for (String k : map.keySet()) {
			System.out.println(k + " : " + map.get(k));
		}
	}
	
}
// Map<String, Integer> hashList = PosAnalyzer.getHashTags(post); //
// PosAnalyzer에게 post 객체를 던져주면 String의 출현 횟수가 나올것.

/*
 * hashList = PosAnalyzer.getHashTags(post.getTitle()); hashList =
 * PosAnalyzer.getHashTags(post.getContent()); hashList =
 * PosAnalyzer.getHashTags(post.getWriter().getName());
 */

// 이렇게 개발을 하면, 있는 객체들마다 찾아야 한다. 총 4개 -> 코드가 길어지지
