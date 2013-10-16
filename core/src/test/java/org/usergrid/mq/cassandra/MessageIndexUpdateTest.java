package org.usergrid.mq.cassandra;

import me.prettyprint.cassandra.serializers.ByteBufferSerializer;
import me.prettyprint.hector.api.mutation.Mutator;
import org.junit.Test;
import org.usergrid.CoreITSuite;
import org.usergrid.mq.Message;
import org.usergrid.persistence.cassandra.CassandraService;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import static me.prettyprint.hector.api.factory.HFactory.createMutator;

/**
 * Created with IntelliJ IDEA.
 * User: ApigeeCorporation
 * Date: 9/24/13
 * Time: 4:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class MessageIndexUpdateTest {

    @Test
    public void testMessageIndexUpdate() {
        Message m = new Message();
        UUID applicationId = UUID.randomUUID();
        UUID queueId = UUID.randomUUID();
        List value = new ArrayList();
        value.add(1) ;
        m.setProperty("oldest", value);
        m.setIndexed(true);
        MessageIndexUpdate miu = new MessageIndexUpdate(m);
        miu.getMessage();
        CassandraService cassandraService = CoreITSuite.cassandraResource.getBean( CassandraService.class );

        ByteBufferSerializer be = new ByteBufferSerializer();
        Mutator<ByteBuffer> batch = createMutator(cassandraService.getApplicationKeyspace(applicationId), be);

        miu.addToMutation(batch,queueId,4l,6l );
    }
}
