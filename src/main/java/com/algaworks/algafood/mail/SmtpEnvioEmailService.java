package com.algaworks.algafood.mail;

import com.algaworks.algafood.exception.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class SmtpEnvioEmailService implements EnvioEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EmailProperties emailProperties;

    @Override
    public void enviar(Email email) {

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        /*
            O Helper ajuda a configurar o mimemessage de uma forma mais simples
            o MimeMessage é o email em si a ser enviado
         */
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom(emailProperties.getRemetente());
            mimeMessageHelper.setSubject(email.getAssunto());

            // O true serve para informar que é um e-mail HTML
            mimeMessageHelper.setText(email.getCorpo(), true);
            mimeMessageHelper.setTo(email.getDestinatarios().toArray(new String[0]));

            javaMailSender.send(mimeMessage);

        } catch (Exception e) {
            throw new EmailException("Não foi possível enviar o e-mail", e);
        }

    }

}
