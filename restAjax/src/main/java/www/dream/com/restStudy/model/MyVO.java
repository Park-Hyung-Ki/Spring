package www.dream.com.restStudy.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

// POJO 만들기

@Data
public class MyVO {
	private int asd;
	private String efg;
	
	private List<DetailVO> ab = new ArrayList<>();
	
	public void add(DetailVO a1) {
		ab.add(a1);
	}
	
	public String getKey() {
		return "ab" + asd;
	}
	
	public MyVO getObj() { //전체 반환
		try {
			return (MyVO) this.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		MyVO clone = new MyVO();
		clone.asd = asd;
		clone.efg = efg;
		return clone;
	}
	
	
}
