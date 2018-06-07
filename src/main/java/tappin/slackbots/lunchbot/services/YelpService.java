/**
 * (c) Midland Software Limited 2018
 * Name     : YelpService.java
 * Author   : Ash Tappin
 * Date     : 06 Jun 2018
 */
package tappin.slackbots.lunchbot.services;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tappin.slackbots.lunchbot.models.PlaceResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Component
public class YelpService {

    final String yelpSearchUrl = "https://api.yelp.com/v3/businesses/search?latitude=52.2930&longitude=-1.531";

    public List<PlaceResponse> getVeganPlaces() {
        return getResponsesForSearchTerm("vegan");
    }

    public List<PlaceResponse> getBeerPlaces() {
        return getResponsesForSearchTerm("beer");
    }

    public List<PlaceResponse> getGlutenFreePlaces() {
        return getResponsesForSearchTerm("gluten free");
    }

    public List<PlaceResponse> getResponsesForSearchTerm(final String searchTerm) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer yo9dVnYC9B0T78DAGSRfXkAPBR1xZirXddFWJWP9y-UtoOuBnbQZJSgKbHbC7CGxQSFjXlAC8wppZvJe5TDWAPvSf-tiRq8cPraur_ojaBSGOGdFZwKkV8ThFjYVW3Yx");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        ResponseEntity<String> result = restTemplate.exchange(yelpSearchUrl + "&term=" + searchTerm, HttpMethod.GET, entity, String.class);

        JSONObject jsonObject = new JSONObject(result.getBody());
        ArrayList<HashMap> veganBusinesses = (ArrayList) jsonObject.toMap().get("businesses");
        List<PlaceResponse> veganPlaceNames = veganBusinesses
                .stream()
                .map(json -> new PlaceResponse(
                        json.get("name").toString(),
                        json.get("url").toString(),
                        ((HashMap)json.get("location")).get("city").toString(),
                        Double.valueOf(json.get("rating").toString())))
                .filter(place -> place.getCity().contains("Leamington Spa"))
                .collect(Collectors.toList());
        return veganPlaceNames;
    }
}
