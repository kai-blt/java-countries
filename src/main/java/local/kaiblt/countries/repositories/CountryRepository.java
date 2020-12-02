package local.kaiblt.countries.repositories;

import local.kaiblt.countries.models.Country;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<Country, Long> {
}
