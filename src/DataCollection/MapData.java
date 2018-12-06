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
 * @version 2018-12-05
 * 
 */

public class MapData 
{
    /**
     * Hash map to store the data from the file
     */
    HashMap<String, ArrayList<Observation>> dataCatalog = new HashMap<String, ArrayList<Observation>>();
    
    /**
     * Enumerator map that stores the type of statistic and a tree map with key string id and value statistic
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
     * String containing WSPD
     */
    private String WSPD = "WSPD";
    
    /**
     * String containing PRES
     */
    private String PRES = "PRES";
	
	/**
     * String containing STID
     */
	private String STID = "STID"; 		
	
	/**
     * Stores the date 
     */
	private GregorianCalendar utcDateTime;
	
	/**
	 * Stores the name of a file
	 */
	private String fileName;
	
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
	    ArrayList<Observation> WSPDHeaderData = new ArrayList<Observation>();
	    ArrayList<Observation> PRESSHeaderData = new ArrayList<Observation>();
	    
	    //get the position of each parameter 
	    int TA9MPosition = getIndexOf(TA9M);
	    int TAIRPosition = getIndexOf(TAIR);
	    int SRADPosition = getIndexOf(SRAD);
	    int STIDPosition = getIndexOf(STID);
	    int WSPDPosition = getIndexOf(WSPD);
	    int PRESSPosition = getIndexOf(PRES);
	    
	    //loop through the file and add each position
	    for (int i = 0; i < lineData.size(); i++)
	    {
	        String[] lineParts = lineData.get(i).split("\\s+");
	        String ta9m = lineParts[TA9MPosition];
	        String tair = lineParts[TAIRPosition];
	        String srad = lineParts[SRADPosition];
	        String stid = lineParts[STIDPosition];
	        String wspd = lineParts[WSPDPosition];
	        String press = lineParts[PRESSPosition];	        
	        
	        //convert line data to type Observation
	        Observation ta9mObservation = new Observation(Double.parseDouble(ta9m), stid);
	        Observation tairObservation = new Observation(Double.parseDouble(tair), stid);
	        Observation sradObservation = new Observation(Double.parseDouble(srad), stid);
	        Observation wspdObservation = new Observation(Double.parseDouble(wspd), stid);
	        Observation pressObservation = new Observation(Double.parseDouble(press), stid);
        
	        //add data to array list
	        TA9MHeaderData.add(ta9mObservation);
	        TAIRHeaderData.add(tairObservation); 
	        SRADHeaderData.add(sradObservation);
	        WSPDHeaderData.add(wspdObservation);
	        PRESSHeaderData.add(pressObservation);
	    }
	    
	    //add data to hash map
	    dataCatalog.put(TA9M, TA9MHeaderData);
	    dataCatalog.put(TAIR, TAIRHeaderData);
	    dataCatalog.put(SRAD, SRADHeaderData);
	    dataCatalog.put(WSPD, WSPDHeaderData);
	    dataCatalog.put(PRES, PRESSHeaderData);
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
	    calculateStatistics();
	    
	    //create tree map
	    TreeMap<String, Statistics> statsMap = statistics.get(type);
	    //create statistic 
	    Statistics stats = statsMap.get(paramId);
	       
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
		createFileName(year, month, day, hour, minute, directory); 	
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
     * @param filename
     *            sets the file name
     */
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    /**
     * @return filename
     */
    public String getFileName()
    {
        return this.fileName;
    }
	
    /**
     * Splits the date into an array 
     * @param input
     * @return finalDate An integer array with every component of the date
     */
    public int[] splitDate(String input)
    {
        String inputData = input.replaceAll("(^\\d{4}|\\d{2})(?!$)", "$1.");
        
        String[] tempDate = inputData.split("\\.");
        
        int[] finalDate = new int[tempDate.length - 2];
        
        for (int i = 0; i < tempDate.length - 2; i++)
        {
            finalDate[i] = Integer.parseInt(tempDate[i]);
        }
        
        return finalDate;      
    }
}
