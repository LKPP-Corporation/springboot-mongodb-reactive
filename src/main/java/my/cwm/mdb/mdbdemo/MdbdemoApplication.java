package my.cwm.mdb.mdbdemo;

import java.util.Arrays;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import my.cwm.mdb.mdbdemo.features.kayak.Kayak;
import my.cwm.mdb.mdbdemo.features.kayak.KayakRepository;
import my.cwm.mdb.mdbdemo.features.security.User;
import my.cwm.mdb.mdbdemo.features.security.UserRepository;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class MdbdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MdbdemoApplication.class, args);
	}

	@Bean
	ApplicationRunner init(KayakRepository repository, UserRepository userRepository) {

		Object[][] data = { { "sea", "Andrew", 300.12, "NDK" }, { "creek", "Andrew", 100.75, "Piranha" },
				{ "loaner", "Andrew", 75, "Necky" } };

		User[] users = {
				new User(1, "1", "admin", "$2a$10$3bkyf9ZudNH0eOUmg.tMwu98yQHcgBJQe7mrO7Jj5KLRqxe.WzPam", false, true,
						Arrays.asList(new String[] { "foo", "bar" })),
				new User(2, "2", "user", "$2a$10$v6JgZI9QIDA1Elkrb.Ild.jnNH9evfJmfaOLKyPtXHGoBa.t16zti", false, true,
						Arrays.asList(new String[] { "foo", "bar" })) };
		return args -> {
			repository.deleteAll().thenMany(Flux.just(data).map(array -> {
				return new Kayak((String) array[0], (String) array[1], (Number) array[2], (String) array[3]);
			}).flatMap(repository::save)).thenMany(repository.findAll())
					.subscribe(kayak -> System.out.println("saving " + kayak.toString()));

			userRepository.deleteAll().thenMany(Flux.just(users).flatMap(userRepository::save))
					.thenMany(userRepository.findAll())
					.subscribe(user -> System.out.println("saving " + user.toString()));
		};
	}

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI().components(new Components().addSecuritySchemes("bearer-key",
				new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
	}

}
