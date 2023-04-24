package sprint5;
import java.util.ArrayList;
import java.util.List;


public abstract class Micro implements IVehicle {
    private int id;
    private ITaxiCompany company;
    //new --> changed to a list isntead of a single instance
    private IService service;
    private MicroStatus status;
    private ILocation location;
    private ILocation destination;
    private IStatistics statistics;
    private List<ILocation> route;

    /**
     * Vehicle class constructor, takes in unique user ID and pickup location (x,y)
     */
    public Micro(int id, ILocation location) {
        this.id = id;
        this.service = service; // initialize the array list (it could only contain one)
        this.status = MicroStatus.FREE;
        this.location = location;
        this.destination = ApplicationLibrary.randomLocation(this.location);
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
     * getter method: returns service
     */
    public List<IService> getService() {
        return (List<IService>) this.service; // casting this so it fits IVehicle declaration
    }

    @Override
    /**
     * getter method: returns statistics
     */
    public IStatistics getStatistics() {
        return this.statistics;
    }

    @Override
    /**
     * setter method: change the company to specific taxicompany
     */
    public void setCompany(ITaxiCompany company) {
        this.company = company;
    }

    @Override
    /**
     * pick a service, set destination to the service pickup location, and status to "pickup"
     */
    public void bookService(IService service) {
        this.service = service;

        this.status = MicroStatus.BOOKED;


    }


    /**
     * set destination to the service drop-off location, update the driving route,
     * set status to "service"
     */
    public void startService() {
        // need a method to get the current service -- since we made it an array list
        this.status = MicroStatus.INRIDE;

        this.destination = this.service.getDropoffLocation();
        //used get pickuplocation() as start, could alternatively be this.location

        // does have a route --> account for the fact that it is in use
        this.route = setDrivingRouteToDestination(this.location, this.destination);

    }

    @Override
    /**
     * ending a service, resetting all the settings
     */
    public void endService() {

        // update vehicle statistics

        IService service = this.getService().get(0); // get the first and only object in this list

        this.statistics.updateBilling(this.calculateCost(service));
        this.statistics.updateDistance(service.calculateDistance());
        this.statistics.updateServices();

        // if the service is rated by the user, update statistics

        if (service.getStars() != 0) {
            this.statistics.updateStars(service.getStars());
            this.statistics.updateReviews();
        }


        // set service to null, and status to "free"

        this.service = null;
        this.status = MicroStatus.FREE;



    }


    @Override
    /**
     * notifying the company that the vehicle is at the pickup location,
     * then start the service
     */
    public void notifyArrivalAtPickupLocation() {
        // leaving this method, when the service starts
        this.company.arrivedAtPickupLocation(this);
        // notify company that user arrived at pickup location
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

    }

    @Override
    /**
     * returns true if the status of the vehicle is "free" and false otherwise
     */
    public boolean isFree() {
        if (this.status == MicroStatus.FREE){
            return true;
        }
        return false;
    }


    @Override
    /**
     * ADDED THIS: A getter method to get the status of the vehicle, before there was only a isFree() method
     */


    // fix this --> return type incomptaible with the interface
    public MicroStatus getStatus(){
        return this.status;
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
                // the vehicle continues its random route

                this.destination = ApplicationLibrary.randomLocation(this.location);
                this.route = setDrivingRouteToDestination(this.location, this.destination);


            }
            else {

                IService service = this.getClosestService();
                // checks if the vehicle has arrived to a pickup or drop off location

                ILocation origin = service.getPickupLocation();

                ILocation destination = service.getDropoffLocation();

                if (this.location.getX() == origin.getX() && this.location.getY() == origin.getY()) {

                    notifyArrivalAtPickupLocation();

                } else if (this.location.getX() == destination.getX() && this.location.getY() == destination.getY()) {

                    notifyArrivalAtDropoffLocation();
                }
            }
        }
    }

    @Override
    /**
     * returns the cost of the service as the distance
     * super method of Vehicle, and the specific taxi and shuttle cost
     * are calculated in their own methods
     *
     */
    public int calculateCost(IService service) {


        return (int) (service.calculateDistance() * 5);



    }


    @Override
    /**
     * shows the route of the car in string format
     */
    public String showDrivingRoute(List<ILocation> route) {
        String s = "";

        for (ILocation l : route)
            s = s + l.toString() + " ";

        return s;
    }

    @Override
    /**
     * turns entire method to string --> changed this to incorporate the new rideshare
     */
    public String toString() {

        return this.id + " at " + this.location + " driving to " + this.destination +
                ((this.status == MicroStatus.FREE) ? " is free with path " + showDrivingRoute(this.route) :
                        (this.status == MicroStatus.INRIDE) ? " driving themselves to destination" : "");



    }

    private List<ILocation> setDrivingRouteToDestination(ILocation location, ILocation destination) {
        List<ILocation> route = new ArrayList<ILocation>();

        int x1 = location.getX();
        int y1 = location.getY();

        int x2 = destination.getX();
        int y2 = destination.getY();

        int dx = Math.abs(x1 - x2);
        int dy = Math.abs(y1 - y2);

        for (int i=1; i<=dx; i++) {
            x1 = (x1 < x2) ? x1 + 1 : x1 - 1;

            route.add(new Location(x1, y1));
        }

        for (int i=1; i<=dy; i++) {
            y1 = (y1 < y2) ? y1 + 1 : y1 - 1;

            route.add(new Location(x1, y1));
        }

        return route;
    }

    @Override
    public int getDistanceFromPickUp(IService service) {
        return Math.abs(this.location.getX() -  service.getPickupLocation().getX()) + Math.abs(this.location.getY() -  service.getPickupLocation().getY());
    }

    @Override
    public int getDistanceFromDropoff(IService service) {
        return Math.abs(this.location.getX() - service.getDropoffLocation().getX()) + Math.abs(this.location.getY() - service.getDropoffLocation().getY());
    }

    @Override
    public IService getClosestService() {
        // returns the current and closest service that the vehicle is in (can be more than one)_


        return this.service;

    }

}
