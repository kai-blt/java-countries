package local.kaiblt.countries.controllers;

import local.kaiblt.countries.models.Country;
import local.kaiblt.countries.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CountryController {
    //connect controller to repository
    @Autowired
    CountryRepository countryrepos;


    // http://localhost:2019/names/all
    @GetMapping(value = "/names/all", produces = "application/json")
    public ResponseEntity<?> getAllCountries() {
        List<Country> countryList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(countryList::add);
        return new ResponseEntity<>(countryList, HttpStatus.OK);
    }

    // http://localhost:2019/names/start/u

    // http://localhost:2019/population/total

    // http://localhost:2019/population/min

    // http://localhost:2019/population/max

    // Stretch Goal http://localhost:2019/population/median
}
