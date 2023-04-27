package sprint5;

import java.util.ArrayList;
import java.util.List;


/**
 *  Micro class extends abstract class fleet, implementing some of its own method bodies specific to the micro services 
 */
public class Micro extends Fleet {

    private List<IService> service;
    private FleetStatus status;

    /**
     * sprint5. Micro class constructor, takes in unique user ID and pickup location (x,y)
     *
     * @param id: the ID of the micromobility vehicle
     * @param location: the initial location of the micromobility vehicle
     */
    public Micro(int id, ILocation location) {
        super(id, location, location);
        
        this.status = FleetStatus.FREE;
        this.service = new ArrayList<IService>(1);
        // initialize the service list so that it only allows one service 
    }

    /**
     * getter method: returns service
     */
    @Override
    public List<IService> getService() {
        return this.service;
    }


     /**
     * returns true if the vehicle is free
     */
    @Override
    public boolean isFree() {
        return false;
    }

     /**
     * getter method: returns status
     */
    public FleetStatus getStatus() {
        return this.status;
    }

     /**
     *book service
     */
    @Override
    public void bookService(IService service) {
        this.service.add(service);
        this.status = FleetStatus.BOOKED;

    }

     /**
     * start service --> set status to inride 
     */
    @Override
    public void startService() {
        this.status = FleetStatus.INRIDE;
        this.destination = this.service.get(0).getDropoffLocation();
        //used get pickuplocation() as start, could alternatively be this.location

        // does have a route --> account for the fact that it is in use
        this.route = setDrivingRouteToDestination(this.getLocation(), this.getDestination());
    }

     /**
     * end service and reset settings
     */
    @Override
    public void endService() {
        // update vehicle statistics

        IService service = this.getClosestService(); // get the first and only object in this list

        this.getStatistics().updateBilling(this.calculateCost(service));
        this.getStatistics().updateDistance(service.calculateDistance());
        this.getStatistics().updateServices();

        // if the service is rated by the user, update statistics

        if (service.getStars() != 0) {
            this.getStatistics().updateStars(service.getStars());
            this.getStatistics().updateReviews();
        }


        // set service to null, and status to "free"

        this.service.remove(0);
        this.status = FleetStatus.FREE;

    }

    @Override
    /**
     * gets the next location from the driving route
     */
    public void move() {
        // if not moving and there is a service
        if (this.route.isEmpty() && this.service.size() != 0) {
            IService service = this.getClosestService();
            ILocation destination = service.getDropoffLocation();

            // first check if user is at pickup location (aka assume user made it to pickup location and is ready to go)
            if (this.getStatus() == FleetStatus.BOOKED) {
                //&& (this.getLocation().getX() == service.getUser().getLocation().getX() && this.getLocation().getY() == service.getUser().getLocation().getY())) {
                notifyArrivalAtPickupLocation();
            }
            // then check if at dropoff location
            if (this.location.getX() == destination.getX() && this.location.getY() == destination.getY()) {
                notifyArrivalAtDropoffLocation();
            }
        } else {
            // if somewhere to go, go
            if (this.getStatus() == FleetStatus.INRIDE && !this.route.isEmpty()) {
                this.location = this.route.get(0);
                this.route.remove(0);
            }
        }
    }


    @Override
     /**
     *to string method
     */
    public String toString() {
        return (this.getId() + " at " + this.getLocation() +
                ((this.status == FleetStatus.FREE) ? " is free"
                        : (this.status == FleetStatus.INRIDE) ? " driving themselves to destination" + this.getDestination()
                        : " is booked"));
    }

     /**
     * returns closest service --> returning null if there are only free vehicles
     */
    @Override
    public IService getClosestService() {
        if (this.service.size() == 0 || this.getStatus() == FleetStatus.FREE)
            return null;
        else
            return this.service.get(0);
    } // only 1 service

}
