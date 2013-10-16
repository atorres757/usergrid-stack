package org.usergrid.mq.cassandra;

import me.prettyprint.cassandra.serializers.ByteBufferSerializer;
import me.prettyprint.hector.api.mutation.Mutator;
import org.junit.Test;
import org.usergrid.CoreITSuite;
import org.usergrid.locking.LockManager;
import org.usergrid.locking.noop.NoOpLockManagerImpl;
import org.usergrid.mq.Message;
import org.usergrid.persistence.cassandra.CassandraService;
import org.usergrid.persistence.cassandra.CounterUtils;

import java.nio.ByteBuffer;
import java.util.UUID;

import static me.prettyprint.hector.api.factory.HFactory.createMutator;

/**
 * Created with IntelliJ IDEA.
 * User: ApigeeCorporation
 * Date: 9/24/13
 * Time: 4:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class QueueManagerImplTest {

    @Test
    public void testQueueIndexUpdate() {
        UUID queueId = UUID.randomUUID();
        UUID applicationId = UUID.randomUUID();
        CassandraService cassandraService = CoreITSuite.cassandraResource.getBean( CassandraService.class );
        CassandraMQUtils.getQueueClientTransactionKey(queueId, queueId) ;
        ByteBufferSerializer be = new ByteBufferSerializer();
        Mutator<ByteBuffer> batch = createMutator(cassandraService.getApplicationKeyspace(applicationId), be);
        CounterUtils cu = new CounterUtils();
        LockManager lm = new NoOpLockManagerImpl();
        QueueManagerImpl qmi = new QueueManagerImpl();
        Message m = new Message();
        //MessageIndexUpdate miu = new MessageIndexUpdate(m);
       // qmi.batchPostToQueue(batch, "path",m,null, 6l);
    }
}
