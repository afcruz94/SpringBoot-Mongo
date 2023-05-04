package com.andrecruz.workshopmongo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andrecruz.workshopmongo.domain.User;
import com.andrecruz.workshopmongo.dto.UserDTO;
import com.andrecruz.workshopmongo.repository.UserRepository;
import com.andrecruz.workshopmongo.service.exceptions.ObjectNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User findById(String id) {
		Optional<User> obj = userRepository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException("Object not found!"));
	}

	public User insert(User obj) {
		return userRepository.insert(obj);
	}

	public void delete(String id) {
		userRepository.deleteById(id);
	}

	public User update(User obj) {
		User newUser = findById(obj.getId());
		updateData(newUser, obj);

		return userRepository.save(newUser);
	}

	private void updateData(User newUser, User obj) {
		newUser.setName(obj.getName());
		newUser.setEmail(obj.getEmail());
	}

	public User fromDTO(UserDTO objDTO) {
		return new User(objDTO.getId(), objDTO.getName(), objDTO.getEmail());
	}

}
