package tn.esprit.sporty.config;/*package tn.esprit.sporty.config;

import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class TwilioConfig {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    // Spring will automatically inject the values into the fields
    public TwilioConfig() {
        // Initialize Twilio with your credentials
        Twilio.init(accountSid, authToken);
    }
}
*/