package sprint5;

/**
 * sprint5.Taxi company as a whole
 */

public interface ITaxiCompany {

    String getName();

    int getTotalServices();

    boolean provideService(int user);

    void arrivedAtPickupLocation(IFleet vehicle);

    void arrivedAtDropoffLocation(IFleet vehicle);


}

// more methods will be declared in upcoming sprints


