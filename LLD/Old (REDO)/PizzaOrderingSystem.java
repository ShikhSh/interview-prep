/**
Pizza Ordering System
- Choose from sizes - small, medium, large
- let them add toppings
- add beverage to an order
- store prices for different things and let user know how much is total cost
To check:
- Support delete functionality?
- Support multiple pizzas/beverages?
*/
import java.util.*;
class Item {
	String name;
	double cost;
	Item(String name, double cost) {
		this.cost = cost;
		this.name = name;
	}
}
// we can define toppings, beverages

enum Size {
	SMALL, MEDIUM, LARGE;
}

class Pizza {
	int id;
	Size size;
	double cost;
	HashMap<String, Item> toppings;
	Pizza(Size size, double cost) {
		this.size = size;
		this.toppings = new HashMap<>();
		this.cost = cost;
	}

	public void addTopping(Item topping) {
		toppings.put(topping.name, topping);
		cost += topping.cost;
	}

	public void printPizza() {
		System.out.println("Pizza: " + size);
		System.out.println("Toppings: ");
		for(String topping: toppings.keySet()) {
			System.out.print(topping + " ");
		}
	}
}

class Order { // cart
	int ID_MULTIPLIER = 10000;
	int id;
	Pizza pizza;
	Item beverage;
	Order () {
		id = (int)(Math.random()*ID_MULTIPLIER);
	}
	public void addPizza(Size size, double cost) {
		pizza = new Pizza(size, cost);
	}
	public void addTopping(Item topping) {
		pizza.addTopping(topping);
	}
	public void addBeverage(Item beverage) {
		this.beverage = beverage;
	}
	public double getCost() {
		return pizza.cost+beverage.cost;
	}
	public void printOrder() {
		pizza.printPizza();
		System.out.println("Beverage:" + beverage.name);
		System.out.println("Cost:" + getCost());
	}
}

class PizzaOrderingSystem {
	HashMap<String, Item> toppings;
	HashMap<String, Item> beverages;
	HashMap<Integer, Order> orders;
	HashMap<Size, Double> pizzaPrices;

	PizzaOrderingSystem() {
		toppings = new HashMap<>();
		beverages = new HashMap<>();
		orders = new HashMap<>();
		pizzaPrices = new HashMap<>();
	}

	// add toppings, beverages functions
	public int createOrder() {
		Order newOrder = new Order();
		orders.put(newOrder.id, newOrder);
		return newOrder.id;
	}
	public void addPizza(int ordersId, Size size) {
		// valid order check
		orders.get(ordersId).addPizza(size, pizzaPrices.get(size));
	}

	public void addTopping(int ordersId, String topping) {
		// valid order check
		orders.get(ordersId).addTopping(toppings.get(topping));
	}

	public void addBeverage(int ordersId, String beverage) {
		// valid order check
		orders.get(ordersId).addBeverage(beverages.get(beverage));
	}

	public double getPrice(int ordersId) {
		return orders.get(ordersId).getCost();
	}

	public void printOrder(int ordersId) {
		orders.get(ordersId).printOrder();
	}

    public static void main(String[] args) {
        PizzaOrderingSystem pizzaOrderingSystem = new PizzaOrderingSystem();
        pizzaOrderingSystem.toppings.put("mushroom", new Item("mushroom", 1.5));
        pizzaOrderingSystem.toppings.put("tomato", new Item("tomato", 0.5));
        pizzaOrderingSystem.beverages.put("coke", new Item("code", 2));
        pizzaOrderingSystem.pizzaPrices.put(Size.SMALL, 5.5);

        int orderId = pizzaOrderingSystem.createOrder();
        pizzaOrderingSystem.addPizza(orderId, Size.SMALL);
        pizzaOrderingSystem.addTopping(orderId, "mushroom");
        pizzaOrderingSystem.addTopping(orderId, "tomato");
        pizzaOrderingSystem.addBeverage(orderId, "coke");
        pizzaOrderingSystem.printOrder(orderId);
    }
}

// ==============================================