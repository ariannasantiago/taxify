package sprint5;

/**
 *  The interface sprint5.ISubject declares methods to add and notify observers.
 */
public interface ISubject {
    
    public void addObserver(IObserver observer);
   
    public void notifyObserver(String message);
  
    
}
