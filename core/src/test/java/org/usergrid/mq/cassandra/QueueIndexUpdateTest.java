package org.usergrid.mq.cassandra;

import me.prettyprint.cassandra.serializers.ByteBufferSerializer;
import me.prettyprint.hector.api.mutation.Mutator;
import org.junit.Test;
import org.usergrid.CoreITSuite;
import org.usergrid.persistence.cassandra.CassandraService;
import me.prettyprint.cassandra.utils.TimeUUIDUtils;
import java.nio.ByteBuffer;
import java.util.UUID;

import static me.prettyprint.hector.api.factory.HFactory.createMutator;

/**
 * Created with IntelliJ IDEA.
 * User: ApigeeCorporation
 * Date: 9/24/13
 * Time: 4:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class QueueIndexUpdateTest {

    @Test
    public void testQueueIndexUpdate() {
        CassandraService cassandraService = CoreITSuite.cassandraResource.getBean( CassandraService.class );
        UUID queueId = UUID.randomUUID();
        UUID applicationId = UUID.randomUUID();
        UUID u1 = TimeUUIDUtils.getUniqueTimeUUIDinMillis();
        QueuesCF.QUEUE_PROPERTIES.isComposite();
        ByteBufferSerializer be = new ByteBufferSerializer();
        Mutator<ByteBuffer> batch = createMutator(cassandraService.getApplicationKeyspace(applicationId), be);

        QueueIndexUpdate qiu = new QueueIndexUpdate(batch, "path",queueId,"entry",be,u1 ) ;
        qiu.setBatch(batch);
        qiu.setQueuePath("/path");
        qiu.setQueueId(queueId);
        qiu.setEntryName("name");
        qiu.setEntryValue(be);
        qiu.getEntryValue();
        qiu.setTimestamp(5l);

        //qiu.compareIndexedValues();

    }
}
