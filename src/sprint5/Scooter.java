package sprint5;

/**
 * class sprint5.Taxi extends abstract class sprint5.Vehicle
 * taxi specific information, including taxi specific cost (different from shuttle)
 */
public class Scooter extends Micro {

    public Scooter(int id, ILocation location) {
        super(id, location);
    }

    @Override
    public int calculateCost(IService service) {
        return (super.calculateCost(service) * 3 / 4);
    }

    @Override
    public String toString() {
        return "Scooter " + super.toString();
    }
}
