package br.com.tcc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Population {

    @Id
    @GeneratedValue
    private Long id;

    private Integer from0to9;
    private Integer from10to19;
    private Integer from20to29;
    private Integer from30to39;
    private Integer from40to49;
    private Integer from50to59;
    private Integer from60to69;
    private Integer from70to79;
    private Integer from80up;
    private Integer total;


    public Integer getTotal() {
        return from0to9 + from10to19 + from20to29
                + from30to39 + from40to49 + from50to59 + from60to69 + from70to79 + from80up;
    }

}
