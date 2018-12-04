package DataCollection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Map Data class takes a file and parses it based on white space
 * then calculates air temperature min, max, and average at 1.5 meters and 9 meters
 * as well as solar radiation min, max, and average. This is then printed out.
 * 
 * @author Tim Weaver
 * @version 2018-10-28
 * 
 */

public class MapData 
{
    /**
     * Hash map to store the data from the file
     */
    HashMap<String, ArrayList<Observation>> dataCatalog = new HashMap<String, ArrayList<Observation>>();
    
    /**
     * Enumerator map that stores the type of stat and a tree map with key string id and value stat 
     */
    EnumMap<StatsType, TreeMap<String, Statistics>> statistics = new EnumMap<>(StatsType.class);
    
    /**
     * Tree map that uses the given string Id as the key and stores the value of its position
     */
    TreeMap<String, Integer> paramPositions = new TreeMap<String, Integer>();
    
    /**
     * Array list to store the line data for preparing the data catalog
     */
    ArrayList<String> lineData = new ArrayList<String>();
	
    /**
     * the number of missing observations, initialized at value 10
     */
    int NUMBER_OF_MISSING_OBSERVATIONS = 10;
    
	/**
     * String containing TA9M
     */
	private String TA9M = "TA9M";
	
	/**
     * String containing TAIR 
     */
	private String TAIR = "TAIR";
	
	/**
     * String containing SRAD
     */
	private String SRAD = "SRAD";
	
	/**
     * String containing STID
     */
	private String STID = "STID"; 
	
	/**
	 * Contains the word "Mesonet" which is used
	 * to identify the station
	 */
	private String MESONET = "Mesonet"; 		
	
	/**
     * Stores the date 
     */
	private GregorianCalendar utcDateTime;
	
	/**
	 * assigns the positions of tair, ta9m, srad, and stid
	 * @param inParamStr Checks for errors when parsing the file
	 */
	private void parseParamHeader(String inParamStr)
	{
	    //Trims the string by whitespace
	    String[] id = (inParamStr.trim().split("\\s+"));
	    
	    // Add the array string to tree map	    
	    for (int i = 0; i < id.length; i++)
	    {
	        paramPositions.put(id[i], i + 1);
	    }
	}
	
	/**
	 * Returns the index position for the called upon data
	 * 
	 * @return index The index number of the data 
	 * @param inParamStr passes the String Parameter (i.e. "TAIR", "SRAD", ext.)
	 */
	public Integer getIndexOf(String inParamStr)
	{
	    Integer index = paramPositions.get(inParamStr);
	    return index;
	}
	
	/**
     * Calculates the min, max, and average for ta9m, tair, and srad
     * as well as the station Ids for each indicator
     */
	private void calculateAllStatistics()
	{   
	    //create tree maps for storing data
        TreeMap<String, Statistics> minTree = new TreeMap<String, Statistics>();   
        TreeMap<String, Statistics> maxTree = new TreeMap<String, Statistics>();    
        TreeMap<String, Statistics> avgTree = new TreeMap<String, Statistics>();
         
        //loop through map
        for (Map.Entry<String, ArrayList<Observation>> entry : dataCatalog.entrySet())
        {         
            //create variables
            String minStationId = "";   
            String maxStationId = "";   
            
            double total = 0.0;
            int count = 0;
            double average = 0.0;
            double min = Double.POSITIVE_INFINITY;
            double max = Double.NEGATIVE_INFINITY; 
            
            //do not calculate if key is STID
            if(entry.getKey() != STID)
            {
                //create variables
                String key = entry.getKey();
                ArrayList<Observation> value = entry.getValue();
            
                //loop through array list 
                for (int i = 0; i < value.size(); i++)
                {
                    //Check bounds
                    if (value.get(i).isValid())
                    {
                        //increment counter
                        count ++;
                        total += value.get(i).getValue();
                    
                        //check for min value
                        if (value.get(i).getValue() < min)
                        {
                            minStationId = value.get(i).getStid();
                            min = value.get(i).getValue();
                        }
                        //check for max value
                        if (value.get(i).getValue() > max)
                        {
                            maxStationId = value.get(i).getStid();
                            max = value.get(i).getValue();
                        }
                    }
                    //calculate average          
                    average = total/count;
                }
                
                //Store calculated info as new statistic
                Statistics minimum = new Statistics(min, minStationId, utcDateTime, count, StatsType.MINIMUM);
                Statistics maximum = new Statistics(max, maxStationId, utcDateTime, count, StatsType.MAXIMUM);
                Statistics avg = new Statistics(average, maxStationId, utcDateTime, count, StatsType.AVERAGE); 
            
                //add data to tree map
                minTree.put(key, minimum);
                maxTree.put(key, maximum);            
                avgTree.put(key, avg);    
            }                             
        }
        
        //add data to the enum map
        statistics.put(StatsType.MINIMUM, minTree);
        statistics.put(StatsType.MAXIMUM, maxTree);
        statistics.put(StatsType.AVERAGE, avgTree);
	}
	
