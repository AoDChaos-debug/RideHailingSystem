import java.util.Scanner;
import java.util.ArrayList;

// main class for the ride-hailing booking system
public class RideHailingSystem {
    // Scanner for user input
    static Scanner scan = new Scanner(System.in);
    
    // ArrayList to store all booking records
    static ArrayList<BookingInfo> book_records = new ArrayList<>();
    
    // constants for fare calculation to avoid hardcoding numbers
    private static final double base_fare = 25.0;
    private static final double per_km_rate = 20.0;

    // Main method
    public static void main(String[] args) {
        char choice = ' ';
        
        // do-while loop to keep showing the menu until 'e' is chosen
        do { 
            System.out.println("\n    RIDE-HAILING BOOKING SYSTEM");
            System.out.println("System Menu");
            System.out.println("a. View All Bookings");
            System.out.println("b. Book a Ride");
            System.out.println("c. Delete Booking");
            System.out.println("d. Generate Booking Report");
            System.out.println("e. Exit Application");
            System.out.print("Enter your choice: ");
            
            // read user input and convert to lowercase
            String input = scan.nextLine().toLowerCase();
            
            // check for empty input
            if (input.isEmpty()) {
                System.out.println("\n INVALID INPUT! ENTER A LETTER FROM a-e ");
                continue;
            }
            
            // get the first character of input
            choice = input.charAt(0);
            
            // validate that choice is between 'a' and 'e'
            if (choice < 'a' || choice > 'e') {
                System.out.println("\n INVALID INPUT! ENTER A LETTER FROM a-e ");
                continue;
            }
            
            // switch statement for menu
            switch (choice) {
                case 'a':
                    viewBooking();  // call method to view active bookings
                    break;
                case 'b':
                    addBooking();  // call method to add a new booking
                    break;
                case 'c':
                    delBooking();  // call method to delete a booking
                    break;
                case 'd':
                    genReport();  // call method to generate a report of all bookings
                    break;
                case 'e':
                    System.out.println("\n THANK YOU! HAVE A SAFE RIDE!");
                    break;
            }
        } while (choice != 'e');  // loop until 'e' is chosen
    }

    // method to display a table of active bookings
    public static void showBookingList() {
        
        // check if there are no bookings at all
        if (book_records.isEmpty()) {
            System.out.println(" *NO BOOKINGS FOUND* ");
            return;
        }
        
        // print table header
        System.out.printf("%-5s %-22s %-12s %-12s %-17s %-17s %-12s %-12s%n",
            "ID No.", "Passenger", "Date", "Time", "Pickup", "Dropoff", "Distance", "Fare");
        
        int id_num = 1;  // counter for displaying ID number
        
        // loop through all bookings
        for (BookingInfo b : book_records) {
            
            // only show active bookings
            if (b.status) {
                
                // print a formatted table for the booking
                System.out.printf("%-5d %-22s %-12s %-12s %-17s %-17s %-12.2f %-12.2f%n",
                    id_num++, b.passenger_name, b.date, b.time, b.pickup_loc, b.dropoff_loc, b.distance_km, b.fare);
            }
        }
    }

    // method to view all active bookings
    public static void viewBooking() {
        System.out.println("\n      VIEW ALL BOOKINGS");
        showBookingList();  // display the list of active bookings
    }

