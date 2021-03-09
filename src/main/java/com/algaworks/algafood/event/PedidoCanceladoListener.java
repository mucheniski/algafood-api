package com.algaworks.algafood.event;

import com.algaworks.algafood.entity.Pedido;
import com.algaworks.algafood.mail.EnvioEmailService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.algaworks.algafood.mail.Email;

@Component
public class PedidoCanceladoListener {

    @Autowired
    private EnvioEmailService envioEmailService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void pedidoCancelado(PedidoCanceladoEvent pedidoCanceladoEvent) {

        Pedido pedido = pedidoCanceladoEvent.getPedido();

        var email = Email.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido cancelado!")
                .corpo("pedido-cancelado.html")
                .variavel("pedido", pedido)
                .destinatario(pedido.getUsuarioCliente().getEmail())
                .build();

        envioEmailService.enviar(email);

    }

}
