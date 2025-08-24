# Tips:
- Interviewers might conduct interview one day and review another day, so they remember basic things and what you have written, so comment the below things.
- Do not generate UUIDs, hardcode them in the test function and let them know that this should be generated using a UUID
- Mention the variables should be private and accessed using getters and setters, but do not spend time adding them, add comments
- Mention that the data stored in HashMaps will be fetched from the DAL (Data Access Layers)
Basic Patterns commonly used in interviews:
- Specification Patter: for searches (Uniz, Library, OR Advance Searches anywhere, etc)
- Strategy Pattern: encapsulate an algorithm or policy so you can swap implementations at runtime, egs - for Payment Strategies, etc
- State Pattern: Vending/Coffee machines
# Questions:
### Uniq File Search:
- https://leetcode.com/discuss/post/609070/amazon-ood-design-unix-file-search-api-b-b8py/
- Specification pattern for the Search Criteria
- use BFS
- Params implemented as a separate class
- Filters implement a common interface, and ALL of them stored in 1 FileFilter in an arraylist and then they are used to validate. If corresponding param == null, we let them be, else use it
- Check file for more

### Vending Maching/Coffee Maker:
- Explanation: https://www.youtube.com/watch?v=8CcB3xAt9p4&ab_channel=RiddhiDutta
- Code: https://github.com/ashishps1/awesome-low-level-design/tree/main/solutions/java/src/vendingmachine
- Patterns:
    - State Pattern because vending machine goes from Idle->SelectProduct->AcceptingPayment->Dispensing->Idle
    - Strategy Pattern for Payment Strategy -> card, cash, etc
    - Store file extension separately, don't complicate code in trying to find it!
    - Check file for comments and more intricacies:

### Amazon Locker System:
Partial but good: https://leetcode.com/discuss/post/233869/design-amazon-locker-system-by-anonymous-lgpn/comments/2421163/ 
Patterns:
- Singleton for the Amazon Locker Management
- Strategy for the locker distance
- enum:
    - Size -> for package and lockers both
- interfaces:
    - DistanceCalculationStrategy
        Impl -> EucledianDistance
- Entities:
    - Locker -> id, isOccupied, size
    - Location -> id, latitude, longitude, HashMap<PackageSize, Locker> freeLockers, occupiedLockers
    - Customer -> id, name, email, phone
    - Package -> size, user, locker
- main classes:
    - LockerManagementService
        - Mappings:
            - UserID-User
            - LocationId-Location
            - OTP-Package
        - addLocker/addLocation
        - findClosest -> Use Strategy Pattern for finding closest locker
        - assignPackage
        - retrievePackage

# Mention
- Mention ideally these fields should be private and we'd be adding getters and setters, but for interview skipping this part

# UUID Java:
```
import java.util.Random;

public class RandomStringGenerator {

    public static String generateRandomAlphaNumericString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        int desiredLength = 10; // Example length
        String randomString = generateRandomAlphaNumericString(desiredLength);
        System.out.println("Generated random string: " + randomString);
    }
}
```