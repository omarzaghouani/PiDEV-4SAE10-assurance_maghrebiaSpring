package tn.esprit.examen.nomPrenomClasseExamen.service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;

    public void sendSms(String to, String messageBody) {
        // Ensure Twilio is initialized
        Twilio.init(accountSid, authToken);

        // Correct usage of PhoneNumber class
        Message message = Message.creator(
                new PhoneNumber(to),             // Recipient's phone number
                new PhoneNumber(twilioPhoneNumber), // Twilio sender number
                messageBody                      // Message content
        ).create();

        System.out.println("Message sent! SID: " + message.getSid());
    }
}