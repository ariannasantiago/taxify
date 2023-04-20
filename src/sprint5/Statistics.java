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


    public Statistics(){
        this.services = 0;
        this.reviews = 0;
        this.stars = 0;
        this.distance = 0;
        this.billing = 0;
    }

    @Override 
    public int getServices(){
        return this.services;

    }
    @Override 
    public int getReviews(){
        return this.reviews;

    }
    @Override 
    public double getStars(){
        return this.stars;

    }
    @Override 
    public int getDistance(){
        return this.distance;

    }
    @Override 
    public int getBilling(){
        return this.billing;

    }

    // this update doesnt take parameters since it is just updating the number of services 
    // (incrementing)

    @Override 
    public void updateServices(){
        this.services++;
    }
    @Override 
    public void updateReviews(){
        this.reviews++;

    }
    @Override 
    public void updateStars(int stars){
        this.stars = stars;

    }
    @Override 
    public void updateDistance(int distance){
        this.distance = distance;

    }
    @Override 
    public void updateBilling(int billing){
        this.billing = billing;

    }

}
