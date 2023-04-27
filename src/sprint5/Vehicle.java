package sprint5;

import java.util.ArrayList;
import java.util.List;

/**
     * class Vehicle extends abstract class fleet 
     */
public class Vehicle extends Fleet{
    private List<IService> service;
    //private VehicleStatus status;


    /**
     * sprint5.Vehicle class constructor, takes in unique user ID and pickup location (x,y)
     *
     * @param id: the vehicle's ID
     * @param location: the vehicle's initial location
     */
    public Vehicle(int id, ILocation location) {
        super(id, location, ApplicationLibrary.randomLocation(location));
        this.service = new ArrayList<IService>(); // initialize the array list (it could only contain one)
        this.status = FleetStatus.FREE;
    }

    /**
     * getter method: returns service
     */
    public List<IService> getService() {
        return this.service;
    }

    @Override
    /**
     * pick a service, set destination to the service pickup location, and status to "pickup"
     */
    public void bookService(IService service) {

        this.service.add(service);
        this.destination = service.getPickupLocation();
        this.route = setDrivingRouteToDestination(this.getLocation(), this.getDestination());
        this.status = FleetStatus.PICKUP;

    }

    @Override
    /**
     * returns true if the status of the vehicle is "free" and false otherwise
     */
    public boolean isFree() {
        if (this.status == FleetStatus.FREE){
            return true;
        }
        return false;
    }


    /**
     * set destination to the service drop-off location, update the driving route,
     * set status to "service"
     */
    public void startService() {
        // need a method to get the current service -- since we made it an array list
        this.status = FleetStatus.SERVICE;
        this.destination = this.getClosestService().getDropoffLocation();
        //used get pickuplocation() as start, could alternatively be this.location
        this.route = setDrivingRouteToDestination(this.getLocation(), this.getDestination());
    }

    /**
     * ending a service, resetting all the settings
     */
    public void endService() {

        // update vehicle statistics

        IService service = this.getClosestService();

        this.getStatistics().updateBilling(this.calculateCost(service));
        this.getStatistics().updateDistance(service.calculateDistance());
        this.getStatistics().updateServices();

        // if the service is rated by the user, update statistics

        if (service.getStars() != 0) {
            this.getStatistics().updateStars(service.getStars());
            this.getStatistics().updateReviews();
        }


        // set service to null, and status to "free"

        this.service.remove(service);

        if (this.service.size() ==0){
            this.destination = ApplicationLibrary.randomLocation(this.getLocation());
            this.status = FleetStatus.FREE;
            this.route = setDrivingRouteToDestination(this.getLocation(), this.getDestination());

        }
        else{
            this.destination = this.getClosestService().getDropoffLocation();
            this.status = FleetStatus.SERVICE;
            this.route = setDrivingRouteToDestination(this.getLocation(), this.getDestination());


        }
    }

    
    /**
     * get distance from service to the pickip location of user
     */
    public int getDistanceFromPickUp(IService service) {
        return Math.abs(this.getLocation().getX() -  service.getPickupLocation().getX()) + Math.abs(this.getLocation().getY() -  service.getPickupLocation().getY());
    }

    /**
     * get distance from service to the dropoff location of user
     */
    public int getDistanceFromDropoff(IService service) {
        return Math.abs(this.getLocation().getX() - service.getDropoffLocation().getX()) + Math.abs(this.getLocation().getY() - service.getDropoffLocation().getY());
    }

    /**
     * get the closest service to the vehicle
     */
    public IService getClosestService() {
        // returns the current and closest service that the vehicle is in (can be more than one)_

        if (this.status == FleetStatus.PICKUP){
            // return the most recently added service
            IService last_service = this.service.get(this.service.size() - 1);
            return last_service;

        }
        else if (this.status == FleetStatus.SERVICE){

            IService service = null;
            int min = 1000000;

            for (IService serv : this.service) {

                if (this.getDistanceFromDropoff(serv)< min) {
                    min = this.getDistanceFromDropoff(serv);
                    service = serv;
                }

            }
            return service;
        }
        return null;


    }

    @Override
    /**
     * gets the next location from the driving route
     */
    public void move() {

        // to do --> fix this for two cars
        if (!this.route.isEmpty()) {
            this.location = this.route.get(0);
            this.route.remove(0);
        }

        if (this.route.isEmpty()) {
            // check types here
            if (this.service.size() == 0) {
                // the vehicle continues its random route

                this.destination = ApplicationLibrary.randomLocation(this.getLocation());
                this.route = setDrivingRouteToDestination(this.getLocation(), this.getDestination());


            }
            else {

                IService service = this.getClosestService();
                // checks if the vehicle has arrived to a pickup or drop off location

                ILocation origin = service.getPickupLocation();

                ILocation destination = service.getDropoffLocation();

                if (this.getLocation().getX() == origin.getX() && this.getLocation().getY() == origin.getY()) {

                    notifyArrivalAtPickupLocation();

                } else if (this.getLocation().getX() == destination.getX() && this.getLocation().getY() == destination.getY()) {

                    notifyArrivalAtDropoffLocation();
                }
            }
        }
    }

    @Override
    /**
     * turns entire method to string --> changed this to incorporate the new rideshare
     */
    public String toString() {

        if (this.getStatus() == FleetStatus.FREE) {
            return " at " + this.getLocation() + " driving to " + this.getDestination() + " is free with path " + showDrivingRoute(this.route);
        } else if (this.service.size() == 1) {
            return " at " + this.getLocation() + " driving to " + this.getDestination() + " is available with path " + showDrivingRoute(this.route)
            + ((this.getStatus() == FleetStatus.PICKUP) ? " to pickup " : " to service ")
            + "user " + this.getClosestService().getUser().getId();
        } else {
            return " at " + this.getLocation() + " driving to " + this.getDestination() + "is available with path " + showDrivingRoute(this.route)
                    + ((this.getStatus() == FleetStatus.PICKUP) ? " to pickup " : " to dropoff ")
                    + "user " + this.getClosestService().getUser().getId();
                    //+ " and has " + this.getService().size() + " rideshare users";
        }


    }

}
