/**
Airline Management System:
- Users:
    - Should be able to search for flights based on from, to, date
    - Should be able to book flights
    - login
    - Store profile to fill during the booking
    - look at previous and upcoming bookings
    - select seat
- Flight details:
    - From
    - To
    - Date
    - Aircraft type
- Make Payments
- Support multiple types of seats
    - Economy, business
- Handle cancellations, refunds, flight changes
- Data consistency
- Flight crew and aircraft assignments

=========================
- Person -> User -> Crew, since crew can also be a User, so it has user privileges
and over that crew ones
*/

// how to incorporarte the reserving seat and then unblocking them after certain time?
// how do we incorporate multiple payment instruments and payment processors? which design pattern should i use since altho they inherit from the same PaymentInstrument class which is passed on to the interface function and hence its implementation, is there a way to avoid switch/if else based on the type of Payment instrument?
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
class Person {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String password;
}

class User extends Person {
    private List<PaymentInstrument> paymentInstruments;
}

class Crew extends User {
    private String employeeId;
}

class PaymentInstrument {
    private String name;
    private PaymentInstrumentType instrumentType;
}

interface PaymentProcessor {
    public void pay(PaymentInstrument paymentInstrument);
}

enum PaymentInstrumentType {
    CARD,
    BANK_ACCOUNT;
}

class Card extends PaymentInstrument {
    private String number;
    private String cvv;
    private String expiry;
}

class Bank extends PaymentInstrument {
    private String bankName;
    private String number; // different format
}

class Booking {
    private String flightDetailsId;
    private String userId;
    private Invoice invoice;
    private String seatNumber;
    private int bagCount;
}

class Invoice {
    String userId;
    String flightId;
    String bookingId;
    double amountPaid;
}

enum Airport {
    AUS, JFK, DAL;
}
enum AircraftType {
    BOEING_747, AIRBUS_1;
}
enum Carrier {
    SPIRIT, AMERICAN, DELTA, FRONTIER, ALASKA, SOUTHWEST;
}
enum FlightSchedule {
    DELAYED, BOARDING, SCHEDULED_ON_TIME;
}
class FlightDetails {
    private String flightDetailsId;
    private Airport from;
    private Airport to;
    private Date date;
    private Time takeOffTime;
    private Time boardingTime;
    private Time landingTime;
    private Time duration;
    private AircraftType aircraftType;
    private Carrier carrier;
    private FlightSchedule flightSchedule;
}

class UserService {
    private Map<String, User> userMap; // userIdToUserObject
    private Map<String, Crew> crewMap;


    public void createUser(String email, String passwordHash) {
        // more functions to add details to the user object
    }

    public void login(String email, String passwordHash) {

    }
}
enum SeatClass {
    BUSINESS, ECONOMY;
}
class Seat {
    private String id;
    private String seatNumber;
    private double cost;
    private SeatClass seatClass;
    private SeatStatusDetails seatStatusDetails;
}

enum SeatStatus {
    BOOKED, RESERVED, FREE, BLOCKED;
}
class SeatStatusDetails {
    private SeatStatus seatStatus;
    private String reservedUntil;
}

enum Response {
    SUCCESS, ERROR;
}

class FlightDetailsService {
    private Map<Date, Map<Airport, Map<Airport, List<String>>>> dateFromToFlightDetailsIdMap;
    private Map<String, FlightDetails> flightDetailsMap;

    public List<FlightDetails> getFlights(Date date, Airport from, Airport to) {
        // can be used for searching
        return null;
    }

    public FlightDetails getFlightDetailsForFlightId (String id) {
        return flightDetailsMap.get(id);
    }
}

class BookingService {
    private UserService userService;
    private Map<String, String> flightDetailsIdToBookingIdMap;
    private Map<String, Booking> bookingMap;
    private Map<String, Map<SeatClass, List<String>>> flightIdSeatIdMap;
    private Map<String, Seat> seatMap;

    public Booking bookFlight (String flightDetailsId, String seatId, String userId) {
        // autofill info from User Object
        // create a booking object, add seat, user, flightDetailsID
        // add to the bookingMap;
        // update the flightDetailsIdToBookingIdMap
        // remove seat from flightIdSeatIdMap
        // create invoice;
        return null;// return booking
    }

    public List<Seat> getSeatsForFlightId (String id, SeatClass seatClass) {
        return flightIdSeatIdMap.get(id).get(seatClass).stream().map(seatMap::get).collect(Collectors.toList());
    }

    public void addBags (String bookingId, int bagsCount) {
        // update the bags count in the booking, update invoice
    }
}

class CheckoutService {
    private BookingService bookingService;
    private UserService userService;
    public synchronized void checkout (Booking booking, PaymentInstrument paymentInstrument) {
        // get user ID
        // userservice shows the payment options
        // payment processor pays
    }
}

class AircraftManagementService {
    private FlightDetailsService flightDetailsService;
    private BookingService bookingService;
    private Map<String, String> flightDetailsToCrewMap;
    private Map<String, Crew> crewMap;

    public void addFlight() { // add appropriate flight details

    }
    public void addSeats() {

    }
    public void addCrew(String flightId, String crewId) {

    }
}