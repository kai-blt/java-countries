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
    private List<Country> countryList = new ArrayList<>();

    private void refreshList() {
        countryList.clear();
        countryrepos.findAll().iterator().forEachRemaining(countryList::add);
    }

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
        refreshList();
        countryList.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        return new ResponseEntity<>(countryList, HttpStatus.OK);
    }

    // http://localhost:2019/names/start/u
    // Note lowercase "u" in url. Character.toUpperCase is used on the PathVariable to match uppercase country names when
    // typed this way in the url
    @GetMapping(value = "/names/start/{letter}", produces = "application/json")
    public ResponseEntity<?> getByLetter(@PathVariable char letter) {
        refreshList();
        List<Country> filteredList = filterCountries(countryList, c -> c.getName().charAt(0) == Character.toUpperCase(letter));
        filteredList.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        return new ResponseEntity<>(filteredList, HttpStatus.OK);
    }

    // http://localhost:2019/population/total
    @GetMapping(value = "/population/total", produces = "application/json")
    public ResponseEntity<?> getTotalPopulation() {
        refreshList();
        long totalPopulation = 0;
        for (Country c : countryList) {
            totalPopulation += c.getPopulation();
        }
        System.out.println("The Total Population is " + totalPopulation);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // http://localhost:2019/population/min
    @GetMapping(value = "/population/min", produces = "application/json")
    public ResponseEntity<?> getMinPopulation() {
        refreshList();
        countryList.sort((c1, c2) -> (int) (c1.getPopulation() - c2.getPopulation()));
        return new ResponseEntity<>(countryList.get(0), HttpStatus.OK);
    }

    // http://localhost:2019/population/max
    @GetMapping(value = "population/max", produces = "application/json")
    ResponseEntity<?> getMaxPopulation() {
        refreshList();
        countryList.sort((c1, c2) -> (int) (c2.getPopulation() - c1.getPopulation()));
        return new ResponseEntity<>(countryList.get(0), HttpStatus.OK);
    }

    // Stretch Goal http://localhost:2019/population/median
    @GetMapping(value = "/population/median", produces = "application/json")
    public ResponseEntity<?> getMedianPopulation() {
        refreshList();
        countryList.sort((c1, c2) -> (int) (c1.getPopulation() - c2.getPopulation()));
        int medianIndex = (int) Math.ceil(countryList.size() / 2.00);
        System.out.println(medianIndex);
        return new ResponseEntity<>(countryList.get(medianIndex), HttpStatus.OK);
    }
}
