package sprint5;

import java.util.List;

/**
 * sprint5.Vehicle implements sprint5.IVehicle -- from vehicle comes taxi, shuttle etc
 * specific information about the vehicle, service info, pickup/dropoff, route, etc
 */
public interface IVehicle {

    public int getId();
    public ILocation getLocation();
    public ILocation getDestination();
    public List<IService> getService();
    public IStatistics getStatistics();
    public void setCompany(ITaxiCompany company);
    public void pickService(IService service);

   
    public void startService();
    
    public void endService();
   
    public void notifyArrivalAtPickupLocation();
    
    public void notifyArrivalAtDropoffLocation();
    public boolean isFree();
    public VehicleStatus getStatus();
    public void move();

    // new : add parameter to caclulate cost so that if the ride is shared, the billing is updated with the correct amount
    public int calculateCost(IService service);

    //updated this ebcause it was showing incorrect route when vehicles werent free
    public String showDrivingRoute(List<ILocation> route);
    public String toString();

    // added methods: in vehicle to use in taxicompany to make easier checking for rideshare 
    public int getDistanceFromPickUp(IService service);
    public int getDistanceFromDropoff(IService service);

    // add this to keep track of the current service --> since there is now a list of services
    public IService getClosestService();

    
}
