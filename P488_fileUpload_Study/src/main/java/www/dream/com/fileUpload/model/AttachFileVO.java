package www.dream.com.fileUpload.model;

import java.io.File;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Data;
import www.dream.com.fileUpload.control.MultimediaType;

@Data
public class AttachFileVO {
	public static final String THUMBNAIL_FILE_PREFIX = "thumb_";
	public static final String UUID_PURE_SEP = "_";
	
	//사용자가 업로드한 순수 파일 이름
	private String pureFileName;
	
	private String pureSaveFileName;
	private String pureThumbnailFileName;
	//Server에서 저장된 폴더 이름
	private String savedFolderPath;
	//UUID
	private String uuid;
	//MultiMediaType
	private MultimediaType multimediaType;
	
	public String getFileCallPath() { //Page 528 varCallPath부분
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("");
		builder.queryParam("fileName", savedFolderPath + File.separator +  pureThumbnailFileName);
		return builder.toUriString(); //?fileName=name~~~~ 이런 String이 만들어진다.   
	}
}