	/**
	 * Parses file and adds data to the dataCatalog hash map
	 */
	private void prepareDataCatalog()
	{
	    //Create new array lists
	    ArrayList<Observation> TA9MHeaderData = new ArrayList<Observation>(); 
	    ArrayList<Observation> TAIRHeaderData = new ArrayList<Observation>(); 
	    ArrayList<Observation> SRADHeaderData = new ArrayList<Observation>(); 
	    
	    //get the position of each parameter 
	    int TA9MPosition = getIndexOf(TA9M);
	    int TAIRPosition = getIndexOf(TAIR);
	    int SRADPosition = getIndexOf(SRAD);
	    int STIDPosition = getIndexOf(STID);
	    
	    //loop through the file and add each position
	    for (int i = 0; i < lineData.size(); i++)
	    {
	        String[] lineParts = lineData.get(i).split("\\s+");
	        String ta9m = lineParts[TA9MPosition];
	        String tair = lineParts[TAIRPosition];
	        String srad = lineParts[SRADPosition];
	        String stid = lineParts[STIDPosition];
	        
	        //convert line data to type Observation
	        Observation ta9mObservation = new Observation(Double.parseDouble(ta9m), stid);
	        Observation tairObservation = new Observation(Double.parseDouble(tair), stid);
	        Observation sradObservation = new Observation(Double.parseDouble(srad), stid);
        
	        //add data to array list
	        TA9MHeaderData.add(ta9mObservation);
	        TAIRHeaderData.add(tairObservation); 
	        SRADHeaderData.add(sradObservation);
	    }
	    
	    //add data to hash map
	    dataCatalog.put(TA9M, TA9MHeaderData);
	    dataCatalog.put(TAIR, TAIRHeaderData);
	    dataCatalog.put(SRAD, SRADHeaderData);
	}
	
	/**
	 * Returns the statistics 
	 * 
	 * @param type The type of statistic we are calling. (i.e. Average, Min, Max)
	 * @param paramId The parameter ID
	 * @return stats The different statistics (i.e. Average, Min, Max)
	 */
	public Statistics getStatistics(StatsType type, String paramId)
    {
	    //create tree map
	    TreeMap<String, Statistics> statsMap = statistics.get(type);
	    //create statistic 
	    Statistics stats = (Statistics) statsMap.get(paramId);
	       
	    //Return the statistic
	    return stats;
    }
	
	/**
	 * Calls calculate all statistics
	 */
	private void calculateStatistics()
	{
		calculateAllStatistics();            
	}

