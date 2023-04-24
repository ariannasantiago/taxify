package sprint5;

import java.util.ArrayList;

public class Micro extends Fleet{

    private IService service;
    private MicroStatus status;

    /**
     * sprint5. Micro class constructor, takes in unique user ID and pickup location (x,y)
     *
     * @param id
     * @param location
     */
    public Micro(int id, ILocation location) {
        super(id, location);
        this.service = service; // initialize the array list (it could only contain one)
        this.status = MicroStatus.FREE;

    }


    @Override
    public boolean isFree() {
        return false;
    }

    public MicroStatus getStatus() {
        return this.status;
    }

    @Override
    public void bookService(IService service) {
        this.service = service;
        this.status = MicroStatus.BOOKED;

    }

    @Override
    public void startService() {
        this.status = MicroStatus.INRIDE;

        this.destination = this.service.getDropoffLocation();
        //used get pickuplocation() as start, could alternatively be this.location

        // does have a route --> account for the fact that it is in use
        this.route = setDrivingRouteToDestination(this.getLocation(), this.getDestination());
    }

    @Override
    public void endService() {
        // update vehicle statistics

        IService service = this.getService(); // get the first and only object in this list

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
        this.status = MicroStatus.FREE;

    }

    @Override
    /**
     * gets the next location from the driving route
     */
    public void move() {

        // to do --> fix this for two cars

        this.location = this.route.get(0);

        this.route.remove(0);


        if (this.route.isEmpty()) {
            if (this.service== null) {
                // stays in place, do nothing

//                this.destination = ApplicationLibrary.randomLocation(this.location);
//                this.route = setDrivingRouteToDestination(this.location, this.destination);


            }
            else {

                IService service = this.getService();
                // checks if the vehicle has arrived to a drop off location

                //ILocation origin = service.getPickupLocation();
                ILocation destination = service.getDropoffLocation();

//                if (this.location.getX() == origin.getX() && this.location.getY() == origin.getY()) {
//
//                    notifyArrivalAtPickupLocation();
//
//                } else
                if (this.location.getX() == destination.getX() && this.location.getY() == destination.getY()) {

                    notifyArrivalAtDropoffLocation();
                }
            }
        }
    }



    @Override
    public String toString() {
        return this.getId() + " at " + this.getLocation() + " driving to " + this.getDestination() +
                ((this.status == MicroStatus.FREE) ? " is free with path " + showDrivingRoute(this.route) :
                        (this.status == MicroStatus.INRIDE) ? " driving themselves to destination" : "");    }

    public IService getService() {
        return this.service;
    }

}
