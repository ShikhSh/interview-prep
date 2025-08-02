/**
https://github.com/ycwkatie/OOD-Object-Oriented-Design/blob/main/ood/amazon_locker.md
Usecases:
- Receivers
    - Add new user
    - Remove user
    - Receive Notification about password with OTP

- PackageLocatorService
    - Enter otp/mobile phone to open locker
    - Given delivery Size, point to relevant locker

- DeliveryPerson
    - login for delivery person

- Users
    - Name
    - Phone
    - Email
    - House address

- DeliveryInfo
    - user Id
    - package Id
    - locker Id
    - DeliveryStatus

- DeliveryStatus
    - picked, pending, overdue

- LockerInfo
    - KioskId
    - LockerId
    - Status

- Status
    - Free
    - Occupied

- FinecalculationService
- NotificationService

=============================== Enhancements:
- Have a class Receipt to send to the Notification service
- OtpGenerator -> an interface and an implementation of it
- it should store the length of otp to be generated
- Slot filter strategy -> interface and one implementation
- Define exceptions:
    - LockerAlreadyExistsException
    - UserAlreadyExistsException
    - NoSlotExistsException
- batch job to go over the deliveries and notify each user every day
- fine calculation service
*/

// check about password and hashing it and how does encryption work?
class Person {
    String name;
    String id;
    String passwordHash;
    String email;
    String phone;
}

class User extends Person {
    int houseNumber;
    // what fields to include here
}

class Admin extends Person {
    String employeeId;
    // what fields to include here
}

class DeliveryPerson extends Person {
    // what fields to include here
}

interface UserDao {
    public void addUser (User user);
    public void removeUser (User user);
    public List<User> getUserForHouseNumber(int houseNumber);
}

class UserDaoImpl implements UserDao {
    private Map<Integer, List<Integer>> houseNumberToUserIdMap;
    private Map<Integer, User> userMap;
}

interface UserService {
    public void addUser();
    public void removeUser (int houseNumber);
}

class UserServiceImpl {
    public void addUser () {
        // create a new User Object
        // update the houseNumberToUserIdMap, userMap
    }
    public void removeUser (int houseNumber) { // lease ended for that house
        // update the houseNumberToUserIdMap, userMap based on choices given to user
    }
}

interface NotificationService {
    public void notifyUser(User user);
}

class NotificationServiceImpl {
    public void notifyUser (User user) {
        // send notification to the user
    }
}

class Size {
    private int length,breadth,width;
}

class Package {
    private Size size;
}

class Delivery {
    private String deliveryId;
    private Stribng user;
    private String lockerId;
    private Date deliveryDate;
    private Date dueDate;
}

interface DeliveryDao {
    public void addDelivery(Delivery delivery, String userPhone, String otp); // called when a new deliver is entered
    // public void removeDelivery(Delivery delivery); -> maybe not needed
    public String getDeliveryWithOtp(String otp); // returns lockerId
    public String getDeliveryWithPhoneNumber(String phone);
}

class DeliveryDaoImpl {
    private HashMap<Long, String> otpToDeliveryIdMap;
    private HashMap<Long, String> phoneToDeliveryIdMap;
    private HashMap<String, Delivery> deliveryIdMap;
}

class Locker {
    private String id;
    private String location;
    private Size dimentions;
    private String kioskId;
    private Category category;
}

class LockerDao {
    private Map<Category, List<String>> categoryToAvailableLockerId;
    private Map<String, Locker> lockerMap;
    private Set<String> usedLockers;

    public Category sizeToCategoryMapper (Size size) {
        // based on static conditions return a category
    }
    public String retrieveLockerId (Category category) {
        return "";
    }

}

class LockerService {
    // add/remove Lockers
}

class DeliveryService {
    public void deliveryPersonLogin(String id, String password) {
        // check from a DeliveryPerson's Dao
    }
    public void deliver (int length, int breadth, int width, int housenumber) {
        // generate size from the dimensions given
        // get lockerId from LockerDao
        // get user from UserDao
        // create a delivery object
        // invoke notification service
    }  
}


class CollectorService {
    public void collectWithPhone(String phone) {
        // call locker opening service
    }
    public void collectWithOtp(String phone) {
        // call locker opening service
    }
}

class LockerOpeningService {}

class LockerManagementService {
    // shows user initial option to login as a delivery person/user/admin or sign up
}