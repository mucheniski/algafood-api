package com.algaworks.algafood.mail;

import com.algaworks.algafood.exception.EmailException;
import freemarker.template.Configuration;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.io.IOException;

@Service
public class SmtpEnvioEmailService implements EnvioEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EmailProperties emailProperties;

    @Autowired
    private Configuration freeMarkerConfiguration;

    @Override
    public void enviar(Email email) {

        try {

            var corpo = procesarTemplate(email);
            System.out.println("Corpo do email em SMTP ===== " + corpo);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        /*
            O Helper ajuda a configurar o mimemessage de uma forma mais simples
            o MimeMessage é o email em si a ser enviado
         */
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom(emailProperties.getRemetente());
            mimeMessageHelper.setSubject(email.getAssunto());

            // O true serve para informar que é um e-mail HTML
            mimeMessageHelper.setText(corpo, true);
            mimeMessageHelper.setTo(email.getDestinatarios().toArray(new String[0]));

            /*
               Comentado porque não foi configurado o servidor de e-mails, utilizado apenas para testes
               após configurar remover esse comentário.
            */
            // javaMailSender.send(mimeMessage);

        } catch (Exception e) {
            throw new EmailException("Não foi possível enviar o e-mail", e);
        }

    }

    private String procesarTemplate(Email email) {
        try {
            var template = freeMarkerConfiguration.getTemplate(email.getCorpo());
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, email.getVariaveis());
        } catch (Exception e) {
            throw new EmailException("Não foi possível montar o template do e-mail ", e);
        }
    }

}
