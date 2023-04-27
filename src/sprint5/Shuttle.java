package sprint5;

/**
 * class sprint5.Shuttle extends abstract class sprint5.Vehicle
 * shuttle specific information, including shuttle specific cost (different from taxi)
 */
public class Shuttle extends Vehicle {

    /**
     * constructor --> takes in id and location
     */
    public Shuttle(int id, ILocation location) {
        super(id, location);
    }

    /**
     * overridden calculate cost method
     */
    @Override
    
    public int calculateCost(IService service) {
        return (int) (super.calculateCost(service) * 1.5);
    }

    /**
     * overridden to string
     */
    @Override
    public String toString() {
        return ("Shuttle " + this.getId() + " with " + this.getService().size() + " services "+ super.toString());
    }
}
