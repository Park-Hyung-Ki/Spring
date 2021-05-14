package www.dream.com.party.model;

public enum ContactPointType {
	addr("test_addr"), phoneNum("test_phoneNum"), mobileNum("test_mobileNum"); //4. 검사식
	
	private String validatingRegEx; // 5.검사 유효성 검사 용도 정규식
	private ContactPointType(String validatingRegEx) { // 6. 생성자 생성
		this.validatingRegEx = validatingRegEx; // 7. 생성자를 사용하기위한 선언
	}

}
