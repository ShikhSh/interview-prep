### Word Ladder:
https://leetcode.com/problems/word-ladder/
    Find min length of path from start to end word given a list of words and we can only change 1 character at a time:
    hit -> hot -> lot -> log -> cog
- Use BFS instead of DFS -> we want to find the shortest path
- To find adjacency list, instead of traversing over the whole of the list of words twice with squared complexity:
    - Generate all possible combinations by putting '*' instead of every character and map words for them:
    hit-> *it, h*t, hi*
    now *it -> hit, bit, kit, mit ... so on
    - Reduces the complexity from O(N-square) to O(N)
    - since we are using bfs, a word needs to be added to the queue only once, if it has been added, don't add it again!

### O(1) insert, delete, generate random:
https://leetcode.com/problems/insert-delete-getrandom-o1/submissions/
- use HashMap, ArrayList
- insert - HashMap, list - O(1)
- delete - HashMap = O(1), ArrayList - swap with the last element and then delete last element - O(1)
- random - (int)(start + math.random() * (end-start+1)) -> get the random index and return it

### Subarray sum == k
https://leetcode.com/problems/subarray-sum-equals-k/submissions/1695727208/
- use prefix sum trick, prefixSum2-prefixSum1 = k, then for every prefixSum, check the count of prefix[i]+k BEFORE it!