package org.usergrid.utils;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: ApigeeCorporation
 * Date: 9/23/13
 * Time: 12:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class VersionTest {

    private static final Logger logger = LoggerFactory.getLogger(VersionTest.class);

    @Test
    public void testVersion() {
          String STD_PAT = "^([^\\d]*?)(\\d+)(?:\\.(\\d+))?(?:\\.(\\d+))?(?:\\.(\\d+))?(.*)$";
        UUIDUtils u = new UUIDUtils();
      Version v = new Version();
        Version v1 = new Version();
        Version v2 = new Version();
        Version v3 = new Version();
        Version v4 = new Version();
      String vstring = v.toString();
        v.toString(8)  ;
        v.toString(2)  ;
        v.toString(3)  ;
        v.addBuild(2);
        v.addMajor(1);
        v4.addMajor(-1);
        v.addMinor(10);
        v4.addMinor(-7);
        v.addRevision(3);
        v4.addRevision(-3);
        v.equals(v1)     ;
        v.equals(u)     ;
        try{
        v.clone() ;
            v.parse(STD_PAT);
            v.parse("STD_PAT");


        }
        catch(Exception e)      {

        }
        v.setNumberOfComponents(0);
        v.setMajor(2);

        v.setRevision(6);
        v.toStringRaw();
        v.setBuild("2");
       // v.setBuild(7);
        v.getSuffix();

        v.setNumberOfComponents(3);

        v1.setMajor(1);
        v.setMinor("10");
        v1.setMinor(1);
        v.compareTo(v1)     ;
        v3.setMinor(9);



         v.getBuild();
        v.getMajor();
        v.getMinor();
       v.compareTo(v)     ;
        v.compareTo(v1)     ;
        v.compareTo(v2)     ;
        v.compareTo(v3)     ;
        v1.compareTo(v2)     ;
        int noc = v.getNumberOfComponents();
        v.hashCode();
        v.getPrefix();
        v.getRevision();
        v.toStringWithPrefixAndSuffix();
        v.toStringWithPrefixAndSuffix(2)  ;
        v.toStringWithPrefixAndSuffix(3)  ;

        v.toStringWithPrefixAndSuffix(8)  ;
    }

    @Test(expected = Exception.class)
    public void testMajorException() {
        Version v = new Version();
        v.setMajor(null);

    }

    @Test(expected = Exception.class)
    public void testMinorException() {
        Version v = new Version();

        v.setMinor(null);
    }

    @Test(expected = Exception.class)
    public void testBuildException() {
        Version v = new Version();

        v.setBuild(null);
    }

    @Test(expected = Exception.class)
    public void testRevisionException() {
        Version v = new Version();

        v.setRevision(null);
    }

    @Test(expected = Exception.class)
    public void testRevisionPMException() {
        Version v = new Version();
        v.setRevision("test");

    }

    @Test(expected = Exception.class)
    public void testBuildPMException() {
        Version v = new Version();
        v.setNumberOfComponents(2);
        v.setBuild("test");

    }

    @Test(expected = Exception.class)
    public void testMajorPMException() {
        Version v = new Version();
        v.setMajor("test");

    }

    @Test(expected = Exception.class)
    public void testMinorPMException() {
        Version v = new Version();

        v.setMinor("test");
    }

    @Test(expected = Exception.class)
    public void testMinorNException() {
        Version v = new Version();

        v.setMinor(-9);
        v.addMinor(8);
    }


}
