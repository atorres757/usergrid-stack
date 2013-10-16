package org.usergrid.mq.cassandra;

import me.prettyprint.cassandra.serializers.ByteBufferSerializer;
import me.prettyprint.hector.api.mutation.Mutator;
import org.junit.Test;
import org.usergrid.CoreITSuite;
import org.usergrid.locking.LockManager;
import org.usergrid.locking.noop.NoOpLockManagerImpl;
import org.usergrid.persistence.cassandra.CassandraService;
import org.usergrid.persistence.cassandra.CounterUtils;

import java.nio.ByteBuffer;
import java.util.UUID;

import static me.prettyprint.hector.api.factory.HFactory.createMutator;

/**
 * Created with IntelliJ IDEA.
 * User: ApigeeCorporation
 * Date: 9/24/13
 * Time: 4:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class QueueManagerFactoryImplTest {
    @Test
    public void testQueueManagerFactoryImpl() {

        CassandraService cassandraService = CoreITSuite.cassandraResource.getBean( CassandraService.class );
        CounterUtils cu = new CounterUtils();
        LockManager lm = new NoOpLockManagerImpl();
        QueueManagerFactoryImpl qmfi = new QueueManagerFactoryImpl(cassandraService,cu,lm);
        try{
        qmfi.getImpementationDescription();
        }catch(Exception e){

        }
    }
}
