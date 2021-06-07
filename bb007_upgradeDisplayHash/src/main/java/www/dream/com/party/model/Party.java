package www.dream.com.party.model;

import java.util.ArrayList;
import java.util.Date;
/**
 * 모든 행위자 정보의 공통적인 상위 정보를 담고있는 추상적인 클래스 
 * @author Park
 */
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import www.dream.com.common.model.CommonMngVO;
import www.dream.com.framework.lengPosAnalyzer.HashTarget;
import www.dream.com.framework.printer.ClassHeaderTarget;
import www.dream.com.framework.printer.HeaderTarget;
@Data
@NoArgsConstructor
@ClassHeaderTarget
public abstract class Party extends CommonMngVO { // 4. CommonMngVO를 상속받는 Party 클래스를 만들기.
	/*
	private String user_id;	 // 로그인 ID
	private String user_pwd;  // 암호, 암호화는 나중에
	private String 	name;	 // User의 사람 이름
	private Date 	birth_dt;  // 생년월일
	private boolean sex;		// 성별
	private boolean	enabled;	// 가입중, 탈퇴 시 false
	*/
		// 5. 이곳에 VO로 지정할 사항들 가져오기.
	// 6. Java형식에 맞게끔 자료형 변수명 맞춰주기
	private String userId;	 // 로그인 ID
	private String userPwd;  // 암호, 암호화는 나중에
	@HashTarget 
	@HeaderTarget(order=250, caption="닉네임")
	private String 	name;	 // User의 사람 이름
	private Date 	birthDate;  // 생년월일
	private boolean male;		// 성별
	private boolean	enabled;	// 가입중, 탈퇴 시 false
	
	@HashTarget
	private List<ContactPoint> listContactPoint = new ArrayList<>();
	// 12. 11번과 이어지는 1:N 관계의 속성을 정의하기 위한 list Master_Detail. ContactPoint.java에 있는 11번 주석읽어볼 것

	public Party(String userId) {
		this.userId = userId; //Party에 있는 userId 생성자 만들어주기. (0521. BoardVO, PostVO Test하기위함) 
	}
	
	public void addContactPoint(ContactPoint cp) {
		listContactPoint.add(cp);
	}
	@Override
	public String toString() {
		return "Party [userId=" + userId + ", userPwd=" + userPwd + ", name=" + name + ", birthDate=" + birthDate
				+ ", male=" + male + ", enabled=" + enabled + ", listContactPoint=" + listContactPoint
				+ ", toString()=" + super.toString() + "]";
	}

}
