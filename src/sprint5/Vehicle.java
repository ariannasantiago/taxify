package sprint5;

import java.util.ArrayList;
import java.util.List;

public class Vehicle extends Fleet{
    private List<IService> service;
    private VehicleStatus status;


    /**
     * sprint5.Vehicle class constructor, takes in unique user ID and pickup location (x,y)
     *
     * @param id
     * @param location
     */
    public Vehicle(int id, ILocation location) {
        super(id, location);
        this.service = new ArrayList<IService>(); // initialize the array list (it could only contain one)
        this.status = VehicleStatus.FREE;

    }

    /**
     * getter method: returns service
     */
    public List<IService> getService() {
        return this.service;
    }

    /**
     * pick a service, set destination to the service pickup location, and status to "pickup"
     */
    public void pickService(IService service) {

        this.service.add(service);
        this.destination = service.getPickupLocation();
        this.route = setDrivingRouteToDestination(this.getLocation(), this.getDestination());
        this.status = VehicleStatus.PICKUP;

    }

    @Override
    /**
     * returns true if the status of the vehicle is "free" and false otherwise
     */
    public boolean isFree() {
        if (this.status == VehicleStatus.FREE){
            return true;
        }
        return false;
    }

    /**
     * ADDED THIS: A getter method to get the status of the vehicle, before there was only a isFree() method
     */
    public VehicleStatus getStatus(){
        return this.status;
    }

    /**
     * set destination to the service drop-off location, update the driving route,
     * set status to "service"
     */
    public void startService() {
        // need a method to get the current service -- since we made it an array list
        this.status = VehicleStatus.SERVICE;
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
            this.status = VehicleStatus.FREE;
            this.route = setDrivingRouteToDestination(this.getLocation(), this.getDestination());

        }
        else{
            this.destination = this.getClosestService().getDropoffLocation();
            this.status = VehicleStatus.SERVICE;
            this.route = setDrivingRouteToDestination(this.getLocation(), this.getDestination());


        }
    }


    @Override
    /**
     * notifying the company that the vehicle is at the pickup location,
     * then start the service
     */
    public void notifyArrivalAtPickupLocation() {
        this.getCompany().arrivedAtPickupLocation(this);
        this.startService();
    }

    public int getDistanceFromPickUp(IService service) {
        return Math.abs(this.getLocation().getX() -  service.getPickupLocation().getX()) + Math.abs(this.getLocation().getY() -  service.getPickupLocation().getY());
    }

    public int getDistanceFromDropoff(IService service) {
        return Math.abs(this.getLocation().getX() - service.getDropoffLocation().getX()) + Math.abs(this.getLocation().getY() - service.getDropoffLocation().getY());
    }

    @Override
    public IService getClosestService() {
        // returns the current and closest service that the vehicle is in (can be more than one)_

        if (this.status == VehicleStatus.PICKUP){
            // return the most recently added service
            IService last_service = this.service.get(this.service.size() - 1);
            return last_service;

        }
        else if (this.status == VehicleStatus.SERVICE){

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
     * turns entire method to string --> changed this to incorporate the new rideshare
     */
    public String toString() {

        if (this.service.size()==1){
            return this.getId() + " at " + this.getLocation() + " driving to " + this.getDestination() +
                    ((this.status == VehicleStatus.FREE) ? " is free with path " + showDrivingRoute(this.route): ((this.status == VehicleStatus.PICKUP) ? " to pickup user " + this.getClosestService().getUser().getId() : " in service "));


        }
        else{
            return this.getId() + " at " + this.getLocation() + " driving to " + this.getDestination() +
                    ((this.status == VehicleStatus.FREE) ? " is free with path " + showDrivingRoute(this.route): ((this.status == VehicleStatus.PICKUP) ? " to pickup user " + this.getClosestService().getUser().getId() : " in service ")) + " shared ride with user" ;



        }
    }

}