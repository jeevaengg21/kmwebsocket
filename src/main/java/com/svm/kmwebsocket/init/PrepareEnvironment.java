/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.svm.kmwebsocket.init;

import com.google.gson.Gson;
import com.svm.kmwebsocket.CustomerEndpoint;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;

/**
 *
 * @author jeevanantham
 */
@ApplicationScoped
public class PrepareEnvironment implements Serializable {

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        try {
            System.out.println("CustomerEndpoint StartupListener Ready...");
            String metainfo = getMetaInfo();
            Gson gson = new Gson();
            Map metamap = gson.fromJson(metainfo, Map.class);
            Map metaLabelsMap = (Map) metamap.get("Labels");
            String task_arn = (String) metaLabelsMap.get("com.amazonaws.ecs.task-arn");
            int last_separator = task_arn.lastIndexOf("/");
            String task_id = task_arn.substring(last_separator + 1);
            System.setProperty("ECS_CONTAINER_METADATA_INFO", metainfo);
            System.setProperty("ECS_CONTAINER_TASK_ID", task_id);
        } catch (Exception e) {
        }
    }

    public String getMetaInfo() {
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
}
