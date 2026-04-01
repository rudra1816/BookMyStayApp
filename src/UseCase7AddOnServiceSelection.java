import java.util.*;

// Reservation class (from previous use cases concept)
class Reservation {
    private String reservationId;
    private String roomType;
    private String guestName;

    public Reservation(String reservationId, String roomType, String guestName) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.guestName = guestName;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getGuestName() {
        return guestName;
    }
}

// Booking History (stores all confirmed bookings)
class BookingHistory {

    private List<Reservation> reservations;

    public BookingHistory() {
        reservations = new ArrayList<>();
    }

    // Add confirmed reservation
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    // Get all reservations
    public List<Reservation> getAllReservations() {
        return reservations;
    }
}

// Booking Report Service
class BookingReportService {

    // Display all bookings
    public void displayAllBookings(List<Reservation> reservations) {
        System.out.println("---- Booking History ----");

        for (Reservation r : reservations) {
            System.out.println("Reservation ID: " + r.getReservationId());
            System.out.println("Guest Name   : " + r.getGuestName());
            System.out.println("Room Type    : " + r.getRoomType());
            System.out.println("-------------------------");
        }
    }

    // Generate summary report
    public void generateSummary(List<Reservation> reservations) {
        System.out.println("---- Booking Summary ----");
        System.out.println("Total Bookings: " + reservations.size());

        Map<String, Integer> roomCount = new HashMap<>();

        for (Reservation r : reservations) {
            roomCount.put(r.getRoomType(),
                    roomCount.getOrDefault(r.getRoomType(), 0) + 1);
        }

        System.out.println("Bookings by Room Type:");
        for (String type : roomCount.keySet()) {
            System.out.println(type + " : " + roomCount.get(type));
        }
    }
}

// Main class (your required name)
public class BookMyStayApp {

    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulating confirmed bookings (from UC6)
        history.addReservation(new Reservation("RES101", "Deluxe", "Rudra"));
        history.addReservation(new Reservation("RES102", "Standard", "Amit"));
        history.addReservation(new Reservation("RES103", "Deluxe", "Priya"));

        // Display all bookings
        reportService.displayAllBookings(history.getAllReservations());

        // Generate summary report
        reportService.generateSummary(history.getAllReservations());
    }
}