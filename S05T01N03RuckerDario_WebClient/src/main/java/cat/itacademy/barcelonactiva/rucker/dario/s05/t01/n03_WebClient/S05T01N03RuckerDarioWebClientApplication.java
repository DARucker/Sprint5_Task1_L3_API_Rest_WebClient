package cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n03_WebClient;

import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n03_WebClient.router.FlowerRouter;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@SpringBootApplication
public class S05T01N03RuckerDarioWebClientApplication {

	public static void main(String[] args) {

		SpringApplication.run(S05T01N03RuckerDarioWebClientApplication.class, args);
	}

/*	@Bean
	public GroupedOpenApi flowerOpenApi() {//@Value("${springdoc.version}") String appVersion) {
		String[] paths = { "/flower/**" };
		return GroupedOpenApi.builder().group("flower")
				.addOpenApiCustomiser(openApi -> openApi.info(new Info().title("Flower API").version("appVersion")))
				.pathsToMatch(paths)
				.build();
	}*/


}
