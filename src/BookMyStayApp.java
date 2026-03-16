class Reservation {

    String guestName;
    String roomType;

    Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    void displayReservation() {
        System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
    }
}

class BookingRequestQueue {

    java.util.Queue<Reservation> queue;

    BookingRequestQueue() {
        queue = new java.util.LinkedList<>();
    }

    void addRequest(Reservation reservation) {
        queue.add(reservation);
        System.out.println("Booking request added for " + reservation.guestName);
    }

    void displayQueue() {
        System.out.println("\nCurrent Booking Requests (FIFO Order):");
        System.out.println("--------------------------------------");

        for (Reservation r : queue) {
            r.displayReservation();
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Book My Stay App v5.1");
        System.out.println("----------------------------");

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Double Room");
        Reservation r3 = new Reservation("Charlie", "Suite Room");

        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        bookingQueue.displayQueue();
    }
}