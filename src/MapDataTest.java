
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.ParseException;
import java.util.GregorianCalendar;
import org.junit.Assert;
import org.junit.Test;
/** 
 * Tests the Map Data Class
 * 
 * @author Tim Weaver
 * @version 2018-10-28
 */
public class MapDataTest {  

	/** 
	 * Test full constructor and getters
	 */
	@Test
	public void testMapData() {
		MapData test = new MapData(2018, 8, 30, 17, 45, "data/");  	
		GregorianCalendar gc = new GregorianCalendar(2018, 8, 30, 0, 0, 0); 	  
		
		Assert.assertEquals(gc.get(GregorianCalendar.YEAR), test.getDateTime().get(GregorianCalendar.YEAR));
		Assert.assertEquals(gc.get(GregorianCalendar.MONTH), test.getDateTime().get(GregorianCalendar.MONTH));
		Assert.assertEquals(gc.get(GregorianCalendar.DAY_OF_MONTH), test.getDateTime().get(GregorianCalendar.DAY_OF_MONTH)); 
		Assert.assertEquals(gc.get(GregorianCalendar.HOUR), test.getDateTime().get(GregorianCalendar.HOUR));
		Assert.assertEquals(gc.get(GregorianCalendar.MINUTE), test.getDateTime().get(GregorianCalendar.MINUTE));
		Assert.assertEquals(gc.get(GregorianCalendar.SECOND), test.getDateTime().get(GregorianCalendar.SECOND));   
	}
	
	/** 
	 * Test if file is parsing correctly
	 * @throws IOException Make sure inputs are valid
	 * @throws ParseException Checks for errors when parsing the file
	 */
	@Test
	public void testParseFile() throws IOException, ParseException
	{
		MapData test = new MapData(2018, 8, 30, 17, 45, "data/");
		
		String path = test.createFileName(2018, 8, 30, 17, 45, "data/");
		
		test.parseFile(path);
		
		Assert.assertFalse(test.dataCatalog == null);    
	}
	
	/** 
	 * Make sure file name is being created correctly 
	 */
	@Test
	public void testCreateFileName() {
		MapData test = new MapData(2018, 8, 30, 17, 45, "data/"); 
		
		String expected = "201811201035.mdf";
		String actual = test.createFileName(2018, 11, 20, 10, 35, ""); 
		
		Assert.assertEquals(expected, actual); 
	} 
	
	/** 
     * Test the to string output
     */
    @Test
    public void testToString() {
        MapData test = new MapData(2018, 8, 30, 17, 45, "data/"); 
        
        String expected = 
                "=========================================================\n" + 
                "=== 2018-08-30 00:00:00 ===\n" + 
                "=========================================================\n" + 
                "Maximum Air Temperature[1.5m] = 36.5 C at HOOK \n" + 
                "Minimum Air Temperature[1.5m] = 20.8 C at MIAM \n" + 
                "Average Air Temperature[1.5m] = 32.4 C at Mesonet \n" + 
                "=========================================================\n" + 
                "=========================================================\n" + 
                "Maximum Air Temperature[9.0m] = 34.9 C at HOOK \n" + 
                "Minimum Air Temperature[9.0m] = 20.7 C at MIAM \n" + 
                "Average Air Temperature[9.0m] = 31.6 C at Mesonet \n" + 
                "=========================================================\n" + 
                "=========================================================\n" + 
                "Maximum Solar Radiation[1.5m] = 899.0 W/m^2 at BESS \n" + 
                "Minimum Air Temperature[1.5m] = 163.0 W/m^2 at MIAM \n" + 
                "Average Solar Radiation[1.5m] = 820.5 W/m^2 at Mesonet \n" + 
                "=========================================================";
        String actual = test.toString(); 
        
        Assert.assertEquals(expected, actual); 
    } 
    
    /** 
     * Test Get Statistics method
     */
    @Test
    public void testGetStatistics() {
        GregorianCalendar testCal = new GregorianCalendar(2018, 8, 30, 17, 45, 0);
        MapData test = new MapData(2018, 8, 30, 17, 45, "data/"); 
        StatsType avg = (StatsType.AVERAGE);
        
        Statistics expected = new Statistics(10.5, "ACEME", testCal, 5, StatsType.AVERAGE);
        Statistics actual = test.getStatistics(avg, "TAIR"); 
        
        assertEquals(expected, actual); 
    } 
}
