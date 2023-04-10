package cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n03_WebClient.handler;

import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n03_WebClient.controller.FlowerControllerFunctionalEndpoints;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n03_WebClient.flowerdto.Flowerdto;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n03_WebClient.router.FlowerRouter;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n03_WebClient.service.FlowerServiceRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import static org.springframework.web.reactive.function.BodyInserters.*;

@Component
public class FlowerHandler {
    @Autowired
    private FlowerServiceRestClient flowerServiceRestClient;
    @Autowired
    private FlowerControllerFunctionalEndpoints flowerControllerFunctionalEndpoints;

    private Mono<ServerResponse> response404 = ServerResponse.notFound().build();
    private Mono<ServerResponse> response406 = ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).build();

    private static Logger log = LoggerFactory.getLogger(FlowerHandler.class);


    /**
     * Find All flowers
     *
     * @param request
     * @return Mono<ServerResponse>
     */

    public Mono<ServerResponse> getAllFlowerService(ServerRequest request) {
        log.info("getAllFlowerService");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(flowerServiceRestClient.listAll(), Flowerdto.class);
    }

    public Mono<ServerResponse> getAllFlowerController(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(flowerControllerFunctionalEndpoints, Flowerdto.class);
    }

    /**
     * Find 1 flower by ID
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> findById(ServerRequest request) {
        int id = Integer.parseInt(request.pathVariable("id"));
        return Mono.just(flowerServiceRestClient.findById(id))
                .flatMap(flowerdto ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(fromValue(flowerdto)))
                .switchIfEmpty(response404);
    }

    /**
     * Add 1 new flower to the DB
     *
     * @return
     */
/*    public Mono<ServerResponse> saveFlower(ServerRequest request) {
        Mono<Flowerdto> flowerdto = request.bodyToMono(Flowerdto.class);
        return Mono.just(flowerdto)
                .flatMap(flowerdto1 -> flowerServiceRestClient.create(flowerdto1)
                        .flatMap(flowerdtoSaved -> ServerResponse.accepted()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(fromValue(flowerdtoSaved))))
                .switchIfEmpty(response404);
    }*/

/*    public Mono<ServerResponse> update(ServerRequest request) {
        Mono<Flowerdto> flowerdtoMono = request.bodyToMono(Flowerdto.class);
        int id = Integer.parseInt(request.pathVariable("id"));

        Mono<Flowerdto> flowerdtoUpd = flowerdtoMono.flatMap(flowerdto ->
                Mono.just(flowerServiceRestClient.findById(id))
                        .flatMap(flowerdtoUpd -> {
                            flowerdtoUpd.setName(flowerdto.getName());
                            flowerdtoUpd.setCountry(flowerdto.getCountry());
                        return flowerServiceRestClient.create(flowerdtoUpd);
                        }));
        return flowerdtoUpd.flatMap(flowerdto ->
                ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(flowerdto)))
                .switchIfEmpty(response404);
    }*/

    public Mono<ServerResponse> delete (ServerRequest request){
        int id = Integer.parseInt(request.pathVariable("id"));
        Mono<Void> flowerdtoDelete = Mono.just(flowerServiceRestClient.delete(id)).then();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(flowerdtoDelete, Void.class);
    }
}
