## Random number java: ( can be used for generating IDs)
- UUID java.util:
```
String uuid = UUID.randomUUID().toString();
```
- Issue with below: Generates negative numbers too!
https://docs.oracle.com/javase/8/docs/api/java/util/Random.html
```
import java.util.Random;

public class RandomExample {
    public static void main(String[] args) {
        Random random = new Random();

        // Generate a random integer
        int randomInt = random.nextInt();
        System.out.println("Random integer: " + randomInt);

        // Generate a random integer within a bound (0 to bound-1)
        int randomIntBounded = random.nextInt(100); // Generates 0-99
        System.out.println("Random integer (0-99): " + randomIntBounded);

        // Generate a random integer within a specific range (min to max)
        int min = 10;
        int max = 50;
        int randomInRange = random.nextInt(max - min + 1) + min;
        System.out.println("Random integer (" + min + "-" + max + "): " + randomInRange);

        // Generate a random double (0.0 to 1.0)
        double randomDouble = random.nextDouble();
        System.out.println("Random double (0.0-1.0): " + randomDouble);

        // Generate a random long
        double randomLong = random.nextLong();
        System.out.println("Random long: " + randomLong);
    }
}
```