    // method to add a new booking
    public static void addBooking() {
        System.out.println("\n      BOOK A RIDE");
        
        System.out.print("Enter your name: ");
        String name = scan.nextLine();
        
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scan.nextLine();
        
        System.out.print("Enter time (HH:MM): ");
        String time = scan.nextLine();
        
        System.out.print("Enter your pickup location: ");
        String pickup = scan.nextLine();
        
        System.out.print("Enter your dropoff location: ");
        String dropoff = scan.nextLine();
        
        // prompt and read distance with error handling
        System.out.print("Enter the distance (in km): ");
        double distance = 0.0;
        try {
            distance = Double.parseDouble(scan.nextLine());  // convert string to double
            
            // validate that distance is positive
            if (distance <= 0) {
                System.out.println("Distance must be positive. Booking cancelled.");
                return; 
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid distance. Please enter a number.");
            return;  
        }
        
        // calculate fare
        double calculated_fare = calculateFare(distance);
        
        // create a new BookingInfo object and add it to the list
        BookingInfo new_booking = new BookingInfo(name, date, time, pickup, dropoff, distance, calculated_fare);
        book_records.add(new_booking);
        
        System.out.println("\n      Booking successfully added!");
    }

    // method to calculate fare
    private static double calculateFare(double distance) {
        double fare = base_fare;  // Start with base fare
        if (distance > 1) {
            fare += (distance - 1) * per_km_rate;  // Add per-km rate for extra distance
        }
        return fare;  // return the total fare
    }

    // method to delete a booking by setting its status to false
    public static void delBooking() {
        
        // check if any active bookings exist
        boolean active_status = false;
        
        // loop to check for an active booking
        for (BookingInfo b : book_records) {
            if (b.status) {  
                active_status = true;
                break;
            }
        }
        
        if (!active_status) {
            System.out.println("*NO CURRENT BOOKINGS*");
            return;
        }
        
        showBookingList();
        
        // prompt for the ID to delete
        System.out.print("\n Enter the ID number you want to delete: ");
        int id_selected = 0;
        try {
            id_selected = Integer.parseInt(scan.nextLine());  // Convert to int
        } catch (NumberFormatException e) {
            System.out.println("INVALID INPUT! ENTER A NUMBER");
            return;
        }
        
        int id_counter = 0;  // counter to match user-selected ID
        
        // loop through bookings to find the one to delete
        for (int i = 0; i < book_records.size(); i++) {
            if (!book_records.get(i).status) continue;  // skip deleted bookings
            
            id_counter++;  // increment for active bookings only
            if (id_counter == id_selected) {  
                BookingInfo to_delete = book_records.get(i);
                
                // confirm deletion
                System.out.print("Are you sure you want to delete this booking? (y/n): ");
                String confirm = scan.nextLine().trim().toLowerCase();
                if (confirm.equals("y")) {
                    to_delete.status = false;  // soft-delete by setting status to false
                    System.out.println("Booking for " + to_delete.passenger_name + " has been removed.");
                } else {
                    System.out.println("Deletion cancelled.");
                }
                return;
            }
        }
        // if no matching ID found
        System.out.println("INVALID BOOKING NUMBER");
    }

    // method to generate a report of all bookings
    public static void genReport() {
        System.out.println("\n      BOOKING REPORT");
        
        // check if there are no bookings
        if (book_records.isEmpty()) {
            System.out.println(" *NO BOOKINGS FOUND* ");
            return;
        }
        
        // variables for summary
        int totalBookings = book_records.size();  // total bookings
        int activeBookings = 0;
        int deletedBookings = 0;
        double totalFare = 0.0;
        double totalDistance = 0.0;
        
        // loop through all bookings to calculate stats
        for (BookingInfo b : book_records) {
            totalFare += b.fare;  // sum of fares
            totalDistance += b.distance_km;  // sum of distances
            if (b.status) {
                activeBookings++;
            } else {
                deletedBookings++;
            }
        }
        
        // calculate average distance
        double avgDistance = totalDistance / totalBookings;
        
        // print summary of stats
        System.out.println("Total Bookings (All Time): " + totalBookings);
        System.out.println("Active Bookings: " + activeBookings);
        System.out.println("Deleted Bookings: " + deletedBookings);
        System.out.printf("Total Fare Collected (All Time): %.2f%n", totalFare);
        System.out.printf("Average Distance (All Time): %.2f km%n", avgDistance);
        
        System.out.println("\nDetailed List of All Bookings:");
        System.out.printf("%-5s %-22s %-12s %-12s %-17s %-17s %-12s %-12s %-10s%n",
            "ID No.", "Passenger", "Date", "Time", "Pickup", "Dropoff", "Distance", "Fare", "Status");
        
        int id_num = 1;
        
        // loop through all bookings for the detailed list
        for (BookingInfo b : book_records) {
            String statusStr = b.status ? "Active" : "Deleted";
            
            // print each booking's details
            System.out.printf("%-5d %-22s %-12s %-12s %-17s %-17s %-12.2f %-12.2f %-10s%n",
                id_num++, b.passenger_name, b.date, b.time, b.pickup_loc, b.dropoff_loc, b.distance_km, b.fare, statusStr);
        }
    }
}

// class to represent a single booking. Stores all details as fields.
class BookingInfo {
    
    final String passenger_name;
    final String date;
    final String time;
    final String pickup_loc;
    final String dropoff_loc;
    final double distance_km;
    final double fare;
    boolean status;  // mutable field for soft-deletion
    
    // constructor, initializes a new booking with given details
    public BookingInfo(String passenger_name, String date, String time, String pickup_loc, String dropoff_loc, double distance_km, double fare) {
        this.passenger_name = passenger_name;
        this.date = date;
        this.time = time;
        this.pickup_loc = pickup_loc;
        this.dropoff_loc = dropoff_loc;
        this.distance_km = distance_km;
        this.fare = fare;
        this.status = true;
    }
    
    @Override
    public String toString() {
        return String.format("Booking: %s on %s at %s from %s to %s, Distance: %.2f km, Fare: %.2f",
            passenger_name, date, time, pickup_loc, dropoff_loc, distance_km, fare);
    }
}
