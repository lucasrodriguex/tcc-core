package br.com.tcc.controller;

import br.com.tcc.dto.CountryDto;
import br.com.tcc.dto.PopulationDto;
import br.com.tcc.model.Country;
import br.com.tcc.model.Population;
import br.com.tcc.repository.CountryRepository;
import br.com.tcc.repository.PopulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TccController {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private PopulationRepository populationRepository;

    @PostMapping("/insertCountries")
    public List<Country> setCountryInfo(@RequestBody List<CountryDto> countryDtos) {
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

            countryRepository.save(country);
        });

        List<Country> countries = countryRepository.findAll();

        return countries;

    }

    @PostMapping("/insertPopulation")
    public List<Country> setPopulationInfo(@RequestBody List<PopulationDto> populationDtos) {
        populationDtos.forEach(populationDto -> {

            Country country = countryRepository.findByCommonNameIgnoreCase(populationDto.getName());

            Population population = new Population();
            if(country != null) {
                population.setFrom0to9(populationDto.getFrom0to9());
                population.setFrom10to19(populationDto.getFrom10to19());
                population.setFrom20to29(populationDto.getFrom20to29());
                population.setFrom30to39(populationDto.getFrom30to39());
                population.setFrom40to49(populationDto.getFrom40to49());
                population.setFrom50to59(populationDto.getFrom50to59());
                population.setFrom60to69(populationDto.getFrom60to69());
                population.setFrom70to79(populationDto.getFrom70to79());
                population.setFrom80up(populationDto.getFrom80up());

                country.setPopulation(population);

                populationRepository.save(population);
                countryRepository.save(country);
            }

        });

        return countryRepository.findAll();
    }

    @GetMapping("/allCountries")
    public List<Country> getCountries() {
        return countryRepository.findAll();
    }

    @GetMapping("/country/{name}")
    public Country getCountries(@PathVariable String name) {
        return countryRepository.findByCommonNameIgnoreCase(name);
    }

}
