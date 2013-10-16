package org.usergrid.mq;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: ApigeeCorporation
 * Date: 9/24/13
 * Time: 9:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class QueueSetTest {

    @Test
    public void testQueueSet() {
        QueueSet qs = new QueueSet();
        QueueSet qs1 = new QueueSet();
        UUID uid1 = UUID.randomUUID();
        UUID uid2 = UUID.randomUUID();
        qs.isMore();
        qs.hasMore();
        qs.setMore(true);
        QueueSet.QueueInfo qf = new QueueSet.QueueInfo();
        QueueSet.QueueInfo qf1 = new QueueSet.QueueInfo();
        qf.setPath("/path");
        qf.setUuid(UUID.randomUUID());
        qf.hashCode();
        qf.toString();
        qf.equals(null)  ;
        qf.equals(qf)  ;
        qf.equals(qs)  ;
        qf.setPath(null);
       qf.setUuid(null);
        qf1.setUuid(uid2);
        qf1.setPath("/path");
        qf.equals(qf1)  ;

        qf.setPath("/pp");
        qf1.setPath("/path");
        qf.equals(qf1);

        qf.setPath("/path");
        qf.setUuid(null);
        qf1.setUuid(UUID.randomUUID());
        qf.equals(qf1)  ;

        qf.setUuid(UUID.randomUUID());
        qf1.setUuid(UUID.randomUUID());
        qf.equals(qf1);

        qf.setUuid(uid1);
        qf1.setUuid(uid1);
        qf.equals(qf1);

        qs.getCursor();
        qs.setCursorToLastResult();

        qs.setQueues(null);
        List ql = new ArrayList();
        ql.add(qf);
        ql.add(qf1);
        qs.setQueues(ql);
        List ql1 = new ArrayList();
        ql1.add(qf);
        ql1.add(qf1);
        qs1.setQueues(ql1);
        qs.and(qs1);

    }
}
