package www.dream.com.bulletinBoard.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import www.dream.com.framework.lengPosAnalyzer.HashTarget;
import www.dream.com.framework.printer.ClassPrintTarget;
import www.dream.com.framework.printer.PrintTarget;
import www.dream.com.framework.util.ToStringSuperHelp;
import www.dream.com.party.model.Party;

/**
 * 게시글
 * 
 * @author Park
 *
 */
@Data
@NoArgsConstructor // 여기서도 생성자를 강제로 만들거기 때문에
@ClassPrintTarget
public class PostVO extends ReplyVO {
	public static final String DESCRIM4POST = "post";

	@HashTarget
	private String title;
	private int readCnt;
	private int likeCnt; // 좋아요 수
	private int dislikeCnt; // 싫어요 수

	public PostVO(String title, String content, Party writer) {
		super(content, writer);
		this.title = title;
	}
	
	@PrintTarget(order=100, caption="제목", withAnchor=true)
	public String getTitleWithCnt() {
		return title + " [" + super.replyCnt + "]"; 
	}

	@Override
	public String toString() {
		return "PostVO [" + ToStringSuperHelp.trimSuperString(super.toString()) + "," + " title=" + title + ",readCnt="
				+ readCnt + ", likeCnt=" + likeCnt + ", dislikeCnt=" + dislikeCnt + "]";
	}
}
