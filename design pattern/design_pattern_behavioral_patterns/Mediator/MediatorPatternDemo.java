package Mediator;

public class MediatorPatternDemo {

	/*This pattern provides a mediator class which normally handles all the communications between different classes 
	 * and supports easy maintenance of the code by loose coupling.*/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	      User robert = new User("Robert");
	      User john = new User("John");

	      robert.sendMessage("Hi! John!");
	      john.sendMessage("Hello! Robert!");
	}

}
