package sprint5;

/**
 * sprint5.User implements sprint5.IUser, gives info about the user
 * services that the user can request, user can rate, user specific information
 */
public interface IUser {

    public int getId();

    public String getFirstName();

    public String getLastName();

    public boolean getService();

    public void setService(boolean service);

    public ILocation getLocation();

    public void requestService();

    public void rateService(IService service);

    public void setCompany(ITaxiCompany company);

    public String toString();

    // NEW : added a getLocation() method to the user class

}
