package br.com.tcc.repository;

import br.com.tcc.model.Population;
import org.springframework.data.repository.CrudRepository;

public interface PopulationRepository extends CrudRepository<Population, Long> {
}
