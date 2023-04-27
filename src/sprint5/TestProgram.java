package sprint5; /**
 * test program to test changes made to taxify application
 * testing continuously on each sprint
 **/

import java.util.ArrayList;
import java.util.List;


public class TestProgram {

    public static void main(String[] args) {

        //Declare a list of users. Instantiate at least 15 users
        List<IUser> users = new ArrayList<IUser>();

        IUser liza = new User(1, "liza", "trundle", ApplicationLibrary.randomLocation());
        IUser ben = new User(2, "ben", "smith", ApplicationLibrary.randomLocation());
        IUser max = new User(3, "max", "moore", ApplicationLibrary.randomLocation());
        IUser emma = new User(4, "emma", "wayne", ApplicationLibrary.randomLocation());
        IUser nate = new User(5, "nate", "stone", ApplicationLibrary.randomLocation());
        IUser rachel = new User(6, "rachel", "wilson", ApplicationLibrary.randomLocation());
        IUser arianna = new User(7, "arianna", "santiago", ApplicationLibrary.randomLocation());
        IUser chris = new User(8, "chris", "wack", ApplicationLibrary.randomLocation());
        IUser james = new User(9, "james", "gresham", ApplicationLibrary.randomLocation());
        IUser kelly = new User(10, "kelly", "kat", ApplicationLibrary.randomLocation());
        IUser sam = new User(11, "sam", "davenport", ApplicationLibrary.randomLocation());
        IUser cameron = new User(12, "cameron", "green", ApplicationLibrary.randomLocation());
        IUser teddy = new User(13, "teddy", "donahue", ApplicationLibrary.randomLocation());
        IUser lily = new User(14, "lily", "grayson", ApplicationLibrary.randomLocation());
        IUser catherine = new User(15, "catherine", "frank", ApplicationLibrary.randomLocation());

        users.add(liza);
        users.add(ben);
        users.add(max);
        users.add(emma);
        users.add(nate);
        users.add(rachel);
        users.add(arianna);
        users.add(chris);
        users.add(james);
        users.add(kelly);
        users.add(sam);
        users.add(catherine);
        users.add(lily);
        users.add(teddy);
        users.add(cameron);

        //Declare a list of vehicles. Instantiate at least 10 vehicles (Taxis and Shuttles) and
        //place them at random locations of the city map

        List<IFleet> vehicles = new ArrayList<IFleet>();

        IFleet car1 = new Shuttle(1, ApplicationLibrary.randomLocation());
        IFleet car2 = new Taxi(2, ApplicationLibrary.randomLocation());
        IFleet car3 = new Taxi(3, ApplicationLibrary.randomLocation());
        IFleet car4 = new Taxi(4, ApplicationLibrary.randomLocation());
        IFleet car5 = new Shuttle(5, ApplicationLibrary.randomLocation());
        IFleet car6 = new Shuttle(6, ApplicationLibrary.randomLocation());
        IFleet car7 = new Shuttle(7, ApplicationLibrary.randomLocation());
        IFleet car8 = new Taxi(8, ApplicationLibrary.randomLocation());
        IFleet car9 = new Taxi(9, ApplicationLibrary.randomLocation());
        IFleet car10 = new Taxi(10, ApplicationLibrary.randomLocation());
        IFleet car11 = new Taxi(11, ApplicationLibrary.randomLocation());
        IFleet car12 = new Taxi(12, ApplicationLibrary.randomLocation());
        IFleet car13 = new Taxi(13, ApplicationLibrary.randomLocation());
        IFleet micro1 = new Scooter(14, ApplicationLibrary.randomLocation());
        IFleet micro2 = new Scooter(15, ApplicationLibrary.randomLocation());
        IFleet micro3 = new Bike(16, ApplicationLibrary.randomLocation());



        vehicles.add(car1);
        vehicles.add(car2);
        vehicles.add(car3);
        vehicles.add(car4);
        vehicles.add(car5);
        vehicles.add(car6);
        vehicles.add(car7);
        vehicles.add(car8);
        vehicles.add(car9);
        vehicles.add(car10);
        vehicles.add(car11);
        vehicles.add(car12);
        vehicles.add(car13);
        vehicles.add(micro1);
        vehicles.add(micro2);
        vehicles.add(micro3);

        TaxiCompany taxify = new TaxiCompany("Taxify", users, vehicles);
        ApplicationSimulator application = new ApplicationSimulator(taxify, users, vehicles);
        taxify.addObserver(application);


        application.show();
        for (int i = 1; i <= 5; i++) {
            application.requestService();
        }

        do {

            application.show();
            application.update();

            if (ApplicationLibrary.rand() % 4 == 0) application.requestService();

        } while (application.getNumRequestedServices() <= 150 && application.getTotalServices() != 0);

        application.showStatistics();


    }
}

      
