package sprint5;

/**
 * class scooter extends micro 
 */
public class Scooter extends Micro {

    /**
 * constructor --> takes in id and location
 */
    public Scooter(int id, ILocation location) {
        super(id, location);
    }

    /**
 * overriden calculate cost method
 */
    @Override
    public int calculateCost(IService service) {
        return (super.calculateCost(service) * 3 / 4);
    }

    /**
     * overrriden to string
    */
    @Override
    public String toString() {
        return "Scooter " + super.toString();
    }
}
