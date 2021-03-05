/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.svm.test;

import com.svm.kmwebsocket.init.PrepareEnvironment;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.verification.VerificationMode;

/**
 *
 * @author jeevanantham
 */
@RunWith(MockitoJUnitRunner.class)
public class NewEmptyJUnitTest {

    @InjectMocks
    PrepareEnvironment prepareEnvironment;

    public NewEmptyJUnitTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void hello() {
        String expectedTaskid = "c4a8c0e708bc43a2acb33e90e445bd76";
        String data = "{\"DockerId\":\"0d9376dfa2cbad137f94a08ddd10d10fa72cc5082409c9558a6bf751bb87d1a1\",\"Name\":\"kmwebsocket\",\"DockerName\":\"ecs-kmwebsocket-task-3-kmwebsocket-8292e9c99ecaa6faa501\",\"Image\":\"667996126978.dkr.ecr.ap-southeast-1.amazonaws.com/kmwebsocket:build-71e4485c-6935-4e49-a50f-3dd31ed813dd\",\"ImageID\":\"sha256:e9f0e14349cecbc725dbbd5a849e81c15c251fcf6394935433ce81ea1d9c3dea\",\"Labels\":{\"com.amazonaws.ecs.cluster\":\"arn:aws:ecs:ap-southeast-1:667996126978:cluster/websocket-cls\",\"com.amazonaws.ecs.container-name\":\"kmwebsocket\",\"com.amazonaws.ecs.task-arn\":\"arn:aws:ecs:ap-southeast-1:667996126978:task/websocket-cls/c4a8c0e708bc43a2acb33e90e445bd76\",\"com.amazonaws.ecs.task-definition-family\":\"kmwebsocket-task\",\"com.amazonaws.ecs.task-definition-version\":\"3\"},\"DesiredStatus\":\"RUNNING\",\"KnownStatus\":\"RUNNING\",\"Limits\":{\"CPU\":0,\"Memory\":0},\"CreatedAt\":\"2021-02-25T12:44:23.608707454Z\",\"StartedAt\":\"2021-02-25T12:44:24.853758024Z\",\"Type\":\"NORMAL\",\"Networks\":[{\"NetworkMode\":\"awsvpc\",\"IPv4Addresses\":[\"10.0.1.35\"],\"AttachmentIndex\":0,\"IPv4SubnetCIDRBlock\":\"10.0.1.0/24\",\"MACAddress\":\"06:1d:0e:8a:db:5e\",\"DomainNameServers\":[\"10.0.0.2\"],\"DomainNameSearchList\":[\"ap-southeast-1.compute.internal\"],\"PrivateDNSName\":\"ip-10-0-1-35.ap-southeast-1.compute.internal\",\"SubnetGatewayIpv4Address\":\"10.0.1.1/24\"}]}";
        PrepareEnvironment spyTemp = spy(prepareEnvironment);
        doReturn(null).when(spyTemp).getMetaInfo();
        spyTemp.init("test");
        String taskid = System.getProperty("ECS_CONTAINER_TASK_ID");
        System.out.println("taskid : " + taskid);
        assertEquals(expectedTaskid, taskid);
        verify(spyTemp, times(1)).getMetaInfo();
    }

}
