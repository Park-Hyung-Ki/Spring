package www.dream.com.party.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
@Data
public class Party {
	// Data 재구성을 하려고하는데 이 부분은 고객의 기본 정보들
	private long id;
	private String name;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date birthDate; // 15. 0517. 이 부븐을 처리하는 것에 대하여 
	// 16. String으로 해도 잘 나오긴한다. -> 문자 출력
	//Party(id=0, name=qee, birthDate=2021-05-04, sex=true, job=null, listContactPoint=[ContactPoint(contactPointType=addr, value=1123), ContactPoint(contactPointType=phoneNum, value=null)])
	
	// 17. @DateTimeFormat을 사용한 결과 -> Date 객체 출력
	//Party(id=0, name=Park, birthDate=Mon May 17 00:00:00 KST 2021, sex=true, job=null, listContactPoint=[ContactPoint(contactPointType=addr, value=112345), ContactPoint(contactPointType=phoneNum, value=null)])
	private boolean sex;
	private String job;
	
	private List<ContactPoint> listContactPoint = new ArrayList<>(); //Staruml에서 연관 관계를 표현
	
	public void addCP(ContactPoint cp) { // 43. 42에 이어질 수 있게, Party에 새로운 구문 형성
		listContactPoint.add(cp);
	}
	
}
