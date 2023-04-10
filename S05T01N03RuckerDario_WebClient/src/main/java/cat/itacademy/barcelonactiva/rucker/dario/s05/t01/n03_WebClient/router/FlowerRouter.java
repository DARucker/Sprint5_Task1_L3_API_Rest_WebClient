package cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n03_WebClient.router;

import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n03_WebClient.handler.FlowerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;

import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;


@Configuration
public class FlowerRouter {

    private static Logger log = LoggerFactory.getLogger(FlowerRouter.class);

    @Bean
    public RouterFunction<ServerResponse> flowerRouterMethod (FlowerHandler flowerHandler){

        log.info("Flower router");

        return RouterFunctions
                .route(GET("/api/functional/flower/").and(accept(MediaType.APPLICATION_JSON)),flowerHandler::getAllFlowerService)
                .andRoute(GET("/api/functional/flower/{id}").and(accept(MediaType.APPLICATION_JSON)),flowerHandler::findById)
                //.andRoute(POST("/functional/flower/"),flowerHandler::saveFlower)
                //.andRoute(PUT("/functional/flower/{id}"),flowerHandler::update)
                .andRoute(DELETE("/api/functional/flower/{id}").and(accept(MediaType.APPLICATION_JSON)),flowerHandler::delete);
    }
}
