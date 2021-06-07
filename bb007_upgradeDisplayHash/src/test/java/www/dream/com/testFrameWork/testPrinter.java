package www.dream.com.testFrameWork;

import org.junit.Test;

import www.dream.com.bulletinBoard.model.PostVO;
import www.dream.com.framework.printer.TableHeader;

public class testPrinter {
	@Test
	public void test() {
		System.out.println(TableHeader.print(PostVO.class));
	}
	

}
