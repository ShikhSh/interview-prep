# interview-prep

## HLD:
### Theory Sources:
- Jordan Videos for theory - https://www.youtube.com/watch?v=crPoHnhkjFE&list=PLjTveVh7FakLdTmm42TMxbN8PvVn5g4KJ&index=53&ab_channel=Jordanhasnolife

### Question Sources:
- Jordan - new series is coming up though - https://www.youtube.com/watch?v=J-5JozlYIqI&list=PLjTveVh7FakJOoY6GPZGWHHl4shhDT8iV&index=20&ab_channel=Jordanhasnolife
- https://www.hellointerview.com/learn/system-design/problem-breakdowns/ticketmaster
- https://bytebytego.com/courses/system-design-interview/real-time-gaming-leaderboard


Additional Resources:
- https://github.com/donnemartin/system-design-primer/tree/master


## LLD 
### Theory:
- Design Principles: https://github.com/prateek27/design-patterns-java
### Questionresources
- Gaurav Sen LLD playlist - https://www.youtube.com/watch?v=I-iZbOVXwxg&list=PLMCXHnjXnTnvQVh7WsgZ8SurU1O2v_UM7&index=3&ab_channel=GauravSen
- Soumyajit Bhattacharayay LLD questions playlist - https://www.youtube.com/watch?v=7LaKmNfMCAo&list=PL12BCqE-Lp650Cg6FZW7SoZwN8Rw1WJI7&index=5&ab_channel=SoumyajitBhattacharyay -> not very useful
- https://github.com/ycwkatie/OOD-Object-Oriented-Design?tab=readme-ov-file -> has questions and handwritten solutions
- https://github.com/ashishps1/awesome-low-level-design/blob/main/problems -> questions+diagrams+solutions+code
### Tips
- Interface should include methods and NOT variables. So, if you want to have an interface with some variables, include its getters/setters in the interface:
- Use classes for entities because you need fields/attributes, and possible methods later. Use interfaces for service layers or when you want to allow multiple implementations (e.g., BooksRepo, UserDao).
```
interface FileSystemNode { 
    String getName();
    String getPath();
    boolean isDirectory();
    // than having propeties name, path, isDirectory
}
```
- How to Generalize the Decision of where mappings should be stored?
Mappings are generally stored in the DAO/repository of the entity being mapped TO, especially if that's your main access pattern.
Seller-to-products: ProductDao, since products are the "leaf" objects and may be queried by seller often.
User-to-Order, Product-to-order: OrderDao, since orders are typically the main entry point for these queries.