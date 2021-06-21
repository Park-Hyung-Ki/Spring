package www.dream.com.framework.util;

public class FileUtil {
	public static String truncateExt(String fileName) {
		return fileName.substring(0, fileName.lastIndexOf('.')); // 파일 확장자로 쓰이는 .jpg, .avi이런거
		
	}
}
