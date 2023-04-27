package sprint5;

import java.util.List;

/**
 * sprint5.Vehicle implements sprint5.IVehicle -- from vehicle comes taxi, shuttle etc
 * specific information about the vehicle, service info, pickup/dropoff, route, etc
 */
public interface IFleet {

    int getId();

    ILocation getLocation();

    ILocation getDestination();

    List<IService> getService();

    IStatistics getStatistics();

    ITaxiCompany getCompany();

    void setCompany(ITaxiCompany company);


    boolean isFree();

    FleetStatus getStatus();


    void bookService(IService service);

    void startService();

    void endService();

    void notifyArrivalAtPickupLocation();

    void notifyArrivalAtDropoffLocation();

    void move();

    // new : add parameter to caclulate cost so that if the ride is shared, the billing is updated with the correct amount
    int calculateCost(IService service);

    //updated this ebcause it was showing incorrect route when vehicles werent free
    String showDrivingRoute(List<ILocation> route);

    String toString();

    // added methods: in vehicle to use in taxicompany to make easier checking for rideshare 
    int getDistanceFromPickUp(IService service);

    int getDistanceFromDropoff(IService service);

    // add this to keep track of the current service --> since there is now a list of services
    IService getClosestService();


}
