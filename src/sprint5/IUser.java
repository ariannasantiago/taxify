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

     // NEW : added a getLocation() and move() method to the user class
    public ILocation getLocation();

    public void requestService();

    public void rateService(IService service);

    public void setCompany(ITaxiCompany company);

    public String toString();

   

}
