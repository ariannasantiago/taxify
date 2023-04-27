package sprint5;

import java.util.Random;



/**
 * this class is a small library that provides functions needed by the
 *  application to calculate the distance between
 *  two locations or set a random location
 */

public class ApplicationLibrary {

    // width and height of the grid for choosing taxis 

    private static final int width = 10;
    private static final int height = 10;   
    

    // random generator to assign 
    public static int rand() {
        Random random = new Random();
        
        return random.nextInt(9767);
    }
    


    //random generator with a max cap 
    public static int rand(int max) {
        Random random = new Random();

        return random.nextInt(9767) % max;
    }
    
    // manhattan distance calculator 
    public static int distance(ILocation a, ILocation b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }
    
    
    // provides a random location
    public static ILocation randomLocation() {
        return new Location(rand(width), rand(height));
    }
    
    // random location with parameters
    public static ILocation randomLocation(ILocation location) {
        ILocation destination;
        
        do {
            destination = new Location(rand(width), rand(height));
            
        } while (distance(location, destination) < 3);  
            
        return destination;
    }
}
