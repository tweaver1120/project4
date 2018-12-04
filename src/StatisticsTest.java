
import static org.junit.Assert.*;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Test;


/**
 * Statistics Test class 
 * 
 * @author Tim Weaver
 * @version 2018-10-28
 * 
 */

public class StatisticsTest 
{ 
    /**
     * Tests the Statistics constructor with the date 
     * as a calendar 
     * @throws ParseException Checks for errors when parsing the file
     */
    @Test
    public void testStatistics() throws ParseException
    {
        LocalDateTime ldt = LocalDateTime.of(2008, 6, 30, 23, 30, 59, 0);
        ZonedDateTime zd = ZonedDateTime.of(ldt, ZoneId.of("UTC"));
        GregorianCalendar expected = new GregorianCalendar(2018, 8, 30, 17, 45, 0);
        Statistics test = new Statistics(10.5, "ACEME", expected, 5, StatsType.MAXIMUM);
        Statistics test2 = new Statistics(10.5, "ACEME", zd, 5, StatsType.MAXIMUM);
        
        double value = test.getValue();
        String stid = test.getStid();
        String date = test.getUTCDateTimeString();
        int station = test.getNumberOfReportingStations();
        
        double value2 = test2.getValue();
        String stid2 = test2.getStid();
        int station2 = test2.getNumberOfReportingStations();
        
        assertEquals(10.5, value, .1);
        assertEquals("ACEME", stid);
        assertEquals("2018-08-30 17:45:00 UTC", date);
        assertEquals(5, station);
        
        assertEquals(10.5, value2, .1);
        assertEquals("ACEME", stid2);
        assertEquals("2018-08-30 17:45:00 UTC", date);
        assertEquals(5, station2);
    }
    
    /**
     * Tests the string from date method
     * @throws ParseException Checks for errors when parsing the file
     */
    @Test
    public void testStringFromDate() throws ParseException
    {
        GregorianCalendar gc = new GregorianCalendar(2018, 8, 30, 17, 45, 0); 
        String expected = "2018-08-30 17:45:00 UTC";
        String actual = Statistics.createStringFromDate(gc);
        
        assertEquals(expected,actual);
    }
    
    /**
     * Tests the string from date method
     * @throws ParseException Checks for errors when parsing the file
     */
    @Test
    public void testStringFromDateZDT() throws ParseException
    {
        LocalDateTime ldt = LocalDateTime.of(2008, 8, 30, 17, 45, 59, 0);
        ZonedDateTime zd = ZonedDateTime.of(ldt, ZoneId.of("UTC"));
        
        String expected = "2008-08-30 17:45:59 UTC";
        String actual = Statistics.createStringFromDate(zd);
        
        assertEquals(expected,actual);
    }
    
    /**
     * Tests the date from string method
     * @throws ParseException Checks for errors when parsing the file
     */
    @Test
    public void testDateFromStringZDT() throws ParseException
    {
        LocalDateTime ldt = LocalDateTime.of(2008, 8, 30, 17, 45, 59, 0);
        ZonedDateTime expected = ZonedDateTime.of(ldt, ZoneId.of("UTC"));
 
        String zd = "2008-08-30 17:45:59 UTC";
        ZonedDateTime actual = Statistics.createZDateFromString(zd);
        
        assertEquals(expected,actual);
    }
    
    /**
     * Tests the date from string method
     * @throws ParseException Checks for errors when parsing the file
     */
    @Test
    public void testDateFromString() throws ParseException
    {
        String testStr = "2018-08-30 17:45:00 UTC";
        GregorianCalendar expected = new GregorianCalendar(2018, 8, 30, 17, 45, 0);
        GregorianCalendar actual = Statistics.createDateFromString(testStr);
        
        assertEquals(expected.get(GregorianCalendar.HOUR_OF_DAY), actual.get(GregorianCalendar.HOUR_OF_DAY));
        assertEquals(expected.get(GregorianCalendar.YEAR), actual.get(GregorianCalendar.YEAR));
        assertEquals(expected.get(GregorianCalendar.DAY_OF_MONTH), actual.get(GregorianCalendar.DAY_OF_MONTH));
        assertEquals(expected.get(GregorianCalendar.SECOND), actual.get(GregorianCalendar.SECOND));
        assertEquals(expected.get(GregorianCalendar.MINUTE), actual.get(GregorianCalendar.MINUTE));
        assertEquals(expected.get(GregorianCalendar.MONTH), actual.get(GregorianCalendar.MONTH));
    }
    
