package tn.esprit.examen.nomPrenomClasseExamen.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.nomPrenomClasseExamen.service.TwilioService;
import com.twilio.exception.ApiException;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/sms")
public class TwilioController {

    private final TwilioService twilioService;

    // E.164 format validation pattern (e.g., +21658008122)
    private static final Pattern E164_PATTERN = Pattern.compile("^\\+\\d{10,15}$");

    public TwilioController(TwilioService twilioService) {
        this.twilioService = twilioService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendSms(@RequestParam String to, @RequestParam String message) {
        try {
            // ✅ Ensure the number starts with '+'
            if (!to.startsWith("+")) {
                to = "+" + to;
            }

            // ✅ Validate phone number format
            if (!E164_PATTERN.matcher(to).matches()) {
                return ResponseEntity.badRequest().body("❌ Invalid phone number format! Use E.164 format (e.g., +21658008122)");
            }

            // ✅ Send SMS
            twilioService.sendSms(to, message);
            return ResponseEntity.ok("✅ SMS sent successfully to " + to);

        } catch (ApiException e) {
            // ✅ Handle Twilio API errors
            return handleTwilioError(e);
        }
    }

    // ✅ Twilio Error Handling Method
    private ResponseEntity<String> handleTwilioError(ApiException e) {
        int errorCode = e.getCode();
        String errorMessage = e.getMessage();

        switch (errorCode) {
            case 21408:
                return ResponseEntity.badRequest().body("❌ Error: Number not verified for trial accounts.");
            case 21606:
                return ResponseEntity.badRequest().body("❌ Error: Invalid 'From' number. Use a valid Twilio number.");
            case 400:
                return ResponseEntity.badRequest().body("❌ Bad request: " + errorMessage);
            default:
                return ResponseEntity.badRequest().body("❌ Error sending SMS: " + errorMessage);
        }
    }
}
