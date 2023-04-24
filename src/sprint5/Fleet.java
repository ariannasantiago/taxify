package sprint5;

import java.util.ArrayList;
import java.util.List;


/**
 * ABSTRACT class vehicle implements sprint5.IVehicle
 * constructor takes in id and location (x,y)
 * implements sprint5.IVehicle methods, includes information for taxi and shuttle as child classes
 */

public abstract class Fleet implements IFleet {
    private int id;
    private ITaxiCompany company;
    //new --> changed to a list isntead of a single instance
    //private List<IService> service;
    //private VehicleStatus status;
    private ILocation location;
    protected ILocation destination;
    private IStatistics statistics;
    protected List<ILocation> route;
        

     /**
     * sprint5.Vehicle class constructor, takes in unique user ID and pickup location (x,y)
     */
    public Fleet(int id, ILocation location) {
        this.id = id;
        //this.service = new ArrayList<IService>(); // initialize the array list (it could only contain one)
        //this.status = VehicleStatus.FREE;
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
     * getter method: returns the company
     */
    public ITaxiCompany getCompany() {
        return this.company;
    }
    
//    @Override
//     /**
//     * getter method: returns service
//     */
//    public List<IService> getService() {
//       return this.service;
//    }
    
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
    
//    @Override
//     /**
//     * pick a service, set destination to the service pickup location, and status to "pickup"
//     */
//    public void pickService(IService service) {
//
//        this.service.add(service);
//        this.destination = service.getPickupLocation();
//        this.route = setDrivingRouteToDestination(this.location, this.destination);
//        this.status = VehicleStatus.PICKUP;
//
//    }

//    @Override
//    /**
//     * set destination to the service drop-off location, update the driving route,
//     * set status to "service"
//     */
//    public void startService() {
//         // need a method to get the current service -- since we made it an array list
//        this.status = VehicleStatus.SERVICE;
//        this.destination = this.getClosestService().getDropoffLocation();
//        //used get pickuplocation() as start, could alternatively be this.location
//        this.route = setDrivingRouteToDestination(this.location, this.destination);
//    }

//    @Override
//    /**
//     * ending a service, resetting all the settings
//     */
//    public void endService() {
//
//        // update vehicle statistics
//
//        IService service = this.getClosestService();
//
//        this.statistics.updateBilling(this.calculateCost(service));
//        this.statistics.updateDistance(service.calculateDistance());
//        this.statistics.updateServices();
//
//        // if the service is rated by the user, update statistics
//
//        if (service.getStars() != 0) {
//            this.statistics.updateStars(service.getStars());
//            this.statistics.updateReviews();
//        }
//
//
//        // set service to null, and status to "free"
//
//        this.service.remove(service);
//
//        if (this.service.size() ==0){
//            this.destination = ApplicationLibrary.randomLocation(this.location);
//            this.status = VehicleStatus.FREE;
//            this.route = setDrivingRouteToDestination(this.location, this.destination);
//
//
//        }
//        else{
//            this.destination = this.getClosestService().getDropoffLocation();
//            this.status = VehicleStatus.SERVICE;
//            this.route = setDrivingRouteToDestination(this.location, this.destination);
//
//
//    }
//}
    
//    @Override
//    /**
//     * notifying the company that the vehicle is at the pickup location,
//     * then start the service
//     */
//    public void notifyArrivalAtPickupLocation() {
//        this.company.arrivedAtPickupLocation(this);
//        this.startService();
//    }
        
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

     abstract void endService();

    /**
     * returns true if the status of the vehicle is "free" and false otherwise
     */
    public abstract boolean isFree();


//    @Override
//    /**
//     * ADDED THIS: A getter method to get the status of the vehicle, before there was only a isFree() method
//     */
//    public VehicleStatus getStatus(){
//        return this.status;
//    }

    
    @Override
    /**
     * gets the next location from the driving route
     */
    public void move() {

        // to do --> fix this for two cars
        
        this.location = this.route.get(0);  
              
        this.route.remove(0);



        if (this.route.isEmpty()) {
            // check types here
            if (this.service.size() == 0) {
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
     * super method of sprint5.Vehicle, and the specific taxi and shuttle cost
     * are calculated in their own methods 
     *
     */
    public int calculateCost(IService service) {

        if (service.getShared()==true){
            return (service.calculateDistance()-2);
        }
        else return service.calculateDistance();
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

//    @Override
//     /**
//     * turns entire method to string --> changed this to incorporate the new rideshare
//     */
//    public String toString() {
//
//        if (this.service.size()==1){
//            return this.id + " at " + this.location + " driving to " + this.destination +
//            ((this.status == VehicleStatus.FREE) ? " is free with path " + showDrivingRoute(this.route): ((this.status == VehicleStatus.PICKUP) ? " to pickup user " + this.getClosestService().getUser().getId() : " in service "));
//
//
//        }
//        else{
//            return this.id + " at " + this.location + " driving to " + this.destination +
//            ((this.status == VehicleStatus.FREE) ? " is free with path " + showDrivingRoute(this.route): ((this.status == VehicleStatus.PICKUP) ? " to pickup user " + this.getClosestService().getUser().getId() : " in service ")) + " shared ride with user" ;
//
//
//
//        }
//    }


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

//    @Override
//    public int getDistanceFromPickUp(IService service) {
//        return Math.abs(this.location.getX() -  service.getPickupLocation().getX()) + Math.abs(this.location.getY() -  service.getPickupLocation().getY());
//      }
//
//      @Override
//      public int getDistanceFromDropoff(IService service) {
//        return Math.abs(this.location.getX() - service.getDropoffLocation().getX()) + Math.abs(this.location.getY() - service.getDropoffLocation().getY());
//      }

//    @Override
//    public IService getClosestService() {
//        // returns the current and closest service that the vehicle is in (can be more than one)_
//
//        if (this.status == VehicleStatus.PICKUP){
//            // return the most recently added service
//            IService last_service = this.service.get(this.service.size() - 1);
//            return last_service;
//
//        }
//        else if (this.status == VehicleStatus.SERVICE){
//
//          IService service = null;
//          int min = 1000000;
//
//          for (IService serv : this.service) {
//
//            if (this.getDistanceFromDropoff(serv)< min) {
//              min = this.getDistanceFromDropoff(serv);
//              service = serv;
//            }
//
//          }
//          return service;
//        }
//        return null;
//
//
//      }
    



}

