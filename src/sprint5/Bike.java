package sprint5;

/**
 * class sprint5.Taxi extends abstract class sprint5.Vehicle
 * taxi specific information, including taxi specific cost (different from shuttle)
 */
public class Bike extends Micro {

    public Bike(int id, ILocation location) {
        super(id, location);
    }

    @Override
    public int calculateCost(IService service) {
        return (super.calculateCost(service) / 2);
    }

    @Override
    public String toString() {
        return "Bike " + super.toString();
    }
}
