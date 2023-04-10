package cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n03_WebClient.controller;

import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n03_WebClient.flowerdto.Flowerdto;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n03_WebClient.service.FlowerServiceRestClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * The following controller was made with non-reactive programming in order
 * to practice comparisons between reactive and non-reactive programming
 */

@RestController
@RequestMapping("/v2/flower")
@Tag(name = "Spring 5 Task 1 - Level 3", description = "The following controller was made with non-reactive programming in order\n" +
        " to practice comparisons between reactive and non-reactive programming.")
public class FlowerRestClientController {

    private static Logger LOG = LoggerFactory.getLogger(FlowerRestClientController.class);

    @Autowired
    private FlowerServiceRestClient flowerServiceRestClient;




    /**
     * Find By Id
     * @param id
     * @return ResponseEntity<Flowerdto>
     */

    @Operation(summary = "Retrives a flower using the flower id", description = "Find the selected flower using the id as the key search")
    @ApiResponse(responseCode = "200", description = "Flower found", content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = Flowerdto.class))})
    @ApiResponse(responseCode = "404", description = "Flower not found with supplied id",
            content = @Content)
    @GetMapping(value = "getOne/{id}")
    public ResponseEntity<?> findById (@PathVariable int id){
        LOG.info("Using method getFlower");
        Flowerdto flowerdto = null;
        try {
            flowerdto = flowerServiceRestClient.findById(id);
            //} catch (ResponseStatusException e){
        }catch (WebClientResponseException e){
            Map<String, Object> error = new HashMap<>();
            error.put("Message", e.getMessage());
            //error.put("Reason", e.getReason());
            return new ResponseEntity<Map<String,Object>>(error, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(flowerdto);
    }

    /**
     * FindAll
     * @return ResponseEntity<List<Flowerdto>>
     */

    @Operation(summary = "Retrives all flowers in database", description = "Find and retrives each flower existing in database")
    @ApiResponse(responseCode = "200", description = "Flowers found", content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = Flowerdto.class))})
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    @GetMapping(value = "/getAll")
    public  ResponseEntity<List<Flowerdto>> getAll(){
        LOG.info("Using method getAll to list every item on DB");
        List<Flowerdto> allFlower = flowerServiceRestClient.listAll();
        if(allFlower.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(allFlower);
    }
}
