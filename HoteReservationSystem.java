import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Room {
    private int roomNumber;
    private String category;
    private double pricePerNight;
    private boolean isAvailable;

    public Room(int roomNumber, String category, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.pricePerNight = pricePerNight;
        this.isAvailable = true;
    }

    public int getRoomNumber() { return roomNumber; }
    public String getCategory() { return category; }
    public double getPricePerNight() { return pricePerNight; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
}

class Booking {
    private String guestName;
    private Room room;
    private int nights;
    private double totalAmount;

    public Booking(String guestName, Room room, int nights) {
        this.guestName = guestName;
        this.room = room;
        this.nights = nights;
        this.totalAmount = room.getPricePerNight() * nights;
    }

    public String getGuestName() { return guestName; }
    public Room getRoom() { return room; }
    public int getNights() { return nights; }
    public double getTotalAmount() { return totalAmount; }
}

public class Main {
    private static List<Room> rooms = new ArrayList<>();
    private static List<Booking> bookings = new ArrayList<>();

    public static void main(String[] args) {
        initializeRooms();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("=========================================");
        System.out.println("   WELCOME TO HOTEL RESERVATION SYSTEM   ");
        System.out.println("=========================================");

        while (running) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Make a Reservation");
            System.out.println("3. View My Booking Details");
            System.out.println("4. Exit");
            System.out.print("Choose an option (1-4): ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    displayAvailableRooms();
                    break;
                case 2:
                    makeReservation(scanner);
                    break;
                case 3:
                    viewBookings();
                    break;
                case 4:
                    running = false;
                    System.out.println("\nThank you for using Hotel Reservation System!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select from 1 to 4.");
            }
        }
        scanner.close();
    }

    private static void initializeRooms() {
        rooms.add(new Room(101, "Standard", 100.0));
        rooms.add(new Room(102, "Standard", 100.0));
        rooms.add(new Room(201, "Deluxe", 200.0));
        rooms.add(new Room(202, "Deluxe", 200.0));
        rooms.add(new Room(301, "Suite", 350.0));
    }

    private static void displayAvailableRooms() {
        System.out.println("\n--- AVAILABLE ROOMS ---");
        boolean found = false;
        for (Room r : rooms) {
            if (r.isAvailable()) {
                System.out.println("Room #" + r.getRoomNumber() + " | Category: " + r.getCategory() + " | Price: $" + r.getPricePerNight() + " per night");
                found = true;
            }
        }
        if (!found) {
            System.out.println("Sorry, no rooms are currently available.");
        }
    }

    private static void makeReservation(Scanner scanner) {
        System.out.print("\nEnter guest name: ");
        String name = scanner.nextLine();

        displayAvailableRooms();
        System.out.print("Enter room number to book: ");
        int roomNum;
        try {
            roomNum = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid room number format!");
            return;
        }

        Room selectedRoom = null;
        for (Room r : rooms) {
            if (r.getRoomNumber() == roomNum && r.isAvailable()) {
                selectedRoom = r;
                break;
            }
        }

        if (selectedRoom == null) {
            System.out.println("Room not available or invalid room number.");
            return;
        }

        System.out.print("Enter number of nights to stay: ");
        int nights;
        try {
            nights = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid number of nights!");
            return;
        }

        Booking booking = new Booking(name, selectedRoom, nights);
        bookings.add(booking);
        selectedRoom.setAvailable(false);

        System.out.println("\n--- BOOKING SUCCESSFUL ---");
        System.out.println("Guest Name  : " + booking.getGuestName());
        System.out.println("Room Number : " + booking.getRoom().getRoomNumber() + " (" + booking.getRoom().getCategory() + ")");
        System.out.println("Stay        : " + booking.getNights() + " night(s)");
        System.out.println("Total Cost  : $" + booking.getTotalAmount());
    }

    private static void viewBookings() {
        System.out.println("\n--- CURRENT RESERVATIONS ---");
        if (bookings.isEmpty()) {
            System.out.println("No active bookings found.");
        } else {
            for (Booking b : bookings) {
                System.out.println("Guest: " + b.getGuestName() + " | Room: " + b.getRoom().getRoomNumber() + " | Nights: " + b.getNights() + " | Total: $" + b.getTotalAmount());
            }
        }
    }
}