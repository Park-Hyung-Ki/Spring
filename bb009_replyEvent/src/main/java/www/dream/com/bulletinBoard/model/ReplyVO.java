package www.dream.com.bulletinBoard.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import www.dream.com.common.model.CommonMngVO;
import www.dream.com.framework.lengPosAnalyzer.HashTarget;
import www.dream.com.framework.printer.AnchorTarget;
import www.dream.com.framework.util.ToStringSuperHelp;
import www.dream.com.party.model.Party;

@Data
@NoArgsConstructor
public class ReplyVO extends CommonMngVO {
	public static final String DESCRIM4REPLY = "reply";
	
	public static final int ID_LENGTH = 5;
	@AnchorTarget
	private String id;
	
	@HashTarget
	private String content;
	
	@HashTarget
	private Party writer;
	
	public ReplyVO(String parentId, String content, Party writer) {
		this.content = content;
		this.writer = writer;
	}
	
	public ReplyVO(String content, Party writer) {
		this.content = content;
		this.writer = writer;
	}
	
	@Override
	public String toString() {
		return "ReplyVO [id=" + id + ", content=" + content
				+ ", writer=" + writer
				+ ", " + ToStringSuperHelp.trimSuperString(super.toString()) + "]";
	}
}
