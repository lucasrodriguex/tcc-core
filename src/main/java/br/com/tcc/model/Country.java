package br.com.tcc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.HashMap;

@Data
@Entity
public class Country  {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    private String officialName;
    private String commonName;
    private String sigla;

    private Double latitude;
    private Double longitude;

    private Integer area;

    @OneToOne
    @JsonIgnoreProperties("id")
    private Population population;
}
