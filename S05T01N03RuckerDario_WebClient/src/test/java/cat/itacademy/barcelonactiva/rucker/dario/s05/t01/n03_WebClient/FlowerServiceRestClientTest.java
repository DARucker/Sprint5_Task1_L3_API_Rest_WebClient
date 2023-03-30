package cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n03_WebClient;

import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n03_WebClient.flowerdto.Flowerdto;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n03_WebClient.service.FlowerServiceRestClient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class FlowerServiceRestClientTest {

    private static final String baseUrl = "http://localhost:9001";
    private WebClient webClient = WebClient.create(baseUrl);

    FlowerServiceRestClient flowerServiceRestClient = new FlowerServiceRestClient(webClient);

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

}
