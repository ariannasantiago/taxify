package sprint5;

/**
 * class sprint5.Service implements Iservice
 * parameters are user, pickup, dropoff, user sets stars after ride
 */
public class Service implements IService {
    private IUser user;
    private ILocation pickup;
    private ILocation dropoff;
    private int stars;
    private boolean shared;

    /**
    * constructor --> takes in user, pickup, dropoff, and boolean shared 
    */
    public Service(IUser user, ILocation pickup, ILocation dropoff, boolean shared) {
        // this.user = new ArrayList<sprint5.IUser>();
        this.user = user;
        this.pickup = pickup;
        this.dropoff = dropoff;
        this.stars = 0;
        this.shared = false;
    }

    /**
    * overriden get shared getter
    */
    @Override
    public boolean getShared() {
        return this.shared;
    }

    /**
    * overriden set shared setter
    */
    @Override
    public void setShared(boolean shared) {
        this.shared = shared;
    }

    /**
     * getter method: returns user
     */
    @Override
    public IUser getUser() {
        return this.user;
    }

    /**
     * getter method: returns pickup location
     */
    @Override
    public ILocation getPickupLocation() {
        return this.pickup;
    }

    /**
     * getter method: returns dropoff location
     */
    @Override
    public ILocation getDropoffLocation() {
        return this.dropoff;
    }

    /**
     * getter method: returns stars
     */
    @Override
    public int getStars() {
        return this.stars;
    }

    /**
     * setter method: sets stars
     */
    @Override
    public void setStars(int stars) {
        this.stars = stars;
    }

    /**
     * calculate distance between pickup and dropoff
     */
    @Override
    public int calculateDistance() {
        return Math.abs(this.pickup.getX() - this.dropoff.getX()) + Math.abs(this.pickup.getY() - this.dropoff.getY());
    }

    /**
     * toString method: returns pickup location to dropoff location
     */
    @Override
    public String toString() {
        return this.getPickupLocation().toString() + " to " + this.getDropoffLocation().toString();
    }
}
