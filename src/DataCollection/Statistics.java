package DataCollection;

import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Statistics class 
 * 
 * @author Tim Weaver
 * @version 2018-10-28
 * 
 */

public class Statistics extends Observation implements DateTimeComparable
{
    /**
     * Date format in the form "yyyy-MM-dd HH:mm:ss z"
     */
    protected static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss z";  
    
    /**
     * Date time formatter
     */
    protected DateTimeFormatter format;
    
    /**
     * Gregorian Calendar variable 
     */
    private GregorianCalendar utcDateTime;
    
    /**
     * zoned date time variable
     */
    private ZonedDateTime zdtDateTime;
    
    /**
     * Integer for reporting the number of stations
     */
    private int numberOfReportingStations;
    
    /**
     * variable for the stats type
     */
    @SuppressWarnings("unused")
    private StatsType statType;
    
    /**
     * Constructor that takes a date as a zoned date time 
     * to create statistics
     * 
     * @param value Value
     * @param stid Station Id
     * @param dateTime Date Time as a Zoned Date Time
     * @param numberOfValidStations Number of Valid Stations
     * @param inStatType State Type
     * @throws ParseException Checks for errors when parsing the file
     */
    public Statistics(double value, String stid, ZonedDateTime dateTime, int numberOfValidStations,
            StatsType inStatType) throws ParseException
    {
        super(value, stid);
        this.statType = inStatType;
        this.numberOfReportingStations = numberOfValidStations; 
        this.zdtDateTime = dateTime;      
    }
    
    /**
     * Constructor that takes a date as a calendar 
     * to create statistics
     * 
     * @param value Value
     * @param stid Station Id
     * @param dateTime Date Time as a Calendar
     * @param numberOfValidStations Number of Valid Stations
     * @param inStatType State Type
     */
    public Statistics(double value, String stid, GregorianCalendar dateTime, 
            int numberOfValidStations, StatsType inStatType)
    {
        super(value, stid);
        this.numberOfReportingStations = numberOfValidStations;
        this.statType = inStatType;     
        this.utcDateTime = dateTime;
    }
    
    /**
     * Takes a string and converts it into
     * a Calendar
     * 
     * @param dateTimeStr date and time
     * @return dateTime The Date of Time Parsed correctly 
     * @throws ParseException Checks for errors when parsing the file
     */
    public static GregorianCalendar createDateFromString (String dateTimeStr) throws ParseException
    {
        //create variable 
        int offSet = 0;
        
        //if our string doesn't contain a - we need to offset the count by 1
        if (!dateTimeStr.contains("-"))
        {
            offSet = 1;
        }
        
        //parse the file
        String[] dateTimeParts = dateTimeStr.split("-|:|\\s");
              
        // assign the different parts to new variables
        int day = Integer.parseInt(dateTimeParts[2 + offSet]);
        int year = Integer.parseInt(dateTimeParts[0 + offSet]);
        int month = Integer.parseInt(dateTimeParts[1 + offSet]);
        int hour = Integer.parseInt(dateTimeParts[3 + offSet]);
        int minute = Integer.parseInt(dateTimeParts[4 + offSet]);
        int second = Integer.parseInt(dateTimeParts[5 + offSet]);
        String timeZone = "UTC";   
         
        //create new calendar
        GregorianCalendar dateTime = new GregorianCalendar(year, month, day, hour, minute, second);
        dateTime.setTimeZone(TimeZone.getTimeZone(timeZone));
        
        //return the date time
        return dateTime;
    }
    
    /**
     * Takes a string and converts it into
     * a Calendar
     * 
     * @param dateTimeStr date and time
     * @return dateTime The Date of Time Parsed correctly 
     */
    public static ZonedDateTime createZDateFromString(String dateTimeStr) 
    {
        //create variable
        int offSet = 0;
        
        //if our string doesn't contain a - we need to offset the count by 1
        if (!dateTimeStr.contains("-"))
        {
            offSet = 1;
        }
        
        //parse the file
        String[] dateTimeParts = dateTimeStr.split("-|:|\\s");
              
        // assign different parts to new variables
        int day = Integer.parseInt(dateTimeParts[2 + offSet]);
        int year = Integer.parseInt(dateTimeParts[0 + offSet]);
        int month = Integer.parseInt(dateTimeParts[1 + offSet]);
        int hour = Integer.parseInt(dateTimeParts[3 + offSet]);
        int minute = Integer.parseInt(dateTimeParts[4 + offSet]);
        int second = Integer.parseInt(dateTimeParts[5 + offSet]); 
         
        //create new zoned date time
        ZonedDateTime dateTime = ZonedDateTime.of(LocalDateTime.of(year, month, day, hour, minute, second), ZoneId.of("UTC"));
        
        //return date time
        return dateTime;      
    }
    