	/**
	 * Map Constructor setting all the fields. Also, reads in a file and calls on parse file method
	 * and calls on calculation methods for Air Temperature at 1.5 meters, Solar Radiation and
	 * Air Temperature at 9 meters 
	 * 
	 * @param year The current year set by the user.
	 * @param month The current month set by the user.
	 * @param day The current day set by the user.
	 * @param hour The current hour set by the user.
	 * @param minute The current minute set by the user.
	 * @param directory The given directory set by the user.
	 */
	public MapData(int year, int month, int day, int hour, int minute, String directory) 
	{
		//Location of the file 
		String path = createFileName(year, month, day, hour, minute, directory); 
	
		//Try to read the file
		try
		{
			parseFile(path);   
		}
		//Print error message if file can not be read
		catch(Exception e)
		{
			System.out.println("Error reading from file!\n"); 
		}	
	
		calculateStatistics();  	
	} 
	
	/**
	 * returns utc Date Time
	 * @return date time
	 */
	public GregorianCalendar getDateTime()
	{
	    return utcDateTime;
	}
	  
	/**
	 * Creates a file name based on year, month, day, hour, and minute.
	 * 
	 * @param year The current year set by the user.
	 * @param month The current month set by the user.
	 * @param day The current day set by the user.
	 * @param hour The current hour set by the user.
	 * @param minute The current minute set by the user.
	 * @param directory The given directory set by the user.
	 * @return The string representation of create file name method, formatted as:
	 * 			(year)(month)(day)(hour)(minute) ex: 201811200324.mdf
	 */
	public String createFileName(int year, int month, int day, int hour, int minute, String directory)
	{
	    /**
	     * The File Name provided by user
	     */
	    String fileName = "";
	    
	    //create file name in the form directory, year, month, day, hour, minute (i.e. data201808010700)
	    fileName = String.format("%s%04d%02d%02d%02d%02d.mdf", directory, year, month, day, hour, minute);
		return fileName;
	}

	/**
	 * Parses out a given file based on white space
	 * 
	 * @param path Imports the set file path location
	 * @throws IOException Checks to make sure there are valid inputs.
	 * @throws ParseException Checks for errors when parsing the file
	 */
	public void parseFile(String path) throws IOException, ParseException
	{			    
		//Begin reading file
		BufferedReader br = new BufferedReader(new FileReader(path));
		
		String strg = br.readLine();
		
		//Parse the date and time
		String dateTime = br.readLine();		
		utcDateTime = Statistics.createDateFromString(dateTime.trim());
		
		//Set header indexes
		String headers = br.readLine();
	    parseParamHeader(headers);	    
		
		//Read new line
		strg = br.readLine();
			
		//Loops through an array while the string is not empty
		while (strg != null)
		{		
			//Read new line
	        lineData.add(strg);
			strg = br.readLine();
		}
		//Close the file
		br.close();
		
	    prepareDataCatalog();
	}
	
