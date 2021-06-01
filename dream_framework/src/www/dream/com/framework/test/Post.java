package www.dream.com.framework.test;

import www.dream.com.framework.lengPosAnalyzer.HashTarget;

// 상속 구조와 내포 구조

public class Post extends Reply {
	private String title;

	@HashTarget // 여기에서는 title에 대하여 핵심단어 분석
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
}
