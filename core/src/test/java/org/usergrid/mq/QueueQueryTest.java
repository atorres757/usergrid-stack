package org.usergrid.mq;

import org.junit.Test;
import org.usergrid.utils.JsonUtils;

import java.util.*;

import static org.usergrid.utils.ClassUtils.cast;
import static org.usergrid.utils.MapUtils.toMapList;

/**
 * Created with IntelliJ IDEA.
 * User: ApigeeCorporation
 * Date: 9/24/13
 * Time: 9:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class QueueQueryTest {
    private static final String SIMPLE_JSON =
            "{\"tableName\":\"Counters\",\"keyName\":\"k1\",\"columnName\":\"c1\",\"value\":1}";


    @Test
    public void testQueueQuery() {
        Query q = new Query("type") ;
        QueueQuery qq = new QueueQuery(q);
        QueueQuery qq1 = new QueueQuery(qq);
        UUID uid = UUID.randomUUID();
        qq.withConsumerId(UUID.randomUUID()) ;
        qq.setLastTimestamp(1l);
        qq.getLastTimestamp();
        qq.withLastTimestamp(2l);
        qq.withLastMessageId(UUID.randomUUID()) ;
        qq.isPositionSet();
        qq.withTimeout(2l);
        qq.setUpdate(true);
        qq.withUpdate(true);
        qq.isUpdate();
        qq.withSynchronized(false);
        qq.isSynchronized();

        qq.withPosition(QueuePosition.START) ;
        qq.setPosition(null);

        qq.withPositionInUnset(QueuePosition.END) ;

        QueueQuery.getConsumerId(null);
        QueueQuery.getConsumerId(uid.toString());
        QueueQuery.getConsumerId("");
        QueueQuery.fromEmail("email@email.com");
        QueueQuery.newQueryIfNull(null);
        QueueQuery.newQueryIfNull(q) ;
        QueueQuery.newQueryIfNull(qq) ;
        QueueQuery.fromQL(null);
        QueueQuery.fromQL("query");

       // Object o = JsonUtils.parse(SIMPLE_JSON);
        Map<String, List<String>> params = new HashMap<String, List<String>>();
        List qlList = new ArrayList();
        qlList.add("ql");
        params.put("ql",qlList)
        ;
        List typeList = new ArrayList();
        typeList.add("type");
        params.put("type",typeList);

        List reversedList = new ArrayList();
        reversedList.add(true);
        params.put("synchronized",reversedList);

        List connectionList = new ArrayList();
        connectionList.add(true);
        params.put("update",connectionList);

        List startList = new ArrayList();
        startList.add(UUID.randomUUID());
        params.put("start",startList)            ;

        List posList = new ArrayList();
        posList.add(QueuePosition.CONSUMER.toString());
        params.put("pos",posList);

        List limitList = new ArrayList();
        limitList.add(UUID.randomUUID().toString());
        params.put("last",limitList)            ;

        List permissionList = new ArrayList();
        permissionList.add("permission");
        params.put("permission",permissionList);

        List start_timeList = new ArrayList();
        start_timeList.add(3);
        params.put("time",start_timeList)            ;

        List end_timeList = new ArrayList();
        end_timeList.add(4l);
        params.put("timeout",end_timeList);

        List consumerList = new ArrayList();
        consumerList.add(UUID.randomUUID().toString());
        params.put("consumer",consumerList);

        qq.fromQueryParams(params);

    }
}
