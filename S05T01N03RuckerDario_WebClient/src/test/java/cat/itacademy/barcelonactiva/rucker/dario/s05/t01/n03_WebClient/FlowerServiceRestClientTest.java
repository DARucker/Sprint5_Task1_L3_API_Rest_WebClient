package cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n03_WebClient;

import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n03_WebClient.flowerdto.Flowerdto;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n03_WebClient.service.ClientDataException;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n03_WebClient.service.FlowerServiceRestClient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class FlowerServiceRestClientTest {

    private static final String baseUrl = "http://localhost:9001";
    private WebClient webClient = WebClient.create(baseUrl);

    FlowerServiceRestClient flowerServiceRestClient = new FlowerServiceRestClient((WebClient.Builder) webClient);

    @Test
    void getAllFlowers(){
        List<Flowerdto> flowerdtoList = flowerServiceRestClient.listAll();
        assertThat(flowerdtoList).isNotNull();
        assertThat(flowerdtoList.size()).isEqualTo(6);
    }
    @Test
    void getFlowerById(){
        Flowerdto expected = flowerServiceRestClient.findById(3);
        assertThat(expected).isNotNull();
        assertThat(expected.getFlowerType()).isEqualTo("EU");
    }
    @Test
    void getFlowerById_NotFound(){
        int id = 23;
        assertThrows(WebClientResponseException.class, () -> flowerServiceRestClient.findById(id));
    }
    @Test
    void getFlowerById_NotFound_FunctionalApproach(){
        int id = 23;
        assertThrows(ClientDataException.class, () -> flowerServiceRestClient.findById_OptionWithFunctionalApproach(id));
    }


    @Test
    void createNewFlower(){
        Flowerdto flowerdto = new Flowerdto(null, "pinya", "Chile", "");
        Flowerdto expected = flowerServiceRestClient.create(flowerdto);
        System.out.println(expected);
        assertThat(expected).isNotNull();
        assertThat(expected.getName()).isEqualTo("pinya");
        assertThat(expected.getFlowerType()).isEqualTo("no EU");
    }
    @Test
    void addNewFlower_ThenThrowsBadRequest(){
        Flowerdto flowerdto = new Flowerdto(null, "pinya", null, "");
        assertThrows(WebClientResponseException.class, () -> flowerServiceRestClient.create(flowerdto));
    }
    @Test
    void updateFlower(){
        Flowerdto flowerdto = new Flowerdto(1, "rosa rococo", "Spain", "");
        Flowerdto expected = flowerServiceRestClient.update(flowerdto);
        assertThat(expected).isNotNull();
        assertThat(expected.getName()).isEqualTo("rosa rococo");
    }
    @Test
    void deleteFlower(){
        Flowerdto flowerdto = new Flowerdto(null, "Tulip", "Spain", "");
        Flowerdto expected = flowerServiceRestClient.create(flowerdto);
        int id = expected.getId();
        Flowerdto expected1 = flowerServiceRestClient.findById(id);
        assertThat(expected1).isNotNull();
        //        Flowerdto flowerDeleted = flowerServiceRestClient.delete(id);
        //assertThat(flowerDeleted).isNull();
        //assertThrows(WebClientResponseException.class, () -> flowerServiceRestClient.findById(id));
    }
}
