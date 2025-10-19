package com.mycompany.ride.hailingsystem;

import java.util.Scanner;
import java.util.ArrayList;

public class RideHailingSystem {
     static Scanner scan = new Scanner(System.in);
     static ArrayList<BookingInfo> book_records = new ArrayList<>();
    
     public static void main(String[] args){
       char choice =' ';
       
       
          do{ 
            System.out.println("\n    RIDE-HAILING BOOKING SYSTEM");
            System.out.println("System Menu");
            System.out.println("a. View All Bookings");
            System.out.println("b. Book a Ride");
            System.out.println("c. Delete Booking");
            System.out.println("d. Generate Booking Report");
            System.out.println("e. Exit Application");
            System.out.print("Enter your choice: ");
            
            String input = scan.nextLine().toLowerCase();
            
            if(input.isEmpty()){
                System.out.println("\n INVALID INPUT! ENTER A LETTER FROM a-e ");
                continue;
            }
            choice = input.charAt(0);
            
            if (choice < 'a' || choice > 'e') {
                System.out.println("\n INVALID INPUT! ENTER A LETTER FROM a-e ");
                continue;
            }
            
           switch (choice) {
            case 'a':
                view_booking();
                break;

            case 'b':
                add_booking();
                break;

            case 'c':
                del_booking();
                break;

            case 'd':
                gen_report();
                break;

            case 'e':
                System.out.println("\n THANK YOU! HAVE A SAFE RIDE!");
                break;
           }


          }
          while(choice != 'e');
           
    }
    
    public static void view_booking(){
        System.out.println("\n      VIEW ALL BOOKINGS");
        if(book_records.isEmpty()) {
            System.out.println(" *NO BOOKINGS FOUND* ");
            return;
            
        }
        System.out.printf("%-5s %-22s %-12s %-12s %-17s %-17s %-12s %-12s%n",
            "ID No.", "Passenger", "Date", "Time", "Pickup", "Dropoff", "Distance", "Fare");
        
        int id_num = 1;
        for (BookingInfo b : book_records) {
            System.out.printf("%-5d %-22s %-12s %-12s %-17s %-17s %-12.2f %-12.2f%n",
                    id_num++, b.passenger_name, "N/A", "N/A",b.pickup_loc, b.dropoff_loc, b.distance_km, b.fare);
        }

    }
    public static void add_booking(){
        System.out.println("\n      BOOK A RIDE");
        
        System.out.print("Enter your name: ");
        String name = scan.nextLine();
        System.out.print("Enter your pickup location: ");
        String pickup = scan.nextLine();
        System.out.print("Enter your dropoff location: ");
        String dropoff = scan.nextLine();
        System.out.print("Enter the distance(in km): ");
        double distance = scan.nextDouble();
        scan.nextLine();
        double calculated_fare = 25.0;
        if(distance > 1){
            calculated_fare += (distance-1) * 20.0;
        }
        
        BookingInfo new_booking = new BookingInfo(name, pickup, dropoff, distance, calculated_fare);
        book_records.add(new_booking);
    }
    public static void del_booking(){
        System.out.println("\n      DELETE BOOKING");
        if(book_records.isEmpty()){
            System.out.println("*NO BOOKINGS TO DELETE*");
        }
        System.out.printf("%-5s %-22s %-12s %-12s %-17s %-17s %-12s %-12s%n",
            "ID No.", "Passenger", "Date", "Time", "Pickup", "Dropoff", "Distance", "Fare");
        
        int id_num = 1;
        for (BookingInfo b : book_records) {
            System.out.printf("%-5d %-22s %-12s %-12s %-17s %-17s %-12.2f %-12.2f%n",
                    id_num++, b.passenger_name, "N/A", "N/A",b.pickup_loc, b.dropoff_loc, b.distance_km, b.fare);
        }
        System.out.println("Enter booking number to delete: ");
        
    }
    public static void gen_report(){
         
    }
}
class BookingInfo {
    
    String passenger_name;
    String pickup_loc;
    String dropoff_loc;
    double distance_km;
    double fare;
    
    public BookingInfo(String passenger_name,String pickup_loc,String dropoff_loc,double distance_km,double fare) {
        this.passenger_name = passenger_name;
        this.pickup_loc = pickup_loc;
        this.dropoff_loc = dropoff_loc;
        this.distance_km = distance_km;
        this.fare = fare;
    }
  
    @Override
    public String toString() {
        return String.format("");
    }
      
}



    

