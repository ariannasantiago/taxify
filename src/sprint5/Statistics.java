package sprint5;

/**
 * class sprint5.Statistics implements Istatistics
 * takes no parameters, sets them with setter methods, initializes data to 0 in constructor
 */

public class Statistics implements IStatistics {
    private int services;
    private int reviews;
    private double stars;
    private int distance;
    private int billing;


    /**
     * constructor --> takes in no parameters, initializes data to 0
     */
    public Statistics() {
        this.services = 0;
        this.reviews = 0;
        this.stars = 0;
        this.distance = 0;
        this.billing = 0;
    }

    /**
     * getter method: returns number of services
     */
    @Override
    public int getServices() {
        return this.services;

    }

    /**
     * getter method: returns number of reviews
     */
    @Override
    public int getReviews() {
        return this.reviews;

    }

    /**
     * getter method: returns number of stars
     */
    @Override
    public double getStars() {
        return this.stars;

    }

    /**
     * getter method: returns distance
     */
    @Override
    public int getDistance() {
        return this.distance;

    }

    /**
     * getter method: returns billing
     */
    @Override
    public int getBilling() {
        return this.billing;

    }

    /**
     * setter method: updates number of services
     */
    @Override
    public void updateServices() {
        this.services++;
    }

    /**
     * setter method: updates number of reviews
     */
    @Override
    public void updateReviews() {
        this.reviews++;

    }

    /**
     * setter method: updates number of stars
     */
    @Override
    public void updateStars(int stars) {
        this.stars = stars;

    }

    /**
     * setter method: updates distance
     */
    @Override
    public void updateDistance(int distance) {
        this.distance = distance;

    }

    /**
     * setter method: updates billing
     */
    @Override
    public void updateBilling(int billing) {
        this.billing = billing;

    }

}
