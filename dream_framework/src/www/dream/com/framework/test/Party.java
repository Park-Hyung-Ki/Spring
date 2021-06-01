package www.dream.com.framework.test;

import java.util.ArrayList;
import java.util.List;

import www.dream.com.framework.lengPosAnalyzer.HashTarget;

@HashTarget
public class Party {
	
	private String name;

	@HashTarget // 이름은 고유명사 NNP이다.(KOMORAN 에서의 약속)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@HashTarget
	private List<ContactPoint> listContactPoint = new ArrayList<>();
	
	public void addCP(ContactPoint cp) {
		listContactPoint.add(cp);
	}
	
	
	
}
