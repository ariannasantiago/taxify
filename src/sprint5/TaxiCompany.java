package sprint5;

import java.util.List;


/**
 * sprint5.TaxiCompany class, implements sprint5.ITaxiCompany and sprint5.ISubject
 * defines the key methods for the taxi company
 */
public class TaxiCompany implements ITaxiCompany, ISubject {
    private String name;
    private List<IUser> users;
    private List<IFleet> vehicles;

    private int totalServices;
    private IObserver observer;

    /**
     * constructor, takes in name of taxi comapany, list of users, and list of vehicles
     * initializes totalServices to inital 0
     **/
    public TaxiCompany(String name, List<IUser> users, List<IFleet> vehicles) {
        this.name = name;
        this.users = users;
        this.vehicles = vehicles;
        this.totalServices = 0;

        // set the taxi company for users and vehicles
        for (int i = 0; i < this.users.size(); i++)
            this.users.get(i).setCompany(this);

        for (int i = 0; i < this.vehicles.size(); i++)
            this.vehicles.get(i).setCompany(this);
    }


    @Override
    /**
     * getter method to get the name of the taxi company 
     */
    public String getName() {
        return this.name;
    }

    @Override
    /**
     * getter method to get the number of total services 
     */
    public int getTotalServices() {
        return this.totalServices;
    }

    @Override
    /**
     * method for users to request a ride (service)
     */
    public boolean provideService(int user) {

        int userIndex = indexOfUserId(user);
        int vehicleIndex = findFreeVehicle();

        // if there is a free vehicle,
        // assign a random pickup and drop-off location to the new service
        // the distance between the pickup and the drop-off location should be at least 3 blocks

        if (vehicleIndex != -1) {
            ILocation origin, destination;

            do {
                origin = ApplicationLibrary.randomLocation();
                destination = ApplicationLibrary.randomLocation(origin);

            } while (ApplicationLibrary.distance(origin, destination) < 3);
            //this.vehicles.get(vehicleIndex).getLocation()


            // update the user status
            this.users.get(userIndex).setService(true);


            // FIRST OPTION: find MicroMobility vehicle and offer it to the user
            int micro_vehicle = -1;
            int distance_to_micro = 100000;
            for(IFleet ve : this.vehicles) {
                int distanceFromUser = ApplicationLibrary.distance(this.users.get(userIndex).getLocation(), ve.getLocation());
                if ((ve.getClass().getSimpleName().equals("Scooter") || (ve.getClass().getSimpleName().equals("Bike")) )
                        && ve.getStatus() == FleetStatus.FREE
                        && distanceFromUser < distance_to_micro && distanceFromUser >= 2) {
                    distance_to_micro = distanceFromUser;
                    micro_vehicle = ve.getId();
                }
            }

            if (micro_vehicle != -1 && ApplicationLibrary.rand() % 3 == 0) {
                micro_vehicle--; //decrement value to get index
                IFleet micro = this.vehicles.get(micro_vehicle);

                // create a service with the user, the micro's location as the origin, and the drop-off location
                Service service = new Service(this.users.get(userIndex), micro.getLocation(), destination, false);
                micro.bookService(service);

                notifyObserver("User " + this.users.get(userIndex).getId() + " requests a micro service from " + service + ", the ride is assigned to " +
                        micro.getClass().getSimpleName() + " " + micro.getId() + " at location " +
                        micro.getLocation());

                // update the counter of services

                this.totalServices++;

                return true;

            }


            // SECOND OPTION: find shared vehicle and offer it to the user

            // create a service with the user, the pickup and the drop-off location
            Service service = new Service(this.users.get(userIndex), origin, destination, false);

            int shared_vehicle = -1;
            int distance_to_shared = 100000;

            // determining "close" as manhattan distance of 2
            // look for shared vehicle first
            for (IFleet ve : this.vehicles) {
                if ((!ve.getClass().getSimpleName().equals("Scooter") && (!ve.getClass().getSimpleName().equals("Bike")) )
                        && ve.getStatus() == FleetStatus.SERVICE
                        && ve.getDistanceFromPickUp(service) < distance_to_shared && ve.getDistanceFromPickUp(service) >= 2) {
                    distance_to_shared = (ve.getDistanceFromPickUp(service));
                    shared_vehicle = ve.getId();
                }
            }

            // randomizing ride share acceptance
            if (shared_vehicle != -1 && ApplicationLibrary.rand() % 2 == 0) {
                shared_vehicle--;
                // getting an index out of bounds error so tried to decrement index

                IFleet shared = this.vehicles.get(shared_vehicle);

                // if this pickup is closer than other service dropoff, change this to current service and do pickup
                if (shared.getDistanceFromPickUp(service) < shared.getDistanceFromDropoff(shared.getClosestService())) {

                    if (shared.getService().size() == 1) {
                        shared.bookService(service);
                        service.setShared(true);
                    }

                    notifyObserver("User " + this.users.get(userIndex).getId() + " requests a shared service from " + service + ", the ride is assigned to " +
                            shared.getClass().getSimpleName() + " " + shared.getId() + " at location " +
                            shared.getLocation());

                    // update the counter of services

                    this.totalServices++;

                    return true;

                }
            } else { // rideshare rejected OR pickup farther than dropoff, doing the closest a free vehicle

                int free_vehicle = -1;
                int distance_to_free = 100000;

                //look for the closest free vehicle that's at least 2 blocks away
                for (IFleet ve : this.vehicles) {
                    if ( (!ve.getClass().getSimpleName().equals("Scooter") && (!ve.getClass().getSimpleName().equals("Bike")))
                            && ve.getStatus() == FleetStatus.FREE
                            && ve.getDistanceFromPickUp(service) < distance_to_free && ve.getDistanceFromPickUp(service) >= 2) {
                        distance_to_free = (ve.getDistanceFromPickUp(service));
                        free_vehicle = ve.getId();
                        //System.out.println("vehicle " + ve.getId() + "class name is: " + ve.getClass().getSimpleName());
                    }
                }

                this.users.get(userIndex).setService(true);

                // create a service with the user, the pickup and the drop-off location
                Service not_shared_service = new Service(this.users.get(userIndex), origin, destination, false);

                // assign the new service to a close free vehicle
                if (free_vehicle != -1) {
                    --free_vehicle;
                    this.vehicles.get(free_vehicle).bookService(not_shared_service);

                    notifyObserver("User " + this.users.get(userIndex).getId() + " requests a nonshared service from " + service + ", the ride is assigned to " +
                            this.vehicles.get(free_vehicle).getClass().getSimpleName() + " " + this.vehicles.get(free_vehicle).getId() + " at location " +
                            this.vehicles.get(free_vehicle).getLocation());
                } else {
                    // or to the free vehicle found initially
                    this.vehicles.get(vehicleIndex).bookService(not_shared_service);

                    notifyObserver("User " + this.users.get(userIndex).getId() + " requests a nonshared service from " + service+ ", the ride is assigned to " +
                            this.vehicles.get(vehicleIndex).getClass().getSimpleName() + " " + this.vehicles.get(vehicleIndex).getId() + " at location " +
                            this.vehicles.get(vehicleIndex).getLocation());
                }


                // update the counter of services

                this.totalServices++;

                return true;
            }
        }
        return false;
    }


