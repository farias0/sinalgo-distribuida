/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projects.trabalhofinal.nodes.messages;

import sinalgo.nodes.Node;
import sinalgo.nodes.messages.Message;

/**
 *
 * @author pozza
 */
public class WsnMessage extends Message {
    //Identificador da mensagem
    public Integer sequenceID;
//Tempo de vida do Pacote
    public Integer ttl;
//No de destino
    public Node destination;
//No de origem
    public Node source;
//No que vai reencaminhar a mensagem
    public Node forwardingHop;
//Numero de saltos ate o destino
    public Integer hopsCounter = 0;
//Tipo do Pacote. 0 para Estabelecimento de Rotas e 1 para pacotes de dados
    public MessageType type = MessageType.ROUTE_CREATION;
//Construtor da Classe
   
    public WsnMessage(Integer seqID, Node source, Node destination, Node forwardingHop, MessageType type) {
        this.sequenceID = seqID;
        this.source = source;
        this.destination = destination;
        this.forwardingHop = forwardingHop;
        this.type = type;
    }

    @Override
    public Message clone() {
        WsnMessage msg = new WsnMessage(this.sequenceID, this.source,
        this.source, this.forwardingHop, this.type);
        msg.ttl = this.ttl;
        msg.hopsCounter = hopsCounter;
        return msg;
    }
}
