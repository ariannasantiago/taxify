package sprint5;

public interface IApplicationSimulator {

    public void show();
    public void showStatistics();
    public void update();
    public void requestService();
    public int getTotalServices();

    public int getNumRequestedServices();
    
}
