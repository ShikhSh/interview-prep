/**
Library Management System:
- Initial welcome/login page with search

Use Cases:
- Users:
    - Should be able to borrow books
    - Should be able to return books
    - Should be able to see reserved books
    - Should be able to see their fine
- Library:
    - Should be able to add, remove books


================================ Review Notes ================================
What are DAOs/Repositories?
- DAO (Data Access Object) or Repository is responsible for storing, retrieving,
and querying data for a particular entity or aggregate (e.g., UserDao, BookRepository).
- They are the only classes that should be interacting directly with data structures
(maps, lists, or even real databases).
- Services (like UserService, ReservationService) should use DAOs to fetch, persist,
or modify data but should not directly manage raw data structures themselves.
- Just One Small Note
Your methods like reserveBook and returnBook should not themselves update the DAOs directly; rather, they should delegate all business logic to the relevant service, which then uses DAOs.
Example:
public void reserveBook(String userId, String bookId) {
    userService.borrow(userId, bookId); // userService handles all business logic and DAO calls
}
- LibraryManagementSystem class structure is good as a top-level controller or “facade” for user flows.
It centralizes routing for login, home, and core actions, then delegates to relevant services
(like UserService, SearchService, etc.), which is perfectly fine if you don’t let it grow into a god class.
For an LLD interview, you can say:

- How would it interact with FrontEnd?
"I would expose the core functionalities—login, search, borrow, return, etc.—as REST API endpoints from the backend.
The frontend could be built in any popular technology like React or Angular, or even NodeJS if server-side rendering
or an API proxy is desired. The frontend would simply call the appropriate backend endpoints for each action,
such as /login, /search, /borrow, and handle user flows accordingly."

- About DAOs:
Why is this Good?
Encapsulation: All data logic is in one place.
Single Responsibility: DAOs manage data; services manage business logic.
Easy to change storage: Replace in-memory DAO with a database-backed DAO without changing service/business logic.
Testability: You can easily mock DAOs for unit testing services.

- About date to calculate due date:
LocalDate targetDate = LocalDate.now().plusDays(2);

- Further Enums:
- BookFormat - hardcover, paperback, ebook, audiobook
- Reservation - can be made online or in-person
- Reservation - can have a status (active, completed, cancelled)
- Can enhance search functionality like in the Unix File Search
- Can give them option to renew reservation
- User service can invoke notification service to provide notifications to users
- Synchronization for book reservations
- Schedule notifications 2 days before due date
    “I’d create a NotificationService for delivering messages, and a scheduled background job
    (either using a Java scheduler or cron) that runs daily, scans reservations whose due date
    is in two days, and sends reminders via NotificationService. Each reservation would be marked
    to prevent duplicate notifications. This is robust, scalable, and ensures notifications go out
    even if the app restarts. In a production system, this scheduler could be a microservice or a cloud function.”
    
    a) Daily or Hourly Job (Scheduled by System/Framework)
    At a fixed time interval (e.g., every night at midnight), run a task:
    Scan all reservations where dueDate == (today + 2 days) and reminderNotSent == false
    For each, call NotificationService.sendDueDateReminder(user, reservation)
    Mark reminderNotSent = true (so you don’t send again)
    b) Enhance Reservation Object to mark if reminder was sent 2 days before due date, maybe in a HM
    c) Enhance reservationDao to send notifications whose due date is in 2 days
    d) Sample Pseudo-Code for Scheduler
        class DueDateReminderScheduler {
            private ReservationDao reservationDao;
            private UserDao userDao;
            private NotificationService notificationService;

            public void run() {
                LocalDate targetDate = LocalDate.now().plusDays(2);
                List<Reservation> reservations = reservationDao.findDueOn(targetDate);
                for (Reservation reservation : reservations) {
                    if (!reservation.isReminderSent()) {
                        User user = userDao.getUser(reservation.getUserId());
                        notificationService.sendDueDateReminder(user, reservation);
                        reservation.setReminderSent(true);
                        reservationDao.update(reservation);
                    }
                }
            }
        }

================================== Visualizing the Flow
- UI Layer:
User chooses “login as user” → UI sends login request to UserService (via API).
User searches for books → UI calls SearchService or UserService as appropriate.
User borrows book → UI calls UserService.borrow(userId, bookId) via an endpoint.

- Service Layer
Services encapsulate the business logic for each action (validations, updating DB via DAOs, etc.)
Only surface the methods actually needed for their user role.

- LibraryManagementService or Main Controller
Optional “welcome” or routing logic. It might simply direct the UI to the correct page/option set, or act as a single point for integration in smaller apps.
Most logic lives in dedicated service classes.


*/

