package br.com.tcc.repository;

import br.com.tcc.model.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends CrudRepository<Country, Long> {

    Country findByOfficialName(String officialName);
    Country findByCommonNameIgnoreCase(String commonName);
    List<Country> findAll();
}
