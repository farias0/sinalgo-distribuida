/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projects.trabalhofinal.nodes.nodeImplementations;

import java.util.Random;

import projects.trabalhofinal.nodes.messages.MessageType;
import projects.trabalhofinal.nodes.messages.WsnMessage;
import projects.trabalhofinal.out.Out;
import sinalgo.configuration.WrongConfigurationException;
import sinalgo.nodes.Node;
import sinalgo.nodes.messages.Inbox;
import java.awt.Graphics;
import java.awt.Color;
import sinalgo.gui.transformation.PositionTransformation;

public class SimpleNode extends Node {
	
    private SinkNode closestSink;
    private Node nextHopToSink;
    private Integer distanceToSink = -1;
    
    private Integer lastMessageSequenceID = 0;
    
    private static final Integer MIN_INTERVAL = 100;
    private static final Integer MAX_INTERVAL = 150;
    private Integer updateInterval;
    private Integer updateRoundCounter = 0;
    private Integer updateNextId = 0;
    
    private void log(String msg) {
    	Out.log("NODE " + this.ID + ": " + msg);
    }
    
    @Override
    public void handleMessages(Inbox inbox) {
        while (inbox.hasNext()) {
            var nextMessage = inbox.next();
            
            if (!(nextMessage instanceof WsnMessage)) {
            	return;
            }
            
            var message = (WsnMessage) nextMessage;
            
            if (message.forwardingHop.equals(this)) {
                return; // is this needed?
            }
            
            message.forwardingHop = this;
            
            switch (message.type) {
	            case TO_SINK:
	            	message.destination = nextHopToSink;
	            	message.hopsCounter++;
	            	log("forwarding to node=" + nextHopToSink.ID + " aiming sink=" + closestSink.ID);
	            	send(message, nextHopToSink);
	            	return;
                case ROUTE_CREATION:
                	if (closestSink == null || distanceToSink > message.hopsCounter) {
                    	closestSink = (SinkNode) message.source;
                        nextHopToSink = inbox.getSender();
                        distanceToSink = message.hopsCounter;
                        lastMessageSequenceID = message.sequenceID;
                    } else if (lastMessageSequenceID < message.sequenceID) {
                        lastMessageSequenceID = message.sequenceID;
                    } else {
                        return;
                    }
                	log("updating and broadcasting closest sink=" + message.source.ID);
                	message.hopsCounter++;
                    broadcast(message);
                    return;
            }
        }
    }

    @Override
    public void preStep() {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void init() {
        this.updateInterval = MIN_INTERVAL + new Random().nextInt(MAX_INTERVAL - MIN_INTERVAL);
        log("created with interval=" + this.updateInterval);    
    }

    @Override
    public void neighborhoodChange() {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void postStep() {
        if (closestSink != null) {
        	updateRoundCounter++;
        	if (updateRoundCounter == updateInterval) {
        		var message = new WsnMessage(updateNextId, this, nextHopToSink, this, MessageType.TO_SINK);
        		message.hopsCounter++;
        		send(message, nextHopToSink);
        		log("initiatign update, forwarding to node=" + nextHopToSink.ID + " aiming sink=" + closestSink.ID);
        		updateNextId++;
        		updateRoundCounter = 0;
        	}
        }
    }

    @Override
    public void checkRequirements() throws WrongConfigurationException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public void draw(Graphics g, PositionTransformation pt, boolean highlight) {
    	this.setColor(Color.RED);
    	super.drawNodeAsDiskWithText(g, pt, highlight, String.valueOf(this.ID), 10, Color.WHITE);
    }
}
