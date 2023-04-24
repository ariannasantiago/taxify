package sprint5;

/**
 * sprint5.Taxi company as a whole
 */

public interface ITaxiCompany {

    public String getName();    
    public int getTotalServices();
    public boolean provideService(int user);
    public void arrivedAtPickupLocation(IFleet vehicle);
    public void arrivedAtDropoffLocation(IFleet vehicle);

        
    }

    // more methods will be declared in upcoming sprints


