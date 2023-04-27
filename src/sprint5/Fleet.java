package sprint5;

import java.util.ArrayList;
import java.util.List;


/**
 * ABSTRACT class fleet implements IFleet
 * constructor takes in id and location (x,y)
 */

public abstract class Fleet implements IFleet {
    protected FleetStatus status;
    protected ILocation location;
    protected ILocation destination;
    protected List<ILocation> route;
    private final int id;
    private ITaxiCompany company;
    //new --> changed to a list isntead of a single instance
    private List<IService> service;
    private final IStatistics statistics;


    /**
     *Fleet class constructor, takes in unique user ID and pickup location (x,y), and destination
     */
    public Fleet(int id, ILocation location, ILocation destination) {
        this.id = id;
        //this.service = new ArrayList<IService>(); // initialize the array list (it could only contain one)
        //this.status = VehicleStatus.FREE;
        this.location = location;
        this.destination = destination;
        this.statistics = new Statistics();
        this.route = setDrivingRouteToDestination(this.location, this.destination);
    }

    @Override
    /**
     * returns users unique id 
     */
    public int getId() {
        return this.id;
    }


    @Override
    /**
     * getter method: returns users pickup location, as an Ilocation object (x,y) coordinate
     */
    public ILocation getLocation() {
        return this.location;
    }

    @Override
    /**
     * getter method: returns users destination
     */
    public ILocation getDestination() {
        return this.destination;

    }

    @Override
    /**
     * getter method: returns the company
     */
    public ITaxiCompany getCompany() {
        return this.company;
    }

//    @Override
//     /**
//     * getter method: returns service
//     */

    @Override
    /**
     * setter method: change the company to specific taxicompany
     */
    public void setCompany(ITaxiCompany company) {
        this.company = company;
    }

    @Override
    public List<IService> getService() {
        return this.service;
    }

    @Override
    /**
     * getter method: returns statistics
     */
    public IStatistics getStatistics() {
        return this.statistics;
    }
    /* 
     * abstract classes --> implemented in the specific class 
     */
    public abstract boolean isFree();

    public abstract void bookService(IService service);

    public abstract void startService();

    public abstract void endService();

    public abstract void move();
    
    public abstract IService getClosestService();


    @Override
    /**
     * notifying the company that the vehicle is at the pickup location,
     * then start the service
     */
    public void notifyArrivalAtPickupLocation() {
        this.company.arrivedAtPickupLocation(this);
        this.startService();
    }

    @Override
    /**
     * notifying the company that the vehicle is at the dropoff location,
     * then end the service 
     */
    public void notifyArrivalAtDropoffLocation() {
        this.company.arrivedAtDropoffLocation(this);
        this.endService();
        // this.calculateCost();
    }


    @Override
    /**
     * ADDED THIS: A getter method to get the status of the vehicle, before there was only a isFree() method
     */
    public FleetStatus getStatus() {
        return this.status;
    }

    

    @Override
    /**
     * returns the cost of the service as the distance
     * super method of sprint5.Vehicle, and the specific taxi and shuttle cost
     * are calculated in their own methods 
     *
     */
    public int calculateCost(IService service) {

        if (service.getShared()) {
            return (service.calculateDistance() - 2);
        } else return service.calculateDistance();
    }


    /**
     * shows the route of the car in string format
     */
    public String showDrivingRoute(List<ILocation> route) {
        String s = "";

        for (ILocation l : route)
            s = s + l.toString() + ", ";

        return s;
    }


    /**
     * turns entire method to string --> changed this to incorporate the new rideshare
     */
    public abstract String toString();

    protected List<ILocation> setDrivingRouteToDestination(ILocation location, ILocation destination) {
        List<ILocation> route = new ArrayList<ILocation>();

        int x1 = location.getX();
        int y1 = location.getY();

        int x2 = destination.getX();
        int y2 = destination.getY();

        int dx = Math.abs(x1 - x2);
        int dy = Math.abs(y1 - y2);

        for (int i = 1; i <= dx; i++) {
            x1 = (x1 < x2) ? x1 + 1 : x1 - 1;

            route.add(new Location(x1, y1));
        }

        for (int i = 1; i <= dy; i++) {
            y1 = (y1 < y2) ? y1 + 1 : y1 - 1;

            route.add(new Location(x1, y1));
        }

        return route;
    }

    /**
     * get distance of the service from the pickup location of the user 
     */
    @Override
    public int getDistanceFromPickUp(IService service) {
        return Math.abs(this.location.getX() - service.getPickupLocation().getX()) + Math.abs(this.location.getY() - service.getPickupLocation().getY());
    }

    /**
     * get distance from service from the dropoff location of the user
     */
    @Override
    public int getDistanceFromDropoff(IService service) {
        return Math.abs(this.location.getX() - service.getDropoffLocation().getX()) + Math.abs(this.location.getY() - service.getDropoffLocation().getY());
    }
}
