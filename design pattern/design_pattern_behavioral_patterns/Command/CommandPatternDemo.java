package Command;

public class CommandPatternDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	      Stock abcStock = new Stock();

	      BuyStock buyStockOrder = new BuyStock(abcStock);
	      SellStock sellStockOrder = new SellStock(abcStock);

	      Broker broker = new Broker();
	      broker.takeOrder(buyStockOrder);
	      broker.takeOrder(sellStockOrder);

	      broker.placeOrders();
	}

}
