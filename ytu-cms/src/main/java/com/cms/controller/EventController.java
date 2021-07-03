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
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cms.YtuCmsApplication;
import com.cms.service.EventService;
import com.model.Event;

@RestController
@RequestMapping(path = "api/events")
public class EventController {
	private static String UPLOADED_FOLDER = YtuCmsApplication.path + "/images/events/";

	private final EventService service;

	@Autowired
	public EventController(EventService service) {
		this.service = service;
	}

	@GetMapping("/")
	public List<Document> getDocuments(@RequestParam Optional<String> f) {
		System.out.println("Get");
		final String[] filter = f.orElse("").split(",");
		boolean isFiltered = filter.length > 0 && filter[0] != "";
		System.out.println("forEach() do filter :" + isFiltered + "(" + Arrays.asList(filter).toString() + ")");
		try {
			List<Document> response = new LinkedList<Document>();
			EventService.getDocuments().forEach(iter -> {
				System.out.println("->iter\t" + iter.getObjectId("_id"));
				if (isFiltered) {
					Document element = new Document();
					for (int i = 0; i < filter.length; i++) {
						if (Event.components.contains(filter[i])) {
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

	@GetMapping("/{id}")
	public List<Document> getDocuments(@RequestParam Optional<String> f, @PathVariable("id") ObjectId id) {
		System.out.println("Get Club Docs");
		final String[] filter = f.orElse("").split(",");
		boolean isFiltered = filter.length > 0 && filter[0] != "";
		System.out.println("forEach() do filter :" + isFiltered + "(" + Arrays.asList(filter).toString() + ")");
		try {

			List<Document> response = new LinkedList<Document>();
			EventService.findClubEvents(id).forEach(key -> {
				Document iter = key.toDocument(true);
				System.out.println("->iter\t" + iter.getObjectId("_id"));
				if (isFiltered) {
					Document element = new Document();
					for (int i = 0; i < filter.length; i++) {
						if (Event.components.contains(filter[i])) {
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

	@GetMapping("/{club_id}/{_id}")
	public Document findEvent(@PathVariable("club_id") ObjectId clubId, @PathVariable("_id") ObjectId _id,
			@RequestParam Optional<String> f) {
		System.out.println("Get ID " + _id);
		final String[] filter = f.orElse("").split(",");
		boolean isFiltered = filter.length > 0 && filter[0] != "";
		System.out.println("find() do filter :" + isFiltered + "(" + Arrays.asList(filter).toString() + ")");
		try {
			Document response = EventService.findEvent(_id).get().toDocument(false);
			if (isFiltered) {
				Document element = new Document();
				for (int i = 0; i < filter.length; i++) {
					if (Event.components.contains(filter[i])) {
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
	@ResponseBody
	public Document postDocument(@RequestBody Document document) {
		try {
			return service.addDocument(document).toDocument(true);
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return new Document().append("Exception", e.getLocalizedMessage());
		}
	}

	@PutMapping(value = { "/{_id}" }, consumes = "application/json")
	@ResponseBody
	public Document editEvent(@RequestBody Document clubDocument, @PathVariable("_id") ObjectId _id) {
		try {
			return service.editDocument(_id, clubDocument);
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return new Document().append("Exception", e.getLocalizedMessage());
		}
	}

	@GetMapping(value = "/{_id}/image", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<Resource> downloadImage(@PathVariable("_id") ObjectId _id) throws IOException {
		ByteArrayResource inputStream;
		try {
			inputStream = new ByteArrayResource(Files.readAllBytes(Paths.get(UPLOADED_FOLDER + _id)));
			return ResponseEntity.status(HttpStatus.OK).contentLength(inputStream.contentLength()).body(inputStream);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ByteArrayResource(null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ByteArrayResource(null));
		}

	}

	@PostMapping("/{_id}/image")
	public Document uploadImage(@PathVariable("_id") ObjectId _id, @RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			System.out.println("NoFileException");
			return new Document().append("Exception", "NoFileException");
		}
		try {
			String extentionName = Controller.getExtensionByStringHandling(file.getName()).orElseThrow().toLowerCase();
			if (!(extentionName.equals("png") || extentionName.equals("jpg") || extentionName.equals("jpeg")))
				throw new Exception("WrongFileFormatException");
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_FOLDER + _id);
			Files.write(path, bytes);

			return new Document().append("Status", "Success");
		} catch (IOException e) {
			return new Document().append("Exception", "CorruptFileException");
		} catch (Exception e) {
			return new Document().append("Exception", "CorruptFileException");
		}

	}

}
