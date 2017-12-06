package br.com.tcc.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.HashMap;

@Data
@Entity
public class Country  {

    @Id
    @GeneratedValue
    private Long id;

    private String officialName;
    private String commonName;
    private String sigla;

    private Double latitude;
    private Double longitude;

    private Integer area;

    private HashMap<String, Integer> population;
//    private Integer from0to9;
//    private Integer from10to19;
//    private Integer from20to29;
//    private Integer from30to39;
//    private Integer from40to49;
//    private Integer from50to59;
//    private Integer from60to69;
//    private Integer from70to79;
//    private Integer from80up;

    private Integer total;
}
