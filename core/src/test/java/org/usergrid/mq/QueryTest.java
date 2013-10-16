package org.usergrid.mq;

import org.junit.Test;
import org.usergrid.persistence.Identifier;
import org.usergrid.persistence.Results;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ApigeeCorporation
 * Date: 9/25/13
 * Time: 10:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class QueryTest {

    private static final String SIMPLE_JSON =
            "{\"tableName\":\"Counters\",\"keyName\":\"k1\",\"columnName\":\"c1\",\"value\":1}";

    @Test
    public void testQuery() {
        Query q = new Query();
        Query q1 = new Query();


        Query.newQueryIfNull(null);
        Query.fromJsonString(SIMPLE_JSON);
        Query.searchForProperty("name", q);
        Query.fromUUID(UUID.randomUUID());
        Query.fromName("name");
        Query.fromIdentifier(q);
        q.setIdsOnly(true);
        q.isIdsOnly();

        q.setResultsLevel(Results.Level.ALL_PROPERTIES);
        q.getResultsLevel();
        q.withResultsLevel(Results.Level.ALL_PROPERTIES);
        q.withEntityType("type");
        q.getEntityType();
        q.setConnectionType("jdbc");
        q.withConnectionType("jdbc");
        q.getConnectionType();
        q.toString();
        q.hashCode();
        q.equals(q1);
        List permissions = q.getPermissions();
        q.withPermissions(permissions);
        q.addSelect("Select");
        q.addSelect(null,"output") ;
        q.addSelect("*","output") ;
        q.addSelect("select","output") ;
        q.hasFiltersForProperty("filter name");
        Results r = new Results();
        q.getSelectionResult(r) ;
        q.getSelectionResult(r);
        q.getSelectSubjects();
        q.getSelectAssignments();
        q.setMergeSelectResults(true);
        q.withMergeSelectResults(true) ;
        q.isMergeSelectResults();
        q.withStartResult(UUID.randomUUID());
        q.getStartResult();
        q.withCursor("cursor") ;
        q.withLimit(5);
        q.isLimitSet();
        q.isReversedSet();
        q.getStartTime();
        q.getFinishTime();
        q.isPad();
        q.getResolution();
        q.getUsers();
        List iList = new ArrayList();
        Identifier i = new Identifier();
        Identifier i1 = new Identifier();
        iList.add(i1);
        iList.add(i);
        q.addUser(i);
        q.setUsers(iList);
        q.addSort("");
        q.addSort("name,type");
        q.addSort("'name','test'");
        q.addSort("+name");
        q.addSort("-name");
        q.addSort("",Query.SortDirection.ASCENDING);
        q.fromJsonString("");
        q.hasSortPredicates();
        q.addFilter("type", null,i) ;
        q.addFilter("type", Query.FilterOperator.EQUAL,q) ;
        q.addFilter("connection", Query.FilterOperator.EQUAL,q) ;
        q.getEqualityFilters();
        q.removeFiltersForProperty(null);
        q.removeFiltersForProperty("type");
        q.addGroup(i);
        q.setGroups(iList);
        q.getIdentifiers();
        q.setIdentifiers(iList);
        q.getGroups();
        q.containsUuidIdentifersOnly();
        q.containsSingleUuidIdentifier();
        q.getUuidIdentifiers();
        q.getSingleUuidIdentifier();
        q.containsNameIdentifiersOnly();
        q.containsSingleNameIdentifier();
        q.getNameIdentifiers();
        q.getSingleNameIdentifier();
        q.containsEmailIdentifiersOnly();
        q.getEmailIdentifiers();
        q.containsSingleEmailIdentifier();
        q.getSingleEmailIdentifier();
        q.containsNameOrEmailIdentifiersOnly();
        q.containsSingleNameOrEmailIdentifier();
        q.getNameAndEmailIdentifiers();
        q.getSingleNameOrEmailIdentifier();
        q.getCategories();
        q.addCategory(null);
        q.addCategory("category");
        List categories = new ArrayList();
        String category = new String();
        categories.add(category);
        q.setCategories(categories);
        q.getCounterFilters();
        q.addCounterFilter("counter");

        Query.SortDirection.find("des");
        Query.SortDirection.find("+");
        Query.SortDirection.find("-");
        Query.SortDirection.find("");
        Query.SortPredicate sp = new Query.SortPredicate("name", Query.SortDirection.ASCENDING);
        Query.SortPredicate sp1 = new Query.SortPredicate("name", Query.SortDirection.find("asc"));
        sp.toFilter();
        sp.equals(sp1);
        sp.hashCode();
        sp.toString();

        Query.FilterOperator.find("name");
        Query.FilterOperator.LESS_THAN.toString();

        Query.CounterFilterPredicate.fromString("customer:customer");
        Query.CounterFilterPredicate.fromString("customer:customer:customer:customer");
        Query.CounterFilterPredicate.fromList(null) ;
        Query.CounterFilterPredicate  cfp = new Query.CounterFilterPredicate("name", i,
                i, "queue", "category");

        cfp.getCategory();
        cfp.getGroup();
        cfp.getName();
        cfp.getQueue();
        cfp.getUser();

        //Query.fromQL("order by")  ;
        Query.fromQL(null) ;

        Map<String, List<String>> params = new HashMap<String, List<String>>();
        List qlList = new ArrayList();
        qlList.add("resolution");
        params.put("resolution",qlList)
        ;
        List counterList = new ArrayList();
        counterList.add("counter");
        params.put("counter",counterList);


        List connectionList = new ArrayList();
        connectionList.add("connection");
        params.put("connection",connectionList)
        ;
        List typeList = new ArrayList();
        typeList.add("filter");
        params.put("filter",typeList);

        List sortList = new ArrayList();
        sortList.add("sort");
        params.put("sort",sortList);

        List cursorList = new ArrayList();
        cursorList.add("cursor");
        params.put("cursor",cursorList);

        List limitList = new ArrayList();
        limitList.add(1);
        params.put("limit",limitList);

        List start_timeList = new ArrayList();
        start_timeList.add(3l);
        params.put("start_time",start_timeList)            ;

        List end_timeList = new ArrayList();
        end_timeList.add(4l);
        params.put("end_time",end_timeList);


       // params.put("user",iList);

       /* List categoriesList = new ArrayList();
        categoriesList.add("categories");
        params.put("categories",sList);  */


       // params.put("group",iList);

        List reversedList = new ArrayList();
        reversedList.add(true);
        params.put("reversed",reversedList);

        List padList = new ArrayList();
        padList.add(true);
        params.put("pad",padList);

        List cnnectionList = new ArrayList();
        cnnectionList.add(true);
        params.put("update",cnnectionList);

        List startList = new ArrayList();
        startList.add(UUID.randomUUID());
        params.put("start",startList)            ;

        List posList = new ArrayList();
        posList.add(QueuePosition.CONSUMER.toString());
        params.put("pos",posList);

        List lastList = new ArrayList();
        lastList.add(UUID.randomUUID().toString());
        params.put("last",lastList)            ;

        List permissionList = new ArrayList();
        permissionList.add("permission");
        params.put("permission",permissionList);



        List consumerList = new ArrayList();
        consumerList.add(UUID.randomUUID().toString());
        params.put("consumer",consumerList);

        Query.fromQueryParams(params);
        Query.FilterPredicate.normalize(null);


        Query.FilterPredicate fp = new Query.FilterPredicate("property", Query.FilterOperator.CONTAINS, q);
           Query.FilterPredicate.normalize(fp);
           fp.setCursor("cursor");
        fp.getCursor();
        fp.hashCode();
        fp.toString();
        Query.FilterPredicate fp3 = new Query.FilterPredicate("property", Query.FilterOperator.WITHIN, q);
        Query.FilterPredicate.normalize(fp3);
        fp.equals(fp3) ;

        try{
            Query.FilterPredicate fp1 = new Query.FilterPredicate("property", null, q);

        }catch(Exception e){

        }

        try{
            Query.FilterPredicate fp2 = new Query.FilterPredicate("property", Query.FilterOperator.IN, q);

        }catch(Exception e){

        }

        try{
            Query.fromQL("order by 1");

        }catch(Exception e){

        }

    }
}
