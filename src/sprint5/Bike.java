package sprint5;

/**
 * micro vehicle Bike class --> extends micro 
 */
public class Bike extends Micro {

     /**
     * constructor 
     */
    public Bike(int id, ILocation location) {
        super(id, location);
    }

    /**
     * overridden calculate cost method
     */
    @Override
    public int calculateCost(IService service) {
        return (super.calculateCost(service) / 2);
    }

    /**
     * overriden to string
     */
    @Override
    public String toString() {
        return "Bike " + super.toString();
    }
}
