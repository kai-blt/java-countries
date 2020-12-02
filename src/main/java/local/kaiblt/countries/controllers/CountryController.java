package local.kaiblt.countries.controllers;

import local.kaiblt.countries.models.Country;
import local.kaiblt.countries.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CountryController {
    //connect controller to repository
    @Autowired
    CountryRepository countryrepos;

    private List<Country> filterCountries(List<Country> list, CheckCountries test) {
        //Create temporary list
        List<Country> filteredList = new ArrayList<>();

        //Check if filter criteria met. If so add to list.
        for (Country c : list) {
            if (test.filter(c)) {
                filteredList.add(c);
            }
        }
        return filteredList;
    }

    // http://localhost:2019/names/all
    @GetMapping(value = "/names/all", produces = "application/json")
    public ResponseEntity<?> getAllCountries() {
        List<Country> countryList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(countryList::add);
        return new ResponseEntity<>(countryList, HttpStatus.OK);
    }

    // http://localhost:2019/names/start/u
    // Note lowercase "u" in url. Character.toUpperCase is used on the PathVariable to match uppercase country names when
    // typed this way in the url
    @GetMapping(value = "/names/start/{letter}", produces = "application/json")
    public ResponseEntity<?> getByLetter(@PathVariable char letter) {
        List<Country> countryList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(countryList::add);
        List<Country> filteredList = filterCountries(countryList, c -> c.getName().charAt(0) == Character.toUpperCase(letter));
        return new ResponseEntity<>(filteredList, HttpStatus.OK);
    }

    // http://localhost:2019/population/total

    // http://localhost:2019/population/min

    // http://localhost:2019/population/max

    // Stretch Goal http://localhost:2019/population/median
}
