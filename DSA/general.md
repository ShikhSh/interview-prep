### DFS v/s BFS -> find shortest path - use BFS instead of DFS!
- https://leetcode.com/problems/word-ladder/
- https://leetcode.com/problems/rotting-oranges/description/

### For patterns requiring String patterns in Sets/Hashmaps:
- use concatenated String as the keys:
```
HashMap<ArrayList<String>, Integer> or HashMap<String[], Integer> == HashMap<String, Integer>
hm.put(new String[]{"a", "b", "c"}, 1) = hm.put("a.b.c", 1)
```
eg - https://leetcode.com/problems/analyze-user-website-visit-pattern/
