// /**
// Facebook System
// - Users
//     - Create account, login, update profile
//     - Post
//     - See Feed, like to it
//     - Comment
//     - Follow with other Users
//     - Send them messages
//     - search based on tags

//     - Description
//     "I chose a modular, service-oriented design for maintainability and scalability. Each service class encapsulates its own domain logic, making the system easier to test and extend. Entities are kept simple, containing only data. To scale to millions of users, I’d back these classes with persistent storage and add distributed cache layers as needed. Features like notifications, friend requests, or new feed types can be added as new services or entities without modifying existing code—keeping us compliant with SOLID and open to growth. This design also makes it straightforward to introduce microservices or deploy services independently as our system scales."

//     - what goes in Pojos v/s Service:
//     "I'd keep only the necessary IDs (like post IDs or follower IDs) in the User entity for reference, and all the logic/manipulation—like following/unfollowing, fetching posts—would go in dedicated service classes. This separation supports scale and maintainability."

//     - Handling deletions:
//     "I'd use an ArrayList for ordering if deletions are rare, but for efficiency at scale, a LinkedHashMap or database table with ordering is better. To handle deletion propagation, I’d mark the post as deleted and filter from feeds dynamically, ensuring consistency and performance."

//     - Passwords & Encryption:
//     Passwords are not encrypted, but hashed. "Passwords should never be stored or transmitted in plain text. On signup, I would use a strong, salted hash (like bcrypt or Argon2) to store passwords securely. On login, I'd hash the entered password and compare hashes."
//     "Passwords are never encrypted but hashed and salted, so even if the DB is breached, they cannot be recovered.
//     For highly sensitive user data (e.g., emails, messages), we can apply encryption at rest,either at the DB or application layer.
//     All communication between clients and backend is encrypted using HTTPS/TLS. For messaging, if end-to-end encryption is required,
//     messages are encrypted on the client and only decrypted by the recipient, with the backend storing only ciphertext."

//     - Conversations ordering:
//         Query:
//         ORDER BY lastActivityTimestamp DESC LIMIT N
//     to get the most recent conversations for the user.
//     Service layer: The MessageService or ConversationService fetches and sorts conversations by latest activity.
//     "I’d keep a lastActivityTimestamp on each conversation and sort the user’s conversations by this field, either in memory for small data or via an indexed DB query at scale."

//     - Image storage:
//     "I’d add a profileImageUrl field to the User object, storing the URL to the image hosted in object storage. This approach decouples heavy binary data from user profiles and scales easily."

//     - Push v/s pull model for feeds:
//     Both push and pull have trade-offs.
//         - The push approach gives fast feed reads but can cause write amplification, especially for high-fanout users, and complicates deletes/edits. Write Amplification occurs when a single post update requires multiple writes to all followers' feeds.
//         This is expensive for people like Elon Musk, who have millions of followers. So one post from him would require writing to millions of feeds and thus replicating it millions of times.
//         - The pull approach is simpler for writes and storage, but can make reading feeds slower, especially for users following many accounts.
//         - In practice, most large-scale systems use a hybrid approach: they push posts to followers’ feeds for regular users, but for celebrity/high-fanout accounts,
//         they store posts centrally and pull them in real time when followers load their feed. This balances storage and performance.
//     Push: Fast reads, expensive writes, harder to scale for celebs.
//     Pull: Slow reads, cheap writes, always fresh.
//     Hybrid: The industry standard for big platforms.
//     Egs:
//     1. Push Model Example (e.g., Instagram):
//         - When Alice posts, her post is pushed into each follower’s timeline/feed (a separate table/collection per user) and is mostly cached for every user.
//         - When Bob opens his feed, it’s just a simple read from his precomputed feed stored in cache.
//         - Problem: If Alice is a celebrity with 10M followers, her post must be inserted into 10M feeds—a heavy write!
//     2. Pull Model Example (e.g., Twitter classic)
//         - When Bob opens his feed, system queries “Who does Bob follow?” and pulls recent posts from all those accounts, sorts by time.
//         - No write amplification, but the read can be expensive if Bob follows 10k people.
//     3. Hybrid (Fan-out-on-read/write)
//         - Regular users: push-based (fan-out-on-write).
//         - High-fanout users (celebrities): pull-based (fan-out-on-read).
//         For example:
//             - When Alice (average user) posts, push to all followers’ feeds.
//             - When Elon Musk (celebrity) posts, only index the post globally; when followers open their feed, pull his posts in real-time.

