package org.usergrid.mq.cassandra.io;

import org.junit.Assert;
import org.junit.Test;
import org.usergrid.mq.QueueQuery;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: ApigeeCorporation
 * Date: 8/21/13
 * Time: 10:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class QueueBoundsTest {

    @Test
    public void testSearchQueueRange() {
        UUID oldId = UUID.randomUUID();
        UUID newId = UUID.randomUUID();
        QueueQuery qq = new QueueQuery();
        QueueBounds qb = new QueueBounds(oldId,newId);
        QueueBounds qb1 = new QueueBounds(oldId,newId);
        QueueBounds qb2 = new QueueBounds(oldId,null);
        QueueBounds qb3 = new QueueBounds(null,newId);

        qb.equals(qq);
        qb3.equals(qb2);
        qb2.equals(qb3);
        qb3.equals(qb1);
        qb1.equals(qb3);
        qb.hashCode();
        qb.equals(qb)     ;
        qb.toString();
        qb.equals(null)   ;
        qb.equals(qb1);

    }
}
