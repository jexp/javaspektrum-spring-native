package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.*;
import org.springframework.data.neo4j.core.schema.*;
import static org.springframework.data.neo4j.core.schema.Relationship.Direction.*;
import org.springframework.data.neo4j.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.neo4j.repository.config.*;

@SpringBootApplication
@EnableNeo4jRepositories(considerNestedRepositories=true)
public class JavaspektrumNativeApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(JavaspektrumNativeApplication.class, args);
	}

    @Autowired SeasonRepository repo;
    
    public void run(String...args) {
        List.of("Spring","Summer","Fall","Winter").stream()
        .map(Season::new).forEach(repo::save);
    }

    @Node
    public record Season(@Id String name) {}

    public interface SeasonRepository extends Neo4jRepository<Season, String> {
    }

    @RestController
    public static class SeasonController {
        @Autowired SeasonRepository repo;
        @GetMapping("/")
        public List<Season> index() {
            return repo.findAll();
        }
    }
}
