/**
 * (c) Midland Software Limited 2018
 * Name     : ConversationRepo.java
 * Author   : Ash Tappin
 * Date     : 06 Jun 2018
 */
package tappin.slackbots.lunchbot.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tappin.slackbots.lunchbot.models.Conversation;

/**
 *
 */
@Repository
public interface ConversationRepo extends CrudRepository<Conversation, String>{
}
