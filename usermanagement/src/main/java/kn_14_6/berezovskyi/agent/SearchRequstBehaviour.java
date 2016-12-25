package kn_14_6.berezovskyi.agent;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class SearchRequstBehaviour extends Behaviour {

    private static final long serialVersionUID = 1L;
    private AID[] aids;
    private String firstName;
    private String lastName;
    
    public SearchRequstBehaviour(AID[] aids, String firstName, String lastName) {
        this.aids = aids;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public void action() {
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.setContent(firstName + "," + lastName);
        if (aids != null) {
            for (int i = 0; 1 < aids.length; i++) {
                message.addReceiver(aids[i]);
            }
            myAgent.send(message);
        }

    }

    @Override
    public boolean done() {
        return true;
    }

}
