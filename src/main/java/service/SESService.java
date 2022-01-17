package service;

import repository.SESRepository;
import software.amazon.awssdk.services.ses.model.*;
import java.util.Set;

public final class SESService {
    private static SESService INSTANCE;

    public static SESService getInstance() {
        if(INSTANCE == null)
            INSTANCE = new SESService();
        return INSTANCE;
    }

    public void sendMsg(Set<String> uniqueKeys) {
        String sender = "sender@mail.com";
        String recipient = "recipient@mail.com";

        // Set the HTML body.
        StringBuilder bodyHTML = new StringBuilder("<html> <head></head> <body><p> The following images contains PPE gear " + "<ol> ");

        // Persist the data into a DynamoDB table.
        for (String myKey : uniqueKeys)
            bodyHTML.append("<li> ").append(myKey).append("</li>");

        bodyHTML.append("</ol></p></body></html>");
        Destination destination = Destination.builder()
                .toAddresses(recipient)
                .build();

        Content content = Content.builder()
                .data(bodyHTML.toString())
                .build();

        Content sub = Content.builder()
                .data("PPE Information")
                .build();

        Body body = Body.builder()
                .html(content)
                .build();

        Message msg = Message.builder()
                .subject(sub)
                .body(body)
                .build();

        SendEmailRequest emailRequest = SendEmailRequest.builder()
                .destination(destination)
                .message(msg)
                .source(sender)
                .build();

        try {
            System.out.println("Attempting to send an email through Amazon SES " + "using the AWS SDK for Java...");
            SESRepository.getInstance().sendEmail(emailRequest);

        } catch (SesException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }
}
