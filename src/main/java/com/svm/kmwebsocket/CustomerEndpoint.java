/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.svm.kmwebsocket;

import com.svm.kmwebsocket.jazrs.SessionStore;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public void greetCustomer(Session session, String name) {
        System.out.print("Preparing greeting for customer '" + name + "' ...");
        session.getAsyncRemote().sendText("Hello, " + name + "!" + System.getenv());
        session.getAsyncRemote().sendText("Props :: " + System.getProperties());
        session.getAsyncRemote().sendText(getMetaInfo());
    }

    private String getMetaInfo() {
        StringBuilder response = new StringBuilder();
        BufferedReader in = null;
        HttpURLConnection con = null;
        DataOutputStream wr = null;
        String inputLine;
        String url = null;
        try {
            url = (String) System.getenv().get("ECS_CONTAINER_METADATA_URI_V4");
            con = (HttpURLConnection) new URL(url).openConnection();
            con.setConnectTimeout(Integer.valueOf(System.getProperty("http_con_timeout", "30000")));
            con.setConnectTimeout(Integer.valueOf(System.getProperty("http_read_timeout", "30000")));
            con.setDoOutput(true);
            in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception connecting URL :: " + url);
        } finally {
            if (wr != null) {
                try {
                    wr.close();
                } catch (IOException ex) {
                    Logger.getLogger(CustomerEndpoint.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(CustomerEndpoint.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (con != null) {
                con.disconnect();
            }

        }
        return "Error";

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
