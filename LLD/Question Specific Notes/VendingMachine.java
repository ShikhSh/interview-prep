/*
* full code - https://github.com/ashishps1/awesome-low-level-design/tree/main/solutions/java/src/vendingmachine
* Main class of the vending machine system
* Singleton class
*/
public class VendingMachine {
    private final static VendingMachine INSTANCE = new VendingMachine();
    private final Inventory inventory = new Inventory();
    /*
     * Stores state of the vending machine
     */
    private VendingMachineState currentVendingMachineState;
    private int balance = 0;
    /*
     * Stores item selected by user
     */
    private String selectedItemCode;

    /*
     * Initialized with Idle State
     */
    public VendingMachine() {
        currentVendingMachineState = new IdleState(this);
    }

    public static VendingMachine getInstance() {
        return INSTANCE;
    }

    public void insertCoin(Coin coin) {
        currentVendingMachineState.insertCoin(coin);
    }

    public Item addItem(String code, String name, int price, int quantity) {
        Item item = new Item(code, name, price);
        inventory.addItem(code, item, quantity);
        return item;
    }

    public void selectItem(String code) {
        currentVendingMachineState.selectItem(code);
    }

    /*
     * Calls the dispense method of the current state, which internally calls the below
     * dispenseItem method if the state is DispensingState!! otherwise throws an error
     */
    public void dispense() {
        currentVendingMachineState.dispense();
    }

    public void dispenseItem() {
        Item item = inventory.getItem(selectedItemCode);
        if (balance >= item.getPrice()) {
            inventory.reduceStock(selectedItemCode);
            balance -= item.getPrice();
            System.out.println("Dispensed: " + item.getName());
            if (balance > 0) {
                System.out.println("Returning change: " + balance);
            }
        }
        reset();
        setState(new IdleState(this));
    }
}

/**
 * State design pattern for vending machine states
 */
abstract class VendingMachineState {
    VendingMachine machine;

    VendingMachineState(VendingMachine machine) {
        this.machine = machine;
    }

    public abstract void insertCoin(Coin coin);
    public abstract void selectItem(String code);
    public abstract void dispense();
    public abstract void refund();
}


/**
 * Idle state - waiting for user to select an item
 * Stores an instance of the vending machine to be able to change its state
 */
class IdleState extends VendingMachineState {
    public IdleState(VendingMachine machine) {
        super(machine);
    }

    @Override
    public void insertCoin(Coin coin) {
        System.out.println("Please select an item before inserting money.");
    }

    @Override
    public void selectItem(String code) {
        if (!machine.getInventory().isAvailable(code)) {
            System.out.println("Item not available.");
            return;
        }
        machine.setSelectedItemCode(code);
        /**
         * Change state to ItemSelectedState after selecting an item
         * Create a new instance of ItemSelectedState and pass the current
         * instance of the vending machine
         */
        machine.setState(new ItemSelectedState(machine));
        System.out.println("Item selected: " + code);
    }

    @Override
    public void dispense() {
        System.out.println("No item selected.");
    }

    @Override
    public void refund() {
        System.out.println("No money to refund.");
    }
}