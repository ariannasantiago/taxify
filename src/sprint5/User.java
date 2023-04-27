package sprint5;

/**
 * class sprint5.User implements Iuser
 * user specific information, create a user with id, firstname, lastname
 * rate service method
 */
public class User implements IUser {
    private int id;
    private String firstName;
    private String lastName;
    private ITaxiCompany company;
    private boolean service;
    // new
    private ILocation location;


    /**
     * constructor
     */
    public User(int id, String firstName, String lastName, ILocation location) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.service = false;
        this.location = location;
    }

    /**
     * get user id 
     */
    @Override
    public int getId() {
        return this.id;
    }


    /**
     * get user first name
     */
    @Override
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * get user last name
     */
    @Override
    public String getLastName() {
        return this.lastName;
    }

    /**
     * get user service
     */
    @Override
    public boolean getService() {
        return this.service;
    }

    /**
     * set user service
     */
    @Override
    public void setService(boolean service) {
        this.service = service;
    }

    // NEW : added a getLocation()  to the user class
    @Override
    public ILocation getLocation() {
        return this.location;
    }

    /**
     *REQUEST SERVICE 
     */
    @Override
    public void requestService() {
        this.company.provideService(this.id);
    }

    /**
     *RATE SERVICE
     */
    @Override
    public void rateService(IService service) {
        // users rate around 50% of the services (1 to 5 stars)

        if (ApplicationLibrary.rand() % 2 == 0)
            service.setStars(ApplicationLibrary.rand(5) + 1);
    }

    /**
     *SET COMPANY
     */
    @Override
    public void setCompany(ITaxiCompany company) {
        this.company = company;

    }

    /**
     *TO STRING
     */
    @Override
    public String toString() {
        return this.getId() + " " + String.format("%-20s", this.getFirstName() + " " + this.getLastName());
    }

}
