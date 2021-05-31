package www.dream.com.bulletinBoard.dto;

import lombok.Data;
import www.dream.com.common.dto.Criteria;

@Data
public class PostCriteria extends Criteria { // 검색 기능을 만들기위한 PostCriteria Class
	
	private String type;
	private String keyword;
	
	//Type을 Split해서 배열로 만드는 함수
	public String[] getTypeAsArray() {
		return type == null ? new String[] {} : type.split(""); // null이면 빈Stirng 비어있지 않으면 split할것 
	}

}