// Should i use an interface or a class for this? since i need to
// store fields and not define functions necessarily
// how should i call this service? Like how should LibraryManagementService redirect to UserService and all ...?

// ----------- use a class for this to encapsulate fields, use an
// interface Person {
//     public String getName();
//     public String getId();
// }
import java.util.*;
class Person {
    private String name;
    private String id;
}

class Author extends Person {
    // her additional fields like biography, etc.
}

class User extends Person {
    private double fine;
    // private Map<String, Reservation>> reservation; -> moved to the ReservationDao
    private String phoneNumber;
}

interface UserDao {
    
    public User getUser(String userId);
    public List<Book> getUserReservations(String userId);
}

class Librarian extends Person {
    // her additional fields like employeeId, etc.
}

class Book {
    private String name;
    private List<Author> authors;
}

enum BookStatus {
    AVAILABLE,
    BORROWED;
}

class BookInstance extends Book {
    private String id;
    private String isbn;
    private String barcode;
    private String publishDate;
    private BookStatus bookStatus;
}

interface BooksRepo {
    public List<BookInstance> searchByName(String bookName);
    public List<BookInstance> searchByAuthor(String authorName);
}

class BooksRepoImpl implements BooksRepo {
    private Map<String, List<BookInstance>> nameToBookMap;
    private Map<String, List<BookInstance>> authorToBookMap;
    private Map<String, Reservation> bookIdToReservation;
    
    @Override
    public List<BookInstance> searchByName(String bookName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchByName'");
    }
    @Override
    public List<BookInstance> searchByAuthor(String authorName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchByAuthor'");
    }
}

class Reservation {
    private String reservationId;
    private String userId;
    private String bookId;
    private Date dueDate;
    private double fine;
}

class ReservationDao {
    private Map<String, Reservation> reservationIdToReservationMap; // maybe needed by libararian to search by reservationId
    private Map<String, Reservation> userIdToReservationMap;
}

class Config {
    public static Config INSTANCE;
    // private Constructor

    private int MAX_BOOKS_ALLOWED;
}

class UserService {
    private SearchService searchService;
    // private AuthenticationService authService; // injected
    private FineCalculationService fubCalculationService;
    public String authenticate (String email, String passwordHash) {
        return "Authenticated";
    }
    public String authenticate (String barcode) {
        return "Authenticated";
    }

    public Reservation borrow(String userId, String bookId) {

        // check if number of reservations == MAX_BOOKS_ALLOWED from config
        // return null
        // create new reservation object
        // update bookInstance status
        // add reservation to User's object
        // update the ReservationDao's reservationIdToReservationMap
        // update BooksRepoImpl's bookIdToReservation
        // return reservation
    }

    public boolean returnBook(String userId, String bookId) {
        // update bookInstance status
        // get reservation from BooksRepoImpl using bookId
        // update user service
        // calculate fine
        // update the ReservationDao's reservationIdToReservationMap
        // update BooksRepoImpl's bookIdToReservation
        // return true if successfully removed
    }
    // add new user?
}

class FineCalculationService {
    public double calculateFine (Date returnDate) {
        // calculateFine for the book
        return 1.9;
    }
}

enum SearchCriterion{
    SEARCH_BY_NAME,
    SEARCH_BY_AUTHOR,
}

class SearchService {
    public List<Book> search(String param, SearchCriterion criterion) {
        // calculateFine for the book
        // based on search criterion - using switch, use different maps from
        // BooksRepoImpl to find the list of books
        return null;
    }
}

class LibrarianService {
    // add or remove books
    private SearchService searchService;
    private UserService userService; // add/remove users
}

class LibraryManagementSystem {
    public static LibraryManagementSystem INSTANCE;
    // private constructor to make it Singleton pattern
    private UserService userService;
    private LibrarianService librarianService;
    private SearchService searchService;

    public void home() {
        // redirect to userService or librarianService based on user type
    }

    public void loginAsUser(String email, String passwordHash) {
        // use userService to authenticate
    }
    public void loginAsLibrarian(String email, String passwordHash) {
        // use userService to authenticate
    }
    public void reserveBook(String userId, String bookId) {
        // use userService to borrow the book
        // update the reservation in ReservationDao
        // update the book status in BooksRepoImpl
    }
    public void returnBook(String userId, String bookId) {
        // use userService to return the book
        // update the reservation in ReservationDao
        // update the book status in BooksRepoImpl
    }
    public List<Book> searchBooks(String param, SearchCriterion criterion) {
        return null; // use searchService to search for books
    }
}