//     - Filter Based on Privacy:
//     While generating the feed, we can filter posts based on the user's privacy settings. Like when fetching from the database, we can add a condition to check if the post's privacy allows it to be visible to the user.
//     ```
//     SELECT * FROM users
//     WHERE name LIKE '%query%'
//     AND (privacy = 'PUBLIC'
//         OR (privacy = 'PRIVATE' AND requesterId IN followers)
//         OR (privacy = 'FRIENDS_ONLY' AND mutual_follow(requesterId, user.id)))
//     ```

// */

// import java.util.*;
// class User {
//     private String id;
//     private String name;
//     private String email;
//     private Set<String> followers;
//     private Set<String> following;
//     private ArrayList<String> posts;
//     private ArrayList<String> feedPostIds;
//     private ArrayList<String> conversationIds;
//     private AccountPrivacy privacy;
// }

// interface UserService {
//     public void addUser (String name, String passwordHash);
//     public void updateProfile(String password);
//     public void followUser (String followerId, String followeeId);
// }
// class UserServiceImpl {
//     // follow Singleton
//     private Map<String, User> userMap;

//     public List<Post> getFeed (String userId) {
//         // return the posts from the user feed
//         return null;
//     }
//     public void addUser (String name, String passwordHash) {
//         // create a new user and it to the userMap
//     }
//     public void followUser (String followerId, String followeeId) {
//         // update hashmaps for both
//     }
// }

// enum AccountPrivacy {
//     PRIVATE,
//     PUBLIC;
// }

// class Post {
//     private String id;
//     private String content;
//     private ArrayList<String> likedByUserId;
//     private List<String> comments;
//     private List<String> tags;
// }

// class PostService {
//     // follow Singleton
//     private Map<String, Post> postMap;
//     private Map<String, Comment> commentMap;
//     private Map<String, List<String>> tagToPostIdMap;
//     public void addComment (String userId, String postId, String content) {
//         // create new Comment object
//         // add it to the commentMap
//         // link the Id to the post ID
//     }
//     public void likePost (String userId, String postId) {
//         // add userId to the post.likedByUserId
//     }
//     public void addPost (String userId, String content, List<String> tags) {
//         // create new post object
//         // add to the User
//         // add it to the feed of all the users following it
//         // add it to the post map
//         // add it to the tagToPostIdMap
//     }
// }

// class Comment {
//     private String id;
//     private String content;
//     private String postId;
//     private String userId;
// }

// class Conversation {
//     private String conversationId;
//     private List<String> userIds;
//     private List<Message> messages;
// }

// class Message {
//     private String from;
//     private String content;
//     private String conversationId;
// }

// enum MessageStatus {
//     DELIVERED,
//     PENDING_DELIVERY;
// }

// class MessageService {
//     // follow Singleton
//     private Map<String, Map<String, List<String>> messageFromToContentMap;
//     private Map<String, Conversation> conversationMap;
//     public MessageStatus sendMessage(String fromUserId, String conversationId, String content) {
//         // create a new message, update the conversation using conversation Id
//         return null;
//     }
//     public void startConversation(String fromUserId, String toUserId) {
//         // create a new conversation, add to the user conversations arraylist for both users
//     }
//     public List<Conversation> getUserConversations(String userId) {
//         // get user conversations
//         return null;
//     }
// }

// class SearchService {

//     public SearchService INSTANCE;
//     // private contructor
    
//     public List<Post> search (String tag) {
//         // return the posts based on the Tags
//         return null;
//     }
// }

// class NotificationService {
//     // singleton
//     public void notifyNewRequest(String fromUserId, String toUserId) {
//         // notify
//     }
//     public void notifyNewLike(String fromUserId, String toUserId, String postId) {
//         // notify
//     }
//     public void notifyNewComment(String fromUserId, String toUserId, String postId, String commentId) {
//         // notify
//     }
// }
