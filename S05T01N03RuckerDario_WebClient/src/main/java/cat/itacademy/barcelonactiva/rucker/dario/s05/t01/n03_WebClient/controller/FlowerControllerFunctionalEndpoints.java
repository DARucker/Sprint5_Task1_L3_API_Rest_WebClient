package cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n03_WebClient.controller;

import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n03_WebClient.flowerdto.Flowerdto;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n03_WebClient.service.FlowerServiceRestClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Spring 5 Task 1 - Level 3. Functional version", description = "Api Rest with Java WebFlux")
public class FlowerControllerFunctionalEndpoints {

    private static Logger LOG = LoggerFactory.getLogger(FlowerControllerFunctionalEndpoints.class);
    @Autowired
    private FlowerServiceRestClient flowerServiceRestClient;

    @Operation(summary = "Retrives all flowers in database", description = "Find and retrives each flower existing in database")
    @ApiResponse(responseCode = "200", description = "Flowers found", content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = Flowerdto.class))})
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    @GetMapping
    public Flux<Flowerdto> findAll() {
        LOG.info("Using method getAll to list every item on DB");
        Flux fluxFlowerdto = Flux.just(flowerServiceRestClient.listAll());
        return fluxFlowerdto;
    }



    @Operation(summary = "Retrives a flower using the flower id", description = "Find the selected flower using the id as the key search")
    @ApiResponse(responseCode = "200", description = "Flower found", content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = Flowerdto.class))})
    @ApiResponse(responseCode = "404", description = "Flower not found with supplied id",
            content = @Content)
    @GetMapping(value = "/{id}")
    public Mono<ResponseEntity<Flowerdto>> findById(@PathVariable int id) {
        return Mono.just(flowerServiceRestClient.findById(id))
                .map(flowerdto -> new ResponseEntity<>(flowerdto, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Adds a new flower", description = "Add a new flower into the database")
    @ApiResponse(responseCode = "201", description = "Flower created correctly", content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = Flowerdto.class))})
    @ApiResponse(responseCode = "406", description = "Flower values not valid", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal Server Error while creating the flower", content = @Content)
    @PostMapping()
    public Mono<ResponseEntity<Flowerdto>> create(@RequestBody Flowerdto flowerdto) {
        return Mono.just(flowerServiceRestClient.create(flowerdto))
                .map(savedFlower -> new ResponseEntity<>(savedFlower, HttpStatus.ACCEPTED))
                .defaultIfEmpty(new ResponseEntity<>(flowerdto, HttpStatus.NOT_ACCEPTABLE));
    }

    @Operation(summary = "Updates a flower using the new flower data", description = "Updates the data of the selected flower")
    @ApiResponse(responseCode = "201", description = "Flower updated", content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = Flowerdto.class))})
    @ApiResponse(responseCode = "404", description = "Flower not found", content = @Content)
    @PutMapping(value = "/{id}")
    public Mono<ResponseEntity<Flowerdto>> update(@RequestBody Flowerdto flowerdto, @PathVariable int id) {
        return Mono.just(flowerServiceRestClient.findById(id))
                .flatMap(updFlower -> {
                    flowerdto.setId(id);
                    return Mono.just(flowerServiceRestClient.update(flowerdto))
                            .map(flowerdto1 -> new ResponseEntity<>(flowerdto1, HttpStatus.ACCEPTED));})
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @Operation(summary = "Deletes a flower", description = "Deletes a flower using the id as a key")
    @ApiResponse(responseCode = "200", description = "Flower DELETED", content = @Content)
    @ApiResponse(responseCode = "404", description = "Flower not found", content = @Content)
    @DeleteMapping(value = "{id}")
    public Mono<ResponseEntity<Object>> delete(@PathVariable int id) {
        return Mono.just(flowerServiceRestClient.delete(id))
                .map(flowerdto1 -> new ResponseEntity<>(HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
         // TODO ver
    }
}
