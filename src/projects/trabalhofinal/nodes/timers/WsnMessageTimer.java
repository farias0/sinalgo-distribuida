/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projects.trabalhofinal.nodes.timers;

import projects.trabalhofinal.nodes.messages.WsnMessage;
import projects.trabalhofinal.nodes.nodeImplementations.SimpleNode;
import sinalgo.nodes.timers.Timer;

/**
 *
 * @author pozza
 */
public class WsnMessageTimer extends Timer {

    private WsnMessage message = null;

    public WsnMessageTimer(WsnMessage message) {
        this.message = message;
    }

    @Override
    public void fire() {
    	message.hopsCounter++;
        node.broadcast(message);
    }

}
