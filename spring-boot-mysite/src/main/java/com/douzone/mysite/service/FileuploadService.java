package com.douzone.mysite.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileuploadService {
	private static final String SAVE_PATH = "/uploads";
	private static final String URL = "/uploads/images";
			
	public String restore(MultipartFile multipartFile) {
		String url = "";
		try {
			// 파일이 없을 경우
			if(multipartFile.isEmpty()) {
				return url;
			}
			
			// 파일명
			String originalFileName = multipartFile.getOriginalFilename();
			// 확장자 분리
			String extName = originalFileName.substring(originalFileName.lastIndexOf('.')+1);
			// 서버에 저장할 파일명
			String saveFileName = generateSaveFileName(extName);
			// 파일 크기
			long fileSize = multipartFile.getSize();
			
			System.out.println("##########" + originalFileName);
			System.out.println("##########" + extName);
			System.out.println("##########" + saveFileName);
			System.out.println("##########" + fileSize);
			
			byte[] fileData = multipartFile.getBytes();
			OutputStream os = new FileOutputStream(SAVE_PATH + "/" + saveFileName);
			os.write( fileData );
			os.close();
			
			url = URL + "/" + saveFileName;
			
		} catch(IOException ex) {
			new RuntimeException("upload fail");
		}
		
		return url;
	}
	
	
	private String generateSaveFileName(String extName) {
		String fileName = "";
		Calendar calendar = Calendar.getInstance();
		
		fileName += calendar.get(calendar.YEAR);
		fileName += calendar.get(calendar.MONTH);
		fileName += calendar.get(calendar.DATE);
		fileName += calendar.get(calendar.HOUR);
		fileName += calendar.get(calendar.MINUTE);
		fileName += calendar.get(calendar.SECOND);
		fileName += calendar.get(calendar.MILLISECOND);
		fileName += ("." + extName);
		
		return fileName;
		
	}
}
