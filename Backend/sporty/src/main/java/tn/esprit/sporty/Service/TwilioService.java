package tn.esprit.sporty.Service;/*package tn.esprit.examen.nomPrenomClasseExamen.services;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {

    @Value("${twilio.phone.number}")
    private String fromPhoneNumber; // No assignment here!

    public void sendWhatsAppMessage(String toPhoneNumber, String message) {
        Message.creator(
                new PhoneNumber("whatsapp:" + toPhoneNumber),
                new PhoneNumber("whatsapp:" + fromPhoneNumber),
                message
        ).create();
    }
}
*/