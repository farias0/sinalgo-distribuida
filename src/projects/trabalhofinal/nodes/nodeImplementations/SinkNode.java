package projects.trabalhofinal.nodes.nodeImplementations;

import projects.trabalhofinal.nodes.messages.MessageType;
import projects.trabalhofinal.nodes.messages.WsnMessage;
import projects.trabalhofinal.nodes.timers.WsnMessageTimer;
import projects.trabalhofinal.out.Out;
import sinalgo.configuration.WrongConfigurationException;
import sinalgo.nodes.Node;
import sinalgo.nodes.messages.Inbox;
import java.awt.Graphics;
import java.awt.Color;
import sinalgo.gui.transformation.PositionTransformation;

public class SinkNode extends Node {

	private void log(String msg) {
		Out.log("SINK " + this.ID + ": " + msg);
	}

	@Override
	public void handleMessages(Inbox inbox) {
		while (inbox.hasNext()) {
			var nextMessage = inbox.next();

			if (nextMessage instanceof WsnMessage) {
				var message = (WsnMessage) nextMessage;

				if (message.type == MessageType.TO_SINK) {
					log("received message=" + message.sequenceID + " from node=" + message.source.ID + " in hops=" + message.hopsCounter);
				}
			}
		}

	}

	@NodePopupMethod(menuText = "Construir Árvore de Roteamento")
	public void buildRoutes() {
		var message = new WsnMessage(1, this, null, this, MessageType.ROUTE_CREATION);
		var timer = new WsnMessageTimer(message);
		timer.startRelative(1, this);
		log("route creation message scheduled for next round");
	}

	 public void draw(Graphics g, PositionTransformation pt, boolean highlight) {
		 this.setColor(Color.BLUE);
		 super.drawNodeAsDiskWithText(g, pt, highlight, String.valueOf(this.ID), 20, Color.RED);
	 }

	@Override
	public void preStep() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void neighborhoodChange() {
		// TODO Auto-generated method stub

	}

	@Override
	public void postStep() {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkRequirements() throws WrongConfigurationException {
		// TODO Auto-generated method stub

	}

}
