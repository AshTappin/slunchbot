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

    public PlaceResponse(final String name, final String url, final String city) {
        this.name = name;
        this.url = url;
        this.city = city;
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
}