	/**
	 * Prints out all of the information gathered and calculated by the program including
	 * year, month, day, hour, minute, max air temp at 1.5m, min air temp at 1.5m, average air temp at 1.5m,
	 * max air temp at 9m, min air temp at 9m, average air temp at 9m, max solar radiation, min solar radiation, and average solar radiation
	 * 
	 * @return The string representation of the MapData class, formatted as:
	 * 			 =========================================================
				 === 2018-08-30 17:45 ===
				 =========================================================
				 Maximum Air Temperature[1.5m] = 21.7 C at MEDI 
				 Minimum Air Temperature[1.5m] = 13.8 C at EVAX 
				 Average Air Temperature[1.5m] = 18.0 C at Mesonet 
				 =========================================================
				 =========================================================
				 Maximum Air Temperature[9.0m] = 23.3 C at MARE 
				 Minimum Air Temperature[9.0m] = 15.8 C at COOK 
				 Average Air Temperature[9.0m] = 19.7 C at Mesonet 
				 =========================================================
				 =========================================================
				 Maximum Solar Radiation[1.5m] = 0.0 W/m^2 at  
				 Minimum Air Temperature[1.5m] = 0.0 W/m^2 at ACME 
				 Average Solar Radiation[1.5m] = 0.0 W/m^2 at Mesonet 
				 =========================================================
	 * 
	 */ 
	public String toString()
	{ 
		String lineBreak = createLineBreak();
				
		String header = String.format("\n" + "=== " + "%02d-%02d-%02d %02d:%02d:%02d" + " ===" + "\n", 
				utcDateTime.get(GregorianCalendar.YEAR), utcDateTime.get(GregorianCalendar.MONTH), utcDateTime.get(GregorianCalendar.DAY_OF_MONTH), 
				utcDateTime.get(GregorianCalendar.HOUR), utcDateTime.get(GregorianCalendar.MINUTE), utcDateTime.get(GregorianCalendar.SECOND)); 
		String tairMaxStrg = String.format("Maximum Air Temperature[1.5m] = %.1f C at %s \n", 
				statistics.get(StatsType.MAXIMUM).get(TAIR).getValue(), statistics.get(StatsType.MAXIMUM).get(TAIR).getStid()); 
		String tairMinStrg = String.format("Minimum Air Temperature[1.5m] = %.1f C at %s \n", statistics.get(StatsType.MINIMUM).get(TAIR).getValue(), 
		        statistics.get(StatsType.MINIMUM).get(TAIR).getStid());
		String tairAverageStrg = String.format("Average Air Temperature[1.5m] = %.1f C at %s \n", 
		        statistics.get(StatsType.AVERAGE).get(TAIR).getValue(), MESONET);
		String ta9mMaxStrg = String.format("Maximum Air Temperature[9.0m] = %.1f C at %s \n", statistics.get(StatsType.MAXIMUM).get(TA9M).getValue(), 
		        statistics.get(StatsType.MAXIMUM).get(TA9M).getStid());
		String ta9mMinStrg = String.format("Minimum Air Temperature[9.0m] = %.1f C at %s \n", statistics.get(StatsType.MINIMUM).get(TA9M).getValue(), 
		        statistics.get(StatsType.MINIMUM).get(TA9M).getStid()); 
		String ta9mAverageStrg = String.format("Average Air Temperature[9.0m] = %.1f C at %s \n", 
		        statistics.get(StatsType.AVERAGE).get(TA9M).getValue(),MESONET);
		String sradMaxStrg = String.format("Maximum Solar Radiation[1.5m] = %.1f W/m^2 at %s \n", statistics.get(StatsType.MAXIMUM).get(SRAD).getValue(), 
		        statistics.get(StatsType.MAXIMUM).get(SRAD).getStid());
		String sradMinStrg = String.format("Minimum Air Temperature[1.5m] = %.1f W/m^2 at %s \n", statistics.get(StatsType.MINIMUM).get(SRAD).getValue(), 
		        statistics.get(StatsType.MINIMUM).get(SRAD).getStid());
		String sradAverageStrg = String.format("Average Solar Radiation[1.5m] = %.1f W/m^2 at %s \n", 
		        statistics.get(StatsType.AVERAGE).get(SRAD).getValue(), MESONET);
	
		String format = lineBreak + header + lineBreak + "\n" + tairMaxStrg + tairMinStrg + tairAverageStrg + lineBreak + "\n" + lineBreak
				+ "\n" + ta9mMaxStrg + ta9mMinStrg + ta9mAverageStrg + lineBreak + "\n" + lineBreak + "\n" + sradMaxStrg + sradMinStrg + 
				sradAverageStrg + lineBreak;
		
		return format;
	}
	
	/**
	 * Creates a line of 57 "=" signs to separate 
	 * information for the toString method
	 * 
	 * @return "=" 57 times to create a line break
	 */
	private String createLineBreak()
	{
	    //declare variable
		String returnStr = "";
		
		//loop though 57 times
		for (int i = 0; i < 57; i++)
		{
			returnStr += "=";
		}
		
		//return 57 "=" 
		return returnStr;
	}
	
}
