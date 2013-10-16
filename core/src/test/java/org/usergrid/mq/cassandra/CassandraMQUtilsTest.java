package org.usergrid.mq.cassandra;

import me.prettyprint.cassandra.serializers.ByteBufferSerializer;
import org.junit.Test;
import me.prettyprint.hector.api.mutation.Mutator;
import org.usergrid.CoreITSuite;
import org.usergrid.mq.Message;
import org.usergrid.mq.Queue;
import org.usergrid.mq.QueuePosition;
import org.usergrid.mq.QueueQuery;
import org.usergrid.persistence.cassandra.CassandraService;

import java.nio.ByteBuffer;
import java.util.UUID;

import static me.prettyprint.hector.api.factory.HFactory.createMutator;

/**
 * Created with IntelliJ IDEA.
 * User: ApigeeCorporation
 * Date: 9/24/13
 * Time: 3:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class CassandraMQUtilsTest {

    @Test
    public void testCassandraMQUtils() {

        ByteBuffer bytes = ByteBuffer.allocate(32);
        CassandraMQUtils.serializeMessage(null) ;
        CassandraMQUtils.serializeQueue(null);
        CassandraMQUtils.getUUIDFromRowKey(bytes);
        CassandraMQUtils.getLongFromRowKey(bytes);
        CassandraMQUtils.getQueueId(null);

        QueueQuery qq = new QueueQuery();
        UUID queueId = UUID.randomUUID();
        UUID applicationId = UUID.randomUUID();
        qq.setConsumerId(null);
        qq.setPosition(QueuePosition.CONSUMER);
        CassandraMQUtils.getConsumerId(queueId, qq);

        Message m = new Message();
        m.setProperty("oldest", null);
        CassandraMQUtils.serializeMessage(m);

        Queue q = new Queue("queue");

        q.setProperty("Queue.QUEUE_NEWEST", null);
        CassandraMQUtils.serializeQueue(q);
        CassandraService cassandraService = CoreITSuite.cassandraResource.getBean( CassandraService.class );
        CassandraMQUtils.getQueueClientTransactionKey(queueId, queueId) ;
        ByteBufferSerializer be = new ByteBufferSerializer();
        Mutator<ByteBuffer> batch = createMutator(cassandraService.getApplicationKeyspace(applicationId), be);
        CassandraMQUtils.addQueueToMutator(batch, null, 8l);
        CassandraMQUtils.addMessageToMutator(batch, null, 8l);
    }
}
