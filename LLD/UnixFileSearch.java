/**
 * https://github.com/ycwkatie/OOD-Object-Oriented-Design/blob/main/ood/file_search.md
Unix File Search command
Description:
- Support constraints:
    - File Name
    - Size greater than
    - Size less than
    - extension
    - And 2 conditions
    - Or 2 conditions
    - not a condition

Design:
!!!! INTERFACE SHOULD ONLY HAVE PUBLIC METHODS !!!! AND NO VARIABLES!!

class FileSearchSystem:
    - FileInfo root
    + List<String> search(String input)
        - split the input based on
        - name

enum ConditionType {
    NAME,
    SIZE_GREATER_THAN,
    SIZE_LESS_THAN,
    EXTENSION,
    AND,
    OR
}

interface Condition
    + matches(FileInfo fileInfo)

////////////////// CHAT-GPT Explaination //////////////
/// Is Creating New Condition Objects Per Query Good Design?
Short Answer:
Yes, it is generally good design.
Each user query (like name=foo.txt AND size>1000) should result in a new set of Condition objects representing that query.
Reason: Each search is logically a new, independent filter—its parameters (values to match) are usually not shared across queries, so sharing/reusing instances is rarely needed or useful.


Why Is This Approach Idiomatic?

Immutability & Thread-Safety:
Each Condition object should be immutable: once constructed, it never changes (e.g., new NameCondition("foo.txt")).
Immutability makes conditions thread-safe, so searches can run concurrently.

Separation of Concerns
The object’s responsibility is to represent a specific query constraint (e.g., “name equals foo.txt”).
The logic for the condition is decoupled from the parsing/user input phase.

No Memory/Performance Problem
These objects are lightweight; JVM object creation is fast.
Modern garbage collectors handle short-lived objects (like per-query conditions) efficiently.

No Global State-
Reusing a single mutable condition object for different queries means shared mutable state—this is a classic source of concurrency bugs and accidental query pollution.


Industry & Framework Analogy:

Hibernate/JPA Criteria API:
Each query has its own Predicate tree—predicates are new objects every time.

Java Streams:
Each filter is a lambda or a new Predicate per stream operation.

Search engines (Lucene, Elasticsearch):
Each query build creates a new tree of query/condition objects.


------- Follow ups:
1. How would you implement the parser for the query string and translate to the Condition objects?
“I would implement a parser that takes a user query string and, using either string splitting for simple queries or a recursive descent parser for complex queries, builds a tree of Condition objects—each representing a logical or atomic file constraint. This tree mirrors the query structure and can be evaluated efficiently for each file.”
Sample Code Sketch (Pseudo-Java):

java
Copy
Edit
Condition parseQuery(String query) {
    // For simplicity, let's say only one AND, no parenthesis.
    // Example: name=foo.txt AND size>1000

    if (query.contains("AND")) {
        String[] parts = query.split("AND");
        Condition left = parseSingleCondition(parts[0].trim());
        Condition right = parseSingleCondition(parts[1].trim());
        return new AndCondition(left, right);
    } else if (query.contains("OR")) {
        String[] parts = query.split("OR");
        Condition left = parseSingleCondition(parts[0].trim());
        Condition right = parseSingleCondition(parts[1].trim());
        return new OrCondition(left, right);
    } else {
        return parseSingleCondition(query.trim());
    }
}

Condition parseSingleCondition(String token) {
    if (token.startsWith("name=")) {
        return new NameMatchingCondition(token.substring(5));
    } else if (token.startsWith("size>")) {
        return new SizeGreaterThanCondition(Long.parseLong(token.substring(5)));
    } else if (token.startsWith("extension=")) {
        return new ExtensionMatchingCondition(token.substring(10));
    }
    // ... add more as needed
    throw new IllegalArgumentException("Unknown condition: " + token);
}


2. How would you add the file structure in this design?
“For the file structure, I’d build a tree of Directory and File objects either by recursively reading the filesystem or, for testing, constructing it from a list of string paths. This makes it easy to traverse and apply search logic.”


*/

/////////// update below to include getters for the below than the variables themselves
// interface StorageElement {
//     private String name;
//     private long size;
//     private boolean isDirectory;
//     private String pathFromRoot;

//     // getters and setters for each
// }
interface FileSystemNode {
    String getName();
    String getPath();
    boolean isDirectory();
    // You can add more common properties here.
}

class File implements StorageElement {
    private String extension;
    // additional getters and setters
}

class Directory implements StorageElement {
    private List<StorageElement> childElements;
    // additional getters and setters
}

interface Condition {
    public boolean matches(File fileName);
}

class NameMatchingCondition implements Condition {
    private String queryName;
    // Add constructor and getters and setters

    public boolean matches(File file) {
        return file.getName().equals(queryName);
    }
}

class NameMatchingCondition implements Condition {
    private String queryName;
    // Add constructor and getters and setters

    public boolean matches(File file) {
        return file.getName().equals(queryName);
    }
}

class SizeGreaterThanCondition implements Condition {
    private Long querySize;
    // Add constructor and getters and setters

    public boolean matches(File file) {
        return file.getsize()>querySize;
    }
}

class AndCondition implements Condition {
    private Condition condition1;
    private Condition condition2;
    // create constructor for this
    public boolean matches(File file) {
        return condition1.matches(file) && condition2.matches(file);
    }
}

class FileSearchService {
    private StorageElement root; //initialized at the beginning
    // create a constructor with initialization
    public List<String> search(Condition condition) {
        List<String> filteredFiles = new ArrayList<String>();
        // apply bfs here
        ArrayDeque<StorageElement> queue = new ArrayDeque<StorageElement>();
        queue.addLast(root);
        while (!queue.isEmpty()) {
            StorageElement removedElement = queue.removeFirst();
            if (removedElement.isDirectory) {
                // iterate over the children and add to the queue at the end
                try {
                    Directory currentDirectory = (Directory)removedElement;
                    for (StorageElement child : currentDirectory.getChildElements()) {
                        queue.addLast(child);
                    }
                } catch (Exception e) {
                    // log exception and continue traversing for the rest of the files
                }
            } else {
                try {
                    File currentFile = (File)removedElement;
                    if (condition.matches(currentFile)) {
                        // res.add(currentFile.getPathFromRoot);
                    }
                } catch (Exception e) {
                    // log exception and continue traversing for the rest of the files
                }
            }
        }
    }
}