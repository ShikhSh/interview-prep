# interview-prep

## LLD 
### Theory:
- Design Principles: https://github.com/prateek27/design-patterns-java
### Questionresources
- Gaurav Sen LLD playlist - https://www.youtube.com/watch?v=I-iZbOVXwxg&list=PLMCXHnjXnTnvQVh7WsgZ8SurU1O2v_UM7&index=3&ab_channel=GauravSen
- Soumyajit Bhattacharayay LLD questions playlist - https://www.youtube.com/watch?v=7LaKmNfMCAo&list=PL12BCqE-Lp650Cg6FZW7SoZwN8Rw1WJI7&index=5&ab_channel=SoumyajitBhattacharyay
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