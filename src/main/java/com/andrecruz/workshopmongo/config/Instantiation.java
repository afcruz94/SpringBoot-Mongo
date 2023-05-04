package com.andrecruz.workshopmongo.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.andrecruz.workshopmongo.domain.User;
import com.andrecruz.workshopmongo.dto.AuthorDTO;
import com.andrecruz.workshopmongo.dto.CommentDTO;
import com.andrecruz.workshopmongo.domain.Post;
import com.andrecruz.workshopmongo.repository.PostRepository;
import com.andrecruz.workshopmongo.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PostRepository postRepository;

	@Override
	public void run(String... args) throws Exception {

		// Delete before insert
		userRepository.deleteAll();
		postRepository.deleteAll();

		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");
		userRepository.saveAll(Arrays.asList(maria, alex, bob));

		Post p1 = new Post(null, Instant.now(), "Partiu viagem", "Vou viajar para o Porto. Abraço!", new AuthorDTO(maria));
		Post p2 = new Post(null, Instant.now(), "Bom dia", "Acordei feliz hoje!", new AuthorDTO(maria));
		CommentDTO c1 = new CommentDTO("Boa viagem mano!", Instant.now(), new AuthorDTO(alex));
		CommentDTO c2 = new CommentDTO("Aproveite!", Instant.now(), new AuthorDTO(bob));
		CommentDTO c3 = new CommentDTO("Tenha um ótimo dia!", Instant.now(), new AuthorDTO(alex));
		p1.getComments().addAll(Arrays.asList(c1, c2));
		p2.getComments().addAll(Arrays.asList(c3));
		postRepository.saveAll(Arrays.asList(p1, p2));

		maria.getPosts().addAll(Arrays.asList(p1, p2));
		userRepository.save(maria);
	}

}
