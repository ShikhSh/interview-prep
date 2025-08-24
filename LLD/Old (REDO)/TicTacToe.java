/**
Clarifying Questions:
- store number of matches won/played for every user?
- rank users?
- undo functionality?
- provide them the opportunity to download their moves?
- how do we store their password or authenticate users?


1. Have user
    - userId
    - matchesWonCount
    - userName
    - userNickName
    - matchesPlayed

    + getters and setters for each
    + int updateMatchesWonCount();
    + int updateMatchesPlayedCount();

2. Board class
    - int N (NxN board)
    - HashMap<Integer, Character> playerMapping
    - int[][] boardNodes
    - boolean won(int x, int y) // to check if player won
    + boolean update(int playerNum, int x, int y)
    + void undo()
    + render() // to display the board


3. Node class -> representing each node
    - int x
    - int y
    - char symbol // symbol which it holds

4. Game class
    - initialize the users, board
    - call board.render()
    - declare winner
    - update the number of game played and won counts

    - Board board
    - player1Id
    - player2Id

======================== Enhancements ========================
- leaderboard in memory:
// Users object has updated matchesWonCount
class Leaderboard {
    private List<User> users = new ArrayList<>();

    // Constructor should get the users list from the database or in memory

    public void addUser(User user) {
        users.add(user);
    }

    public List<User> getRankings() {
        users.sort((u1, u2) -> Integer.compare(u2.getMatchesWonCount(), u1.getMatchesWonCount()));
        return users;
    }

    public void printLeaderboard() {
        int rank = 1;
        for (User user : getRankings()) {
            System.out.println(rank++ + ". " + user.getName() + " - Wins: " + user.getMatchesWonCount());
        }
    }
}

- leaderboard from DB:

Let the database do the sorting for you.
For example, if you use SQL:
SELECT userName, matchesWonCount
FROM Users
ORDER BY matchesWonCount DESC, matchesPlayedCount ASC;

// Pseudocode for fetching from DB
List<User> leaderboard = userDao.getLeaderboard(); // Already sorted by DB
for (User user : leaderboard) {
    System.out.println(user.getName() + " - Wins: " + user.getMatchesWonCount());
}

- Authenticate users:
class User {
    // ...existing code...
    private String passwordHash;

    public void setPassword(String password) {
        this.passwordHash = hashPassword(password);
    }

    public boolean checkPassword(String password) {
        return this.passwordHash.equals(hashPassword(password));
    }

    private String hashPassword(String password) {
        try {
            // Some time hashing algorithm, e.g., SHA-256
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    // ...existing code...
}

https://github.com/ashishps1/awesome-low-level-design/blob/main/problems/tic-tac-toe.md

# POINTS MISSED!
- isFull() -> to check if the board is full
- reset option -> to reset the game
- isvalidMove -> what if the user tries to play on an already occupied cell?
- make it synchronized to ensure thread safety if multiple users are playing simultaneously
- Use enums for symbols, game status!
- implement an interface for winning strategy (e.g., RowWinStrategy, ColumnWinStrategy, DiagonalWinStrategy) to allow for different winning conditions.
- Further enhancements:
    - Add a timer for each player's turn.
        private int[] waitForMoveWithTimeout(Scanner scanner, User player) {
            final int[] move = new int[2];
            final boolean[] inputReceived = {false};
            Timer timer = new Timer();
            Thread inputThread = new Thread(() -> {
                System.out.print("Enter x y: ");
                move[0] = scanner.nextInt();
                move[1] = scanner.nextInt();
                inputReceived[0] = true;
            });
            inputThread.start();

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!inputReceived[0]) {
                        inputThread.interrupt();
                    }
                }
            }, MOVE_TIMEOUT_MS);

            try {
                inputThread.join(MOVE_TIMEOUT_MS + 1000);
            } catch (InterruptedException e) {
                // ignore
            }
            timer.cancel();
            return inputReceived[0] ? move : null;
        }
    - add another interface for winning rules:
        interface WinningStrategy {
            boolean checkWin(Board board, int player);
        }
        implemented by RowWinStrategy, ColumnWinStrategy, DiagonalWinStrategy
*/

class User {
    private int userId;
    private String userName;
    private String userNickName;
    private int matchesPlayedCount;
    private int matchesWonCount;

    public String getName() {
        return userName;
    }
    // getters and setters for each
    public int updateMatchesWonCount() {
        matchesWonCount++;
        return matchesWonCount;
    }
    public int updateMatchesPlayedCount() {
        matchesPlayedCount++;
        return matchesPlayedCount;
    }
}

class Node {
    final private int x;
    final private int y;
    private char symbol;

    // setters for symbol

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }
}

class Board {
    private int N;
    private Node[][] board;
    private ArrayDeque<Node> moves;
    private HashMap<Integer, Character> playerSymbolMap;

    Board(int N, char player1Symbol, char player2Symbol) {
        this.N = N;
        board = new Node[N][N];
        // initialize each node to corresponding x,y,''
        // initialize playerSymbolMap with player1Symbol and player2Symbol
    }

    private boolean playerWon(int x, int y) {
        // in O(N) check if the player one by checking row, column, and 2 diagonals
        return true;
    }

    public boolean playTurn(int player, int x, int y) {
        // set board[x][y] to playerSymbolMap.get(player)

        
        return playerWon(x,y);
    }

    public void undo() {
        // check if !deque.isEmpty()
        // pop from deque end
        // update node symbol to ''
    }

    public void render () {
        // print the board
    }
}

class Game {

    private User player1;
    private User player2;
    private Board board;

    Game () {
        // get player1, player2
        // input N
        // initialize board
        // give them the option to select the symbols
    }

    public void playGame() {
        // for i in N*N times, let the player input x,y based on i%2==0 or 1
        // call board.update for that x,y
        // if won: update won, games played counts
        // else continue till ends
        // if no one won until the end, then update the played games count end exit
    }
    
}
