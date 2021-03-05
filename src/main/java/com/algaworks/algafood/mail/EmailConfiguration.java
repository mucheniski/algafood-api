package com.algaworks.algafood.mail;

import com.algaworks.algafood.enuns.TipoSevicoEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfiguration {

    @Autowired
    private EmailProperties emailProperties;

    /*
        A definição de qual servico vai ser usado é feita no application.properties de acordo com o @Bean definido em EmailProperties
     */
    @Bean
    public EnvioEmailService envioEmailService() {
        if(emailProperties.getServicoUsado().equals(TipoSevicoEmail.MOCK.getDescricao())) {
            return new MockEmailService();
        } else if (emailProperties.getServicoUsado().equals(TipoSevicoEmail.SMTP.getDescricao())) {
            return new SmtpEnvioEmailService();
        }
        return null;
    }

}
