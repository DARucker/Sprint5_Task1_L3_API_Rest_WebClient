package cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n03_WebClient.service;

import java.util.List;

import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n03_WebClient.exception.FlowerServiceException;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n03_WebClient.flowerdto.Flowerdto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n03_WebClient.constants.FlowerConstants.*;
@Service
public class FlowerServiceRestClient {

    private static Logger log = LoggerFactory.getLogger(FlowerServiceRestClient.class);

    private WebClient webClient;
//    public FlowerServiceRestClient (WebClient webClient){
//        this.webClient=webClient;
//    }

    public FlowerServiceRestClient (WebClient.Builder webClientBuielder){
        this.webClient=webClientBuielder.baseUrl("http://localhost:9001").build();
    }
    public List<Flowerdto> listAll(){
        return webClient.get().uri(GET_ALL_FLOWERS)
                .retrieve()
                .bodyToFlux(Flowerdto.class)
                .collectList()
                .block();}

    public Flowerdto create (Flowerdto flowerdto){
        try{
        return webClient.post().uri(CREATE_FLOWER)
                .syncBody(flowerdto)
                .retrieve()
                .bodyToMono(Flowerdto.class)
                .block();
        } catch (WebClientResponseException ex){
            log.error("WebClientResponseException in findByID is: ", ex);
            throw ex;
        } catch (Exception ex){
            log.error("Hemos encontrado el siguiente error: ", ex);
            throw ex;
        }
    }
    public Flowerdto update (Flowerdto flowerdto) {
        try {
            return webClient.put().uri(UPDATE_FLOWER)
                    .syncBody(flowerdto)
                    .retrieve()
                    .bodyToMono(Flowerdto.class)
                    .block();
        } catch (WebClientResponseException ex) {
            log.error("Error: {}. Response body: {}", ex.getRawStatusCode(), ex.getResponseBodyAsString());
            throw ex;
        }
    }

    public Flowerdto findById(int id){
        try {
        return webClient.get().uri(GET_FLOWER_BY_ID, id)
                    .retrieve()
                    .bodyToMono(Flowerdto.class)
                    .block();
        } catch (WebClientResponseException ex){
            log.error("WebClientResponseException in findByID is: ", ex);
            throw ex;
        } catch (Exception ex){
            log.error("Hemos encontrado el siguiente error: ", ex);
            //log.error("WebClientResponseException in findByID is: ", ex);
            throw ex;
        }
    }

        public Mono<Void> delete(int id){
        return webClient.delete().uri(DELETE_FLOWER, id)
                .retrieve()
                .bodyToMono(Void.class);
    }
/**
 * Metodos para el manejo funcional de las excepciones
 */

    public Flowerdto findById_OptionWithFunctionalApproach(int id) {

        return webClient.get().uri(GET_FLOWER_BY_ID, id)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> handle4xxError(clientResponse))
            .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> handle5xxError(clientResponse))
            .bodyToMono(Flowerdto.class)
            .block();
    }

    private Mono<? extends Throwable> handle5xxError(ClientResponse clientResponse) {
        Mono<String> errorMessage = clientResponse.bodyToMono(String.class);
        return errorMessage.flatMap((message) -> {
            log.error("Error Response Code is " + clientResponse.statusCode() + "and the message is "
                    + message);
            throw new FlowerServiceException(message);
        });
    }

    private Mono<? extends Throwable> handle4xxError(ClientResponse clientResponse) {
        Mono<String> errorMessage = clientResponse.bodyToMono(String.class);
        return errorMessage.flatMap((message) -> {
            log.error("Error Response Code is " + clientResponse.statusCode() + "and the message is "
                    + message);
            throw new ClientDataException(message);
        });
    }






}
