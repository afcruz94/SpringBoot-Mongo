package com.andrecruz.workshopmongo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andrecruz.workshopmongo.domain.Post;
import com.andrecruz.workshopmongo.repository.PostRepository;
import com.andrecruz.workshopmongo.service.exceptions.ObjectNotFoundException;

@Service
public class PostService {
	@Autowired
	private PostRepository postRepository;

	public Post findById(String id) {
		Optional<Post> obj = postRepository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException("Object not found!"));
	}

	public List<Post> findByTitle(String text) {
		return postRepository.findByTitleContainingIgnoreCase(text);
	}

	public List<Post> searchTitle(String text) {
		return postRepository.searchTitle(text);
	}

	public List<Post> fullSearch(String text, Date startDate, Date endDate) {
		endDate = new Date(endDate.getTime() + 24 * 60 * 60 * 1000); // next day
		
		return postRepository.fullSearch(text, startDate, endDate);
	}
}
