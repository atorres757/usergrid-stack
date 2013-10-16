package org.usergrid.mq.cassandra.io;

import com.fasterxml.uuid.UUIDComparator;
import me.prettyprint.hector.api.Keyspace;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.usergrid.CoreITSuite;
import java.util.SortedSet;
import org.usergrid.exception.NotImplementedException;
import org.usergrid.mq.Query;
import org.usergrid.mq.QueryProcessor;
import org.usergrid.mq.QueueQuery;
import org.usergrid.persistence.cassandra.CassandraService;
import org.usergrid.mq.QueueResults;
import org.usergrid.mq.QueryProcessor.QuerySlice;

import java.util.UUID;



/**
 * Created with IntelliJ IDEA.
 * User: ApigeeCorporation
 * Date: 8/19/13
 * Time: 3:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class FilterSearchTest {

    private UUID applicationId = UUID.randomUUID();
    private UUID queueId = UUID.randomUUID();
    private CassandraService cass = CoreITSuite.cassandraResource.getBean( CassandraService.class );
    Keyspace ko = cass.getApplicationKeyspace(applicationId);

    private static final Logger logger = LoggerFactory.getLogger(FilterSearchTest.class);

    @Test
    public void testGetResults() {
        FilterSearch fs = new FilterSearch(ko);
        String queuePath = "/a/b/c/";
        QueueQuery qq = new QueueQuery();
        qq.setTimeout(100);
        qq.setLimit(1);
        qq.setLastMessageId(queueId);
        qq.setReversed(true);

        try{
        QueueResults qr = fs.getResults(queuePath, qq);
        } catch (Exception e){
          logger.info(e.getMessage())   ;
        }
    }

    @Test
    public void testSearchQueueRange() {
        String queuePath = "/foo/bar";
        UUID oldId = UUID.randomUUID();


        QueueQuery qq = new QueueQuery();
        UUID id = UUID.randomUUID();
        qq.setConsumerId(id);
        qq.setLastMessageId(id);

        qq.withConsumer("consumer1");
        UUIDComparator uc = new UUIDComparator();
        FilterSearch fs = new FilterSearch(ko);
        Query q = new Query();
        QueryProcessor qp = new QueryProcessor(q);
        QuerySlice    qs = qp.getSliceForProperty("name")  ;
        SortedSet qr = fs.searchQueueRange(ko,queueId,null, qs, id, false, uc) ;
       try{
           QueueBounds bounds = new QueueBounds(oldId, null);
           qr = fs.searchQueueRange(ko,queueId,bounds, qs, id, false, uc) ;

            bounds = new QueueBounds(null, id);
        qr = fs.searchQueueRange(ko,queueId,bounds, qs, id, false, uc) ;
         bounds = new QueueBounds(id, null);
         qr = fs.searchQueueRange(ko,queueId,bounds, qs, id, false, uc) ;
         bounds = new QueueBounds(oldId, null);
        qr = fs.searchQueueRange(ko,queueId,bounds, qs, id, true, uc) ;
       }
       catch(Exception e){
           logger.info(e.getMessage())   ;
       }

    }


}