    /**
     * Takes a gregorian calendar and converts it into
     * a string
     * 
     * @param calendar passes through a calendar 
     * @return dateTime The Date of Time Parsed correctly 
     */
    public static String createStringFromDate(GregorianCalendar calendar)
    {
        //create variables
        String returnString = "";
        DecimalFormat df = new DecimalFormat("00");
        
        //split up and assign different calendar pieces
        returnString += (calendar.get(GregorianCalendar.YEAR) + "-");
        returnString += (df.format(calendar.get(GregorianCalendar.MONTH)) + "-");
        returnString += (df.format(calendar.get(GregorianCalendar.DAY_OF_MONTH)) + " ");
        returnString += (df.format(calendar.get(GregorianCalendar.HOUR_OF_DAY)) + ":");
        returnString += (df.format(calendar.get(GregorianCalendar.MINUTE)) + ":");
        returnString += (df.format(calendar.get(GregorianCalendar.SECOND)) + " ");       
        returnString += "UTC";
        
        //return date as a string
        return returnString;
    }
    
    /**
     * Takes a ZDT calendar and converts it into
     * a string
     * 
     * @param calendar passes through a calendar 
     * @return dateTime The Date of Time Parsed correctly 
     */
    public static String createStringFromDate(ZonedDateTime calendar)
    {
        //create variables
        String returnString = "";
        DecimalFormat df = new DecimalFormat("00");
        
        //split up and assign different zoned date time pieces
        returnString += (calendar.getYear() + "-");
        returnString += (df.format(calendar.getMonthValue()) + "-");
        returnString += (df.format(calendar.getDayOfMonth()) + " ");
        returnString += (df.format(calendar.getHour()) + ":");
        returnString += (df.format(calendar.getMinute()) + ":");
        returnString += (df.format(calendar.getSecond()) + " ");       
        returnString += "UTC";

        //return date as a string
        return returnString;
    }

    /**
     * Gets number of reporting stations
     * @return Number of reporting stations
     */
    public int getNumberOfReportingStations()
    {
        return numberOfReportingStations;
    }
    
    /**
     * Gets date string and assigns proper values
     * to the variables
     * @return String of date time
     */
    public String getUTCDateTimeString()
    {
        //create variables
        String returnString = "";
        DecimalFormat df = new DecimalFormat("00");
        
        //split up and assign different calendar pieces 
        returnString += (utcDateTime.get(GregorianCalendar.YEAR) + "-");
        returnString += (df.format(utcDateTime.get(GregorianCalendar.MONTH)) + "-");
        returnString += (df.format(utcDateTime.get(GregorianCalendar.DAY_OF_MONTH)) + " ");
        returnString += (df.format(utcDateTime.get(GregorianCalendar.HOUR_OF_DAY)) + ":");
        returnString += (df.format(utcDateTime.get(GregorianCalendar.MINUTE)) + ":");
        returnString += (df.format(utcDateTime.get(GregorianCalendar.SECOND)) + " ");       
        returnString += "UTC";
        
        //return date as a string
        return returnString;
    }
    
    /**
     * Checks to see if newer than for Gregorian Calendar
     * @return date time before
     */
    public boolean newerThan(GregorianCalendar inDateTime)
    {
        return utcDateTime.before(inDateTime);
    }
    
    /**
     * Checks to see if older than Gregorian Calendar
     * @return date time after
     */
    public boolean olderThan(GregorianCalendar inDateTime)
    {
        return utcDateTime.after(inDateTime); 
    }
    
    /**
     * Checks to see if same as Gregorian Calendar
     * @return date time equals
     */
    public boolean sameAs(GregorianCalendar inDateTime)
    {
        return utcDateTime.equals(inDateTime);
    }
    
    /**
     * Checks to see if newer than for ZDT
     * @return date time before
     */
    public boolean newerThan(ZonedDateTime inDateTime)
    {
        return zdtDateTime.isAfter(inDateTime);
    }
    
    /**
     * Checks to see if older than for ZDT
     * @return date time after
     */
    public boolean olderThan(ZonedDateTime inDateTime)
    {
        return zdtDateTime.isBefore(inDateTime);
    }
    
    /**
     * Checks to see if same as in for ZDT
     * @return date time equals
     */
    public boolean sameAs(ZonedDateTime inDateTime)
    {
        return zdtDateTime.equals(inDateTime);
    }
    
    /**
     * toString method
     * @return empty string
     */
    public String toString()
    {
        return "";
    }
}
