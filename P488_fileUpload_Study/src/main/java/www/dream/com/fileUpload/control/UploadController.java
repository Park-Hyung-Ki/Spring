package www.dream.com.fileUpload.control;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/uploadFiles/*")
public class UploadController {
	@GetMapping("uploadByAjax")
	public void uploadByAjax() {
		
	}
	// http://localhost:8091/uploadFiles/uploadByAjax 주소
	@PostMapping("/upload")
	public void uploadFilesByAjax(@RequestParam("uploadFile") MultipartFile[] uploadFiles) {
		String uploadFolder = "C:\\uploadedFiles";
		
		for (MultipartFile uf : uploadFiles) {
			String originalFileName = uf.getOriginalFilename(); 
			originalFileName.substring(originalFileName.lastIndexOf("\\") + 1);
			String pureFilename = originalFileName.substring(originalFileName.lastIndexOf("\\") + 1);
			File save = new File(uploadFolder, pureFilename);
			
			try {
				uf.transferTo(save);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}
	}
}
