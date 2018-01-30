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

    @JsonIgnore
    private String officialName;
    private String commonName;

    @JsonIgnore
    private String sigla;

    private Double latitude;
    private Double longitude;

    @JsonIgnore
    private Integer area;

    @OneToOne
    @JsonIgnoreProperties("id")
    private Population population;
}
