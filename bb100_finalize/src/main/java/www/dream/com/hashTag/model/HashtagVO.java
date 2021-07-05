package www.dream.com.hashTag.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HashtagVO { // 0602 HashTag 요소 추가
	
	private int id;
	private String hashtag;
	private String description;
	
	private int occurCnt;

	public HashtagVO(int id, String hashtag) { // hashtag 만 받아들이는 생성자를 만들어줍니다.
		this.id = id;
		this.hashtag = hashtag;
	}
	
	
}
