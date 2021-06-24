package www.dream.com.common.attachFile.control;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.jcodec.api.FrameGrab;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import net.coobird.thumbnailator.Thumbnailator;
import www.dream.com.common.attachFile.model.AttachFileVO;
import www.dream.com.common.attachFile.model.MultimediaType;
import www.dream.com.framework.util.FileUtil;
import www.dream.com.framework.util.StringUtil;

@Controller
@RequestMapping("/uploadFiles/*")
public class UploadController {
	private static final String UPLOAD_FOLDER = "C:\\uploadedFiles";
	
	@GetMapping("uploadByAjax")
	public void uploadByAjax() {
		
	}
	// http://localhost:8091/uploadFiles/uploadByAjax 주소
	@PostMapping(value = "upload", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<String>> uploadFilesByAjax(@RequestParam("uploadFile") MultipartFile[] uploadFiles) {
		List<AttachFileVO> listAttachFileVO = new ArrayList<>();
		File uploadPath = new File(UPLOAD_FOLDER, getFolderName());
		if (! uploadPath.exists()) {
			//필요한 폴더 구조가 없다면 그 전체를 만들어 준다.
			uploadPath.mkdirs(); //필요한 경로들을 다 만들겠다는 함수 mkdirs
		}
		
		for (MultipartFile uf : uploadFiles) {
			AttachFileVO attachFileVO = new AttachFileVO();
			attachFileVO.setSavedFolderPath(uploadPath.getAbsolutePath()); // AbsolutePath : 절대경로
			
			String originalFileName = uf.getOriginalFilename(); 
			String pureFilename = originalFileName.substring(originalFileName.lastIndexOf("\\") + 1);
			attachFileVO.setPureFileName(pureFilename);
			//파일이름이 중복되더라도 Upload 할 수있는 UUID 라는 Library 앞에다가 임의의 숫자+영문 조합을 만들어냄 주소처럼
			String uuid = UUID.randomUUID().toString();
			attachFileVO.setUuid(uuid);
			String pureSaveFileName = attachFileVO.getPureSaveFileName();
			File save = new File(uploadPath, pureSaveFileName);
			
			try {
				uf.transferTo(save);
				makeThumbnail(uploadPath, save, pureSaveFileName, attachFileVO);
				attachFileVO.setMultimediaType(MultimediaType.identifyMultimediaType(save));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			listAttachFileVO.add(attachFileVO);
		}
		
		List<String> ret = listAttachFileVO.stream().map(vo -> vo.getJson()).collect(Collectors.toList());
		
		return new ResponseEntity<>(ret, HttpStatus.OK);
	}
	
	@GetMapping("display")
	@ResponseBody
	public ResponseEntity<byte[]> getThumbnaileFile(@RequestParam("fileName") String fileName) {
		File file = new File(fileName);
		ResponseEntity<byte[]> res = null;
		HttpHeaders headers =new HttpHeaders();
		try {
			headers.add("Content-Type", Files.probeContentType(file.toPath()));
			res = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), headers ,HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * 
	 * @param fileName C:\\uploadedFiles\2021\06\21\myfile.txt Full File Path 형식
	 * @return
	 */
	@GetMapping(value = "download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE) // Byte 배열로 읽고 Return
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent,  @RequestParam("fileName") String fileName) {
		Resource resource =  new FileSystemResource(fileName);
		
		if (! resource.exists()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		
		String resourceFilename = AttachFileVO.filterPureFileName(resource.getFilename());
		HttpHeaders headers = new HttpHeaders();
		
		try {
			String downloadFileName = null;
			if (userAgent.contains("Trident") || userAgent.contains("Edge")) {
				downloadFileName = URLEncoder.encode(resourceFilename, "UTF-8");
			} else {
				downloadFileName = new String(resourceFilename.getBytes("UTF-8"), "ISO-8859-1");
			}
			headers.add("Content-Disposition", "attachment; filename=" + downloadFileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity(resource, headers, HttpStatus.OK);
	}
	
	@PostMapping(value = "deleteFile")
	@ResponseBody
	public ResponseEntity<String> cancelAttatch(AttachFileVO attachVo) {
		
		try {
			
			File pureSaveFile = new File(attachVo.getSavedFolderPath(), attachVo.getPureSaveFileName());
			pureSaveFile.delete();
			if (StringUtil.hasInfo(attachVo.getPureThumbnailFileName())) {
				new File(attachVo.getSavedFolderPath(), attachVo.getPureThumbnailFileName()).delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("Delete Success", HttpStatus.OK);
	}
	
	
	//오늘 날짜에 활용할 폴더 이름을 주세요(Server에 들어있는 폴더이기에 Controller에서 개발)
	private String getFolderName() {
		SimpleDateFormat simpledf = new SimpleDateFormat("yyyy-MM-dd");
		return simpledf.format(new Date()).replace('-', File.separatorChar); // 문자의 자료형으로 replace
	}

	private void makeThumbnail(File uploadPath, File uploadedfile, String pureSaveFileName, AttachFileVO attachFileVO) {
		MultimediaType multimediaType = MultimediaType.identifyMultimediaType(uploadedfile);
		if (multimediaType == MultimediaType.image) {
			String pureThumbnailFileName = AttachFileVO.THUMBNAIL_FILE_PREFIX  + pureSaveFileName;
			attachFileVO.setPureThumbnailFileName(pureThumbnailFileName);
			File thumbnailFile = new File(uploadPath, AttachFileVO.THUMBNAIL_FILE_PREFIX + pureSaveFileName);
			try {
				//FileOutputStream thumb = new FileOutputStream(thumbnailFile);
				Thumbnailator.createThumbnail(uploadedfile, thumbnailFile, 100, 100);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(multimediaType == MultimediaType.video) { //동영상 파일 올리기
			pureSaveFileName = FileUtil.truncateExt(pureSaveFileName);
			String pureThumbnailFileName = AttachFileVO.THUMBNAIL_FILE_PREFIX + pureSaveFileName + ".png";
			attachFileVO.setPureThumbnailFileName(pureThumbnailFileName);
			File thumbnailFile = new File(uploadPath, AttachFileVO.THUMBNAIL_FILE_PREFIX + pureSaveFileName  + ".png"); // Video File에 대한 최종  Thumbnail
			
			try {
				//FileOutputStream thumb = new FileOutputStream(thumbnailFile);
				int frameNumber = 0;
				//Video 파일에서 첫 번째 프레임의 이미지를 가져오기
				Picture picture = FrameGrab.getFrameFromFile(uploadedfile, frameNumber);
				BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);
				ByteArrayOutputStream os = new ByteArrayOutputStream(); 
				ImageIO.write(bufferedImage, "png", os);
				InputStream is = new ByteArrayInputStream(os.toByteArray());
				
				FileOutputStream fileOutputStream = new FileOutputStream(thumbnailFile);
				//가져온 이미지를 Thumbnail로 만들기
				
				Thumbnailator.createThumbnail(is, fileOutputStream, 100, 100);
				fileOutputStream.close(); // 이거 없으면 Thumbnail Image가 보이지 않음
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		
	}
}

