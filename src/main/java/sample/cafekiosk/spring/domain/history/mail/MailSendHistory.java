package sample.cafekiosk.spring.domain.history.mail;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class MailSendHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fromMail;

    private String toEmail;
    private String subject;
    private String content;

    @Builder
    private MailSendHistory(String fromMail, String toEmail, String subject, String content) {
        this.fromMail = fromMail;
        this.toEmail = toEmail;
        this.subject = subject;
        this.content = content;
    }
}
