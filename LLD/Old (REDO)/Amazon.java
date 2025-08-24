/**
 * 
 * How to Generalize the Decision of where mappings should be stored?
Mappings are generally stored in the DAO/repository of the entity being mapped TO, especially if that's your main access pattern.
Seller-to-products: ProductDao, since products are the "leaf" objects and may be queried by seller often.
User-to-Order, Product-to-order: OrderDao, since orders are typically the main entry point for these queries.
    Explaination:
    I keep mappings in the DAO where the entity is the primary focus of queries. For example, I store seller-to-products in ProductDao,
    as you frequently need all products for a given seller. Similarly, orders-to-user and orders-to-product mappings are in OrderDao,
    optimizing for order-centric queries, which are common in e-commerce flows.

 */

/**

- Buyers
    - add to cart
    - add to wishlist
    - checkout
    - add payment methods
    - rate bought products
    - track products, receive notifications
    - check their history, invoices

- Sellers
    - add items to sell
    - add description
    - receieve orders
    - update shipping information

- Shipping Service
    - update tracking information

- Common functionalities/ no login required:
    - Search
*/

// CartService and Dao have same functions, is it duplication?
// user service seems to be having only logging in responsibilities, is this correct?
// should i eliminate cartservice and checkout service add code to userservice?
// what is an efficient way of searching?

/**
 * 
 * Efficient searching (at scale) requires indexing.

Use nameToProductIdMap and categoryToProductIdMap in ProductDao for quick lookups.

For fuzzy search (misspellings, partial matches), you might use:

Tries or Suffix Trees for prefix searching

Inverted Indexes (like in search engines)

Full-text search tools (Elasticsearch, Solr) in real-world systems

For LLD, it’s enough to show you map names/categories to product lists for fast lookups.

You can mention:
“In production, I’d use a search index like Elasticsearch for scalable, fuzzy, and ranked searching.”
 */

 /**
My design separates data access (DAOs) from business logic (Services), and I use the strategy pattern for payment processors so we can easily support new payment methods. Each service has a single, focused responsibility—cart management, checkout, user profile, seller actions, search, etc.—so the system is modular and easy to extend.

For efficient searching, I index products by name and category in ProductDao, and could scale to a real search engine if needed. The facade (AmazonService) offers a unified entry-point, but does not become a god class; flows are handled by specialized services.

This structure follows SOLID and key design patterns, making the system easy to maintain and extend as Amazon grows.”
 */

 /**
  * Monolith (Monolithic Architecture)
Single codebase, often a single deployable application.

All features and logic are part of the same process.

Can still have separation by service classes, DAOs, etc., inside the application—like your well-structured LLD (with services and DAOs).

Microservices
Multiple independent deployable services, each handling a specific domain (e.g., CartService, OrderService, ProductService as separate apps).

Each microservice may have its own database, APIs, and deployment lifecycle.

Communication is via network (HTTP/gRPC/message queues), not method calls.

Your LLD Design: What is It?
What you designed is a “modular monolith.”

You split business logic into clear service and DAO layers inside one codebase/app.

Each service (CartService, OrderService, etc.) is a class/object, not a standalone deployable microservice.

So, What’s the Real Difference?
Separation by services/DAOs within one app is not microservices architecture; it’s just good modular design.

Microservices means each service is a separately deployed application/process.

“My current design is a modular monolith, with separation of concerns via service and DAO layers.
Each major domain (cart, orders, products, search, etc.) has its own service class, making it easy
to maintain and reason about.
If we want to scale further, it’s straightforward to split each service into a microservice—with its own API,
storage, and deployment lifecycle—as each service is already cleanly separated by responsibility.”
  */

 import java.util.*;

class Address {
    
}
class User {
    private String id;
    private String name;
    private String email;
    private String passwordHash;
    private String phoneNumber;
    private List<Address> addresses;
    private Address defaultAddress;
    private List<PaymentOption> paymentOptions;
    private PaymentOption defaultPaymentOption;
}

enum UpdateStatus {
    ERROR,
    SUCCESS;
}
enum PaymentIntrumentType {
    CARD,
    WALLET;
}

abstract class PaymentOption {
    private String id;
    private String name;
    private String number;
    public void renderDetails();
    public PaymentIntrumentType paymentIntrumentType;
}

