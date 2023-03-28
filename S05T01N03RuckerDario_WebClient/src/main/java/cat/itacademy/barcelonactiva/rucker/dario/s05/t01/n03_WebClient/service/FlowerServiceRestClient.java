package cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n03_WebClient.service;

import java.util.List;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n03_WebClient.flowerdto.Flowerdto;
import org.springframework.web.reactive.function.client.WebClient;

import static cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n03_WebClient.constants.FlowerConstants.GET_ALL_FLOWERS;

public class FlowerServiceRestClient {


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
        return null;
    }
    public Flowerdto update (Flowerdto flowerdto){
        return null;
    }
    public Flowerdto findById(int id){
        return null;
    }
    public Flowerdto delete(int id){
        return null;
    }


}
