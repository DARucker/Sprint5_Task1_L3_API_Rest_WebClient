package cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n03_WebClient.flowerdto;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Flowerdto {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer id;
    String name;
    String country;
    private String flowerType;


}
