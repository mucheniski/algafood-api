package com.algaworks.algafood.event;

import com.algaworks.algafood.entity.Pedido;
import com.algaworks.algafood.mail.Email;
import com.algaworks.algafood.mail.EnvioEmailService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoConfirmadoListener {

    @Autowired
    private EnvioEmailService envioEmailService;

    /*
        O método é chamado automáticamente pelo spring pois fica ouvindo os eventos nesse caso esse evento é disparado ao
        ser executado o repository.save(pedido) do PedidoService, porque na classe pedido no método status confirmado o
        evento é registrado registerEvent(new PedidoConfirmadoEvent(this));
     */
    @EventListener
    public void pedidoConfirmado(PedidoConfirmadoEvent pedidoConfirmadoEvent) {

        Pedido pedido = pedidoConfirmadoEvent.getPedido();

        var email = Email.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
                .corpo("pedido-confirmado.html")
                .variavel("pedido", pedido)
                .destinatario(pedido.getUsuarioCliente().getEmail())
                .build();

        envioEmailService.enviar(email);
    }

}
