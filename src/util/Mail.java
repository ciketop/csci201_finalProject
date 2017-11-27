package util;

import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Mail {
	private String to;
    private String from;
    private String message;
    private String subject;
    private String smtpServ;
    private String filename;
    
    public Mail(){
        
    }
    
    public Mail(String to, String from, String message, String subject, String smtpServ, String filename){
        this.to = to;
        this.from = from;
        this.message = message;
        this.subject = subject;
        this.smtpServ = smtpServ;
        this.filename = filename;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSmtpServ() {
        return smtpServ;
    }

    public void setSmtpServ(String smtpServ) {
        this.smtpServ = smtpServ;
    }
    
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    public int sendMail(){
        try
        {
            Properties props = System.getProperties();
              props.put("mail.transport.protocol", "smtp" );
              props.put("mail.smtp.starttls.enable","true" );
              props.put("mail.smtp.host",smtpServ);
              props.put("mail.smtp.port", 587);
              props.put("mail.smtp.auth", "true" );
              Authenticator auth = new SMTPAuthenticator();
              Session session = Session.getInstance(props, auth);
              Message msg = new MimeMessage(session);
              msg.setFrom(new InternetAddress(from));
              msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
              msg.setSubject(subject);
              msg.setText(message);
              msg.setHeader("MyMail", "Mr. XYZ" );
              msg.setSentDate(new Date());
              Transport.send(msg);
              System.out.println("Message sent to " + to + " OK." );
              return 1;
        }
        catch (Exception ex)
        {
          ex.printStackTrace();
          System.out.println("Exception " + ex);
          return 0;
        }
    }

    private class SMTPAuthenticator extends javax.mail.Authenticator {
        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            String username =  "liveclassinc@gmail.com";           
            String password = "liveclass123";                     
            return new PasswordAuthentication(username, password);
        }
  }

}
