package www.dream.com.framework.test;

import www.dream.com.framework.lengPosAnalyzer.HashTarget;

public class Reply {
	private String content;
	
	@HashTarget
	private Party writer;

	@HashTarget // 내용물을 분석하여 여기 긴 문장에서 핵심 단어를 추출해낼것 
	public String getContent() {
		return content;
	}
	
	public Party getWriter() {
		return writer;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setWriter(Party writer) {
		this.writer = writer;
	}
}