    /**
     * Tests the newer than method
     * @throws ParseException Checks for errors when parsing the file
     */
    @Test
    public void testNewerThan() throws ParseException
    {
        boolean flag;
        GregorianCalendar testCalendar = new GregorianCalendar();
        Statistics test = new Statistics(10.5, "ACEME", testCalendar, 5, StatsType.MAXIMUM);
        
        GregorianCalendar testCal = new GregorianCalendar();
        
        flag = test.newerThan(testCal);
        
        assertEquals(false, flag);
    }
    
    /**
     * Tests the older than method
     * @throws ParseException Checks for errors when parsing the file
     */
    @Test
    public void testOlderThan() throws ParseException
    {
        boolean flag;
        GregorianCalendar testCalendar = new GregorianCalendar();
        Statistics test = new Statistics(10.5, "ACEME", testCalendar, 5, StatsType.MAXIMUM); 
        GregorianCalendar testCal = new GregorianCalendar();
        
        test.olderThan(testCalendar);       
  
        flag = test.olderThan(testCal);
        
        assertEquals(false, flag);
    }
    
    /**
     * Tests the same as method
     * @throws ParseException Checks for errors when parsing the file
     */
    @Test
    public void testSameAs() throws ParseException
    {
        boolean flag;
        GregorianCalendar testCalendar = new GregorianCalendar();
        Statistics test = new Statistics(10.5, "ACEME", testCalendar, 5, StatsType.MAXIMUM);       
        
        flag = test.sameAs(testCalendar);
        
        assertEquals(true, flag);
    }   
    
    /**
     * Tests the newer than method for ZDT
     * @throws ParseException Checks for errors when parsing the file
     */
    @Test
    public void testNewerThanZDT() throws ParseException
    {
        boolean flag;
        LocalDateTime ldt = LocalDateTime.of(2008, 6, 30, 23, 30, 59, 0);
        ZonedDateTime zd = ZonedDateTime.of(ldt, ZoneId.of("UTC"));
        Statistics test = new Statistics(10.5, "ACEME", zd, 5, StatsType.MAXIMUM);       
        
        flag = test.newerThan(zd);
        
        assertEquals(false, flag);
    } 
    
    /**
     * Tests the  older than method for ZDT
     * @throws ParseException Checks for errors when parsing the file
     */
    @Test
    public void testOlderThanZDT() throws ParseException
    {
        boolean flag;
        LocalDateTime ldt = LocalDateTime.of(2008, 6, 30, 23, 30, 59, 0);
        ZonedDateTime zd = ZonedDateTime.of(ldt, ZoneId.of("UTC"));
        Statistics test = new Statistics(10.5, "ACEME", zd, 5, StatsType.MAXIMUM);       
        
        flag = test.olderThan(zd);
        
        assertEquals(false, flag);
    } 
    
    /**
     * Tests the  Same As method for ZDT
     * @throws ParseException Checks for errors when parsing the file
     */
    @Test
    public void testSameAsZDT() throws ParseException
    {
        boolean flag;
        LocalDateTime ldt = LocalDateTime.of(2008, 6, 30, 23, 30, 59, 0);
        ZonedDateTime zd = ZonedDateTime.of(ldt, ZoneId.of("UTC"));
        Statistics test = new Statistics(10.5, "ACEME", zd, 5, StatsType.MAXIMUM);       
        
        flag = test.sameAs(zd);
        
        assertEquals(true, flag);
    } 
    
    /** 
     * Test toString method
     */
    @Test
    public void testToString() {
        //create new object
        GregorianCalendar testCalendar = new GregorianCalendar();
        Statistics test = new Statistics(10.5, "ACEME", testCalendar, 5, StatsType.MAXIMUM);   
   
        //test that expected = actual
        Assert.assertEquals("", test.toString());
    }
}
