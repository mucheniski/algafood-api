package com.algaworks.algafood.event;

import com.algaworks.algafood.entity.Pedido;
import com.algaworks.algafood.mail.Email;
import com.algaworks.algafood.mail.EnvioEmailService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class PedidoConfirmadoListener {

    @Autowired
    private EnvioEmailService envioEmailService;

    /*
        O método é chamado automáticamente pelo spring pois fica ouvindo os eventos nesse caso esse evento é disparado ao
        ser executado o repository.save(pedido) do PedidoService, porque na classe pedido no método status confirmado o
        evento é registrado registerEvent(new PedidoConfirmadoEvent(this));

        TransactionanEventListener serve para que possamos indicar em qual momento da transação queremos que o evento seja disparado
        por padrão, se não colocarmos nada na frente é disparado após o commit

        Nesse caso informado o BEFORE_COMMIT o método só é executado antes de ser feito o commit, caso de algum erro é feito rollback
        Aí fica a critério da necessidade da api, caso o método a ser chamado não seja tão importante pode ser deixado o padrão.
        como no exemplo abaixo é só um envio de email, poderia ser deixado sem phase que seria a default AFTER_COMMIT
     */
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void pedidoConfirmado(PedidoConfirmadoEvent pedidoConfirmadoEvent) {

        Pedido pedido = pedidoConfirmadoEvent.getPedido();

        var email = Email.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado!")
                .corpo("pedido-confirmado.html")
                .variavel("pedido", pedido)
                .destinatario(pedido.getUsuarioCliente().getEmail())
                .build();

        envioEmailService.enviar(email);
    }

}
