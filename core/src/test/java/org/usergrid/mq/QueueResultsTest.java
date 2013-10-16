package org.usergrid.mq;

import org.junit.Test;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: ApigeeCorporation
 * Date: 9/24/13
 * Time: 9:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class QueueResultsTest {
    @Test
    public void testQueueResults() {
        QueueResults qr = new QueueResults();
        Message m = new Message();
        QueueResults qr1 = new QueueResults(m);
        List mlist = new ArrayList();
        mlist.add(m);
        QueueResults qr2 = new QueueResults(mlist);
        qr.setPath("/path");
        qr.setMessages(null);
        qr.setMessages(mlist);
        qr.setQueue(UUID.randomUUID());
        qr.setConsumer(UUID.randomUUID());
        qr.setLast(UUID.randomUUID());
    }
}
