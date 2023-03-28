package cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n03_WebClient;

import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n03_WebClient.flowerdto.Flowerdto;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n03_WebClient.service.FlowerServiceRestClient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

public class FlowerServiceRestClientTest {

    private static final String baseUrl = "http://localhost:9001";
    private WebClient webClient = WebClient.create(baseUrl);

    FlowerServiceRestClient flowerServiceRestClient = new FlowerServiceRestClient(webClient);

    @Test
    void getAllFlowers(){
        List<Flowerdto> flowerdtoList = flowerServiceRestClient.listAll();
        Assertions.assertThat(flowerdtoList).isNotNull();
        Assertions.assertThat(flowerdtoList.size()).isEqualTo(4);
    }

}
