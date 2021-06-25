package com.cms.controller;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cms.service.ClubService;
import com.model.Club;
import com.model.User;

@RestController
@RequestMapping(path = "api/clubs/")
public class ClubController {
	private final ClubService service;

	@Autowired
	public ClubController(ClubService service) {
		this.service = service;
	}
	
	

	@GetMapping()
	public List<Document> getClubs(@RequestParam Optional<String> f) {
		System.out.println("Get");
		final String[] filter = f.orElse("").split(",");
		boolean isFiltered = filter.length > 0 && filter[0] != "";
		System.out.println("forEach() do filter :" + isFiltered + "(" + Arrays.asList(filter).toString() + ")");
		try {
			List<Document> response = new LinkedList<Document>();
			ClubService.getClubs().forEach(iter -> {
				System.out.println("->iter\t" + iter.getObjectId("_id"));
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

	@GetMapping("/{_id}")
	public Document findUser(@PathVariable("_id") ObjectId _id, @RequestParam Optional<String> f) {
		System.out.println("Get ID " + _id);
		final String[] filter = f.orElse("").split(",");
		boolean isFiltered = filter.length > 0 && filter[0] != "";
		System.out.println("find() do filter :" + isFiltered + "(" + Arrays.asList(filter).toString() + ")");
		try {
			Document response = ClubService.findClub(_id).toDocument(false);
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
	public Document createClub(@RequestBody Document clubDocument) {
		try {
			return service.addClub(Club.generate(clubDocument, true)).toDocument(true);
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return new Document().append("Exception", e.getLocalizedMessage());
		}
	}

	@PutMapping(value = { "/{_id}" }, consumes = "application/json")
	public Document editUser(@RequestBody Document clubDocument, @PathVariable("_id") ObjectId _id) {
		try {
			return service.editClub(_id, clubDocument);
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return new Document().append("Exception", e.getLocalizedMessage());
		}
	}

	@DeleteMapping("/{_id}")
	public Document deleteUser(@PathVariable("_id") ObjectId _id) {
		try {
			return service.deleteClub(_id);
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return new Document().append("Exception", e.getLocalizedMessage());
		}
	}

}
