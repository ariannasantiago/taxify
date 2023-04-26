package sprint5;

/**
 * sprint5.Service implements sprint5.IService, provides the services of the taxi company -- services of the app, how it changes
 */
public interface IService {

    public boolean getShared();

    public void setShared(boolean shared);

    // instead of having a vehicle status as shared, just made it be an attribute in the service class
    public IUser getUser();

    public ILocation getPickupLocation();

    public ILocation getDropoffLocation();

    public int getStars();

    public void setStars(int stars);

    public int calculateDistance();

    public String toString();


}
