package www.dream.com.bulletinBoard.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import www.dream.com.common.model.CommonMngVO;
import www.dream.com.framework.lengPosAnalyzer.HashTarget;
import www.dream.com.framework.printer.ClassHeaderTarget;
import www.dream.com.framework.printer.HeaderTarget;
import www.dream.com.party.model.Party;
/**
 * 게시글 
 * @author Park
 *
 */
@Data
@NoArgsConstructor // 여기서도 생성자를 강제로 만들거기 때문에
@ClassHeaderTarget
public class PostVO extends CommonMngVO{
	/* DB함수 get_id 부분을 참고하여 상수로 처리한다. */
	public static final int ID_LENGTH =5; 
	
	private String id; //아이디
	
	@HashTarget @HeaderTarget(order=100, caption="작성자")
	private String title;
	
	@HashTarget
	private String content; // 내용
	@HeaderTarget(order=300, caption="조회 수")
	private int readCnt; 
	private int likeCnt;  // 좋아요 수
	private int dislikeCnt;  // 싫어요 수
	
	@HashTarget 
	private Party writer; // 객체 접근성 Party Class에 있는 writer 객체
	
	
	public PostVO(String title, String content, Party writer) {
		this.title = title;
		this.content = content;
		this.writer = writer;
	}

	@Override
	public String toString() {
		System.out.println();
		return "PostVO [id=" + id + ", title=" + title + ", content=" + content + ", readCnt=" + readCnt + ", likeCnt="
				+ likeCnt + ", dislikeCnt=" + dislikeCnt + ", writer=" + writer + ", toString()=" + super.toString() + "]";
 	}
}
