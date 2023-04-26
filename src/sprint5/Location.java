package sprint5;

/**
 * sprint5.Location class implements sprint5.ILocation
 * fills in interface methods --> takes X and Y coordinates as parameters to constructor
 */
public class Location implements ILocation {
    private int x_cord;
    private int y_cord;


    // location constructor takes two parameters, x and y coordinate to locate on map
    public Location(int x_cord, int y_cord) {
        this.x_cord = x_cord;
        this.y_cord = y_cord;
    }

    @Override
    public int getX() {
        return this.x_cord;

    }

    @Override
    public int getY() {
        return this.y_cord;

    }

    // to string method returns location as an ordered pair of points
    @Override
    public String toString() {
        return "(" + x_cord + "," + y_cord + ")";
    }

}
    

