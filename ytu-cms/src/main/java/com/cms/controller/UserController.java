package com.cms.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cms.YtuCmsApplication;
import com.cms.service.UserService;
import com.model.User;

@RestController
@RequestMapping(path = "api/users/")
public class UserController {
	private static String UPLOADED_FOLDER = YtuCmsApplication.path + "/images/users/";

	private final UserService service;

	@Autowired
	public UserController(UserService service) {
		this.service = service;
	}

	@GetMapping()
	public List<Document> getUsers(@RequestParam Optional<String> f) {
		System.out.println("Get");
		final String[] filter = f.orElse("").split(",");
		boolean isFiltered = filter.length > 0 && filter[0] != "";
		System.out.println("forEach() do filter :" + isFiltered + "(" + Arrays.asList(filter).toString() + ")");
		try {
			List<Document> response = new LinkedList<Document>();
			UserService.getUsers().forEach(iter -> {
				System.out.println("->iter\t" + iter.getString("public_id"));
				if (isFiltered) {
					Document element = new Document();
					for (int i = 0; i < filter.length; i++) {
						if (User.components.contains(filter[i])) {
							element.append(filter[i], iter.get(filter[i]));
						}
					}
					iter = element;
				}
				response.add(iter);
			});
			return response;

		} catch (Exception e) {
			return List.of(new Document().append("Exception", e.getLocalizedMessage()));
		}
	}

	@GetMapping("/{public_id}")
	public Document findUser(@PathVariable("public_id") String publicId, @RequestParam Optional<String> f) {
		System.out.println("Get ID " + publicId);
		final String[] filter = f.orElse("").split(",");
		boolean isFiltered = filter.length > 0 && filter[0] != "";
		System.out.println("find() do filter :" + isFiltered + "(" + Arrays.asList(filter).toString() + ")");
		try {
			Document response = UserService.findUser(publicId).toDocument(false);
			if (isFiltered) {
				Document element = new Document();
				for (int i = 0; i < filter.length; i++) {
					if (User.components.contains(filter[i])) {
						element.append(filter[i], response.get(filter[i]));
					}
				}
				response = element;
			}
			return response;
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return new Document().append("Exception", e.getLocalizedMessage());
		}
	}

	@PostMapping(value = { "/" }, consumes = "application/json")
	public Document createUser(@RequestBody Document userDocument) {
		try {
			return service.addUser(User.generate(userDocument, true)).toDocument(true);
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return new Document().append("Exception", e.getLocalizedMessage());
		}
	}

	@PutMapping(value = { "/{public_id}" }, consumes = "application/json")
	public Document editUser(@RequestBody Document userDocument, @PathVariable("public_id") String publicId) {
		try {
			return service.editUser(publicId, userDocument);
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return new Document().append("Exception", e.getLocalizedMessage());
		}
	}

	@DeleteMapping("/{public_id}")
	public Document deleteUser(@PathVariable("public_id") String publicId) {
		try {
			return service.deleteUser(publicId);
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return new Document().append("Exception", e.getLocalizedMessage());
		}
	}

	@GetMapping(value = "/{public_id}/image", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<Resource> downloadImage(@PathVariable("public_id") String publicId) throws IOException {
		ByteArrayResource inputStream;
		try {
			inputStream = new ByteArrayResource(
					Files.readAllBytes(Paths.get(UPLOADED_FOLDER + UserService.getObjectId(publicId))));
			return ResponseEntity.status(HttpStatus.OK).contentLength(inputStream.contentLength()).body(inputStream);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ByteArrayResource(null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ByteArrayResource(null));
		}

	}

	@PostMapping("/{public_id}/image")
	public Document uploadImage(@PathVariable("public_id") String publicId, @RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			System.out.println("NoFileException");
			return new Document().append("Exception", "NoFileException");
		}

		try {
			String extentionName = Controller.getExtensionByStringHandling(file.getName()).orElseThrow().toLowerCase();
			if (!(extentionName.equals("png") || extentionName.equals("jpg") || extentionName.equals("jpeg")))
				throw new Exception("WrongFileFormatException");
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_FOLDER + UserService.getObjectId(publicId));
			Files.write(path, bytes);
			return new Document().append("Status", "Success");
		} catch (IOException e) {
			return new Document().append("Exception", "CorruptFileException");
		} catch (Exception e) {
			return new Document().append("Exception", "CorruptFileException");
		}

	}
}
