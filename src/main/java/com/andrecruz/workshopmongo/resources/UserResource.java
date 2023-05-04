package com.andrecruz.workshopmongo.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.andrecruz.workshopmongo.domain.Post;
import com.andrecruz.workshopmongo.domain.User;
import com.andrecruz.workshopmongo.dto.UserDTO;
import com.andrecruz.workshopmongo.service.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<UserDTO>> findAll() {
		// Convert each user in the DB to UserDTO class on the lambda expression below
		List<UserDTO> listDTO = userService.findAll().stream().map(x -> new UserDTO(x)).collect(Collectors.toList());

		return ResponseEntity.ok().body(listDTO);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<UserDTO> findById(@PathVariable String id) {
		UserDTO userDTO = new UserDTO(userService.findById(id));

		return ResponseEntity.ok().body(userDTO);
	}
	
	@GetMapping(value = "/{id}/posts")
	public ResponseEntity<List<Post>> findPosts(@PathVariable String id) {
		User user = userService.findById(id);

		return ResponseEntity.ok().body(user.getPosts());
	}

	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody UserDTO objDTO) {
		User user = userService.fromDTO(objDTO);
		userService.insert(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objDTO.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		userService.delete(id);

		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<UserDTO> update(@RequestBody UserDTO objDTO, @PathVariable String id) {
		User user = userService.fromDTO(objDTO);
		user.setId(id);
		UserDTO userDTO = new UserDTO(userService.update(user));

		return ResponseEntity.ok().body(userDTO);
	}
}
