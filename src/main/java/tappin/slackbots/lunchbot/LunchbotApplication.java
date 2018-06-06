package tappin.slackbots.lunchbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"me.ramswaroop.jbot", "tappin.slackbots.lunchbot"})
public class LunchbotApplication {

	public static void main(String[] args) {
		SpringApplication.run(LunchbotApplication.class, args);
	}
}
