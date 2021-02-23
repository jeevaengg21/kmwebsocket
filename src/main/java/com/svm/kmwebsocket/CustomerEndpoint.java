/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.svm.kmwebsocket;

import com.svm.kmwebsocket.jazrs.SessionStore;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


/**
 *
 * @author martin
 */
@ServerEndpoint("/customer")
@ApplicationScoped
public class CustomerEndpoint implements Serializable {

    @Inject
    SessionStore sessionStore;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        try {
            System.out.println("CustomerEndpoint StartupListener Ready...");
        } catch (Exception e) {
        }
    }

    @PostConstruct
    public void init() {
        System.out.println("PostConstruct ....................");
    }

    @OnMessage
    public String greetCustomer(Session session, String name) {
        System.out.print("Preparing greeting for customer '" + name + "' ...");

        return "Hello, " + name + "!";

    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        session.setMaxIdleTimeout(900000L);
        String queryString = session.getQueryString();
        System.out.println("queryString :: " + queryString);
        System.out.println("onOpen sessionid :: " + session.getId());        
        sessionStore.getSession().add(session);
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("OnClose sessionid :: " + session.getId());
    }

    public void sendWelcomeMessage() {
        try {
            System.out.println(" >>>>> session >>>" + this.sessionStore.getSession());
            for (Session session : sessionStore.getSession()) {
                if (session.isOpen()) {
                    session.getBasicRemote().sendText("Welcome !!");
                }
            }    

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
