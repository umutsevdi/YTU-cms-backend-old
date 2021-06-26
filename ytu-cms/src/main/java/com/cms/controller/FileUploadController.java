package com.cms.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cms.YtuCmsApplication;

@Controller
@RequestMapping(path = "/loader")
public class FileUploadController {
	private static String UPLOADED_FOLDER = YtuCmsApplication.path+"/documents/"+LocalDate.now().getYear()+"/";
	
	@GetMapping("/")
	public String index() {
		System.out.println("Form requested");
		return "upload";
	}

	@PostMapping("/upload")
	@ResponseBody
	public String singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
		System.out.println("Post requested");
		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			return "redirect:/result";
		}

		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
			Files.write(path, bytes);

			redirectAttributes.addFlashAttribute("message",
					"You successfully uploaded '" + file.getOriginalFilename() + "'");
			System.out.println("Success");

		} catch (IOException e) {
			System.out.println("Error");
			e.printStackTrace();
		}

		return "redirect:/result";
	}

	@GetMapping("/upload/result")
	public String uploadStatus() {
		System.out.println("reached here");
		return "uploadStatus";
	}

}