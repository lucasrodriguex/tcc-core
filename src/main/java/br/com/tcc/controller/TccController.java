package br.com.tcc.controller;

import br.com.tcc.dto.CountryDto;
import br.com.tcc.model.Country;
import br.com.tcc.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TccController {

    @Autowired
    private CountryRepository repository;

    @PostMapping("/population")
    public List<Country> getPopulationInfo(@RequestBody List<CountryDto> countryDtos) {
        countryDtos.forEach( countryDto -> {
            Country country = new Country();
            country.setArea(countryDto.getArea());
            country.setSigla(countryDto.getCca3());

            if(countryDto.getLatlng().size() > 0) {
                country.setLatitude(countryDto.getLatlng().get(0));
                country.setLongitude(countryDto.getLatlng().get(1));
            } else {
                country.setLatitude(new Double(0));
                country.setLongitude(new Double(0));
            }

            country.setOfficialName(countryDto.getName().getOfficial());
            country.setCommonName(countryDto.getName().getCommon());

            repository.save(country);
        });

        List<Country> countries = repository.findAll();

        return countries;

    }

}
