/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.svm.kmwebsocket.jazrs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;


/**
 *
 * @author jeevanantham
 */
@ApplicationScoped
public class SessionStore implements Serializable {

    List<Session> session;
    

    @PostConstruct
    public void init() {
        System.out.println("init session store");
        session = new ArrayList();
        String stagingServerEndpoint = "redis://192.168.11.167:6379";                     
    }

    public List<Session> getSession() {
        return session;
    }  

}
