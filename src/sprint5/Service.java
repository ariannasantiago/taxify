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
    
    public Service(IUser user, ILocation pickup, ILocation dropoff, boolean shared) {
        // this.user = new ArrayList<sprint5.IUser>();
        this.user =user;
        this.pickup = pickup;
        this.dropoff = dropoff; 
        this.stars = 0;
        this.shared = false;
    }

    @Override
    public boolean getShared() {
        return this.shared;
    }

    @Override
    public void setShared(boolean shared) {
        this.shared = shared;
    }
    
    @Override
    public IUser getUser() {
        return this.user;
    }
    
    @Override
    public ILocation getPickupLocation() {
        return this.pickup;
    }
    
    @Override
    public ILocation getDropoffLocation() {
        return this.dropoff;
    }
    
    @Override
    public int getStars() {
        return this.stars;
    }
    
    @Override
    public void setStars(int stars) {
        this.stars = stars;
    }
    
    @Override
    public int calculateDistance() {
        return Math.abs(this.pickup.getX() - this.dropoff.getX()) + Math.abs(this.pickup.getY() - this.dropoff.getY());
    }
    
    @Override
    public String toString() {
        return this.getPickupLocation().toString() + " to " + this.getDropoffLocation().toString();
    }
}
