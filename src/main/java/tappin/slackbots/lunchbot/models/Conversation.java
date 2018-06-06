/**
 * (c) Midland Software Limited 2018
 * Name     : Thing.java
 * Author   : Ash Tappin
 * Date     : 06 Jun 2018
 */
package tappin.slackbots.lunchbot.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import tappin.slackbots.lunchbot.conversationstates.ConversationState;

/**
 *
 */
@Document(collection = "Conversations")
public class Conversation {

    @Id
    String id;

    String userId;

    ConversationState state;

    public Conversation(final String userId, final ConversationState state) {
        this.userId = userId;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ConversationState getState() {
        return state;
    }

    public void setState(ConversationState state) {
        this.state = state;
    }
}
