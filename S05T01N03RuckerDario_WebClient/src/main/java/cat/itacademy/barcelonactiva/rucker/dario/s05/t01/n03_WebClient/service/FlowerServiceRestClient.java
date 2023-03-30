package cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n03_WebClient.service;

import java.util.List;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n03_WebClient.flowerdto.Flowerdto;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import static cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n03_WebClient.constants.FlowerConstants.*;

//@Slf4j
public class FlowerServiceRestClient {

    private static Logger log = LoggerFactory.getLogger(FlowerServiceRestClient.class);


    // http://localhost:9001/flower/update/{id}
    // http://localhost:9001/flower/getAll
    // http://localhost:9001/flower/add
    // http://localhost:9001/flower/getOne/{id}
    // http://localhost:9001/flower/delete/{id}

    private WebClient webClient;
    public FlowerServiceRestClient (WebClient webClient){
        this.webClient=webClient;
    }

    public List<Flowerdto> listAll(){
        return webClient.get().uri(GET_ALL_FLOWERS)
                .retrieve()
                .bodyToFlux(Flowerdto.class)
                .collectList()
                .block();
    }

    public Flowerdto create (Flowerdto flowerdto){
        try{
        return webClient.post().uri(CREATE_FLOWER)
                .syncBody(flowerdto)
                .retrieve()
                .bodyToMono(Flowerdto.class)
                .block();
        } catch (WebClientResponseException ex){
            log.error("El codito del error encontrado en esta peticion hecha con mucha dedicacion es: {}. Response body: {}", ex.getRawStatusCode(), ex.getResponseBodyAsString() );
            log.error("WebClientResponseException in findByID is: ", ex);
            throw ex;
        } catch (Exception ex){
            log.error("Hemos encontrado el siguiente error: ", ex);
            //log.error("WebClientResponseException in findByID is: ", ex);
            throw ex;
        }
    }
    public Flowerdto update (Flowerdto flowerdto){
        return null;
    }


    public Flowerdto findById(int id){
        try {
        return webClient.get().uri(GET_FLOWER_BY_ID, id)
                    .retrieve()
                    .bodyToMono(Flowerdto.class)
                    .block();
        } catch (WebClientResponseException ex){
            log.error("El codito del error encontrado en esta peticion hecha con mucha dedicacion es: {}. Response body: {}", ex.getRawStatusCode(), ex.getResponseBodyAsString() );
            log.error("WebClientResponseException in findByID is: ", ex);
            throw ex;
        } catch (Exception ex){
            log.error("Hemos encontrado el siguiente error: ", ex);
            //log.error("WebClientResponseException in findByID is: ", ex);
            throw ex;
        }
    }

    public Flowerdto delete(int id){
        return null;
    }


}
