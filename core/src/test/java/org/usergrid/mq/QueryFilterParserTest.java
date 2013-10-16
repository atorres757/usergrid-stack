package org.usergrid.mq;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.TokenStream;
import org.junit.Test;



/**
 * Created with IntelliJ IDEA.
 * User: ApigeeCorporation
 * Date: 10/8/13
 * Time: 9:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class QueryFilterParserTest {

    @Test
    public void testQueueIndexUpdate() {
        String qlt = "select * from ";
        ANTLRStringStream in = new ANTLRStringStream(qlt.trim());
        QueryFilterLexer lexer = new QueryFilterLexer(in);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        QueryFilterParser parser = new QueryFilterParser(tokens);

        try{
        parser.second_value();
        parser.third_value();
            parser.select_subject();
            parser.select_assign_target();
            parser.select_assign_source();
            parser.direction();
            parser.order();
            parser.select_assign();

        QueryFilterParser.second_value_return svr = new   QueryFilterParser.second_value_return();
            QueryFilterParser.third_value_return tvr = new   QueryFilterParser.third_value_return();
            QueryFilterParser.select_subject_return ssr = new   QueryFilterParser.select_subject_return();
            QueryFilterParser.select_assign_target_return satr = new   QueryFilterParser.select_assign_target_return();
            QueryFilterParser.select_assign_source_return sasr = new   QueryFilterParser.select_assign_source_return();
            QueryFilterParser.direction_return dr = new   QueryFilterParser.direction_return();
        }
                catch(Exception e){

            }

    }
}