interface PaymentProcessor {
    public PaymentStatus processPayment(Double amount);

}

class Card extends PaymentOption {
    // implements everything for a card
}
class CardPaymentProcessor implements PaymentProcessor {
    // process card payments
}

class Wallet extends PaymentOption {
    // implements everything for a wallet
}
class WalletPaymentProcessor implements PaymentProcessor {
    // process card payments
}

enum PaymentStatus {
    PENDING,
    DECLINED,
    APPROVED,
    CASH_ON_DELIVERY;
}

enum Category {
    WELLNESS,
    KITCHEN,
    FURNITURE,
    CLOTHES;
}

class Review {
    private String id;
    private String userId;
    private String productId;
    private int stars;
    private String description;
}

class Product {
    private String id;
    private String name;
    private String description;
    private Category category;
    private List<Review> reviews;
    private double cost;
    private int count;
}
enum OrderStatus {
    DELIVERED,
    SHIPPED,
    ORDERED,
    OUT_FOR_DELIVERY;
}
class Order {
    String id;
    String userId;
    String productId;
    Date expectedDeliveryDate
    OrderStatus status;
}

class OrderDao {
    private Map<String, Order> orderMap;
    private Map<String, List<Order>> userIdToCurrentOrderMap;
    private Map<String, List<Order>> userIdToOldOrderMap;
    private Map<String, List<Order>> sellerIdToOrderMap;

    public List<Order> getUserOrders(String userId) {
        return null;
    }
    public List<Order> getSellerOrders(String sellerId) {
        return null;
    }
    public OrderStatus getOrderStatus(String orderId) {
        return null;
    }
    public void setOrderStatus(String orderId) {
        return;
    }
}

class ReviewDao {
    private Map<String, User> reviewMap;
    private Map<String, String> userIdToReviewId;
    private Map<String, String> productIdToReviewId;
}
class ProductDao {
    private Map<String, User> productMap;
    private Map<String List<String>> nameToProductIdMap;
    Map<Category, List<String>> categoryToProductIdMap;
}

class UserDao {
    private Map<String, User> userMap;
}

class SellerDao {
    private Map<String, User> sellerMap;
}

class CartDao {
    private Map<String, List<Product>> userItemsInCart;
    public void addProductsToCart (String userId, List<Product> products) {

    }
    public void removeProductFromCart (String userId, Product product) {
        
    }
    public List<Product> getUserCart (String userId) {
        // can be used during checkout
        return null;
    }
}
class CartService {
    public boolean addToUserCart (String userId, String productId) {
        // update cartDao
        // check for product counts before updating
        return true;
    }
    public boolean removeFromUserCart (String userId, String productId) {
        // update cartDao
        return true;
    }
    public List<Product> getUserCart (String userId) {
        // call cartDao
        return null;
    }
}
class CheckoutService {
    // make the below synchronized
    public synchronized List<Order> checkout (String userId) {
        // get items from cart
        // create orders for each
        // add to orderDao for user
        // fetch user details from userService
        return null
    }
}
class UserService {
    // Search provided by SearchService
    // can have a enums for AuthStatus, added to cart
    public boolean authenticateUser (String id, String passwordHash) {
        // call Auth service
        return true;
    }
    // add function to get user address, preferred payment, all payments
}
class SellerService {
    // handles seller login
    // handles seller profile info update/maintaining
    public void addProduct(String sellerId, String productName, double productCost) {

    }
    public void removeProduct(String sellerId, String productName, double productCost) {
    
    }
}
class SearchService {
    public List<Product> searchByCategory(Category category) {
        // use mapping from productDao
        return null;
    }
    public List<Product> searchByName(String name) {
        // use mapping from productDao
        return null;
    }
}
class DeliveryService {
    public void updateOrderStatus(OrderStatus newOrderStatus, String orderId) {
        // use notification service to update the status
    }
}
class NotificationService {
    // used by 
}
class ReviewService {
    public void addUserReview (String userId, String productId, int star, String description) {
        // update review Dao
    }
}
class AmazonService {
    private SearchService searchService;
    private UserService userService;
    private SellerService sellerService;
    public static AmazonService INSTANCE;

    public void login(String id, String passwordHash) {
        // call seller or user
    }
    
}