package br.com.tcc.dto;

import lombok.Data;

import java.util.List;

@Data
public class CountryDto {

    private CountryNameDto name;
    private String cca3;
    private List<Double> latlng;
    private Integer area;
    private Integer from0to9;
    private Integer from10to19;
    private Integer from20to29;
    private Integer from30to39;
    private Integer from40to49;
    private Integer from50to59;
    private Integer from60to69;
    private Integer from70to79;
    private Integer from80up;
}
