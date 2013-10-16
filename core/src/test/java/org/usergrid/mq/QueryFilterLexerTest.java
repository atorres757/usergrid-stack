package org.usergrid.mq;

import org.antlr.runtime.ANTLRStringStream;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: ApigeeCorporation
 * Date: 10/8/13
 * Time: 9:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class QueryFilterLexerTest {

    @Test
    public void testQueueIndexUpdate() {
        String qlt = "select * from ";
        ANTLRStringStream in = new ANTLRStringStream(qlt.trim());
        QueryFilterLexer lexer1 = new QueryFilterLexer();
        QueryFilterLexer lexer = new QueryFilterLexer(in);
        lexer.emitErrorMessage("error");

        try{
        lexer.mT__16();

        }catch(Exception e){

        }
        try{
            lexer.mT__17();

        }catch(Exception e){

        }
        try{
            lexer.mT__18();

        }catch(Exception e){

        }
        try{
            lexer.mT__19();

        }catch(Exception e){

        }
        try{
            lexer.mT__20();

        }catch(Exception e){

        }
        try{
            lexer.mT__21();

        }catch(Exception e){

        }
        try{
            lexer.mT__22();

        }catch(Exception e){

        }
        try{
            lexer.mT__23();

        }catch(Exception e){

        }
        try{
            lexer.mT__24();

        }catch(Exception e){

        }
        try{
            lexer.mT__25();

        }catch(Exception e){

        }
        try{
            lexer.mT__26();

        }catch(Exception e){

        }
        try{
            lexer.mT__27();

        }catch(Exception e){

        }
        try{
            lexer.mT__28();

        }catch(Exception e){

        }
        try{
            lexer.mT__29();

        }catch(Exception e){

        }
        try{
            lexer.mT__30();

        }catch(Exception e){

        }
        try{
            lexer.mT__31();

        }catch(Exception e){

        }
        try{
            lexer.mT__32();

        }catch(Exception e){

        }try{
            lexer.mT__33();

        }catch(Exception e){

        }try{
            lexer.mT__34();

        }catch(Exception e){

        }
        try{
            lexer.mT__35();

        }catch(Exception e){

        }
        try{
            lexer.mT__36();

        }catch(Exception e){

        }
        try{
            lexer.mT__37();

        }catch(Exception e){

        }
        try{
            lexer.mT__38();

        }catch(Exception e){

        }
        try{
            lexer.mT__39();

        }catch(Exception e){

        }
        try{
            lexer.mT__40();

        }catch(Exception e){

        }
        try{
            lexer.mINT();

        }catch(Exception e){

        }
        try{
            lexer.mFLOAT();

        }catch(Exception e){

        }
        try{
            lexer.mSTRING();

        }catch(Exception e){

        }
        try{
            lexer.mBOOLEAN();

        }catch(Exception e){

        }
        try{
            lexer.mUUID();

        }catch(Exception e){

        }
        try{
            lexer.mEXPONENT();

        }catch(Exception e){

        }
        try{
            lexer.mESC_SEQ();

        }catch(Exception e){

        }
        try{
            lexer.mOCTAL_ESC();

        }catch(Exception e){

        }
        try{
            lexer.mUNICODE_ESC();

        }catch(Exception e){

        }


    }
}
