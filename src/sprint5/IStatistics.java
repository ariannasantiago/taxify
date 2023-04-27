package sprint5;

/**
 * sprint5.Statistics implmements sprint5.IStatistics, gives stats of the app
 * outcome of rides, stars from users, reviews from users
 */
public interface IStatistics {

    public int getServices();

    public int getReviews();

    public double getStars();

    public int getDistance();

    public int getBilling();

    public void updateServices();

    public void updateReviews();

    public void updateStars(int stars);

    public void updateDistance(int distance);

    public void updateBilling(int billing);
    
}
