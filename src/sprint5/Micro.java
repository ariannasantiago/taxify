package sprint5;

import java.util.ArrayList;
import java.util.List;


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
        super(id, location);
        // initialize the array list (it could only contain one)
        //this.status = MicroStatus.FREE;
        this.status = FleetStatus.FREE;
        this.service = new ArrayList<IService>(1);

    }

    /**
     * getter method: returns service
     */
    @Override
    public List<IService> getService() {
        return this.service;
    }


    @Override
    public boolean isFree() {
        return false;
    }

    public FleetStatus getStatus() {
        return this.status;
    }

    @Override
    public void bookService(IService service) {
        this.service.add(service);
        //this.status = MicroStatus.BOOKED;
        this.status = FleetStatus.BOOKED;

    }

    @Override
    public void startService() {
        this.status = FleetStatus.INRIDE;

        this.destination = this.service.get(0).getDropoffLocation();
        //used get pickuplocation() as start, could alternatively be this.location

        // does have a route --> account for the fact that it is in use
        this.route = setDrivingRouteToDestination(this.getLocation(), this.getDestination());
    }

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

        this.service = null;
        this.status = FleetStatus.FREE;

    }

    @Override
    /**
     * gets the next location from the driving route
     */
    public void move() {
        // if somewhere to go, go
        if (!this.route.isEmpty()) {
            this.location = this.route.get(0);
            this.route.remove(0);
        }

        // if not moving and there is a service, check if at dropoff location
        if (this.route.isEmpty()) {
            if (this.service.size() != 0) {

                IService service = this.getClosestService();
                ILocation destination = service.getDropoffLocation();
                if (this.location.getX() == destination.getX() && this.location.getY() == destination.getY()) {
                    notifyArrivalAtDropoffLocation();
                }
            }
        }
    }


    @Override
    public String toString() {
        return this.getId() + " at " + this.getLocation() + " driving to " + this.getDestination() +
                ((this.status == FleetStatus.FREE) ? " is free with path " + showDrivingRoute(this.route) :
                        (this.status == FleetStatus.INRIDE) ? " driving themselves to destination" : "");
    }

    public IService getClosestService() {
        if (this.service.size() == 0 || this.getStatus() == FleetStatus.FREE)
            return null;
        else
            return this.service.get(0);
    } // only 1 service

}
