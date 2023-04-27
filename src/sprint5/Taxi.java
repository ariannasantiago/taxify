package sprint5;

/**
 * class Taxi extends abstract class Vehicle
 * taxi specific information, including taxi specific cost (different from shuttle)
 */
public class Taxi extends Vehicle {

    /**
     * constructor --> takes in id and location
     */
    public Taxi(int id, ILocation location) {
        super(id, location);
    }

    /**
     * overridden calculate cost method
     */
    @Override
   
    public int calculateCost(IService service) {
        return super.calculateCost(service) * 2;
    }

    /**
     * overridden to string
     */
    @Override
    public String toString() {
        return ("Taxi    " + this.getId() + " with " + this.getService().size() + " services "+ super.toString());
    }
}
