
/**
 * Abstract Observation class 
 * 
 * @author Tim Weaver
 * @version 2018-10-23
 * 
 */

public abstract class AbstractObservation
{
    protected boolean valid;
    
    /**
     * method is not used in program so it is left blank!
     */
    public AbstractObservation()
    {
        //not used
    }
    
    /**
     * returns method isvalid
     * @return isvalid
     */
    public abstract boolean isValid();
}
