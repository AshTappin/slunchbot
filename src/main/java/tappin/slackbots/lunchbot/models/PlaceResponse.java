/**
 * (c) Midland Software Limited 2018
 * Name     : PlaceResponse.java
 * Author   : Ash Tappin
 * Date     : 06 Jun 2018
 */
package tappin.slackbots.lunchbot.models;

/**
 *
 */
public class PlaceResponse {

    private String name;
    private String url;
    private String city;
    private Double rating;

    public PlaceResponse(final String name, final String url, final String city, final Double rating) {
        this.name = name;
        this.url = url;
        this.city = city;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getCity() {
        return city;
    }

    public Double getRating() {
        return rating;
    }
}
