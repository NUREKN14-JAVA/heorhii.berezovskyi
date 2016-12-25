package kn_14_6.berezovskyi.agent;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.StringTokenizer;

import org.hsqldb.lib.Iterator;

import kn_14_6.berezovskyi.User;
import kn_14_6.berezovskyi.db.DaoFactory;
import kn_14_6.berezovskyi.db.DatabaseException;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class RequestServer extends CyclicBehaviour {

    private static final long serialVersionUID = 1L;

    @Override
    public void action() {
        ACLMessage message = myAgent.receive();
        if (message != null) {
            if (message.getPerformative() == ACLMessage.REQUEST) {
                myAgent.send(createReply(message));
            }
            else {
                Collection<?> users = parseMessage(message);
                ((SearchAgent)myAgent).showUsers(users);
            }
        }
        else {
            block();
        }

    }

    private Collection<?> parseMessage(ACLMessage message) {
        
        Collection<User> users = new LinkedList<User>();
        String content = message.getContent();
        if (content != null && !content.isEmpty()) {
            StringTokenizer tokenizer = new StringTokenizer(content, ";");
            while (tokenizer.hasMoreTokens()) {
                String currentUser = tokenizer.nextToken();
                StringTokenizer userTokenizer = new StringTokenizer(currentUser, ",");
                if (userTokenizer.countTokens() == User.class.getDeclaredFields().length) {
                    String id = userTokenizer.nextToken();
                    String firstName = userTokenizer.nextToken();
                    String lastName = userTokenizer.nextToken();
                    String dateStr = userTokenizer.nextToken();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate dateOfBirthd;
                    try {
                        dateOfBirthd = LocalDate.parse(dateStr,formatter);
                    }
                    catch (DateTimeParseException e) {
                        dateOfBirthd = null;
                    }
                    users.add(new User(new Long(id), firstName, lastName, dateOfBirthd));
                }
            }
                
            }
        return users;
    }

    private ACLMessage createReply(ACLMessage message) {
        ACLMessage reply = message.createReply();
        String content = message.getContent();
        StringTokenizer tokenizer = new StringTokenizer(content,",");
        if (tokenizer.countTokens() ==2) {
            String firstName = tokenizer.nextToken();
            String lastName = tokenizer.nextToken();
            Collection<User> users = null;
            try {
                users = (Collection<User>) DaoFactory.getInstance().getUserDao().find(firstName,lastName);
            } catch (DatabaseException e) {
                e.printStackTrace();
                users = new ArrayList<User>(0);
            }
            StringBuffer buffer= new StringBuffer();
            for (User user: users) {
                buffer.append(user.getId()).append(",");
                buffer.append(user.getFirstName()).append(",");
                buffer.append(user.getDateOfBirthd().toString()).append(",");
                buffer.append(user.getLastName()).append(";");
            }
            reply.setContent(buffer.toString());
        }
        return reply;
    }

}
