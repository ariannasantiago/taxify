package sprint5;

/**
 * sprint5.Taxi company as a whole --> implemented by TaxiCompany to run the program
 */

public interface ITaxiCompany {

    String getName();

    int getTotalServices();

    boolean provideService(int user);

    void arrivedAtPickupLocation(IFleet vehicle);

    void arrivedAtDropoffLocation(IFleet vehicle);


}
