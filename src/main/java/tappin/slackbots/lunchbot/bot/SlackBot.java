/**
 * (c) Midland Software Limited 2018
 * Name     : SlackBot.java
 * Author   : Ash Tappin
 * Date     : 06 Jun 2018
 */
package tappin.slackbots.lunchbot.bot;

import me.ramswaroop.jbot.core.slack.Bot;
import me.ramswaroop.jbot.core.slack.Controller;
import me.ramswaroop.jbot.core.slack.EventType;
import me.ramswaroop.jbot.core.slack.models.Event;
import me.ramswaroop.jbot.core.slack.models.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import tappin.slackbots.lunchbot.conversationstates.ConversationState;
import tappin.slackbots.lunchbot.models.Conversation;
import tappin.slackbots.lunchbot.models.PlaceResponse;
import tappin.slackbots.lunchbot.repositories.ConversationRepo;
import tappin.slackbots.lunchbot.services.YelpService;

import java.util.List;

/**
 *
 */
@Component
public class SlackBot extends Bot {

    @Value("${slackBotToken}")
    private String slackBotToken;

    @Autowired
    ConversationRepo conversationRepo;

    @Autowired
    YelpService yelpService;

    private static final Logger logger = LoggerFactory.getLogger(SlackBot.class);

    @Override
    public String getSlackToken() {
        return slackBotToken;
    }

    @Override
    public Bot getSlackBot() {
        return this;
    }

    @Controller(events = {EventType.DIRECT_MESSAGE}, pattern= "hello|hi")
    public void onReceiveDirectMessage(WebSocketSession session, Event event) {

        conversationRepo.save(new Conversation(event.getUserId(), ConversationState.GREETING));

        reply(session, event, new Message(
                "Oh hello there, I am " + slackService.getCurrentUser().getName()));
    }

    @Controller(events = {EventType.DIRECT_MENTION, EventType.DIRECT_MESSAGE})
    public void onReceiveAnyOtherMessage(WebSocketSession session, Event event) {
       // reply(session, event, new Message("I am afraid I don't understand what you are saying. I'll learn one day..."));
    }

    @Controller(events = {EventType.DIRECT_MENTION, EventType.DIRECT_MESSAGE, EventType.MESSAGE}, pattern = "vegan")
    public void onReceiveVeganMessage(WebSocketSession session, Event event) {
        List<PlaceResponse> veganPlaces = yelpService.getVeganPlaces();
        reply(session, event, new Message("Vegan? You may want to eat at these (they sell good :apple:s and :watermelon:s):" + convertListOfPlacesToMessageString(veganPlaces)));
    }

    @Controller(events = {EventType.DIRECT_MENTION, EventType.DIRECT_MESSAGE, EventType.MESSAGE}, pattern = "gluten free|gluten-free")
    public void onReceiveGlutenFreeMessage(WebSocketSession session, Event event) {
        List<PlaceResponse> glutenFreePlaces = yelpService.getGlutenFreePlaces();
        reply(session, event, new Message("These places offer free gluten: " + convertListOfPlacesToMessageString(glutenFreePlaces)));
    }

    @Controller(events = {EventType.DIRECT_MENTION, EventType.DIRECT_MESSAGE}, pattern = "throw up|What do you think of McDonalds?")
    public void onReceiveThrowUpMessage(WebSocketSession session, Event event) {
        reply(session, event, new Message(":face_vomiting:"));
    }

    @Controller(events = {EventType.DIRECT_MENTION, EventType.DIRECT_MESSAGE, EventType.MESSAGE}, pattern = "best beer")
    public void onReceiveBestBeerMessage(WebSocketSession session, Event event) {
        List<PlaceResponse> beerPlaces = yelpService.getBeerPlaces();
        reply(session, event, new Message("I am an expert on :beer:! Leave work now and head over to one of these:" + convertListOfPlacesToMessageString(beerPlaces)));
    }

    @Controller(events = {EventType.DIRECT_MENTION}, pattern = "Be quiet|Shut up")
    public void onReceiveMessageToSilenceLunchbot(WebSocketSession session, Event event) {
        reply(session, event, new Message("Okay, shutting up now."));
    }

    private String convertListOfPlacesToMessageString(List<PlaceResponse> places) {
        StringBuilder stringBuilder = new StringBuilder("\n\n");
        places.stream().limit(10).forEach(place -> {
            stringBuilder.append(" - " + place.getName() + " (" + place.getUrl() +")");
            stringBuilder.append("\n "+ getStars(place.getRating().intValue()));
            stringBuilder.append("\n\n");
        });
        return stringBuilder.toString();
    }

    private String getStars(final Integer rating) {

        StringBuilder starString = new StringBuilder();

        for (int numberOfStars = 0; numberOfStars <= rating; numberOfStars++) {
            starString.append(":star:");

        }
        return starString.toString();
    }
}
