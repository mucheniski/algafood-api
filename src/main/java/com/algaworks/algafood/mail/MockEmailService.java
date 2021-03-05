package com.algaworks.algafood.mail;

import lombok.extern.slf4j.Slf4j;
import org.jfree.util.Log;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MockEmailService implements EnvioEmailService {

    @Override
    public void enviar(Email email) {

        System.out.println("Chamou o mock");

        Log.info("Início do envio de e-mail");
        Log.info("=========================");
        Log.info("Destinatários:");
        email.getDestinatarios().forEach(destinatario -> Log.info(destinatario));
        Log.info("");
        Log.info("Assunto:");
        Log.info(email.getAssunto());
        Log.info("");
        Log.info("Corpo: ");
        Log.info(email.getCorpo());

    }
}
