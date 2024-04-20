package com.example.cnpm.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


@NoArgsConstructor
@Component
public class SendMail {
    private JavaMailSender mailSender;

    @Autowired
    public SendMail(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMailRender(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        try {
            message.setFrom("devtooltkdclub@gmail.com");
            message.setTo(toEmail);
            message.setText(body);
            message.setSubject(subject);
            mailSender.send(message);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void sendOtpMail(String toEmail, String otp) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setTo(toEmail);
        helper.setSubject("Verify OTP");
        helper.setText("""
                <html>
                    <body>
                        <h1>Verify OTP</h1>
                        <div>
                            Your OTP will be expired in 5 minutes
                            <a href="http://localhost:8020/shop/confirm-otp?username=%s&otp=%s" target="_blank">Click here to verify</a>
                        </div>
                    </body>
                </html>
                """.formatted(toEmail, otp), true);
        mailSender.send(mimeMessage);
    }
}
