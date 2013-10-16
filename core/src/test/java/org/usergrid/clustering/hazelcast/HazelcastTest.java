/*******************************************************************************
 * Copyright 2012 Apigee Corporation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.usergrid.clustering.hazelcast;

import java.util.Collection;
import java.util.Set;

import com.hazelcast.core.*;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;


@Ignore
public class HazelcastTest implements InstanceListener, MessageListener<Object> {

    private static final Logger logger = LoggerFactory
            .getLogger(HazelcastTest.class);

    ClassPathXmlApplicationContext ac;

    @Before
    public void setup() throws Exception {
        // assertNotNull(client);

        String maven_opts = System.getenv("MAVEN_OPTS");
        logger.info("Maven options: " + maven_opts);

        String[] locations = { "usergrid-test-context.xml" };
        ac = new ClassPathXmlApplicationContext(locations);

        AutowireCapableBeanFactory acbf = ac.getAutowireCapableBeanFactory();
        acbf.autowireBeanProperties(this,
                AutowireCapableBeanFactory.AUTOWIRE_BY_NAME, false);
        acbf.initializeBean(this, "testClient");

    }

    @Test
    public void doTest() {
        logger.info("do test");
        Hazelcast.addInstanceListener(this);

        ITopic<Object> topic = Hazelcast.getTopic("default");
        topic.addMessageListener(this);
        topic.publish("my-message-object");

        Collection<Instance> instances = Hazelcast.getInstances();
        for (Instance instance : instances) {
            InstanceEvent createdEvent = new InstanceEvent(InstanceEvent.InstanceEventType.CREATED,instance);
            HazelcastLifecycleMonitor hm = new HazelcastLifecycleMonitor();
            hm.init();
            hm.instanceCreated(createdEvent);
            Assert.assertEquals("CREATED", createdEvent.getEventType().name());
            logger.info("ID: [" + instance.getId() + "] Type: ["
                    + instance.getInstanceType() + "]");
            InstanceEvent destroyedEvent = new InstanceEvent(InstanceEvent.InstanceEventType.DESTROYED,instance);
            hm.instanceDestroyed(destroyedEvent);
            Assert.assertEquals("DESTROYED", destroyedEvent.getEventType().name());
        }

        Set<Member> setMembers = Hazelcast.getCluster().getMembers();
        Cluster c = Hazelcast.getCluster();
        for (Member member : setMembers) {
            MembershipEvent eventAdded = new MembershipEvent(c,member, MembershipEvent.MEMBER_ADDED);
            HazelcastLifecycleMonitor hm = new HazelcastLifecycleMonitor();
            hm.memberAdded(eventAdded);
            Assert.assertEquals(1, eventAdded.getEventType());
            logger.info("isLocalMember " + member.localMember());
            logger.info("member.inetsocketaddress "
                    + member.getInetSocketAddress());

            MembershipEvent eventRemoved = new MembershipEvent(c,member, MembershipEvent.MEMBER_REMOVED);
            hm.memberRemoved(eventRemoved);
            Assert.assertEquals(3, eventRemoved.getEventType());
        }

    }

    @Override
    public void instanceCreated(InstanceEvent event) {
        Instance instance = event.getInstance();
        logger.info("Created instance ID: [" + instance.getId() + "] Type: ["
                + instance.getInstanceType() + "]");
    }

    @Override
    public void instanceDestroyed(InstanceEvent event) {
        Instance instance = event.getInstance();
        logger.info("Destroyed isntance ID: [" + instance.getId() + "] Type: ["
                + instance.getInstanceType() + "]");

    }

    @After
    public void teardown() {
        logger.info("Stopping test");
        ac.close();
    }

    @Override
    public void onMessage(Object msg) {
        logger.info("Message received = " + msg);
    }

}
