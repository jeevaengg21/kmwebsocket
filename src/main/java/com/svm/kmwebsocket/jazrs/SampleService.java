/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.svm.kmwebsocket.jazrs;

import com.svm.kmwebsocket.CustomerEndpoint;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author jeevanantham
 */
@Path("/sample")
@RequestScoped
public class SampleService implements Serializable {

    @Inject
    CustomerEndpoint customerEndpoint;

    @GET
    @Path("/welcome")
    public String ping() {
        try {
            customerEndpoint.sendWelcomeMessage();
        } catch (Exception ex) {
            Logger.getLogger(SampleService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "done :: " + getEcsTaskId();
    }

    @GET
    @Path("/container/taskid")
    public String getEcsTaskId() {
        return (String) System.getProperty("ECS_CONTAINER_TASK_ID", "N/A");
    }

    @GET
    @Path("/container/metainfo")
    public String getEcsMetainfo() {
        return (String) System.getProperty("ECS_CONTAINER_METADATA_INFO", "N/A");
    }
}
