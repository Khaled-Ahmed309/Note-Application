package com.example.notesapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories({
		"com.example.notesapp.user.repository",
		"com.example.notesapp.notes.repository"
})
@EntityScan(basePackages = {
		"com.example.notesapp.user.models.entities",
		"com.example.notesapp.notes.models.entities"
})
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class NotesApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotesApplication.class, args);
	}

}
