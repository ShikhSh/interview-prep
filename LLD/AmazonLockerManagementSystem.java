/**
 * Amazon Locker Management System
- add lockers
- add users
- add package and notify user
- pick-up package

Enhancements:
- 
*/
import java.util.*;

class User {
    long id;
    String name, email;
    User(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}

enum SizeCategory {
    SMALL, MEDIUM, LARGE;
}

class PackageWrapper {
    long lockerId, userId, code;
    PackageWrapper(long lockerId, long userId, long code) {
        this.lockerId = lockerId;
        this.userId = userId;
        this.code = code;
    }
}

class Locker {
    long id;
    String location;
    boolean isOccupied;
    SizeCategory category;
    Locker (long id, String location, SizeCategory category) {
        this.id = id;
        this.location = location;
        this.isOccupied = false;
        this.category = category;
    }
}

class LockerManagementSystem {
    // constructor to initialize all these field
    // private fields
    HashMap<Long, User> users = new HashMap<>();
    HashMap<Long, Locker> lockers = new HashMap<>();
    HashMap<SizeCategory, ArrayDeque<Long>> sizeToFreeLockerIds = new HashMap<>();
    HashMap<Long, PackageWrapper> codeToPackage = new HashMap<>();
    Random random = new Random();
    long LOCKER_ID_MULTIPLIER = 100000000L;

    public void addLocker(long lockerId, String location, SizeCategory category) {
        Locker locker = new Locker(lockerId, location, category);
        lockers.put(lockerId, locker);
        ArrayDeque<Long> lockersForSize =
                sizeToFreeLockerIds.getOrDefault(category, new ArrayDeque<>());
        lockersForSize.addLast(lockerId);
        sizeToFreeLockerIds.put(category, lockersForSize);
    }

    public void addUser(long userId, String name, String email) {
        User user = new User(userId, name, email);
        users.put(userId, user);
    }
    /**
    Assumes the package has been categorized into different sizes and
    the User ID is directly fed into the system
    */
    public long addPackage(SizeCategory category, long userId) {
        if (!sizeToFreeLockerIds.get(category).isEmpty()) {
            long lockerId = sizeToFreeLockerIds.get(category).removeFirst();
            Locker locker = lockers.get(lockerId);
            locker.isOccupied = true;
            // long code = random.nextInt();
            long code = (long)(Math.random()*LOCKER_ID_MULTIPLIER);
            // String uuid = UUID.randomUUID().toString();
            PackageWrapper packageWrapper = new PackageWrapper(lockerId, userId, code);
            codeToPackage.put(code, packageWrapper);
            // use notification service to notify user
            System.out.println("Package added for user " + userId + " in locker " + lockerId + " with code " + code);
            return code; // return the code for package collection
        } else {
            // Display error -> lockers full
            System.out.println("Lockers full for category " + category + ". Please try again later.");
        }
        return -1; // indicate failure to add package
    }

