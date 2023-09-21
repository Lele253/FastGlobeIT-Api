/* package apis.Manga.API.Service;

import lombok.Data;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Data
public class EmailService {

    private final String adressToAusbildungscockpit = "leandro-graf.de/ausbildungscockpit";
    private final JavaMailSender javaMailSender;

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("lelegraf1503@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        javaMailSender.send(message);
    }

    public void sendHtmlEmailToAzubisCausedByNewReports(String email, String name, String von, String bis) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            String subject = "Neuer Bericht in deinem Ausbildungscockpit";
            String html = "<h1>Hallo " + name + ", </h1>" +
                    "<p>Du hast einen neuen Bericht in deinem Ausbildungscockpit. " +
                    "Dieser geht vom " + von + " bis zum " + bis + ". <br><br></p>" +
                    "<p>Besuche jetzt das Ausbildungscockpit und bearbeite deinen Wochenbericht auf</p>" +
                    "<a href=\"https://" + adressToAusbildungscockpit + "\">" + adressToAusbildungscockpit + " <br><br><br></a>" +
                    "<i>Diese Email wurde automatisch versendet.</i>";


            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(html, true);
            javaMailSender.send(message);
            System.out.println("Email an " + email + " wurde versendet");
        } catch (MessagingException e) {
            System.out.println("Email an " + email + " konnte nicht versendet werden: " + e);
        }

    }


    public void sendHtmlEmailToAzubisCausedByNewGeneratedAccount(String name, String email, String password) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            String subject = "Ausbildungscockpit: Es wurde ein neuer Account für dich erstellt";
            String html = "<h1>Hallo " + name + ", </h1>" +
                    "<p> Es wurde für dich ein Account erstellt. Du kannst dich mit den folgenden Daten anmelden " +
                    "und dein Passwort ändern. <br><br></p>" +
                    " Email: " + email + "<br>" +
                    " Passwort: " + password +
                    "<p> <br><br> Besuche jetzt das Ausbildungscockpit und bearbeite deine Wochenberichte auf </p>" +


                    "<a href=\"https://" + adressToAusbildungscockpit + "\">" + adressToAusbildungscockpit + " <br><br><br></a>" +
                    "<i>Diese Email wurde automatisch versendet.</i>";


            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(html, true);
            javaMailSender.send(message);
            System.out.println("Email an " + email + " wurde versendet");
        } catch (MessagingException e) {
            System.out.println("Email an " + email + " konnte nicht versendet werden: " + e);
        }

    }

    public void sentHtmlEmailToAusbilderCausedByNewReport(Azubi azubi, Bericht bericht) {

        for (Ausbilder ausbilder : azubi.getAusbilder()) {

            try {
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);

                String subject = "Ausbildungscockpit: Es gibt neue Berichte zu korrigieren";

                String html = "<h1>Hallo " + ausbilder.getName() + ", </h1>" +
                        "<p> Es gibt neue Berichte in deinem Ausbildungscockpit. <br>" +
                        azubi.getName() + " hat einen neuen Bericht für dich Freigegeben. <br>" +
                        "Der Bericht geht vom " + bericht.getVon() + " bis zum  " + bericht.getBis() + ". </p>" +
                        "<p> <br> Besuche jetzt das Ausbildungscockpit und sehen Sie sich die Berichte an auf </p>" +
                        "<a href=\"https://" + adressToAusbildungscockpit + "\">" + adressToAusbildungscockpit + " <br><br><br></a>" +
                        "<i>Diese Email wurde automatisch versendet.</i>";


                helper.setTo(ausbilder.getEmail());
                helper.setSubject(subject);
                helper.setText(html, true);
                javaMailSender.send(message);
                System.out.println("Email an " + ausbilder.getEmail() + " wurde versendet");
            } catch (MessagingException e) {
                System.out.println("Email an " + ausbilder.getEmail() + " konnte nicht versendet werden: " + e);
            }

        }

    }

    public void sentHtmlEmailToAzubiCausedByRefusedReport(Ausbilder ausbilder, Bericht bericht) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            String subject = "Ausbildungscockpit: Ein Bericht wurde zurückgewiesen";
            String html = "<h1>Hallo " + bericht.getName() + ", </h1>" +
                    "<p> " + ausbilder.getName() + " hat deinen Bericht zurückgewiesen. <br>" +
                    "Der Bericht geht vom " + bericht.getVon() + " bis zum  " + bericht.getBis() + ". <br> </p>" +
                    "Er wurde mit folgendem Kommentar zurückgewiesen: " + bericht.getKommentar() +
                    "<p> <br><br> Besuche jetzt das Ausbildungscockpit und sehen Sie sich den Bericht an auf </p>" +
                    "<a href=\"https://" + adressToAusbildungscockpit + "\">" + adressToAusbildungscockpit + " <br><br><br></a>" +
                    "<i>Diese Email wurde automatisch versendet.</i>";


            helper.setTo(ausbilder.getEmail());
            helper.setSubject(subject);
            helper.setText(html, true);
            javaMailSender.send(message);
            System.out.println("Email an " + ausbilder.getEmail() + " wurde versendet");
        } catch (MessagingException e) {
            System.out.println("Email an " + ausbilder.getEmail() + " konnte nicht versendet werden: " + e);
        }

    }
}*/

