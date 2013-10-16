package org.usergrid.mq;

import org.junit.Test;
import org.usergrid.persistence.Entity;
import org.usergrid.persistence.EntityFactory;
import java.util.List;
import java.util.ArrayList;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: ApigeeCorporation
 * Date: 9/24/13
 * Time: 11:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class QueryProcessorTest {

    @Test
    public void testGetQuery() {
        Query q = new Query();
        QueryProcessor qp = new QueryProcessor(q);
        qp.getSlices();
        qp.getFilters();
        qp.getSorts();
        qp.getCursor();
        qp.getQuery();
        QueryProcessor.RangeValue rv = new QueryProcessor.RangeValue((byte)9,q, true);
        QueryProcessor.RangeValue rv1 = new QueryProcessor.RangeValue((byte)5,q, true);
        ByteBuffer bytes = ByteBuffer.allocate(32);
        QueryProcessor.QuerySlice qs = new QueryProcessor.QuerySlice("name", Query.FilterOperator.WITHIN, q,rv, rv, bytes, true);
        QueryProcessor.QuerySlice qs1 = new QueryProcessor.QuerySlice("name1", Query.FilterOperator.WITHIN, q,rv, rv, bytes, true);

        List qsList = new ArrayList();
        qsList.add(qs);
        qsList.add(qs1);

        Query.FilterPredicate fp = new Query.FilterPredicate("property", Query.FilterOperator.IN, q);
        Query.FilterPredicate fp2 = new Query.FilterPredicate("property", Query.FilterOperator.GREATER_THAN_OR_EQUAL, "*");
        Query.FilterPredicate fp1 = new Query.FilterPredicate("property", Query.FilterOperator.GREATER_THAN_OR_EQUAL, "a*");
         qp.getRangeForFilter(fp2);
        qp.getRangeForFilter(fp1);
        qp.getRangeForFilter(fp);
       // qs.setPropertyName("name1");
       qs.setStart(qs.getStart());
       qs.setFinish(rv);
        qs.setValue(q);
        qs.getValue();
        qs.setCursor(bytes);
        qs.setReversed(true);
        qs.hashCode();
        qs.equals(qs)  ;
        qs.equals(q)  ;
        qs.equals(null)  ;



        qs.setPropertyName(null);
        qs.equals(qs1)  ;
        qs.setPropertyName("name");
        qs1.setPropertyName("name1");
        qs.equals(qs1)  ;

        qs.setPropertyName("name");
        qs1.setPropertyName("name");
        qs.setStart(null);
        qs.equals(qs1)  ;
        qs.setStart(rv);
        qs1.setStart(rv1);
        qs.equals(qs1)  ;
        qs1.setStart(rv);
        qs.equals(qs1)  ;

        qs.setFinish(null);
        qs.equals(qs1)  ;
        qs.setFinish(rv);
        qs1.setFinish(rv1);
        qs.equals(qs1)  ;

        Entity entity = EntityFactory.newEntity(null, "user");
        Entity entity1 = EntityFactory.newEntity(UUID.randomUUID(), "user");
        Entity entity2 = EntityFactory.newEntity(UUID.randomUUID(), "user1");
        List<Entity> entities = new ArrayList();
        entities.add(entity);
        entities.add(entity1);
        entities.add(entity2);
        Query.SortPredicate sp = new Query.SortPredicate("name", Query.SortDirection.ASCENDING);
        Query.SortPredicate sp1 = new Query.SortPredicate("name", Query.SortDirection.ASCENDING);
        Query.SortPredicate sp2 = null;
        q.addSort(sp);
        q.addSort(sp1);
        q.addSort(sp2);

        QueryProcessor qp1 = new QueryProcessor(q);
        qp1.getSorts();
        qp1.sort(entities);


        QueryProcessor.RangeValue r = new QueryProcessor.RangeValue((byte)0xFE,(Comparable)entity1,true);
        QueryProcessor.RangeValue r1 = new QueryProcessor.RangeValue((byte)0xFE,(Comparable)entity1,true);
        QueryProcessor.RangeValue.compare(null,null,true) ;
        QueryProcessor.RangeValue.compare(null,r,true) ;
        QueryProcessor.RangeValue.compare(r1,r,true) ;
        r.setCode((byte)0xFE) ;
        r.setValue(q);
        r.setInclusive(false);
        r.equals(r1) ;
        r.equals(null) ;
        r.equals(q) ;

        r.setInclusive(true);
        r.setValue(null);
        r.equals(r1) ;
        r.setValue((Comparable)entity1);
        r1.setValue((Comparable)entity2);
        r.compareTo(r1, true);
        r.equals(r1) ;
        r1.setValue((Comparable)entity1);
        r.equals(r1) ;
        r.compareTo(r1, true);

        r.setCode((byte)0xFF) ;
        r.compareTo(null, true);
        r.compareTo(r1, true);
        r.setCode((byte)0xFE) ;
        //r.setValue((Comparable)entity1);
        r.setInclusive(false);
       // r1.setValue((Comparable)entity2);
        r1.setInclusive(true);
        r.compareTo(r1, true);
        r.compareTo(r1, false);

        QueryProcessor.RangePair rp =    new  QueryProcessor.RangePair(r,r1);
        QueryProcessor.RangePair rp1 =    new  QueryProcessor.RangePair(r,r1);
        rp.setStart(r);
        rp.getStart();
        rp.setFinish(r1);
        rp.getFinish();
        rp.hashCode();

        rp.equals(rp);
        rp.equals(null);
        rp.equals(r);

        rp.setFinish(null);
        rp.equals(rp1) ;
        rp.setFinish(r);
        rp1.setFinish(r1);
        rp.equals(rp1) ;

        rp.setFinish(r);
        rp1.setFinish(r);
        rp.setStart(null);
        rp.equals(rp1) ;
        rp.setStart(r);
        rp1.setStart(r1);
        rp.equals(rp1) ;
        rp1.setStart(r);
        rp.equals(rp1) ;
    }
}