    public void collectPackage(long code) {
        if (codeToPackage.containsKey(code)) {
            PackageWrapper packageWrapper = codeToPackage.remove(code);
            Locker locker = lockers.get(packageWrapper.lockerId);
            locker.isOccupied = false;
            sizeToFreeLockerIds.get(locker.category).addLast(locker.id);
            System.out.println("Package collected from locker " + locker.id + " for user " + packageWrapper.userId);
            // call the service to open locker
        } else {
            // throw error that code not found, please double check the code
        }
    }
}

class MainClass {
    public static void main(String[] args) {
        LockerManagementSystem system = new LockerManagementSystem();
        system.addLocker(1, "Location1", SizeCategory.SMALL);
        system.addLocker(2, "Location2", SizeCategory.MEDIUM);
        system.addLocker(3, "Location3", SizeCategory.LARGE);
        system.addUser(101, "Alice", "a@gmail.com");
        system.addUser(102, "Jacob", "j@gmail.com");
        long code1 = system.addPackage(SizeCategory.MEDIUM, 101);
        long code2 = system.addPackage(SizeCategory.MEDIUM, 101);
        long code3 = system.addPackage(SizeCategory.SMALL, 102);
        long code4 = system.addPackage(SizeCategory.LARGE, 102);
        system.collectPackage(code1);
        system.collectPackage(code3);
        system.collectPackage(code4);
        long code5 = system.addPackage(SizeCategory.MEDIUM, 101);
        system.collectPackage(code5);
    }
}

/**
 * ---------------------------------------------------------------------
 * SIMPLIFIED, yet COMPLICATED SYSTEM!! can't code all of it in 35-40 minutes!
 * ---------------------------------------------------------------------
 */


// /**
//  * Amazon Locker Management System
// - Add locker
// - Add user
// - Add package
// - Pick-up package
//  */

//  class User {
//     // mention ideally these fields should be private
//     // and we'd be adding getters and setters,
//     // but for interview skipping this part
//     long id;
//     String name;
//     String email;
//     String phone;

//     User (long id, String name, String email, String phone) {
//         this.name = name;
//         this.phone = phone;
//         this.email = email;
//         this.id = id;
//     }
//  }

//  class Size {
//     double dimensions[]; // sorted from min to max length
//     Size (double height, double breadth, double depth) {
//         dimensions = new double[3];
//         dimensions[0] = height;
//         dimensions[1] = breadth;
//         dimensions[2] = depth;
//         Arrays.sort(dimensions);
//     }
//  }
 
//  class Locker {
//     String id;
//     String location;
//     boolean isReserved;
//     LockerCategory category;

//     Locker (String id, String location, LockerCategory category) {
//         this.id = id;
//         this.location = location;
//         this.isReserved = false; // empty when created
//         this.category = category;
//     }
//  }
 
//  class Package {
//     String id;
//     LockerCategory lockerCategory;
//     String userId;
//     String lockerId;
//     String code;
//     Package (String id, Size size, String userId, String lockerId, String code) {
//         this.id = id;
//         this.size = size;
//         this.userId = userId;
//         this.lockerId = lockerId;
//         this.code = code;
//     }
//  }

//  enum LockerCategory {
//     SMALL, MEDIUM, LARGE;
//  }

//  class LockerManagementService {
//     HashMap<String, User> users; // userId to User
//     HashMap<String, Locker> lockers; // lockerId to Locker
//     HashMap<LockerCategory, ArrayList<String>> categoryToFreeLockerIds;
//     HashMap<String, String> codeToPackage;
//     HashMap<String, Package> packages;
//     HashMap<LockerCategory, Size> lockerCategoriesToSizes;

//     LockerManagementService() {
//         users = new HashMap<>();
//         lockers = new HashMap<>();
//         categoryToFreeLockerIds = new HashMap<>();
//         codeToPackage = new HashMap<>();
//         packages = new HashMap<>();
//         lockerCategoriesToSizes = new HashMap<>();
//     }

//     public void addLockerCategory(LockerCategory category, double length, double height, double breadth) {
//         Size size = new Size(length, height, breadth);
//         lockerCategoriesToSizes.put(category, size);
//     }
//     /**
//     Add a locker
//     */
//     public void addLocker(String id, String location, LockerCategory category) {
//         ArrayList<String> lockersForCategory = categoryToFreeLockerIds.getOrDefault(category, new ArrayList<>());
//         lockersForCategory.add(new Locker(id, location, category));
//         categoryToFreeLockerIds.put(category, lockersForCategory);
//     }
//     /**
//     Assuming we show the user to the package delivery person and they select the user
//     */
//     public void addPackage(double length, double breadth, double height,
//     long userId, long packageId, String code) {
//         Size packageSize = new Size(length, breadth, height);
//         Package package = new Package (packageId, packageSize,  )
//     }
//  }


/**
 * ---------------------------------------------------------------------
 * VERY COMPLICATED SYSTEM!! can't code all of it in 35-40 minutes!
 * ---------------------------------------------------------------------
 */


// /**
// https://github.com/ycwkatie/OOD-Object-Oriented-Design/blob/main/ood/amazon_locker.md
// Usecases:
// - Receivers
//     - Add new user
//     - Remove user
//     - Receive Notification about password with OTP

// - PackageLocatorService
//     - Enter otp/mobile phone to open locker
//     - Given delivery Size, point to relevant locker

// - DeliveryPerson
//     - login for delivery person

// - Users
//     - Name
//     - Phone
//     - Email
//     - House address

// - DeliveryInfo
//     - user Id
//     - package Id
//     - locker Id
//     - DeliveryStatus

// - DeliveryStatus
//     - picked, pending, overdue

// - LockerInfo
//     - KioskId
//     - LockerId
//     - Status

// - Status
//     - Free
//     - Occupied

// - FinecalculationService
// - NotificationService

// =============================== Enhancements:
// - Have a class Receipt to send to the Notification service
// - OtpGenerator -> an interface and an implementation of it
// - it should store the length of otp to be generated
// - Slot filter strategy -> interface and one implementation
// - Define exceptions:
//     - LockerAlreadyExistsException
//     - UserAlreadyExistsException
//     - NoSlotExistsException
// - batch job to go over the deliveries and notify each user every day
// - fine calculation service
// */

// // check about password and hashing it and how does encryption work?
// class Person {
//     String name;
//     String id;
//     String passwordHash;
//     String email;
//     String phone;
// }

// class User extends Person {
//     int houseNumber;
//     // what fields to include here
// }

// class Admin extends Person {
//     String employeeId;
//     // what fields to include here
// }

// class DeliveryPerson extends Person {
//     // what fields to include here
// }

// interface UserDao {
//     public void addUser (User user);
//     public void removeUser (User user);
//     public List<User> getUserForHouseNumber(int houseNumber);
// }

// class UserDaoImpl implements UserDao {
//     private Map<Integer, List<Integer>> houseNumberToUserIdMap;
//     private Map<Integer, User> userMap;
// }

// interface UserService {
//     public void addUser();
//     public void removeUser (int houseNumber);
// }

// class UserServiceImpl {
//     public void addUser () {
//         // create a new User Object
//         // update the houseNumberToUserIdMap, userMap
//     }
//     public void removeUser (int houseNumber) { // lease ended for that house
//         // update the houseNumberToUserIdMap, userMap based on choices given to user
//     }
// }

// interface NotificationService {
//     public void notifyUser(User user);
// }

// class NotificationServiceImpl {
//     public void notifyUser (User user) {
//         // send notification to the user
//     }
// }

// class Size {
//     private int length,breadth,width;
// }

// class Package {
//     private Size size;
// }

// class Delivery {
//     private String deliveryId;
//     private Stribng user;
//     private String lockerId;
//     private Date deliveryDate;
//     private Date dueDate;
// }

// interface DeliveryDao {
//     public void addDelivery(Delivery delivery, String userPhone, String otp); // called when a new deliver is entered
//     // public void removeDelivery(Delivery delivery); -> maybe not needed
//     public String getDeliveryWithOtp(String otp); // returns lockerId
//     public String getDeliveryWithPhoneNumber(String phone);
// }

// class DeliveryDaoImpl {
//     private HashMap<Long, String> otpToDeliveryIdMap;
//     private HashMap<Long, String> phoneToDeliveryIdMap;
//     private HashMap<String, Delivery> deliveryIdMap;
// }

// class Locker {
//     private String id;
//     private String location;
//     private Size dimentions;
//     private String kioskId;
//     private Category category;
// }

// class LockerDao {
//     private Map<Category, List<String>> categoryToAvailableLockerId;
//     private Map<String, Locker> lockerMap;
//     private Set<String> usedLockers;

//     public Category sizeToCategoryMapper (Size size) {
//         // based on static conditions return a category
//     }
//     public String retrieveLockerId (Category category) {
//         return "";
//     }

// }

// class LockerService {
//     // add/remove Lockers
// }

// class DeliveryService {
//     public void deliveryPersonLogin(String id, String password) {
//         // check from a DeliveryPerson's Dao
//     }
//     public void deliver (int length, int breadth, int width, int housenumber) {
//         // generate size from the dimensions given
//         // get lockerId from LockerDao
//         // get user from UserDao
//         // create a delivery object
//         // invoke notification service
//     }  
// }


// class CollectorService {
//     public void collectWithPhone(String phone) {
//         // call locker opening service
//     }
//     public void collectWithOtp(String phone) {
//         // call locker opening service
//     }
// }

// class LockerOpeningService {}

// class LockerManagementService {
//     // shows user initial option to login as a delivery person/user/admin or sign up
// }