    @Override
    /**
     * a vehicle arrives at the pickup location
     */
    public void arrivedAtPickupLocation(IFleet vehicle) {

        notifyObserver(String.format("%-8s", vehicle.getClass().getSimpleName()) + vehicle.getId() + " loads user " + vehicle.getClosestService().getUser().getId());
    }

    @Override
    /**
     *  a vehicle arrives at the drop-off location
     */
    public void arrivedAtDropoffLocation(IFleet vehicle) {

        IService service = vehicle.getClosestService();
        int user = service.getUser().getId();
        int userIndex = indexOfUserId(user);

        // the taxi company requests the user to rate the service, and updates its status

        this.users.get(userIndex).rateService(service);
        this.users.get(userIndex).setService(false);

        // update the counter of services

        this.totalServices--;

        notifyObserver(String.format("%-8s", vehicle.getClass().getSimpleName()) + vehicle.getId() + " drops off user " + user);
    }

    @Override
    /**
     *  adding an observer to a service
     */
    public void addObserver(IObserver observer) {
        this.observer = observer;
    }

    @Override
    /**
     *  notifying an observer with message about a service 
     */
    public void notifyObserver(String message) {
        this.observer.updateObserver(message);
    }

    /**
     * finds all free vehicles and returns the index of the vehicle in the list, otherwise it returns -1
     */
    //update this so that it returns all free vehicles not just one
    private int findFreeVehicle() {
        int count = 0;

        for (IFleet ve : this.vehicles) {
            if (ve.isFree() == true) {
                return count;
            }
            count += 1;
        }

        return -1;
    }

    /**
     * finds the index of a user with the input id in the list, otherwise it returns -1
     */
    private int indexOfUserId(int id) {
        int count = 0;

        for (IUser us : this.users) {
            if (us.getId() == id) {
                return count;
            }
            count += 1;
        }

        return -1;
    }

}
