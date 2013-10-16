package org.usergrid.mq;

import org.junit.Test;

import java.util.EnumSet;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: ApigeeCorporation
 * Date: 9/24/13
 * Time: 7:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class QueueTest {

    @Test
    public void testQueue() {
        Queue q = new Queue("/");
        q.toString();
        q.setCreated(1l);
        q.getCreated();
        q.setModified(2l);
        q.getModified();
        Queue.getDestination(null);
        Queue.getDestination("/path");
        Queue.getQueueId(UUID.randomUUID().toString()) ;
        Queue.getQueuePathSegments("");
        Queue.getQueueId(null)     ;
        Queue.getQueueId("qqqqqdfsdfdfsdfsndmfnmsdnfmsdnmgnm")     ;
        Queue.getQueueId("/qqqqqdfsdf/dfsdfsndmfnmsdn/fmsdnmgnm/")     ;
        Queue.getQueueId("/qqqqqdfsdfsdfsdfsndmfnmsdngfmsdnmgn/")     ;
        Queue.normalizeQueuePath("");
        q.setFloatProperty("fname", 2);
        q.getFloatProperty("fname");
        q.setDoubleProperty("dname", 2.3);
        q.getDoubleProperty("dname");
        q.setBooleanProperty("bname", true);
        q.getBooleanProperty("bname")   ;
        q.setIntProperty("iname", 2);
        q.getIntProperty("iname");
        q.setLongProperty("lname", 1l);
        q.getLongProperty("lname") ;
        q.setByteProperty("byname",(byte)0xFE);
        q.getByteProperty("byname");
        q.setObjectProperty("oname", q);
        q.getObjectProperty("oname");
        q.setShortProperty("shname", (short)1);
        q.getShortProperty("shname");
        q.setStringProperty("sname","string");
        q.getStringProperty("sname");
        QueuePosition.find(null);
        QueuePosition.find("name") ;

    }
}
