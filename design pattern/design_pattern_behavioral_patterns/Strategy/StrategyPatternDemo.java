package Strategy;

public class StrategyPatternDemo {

	/*In Strategy pattern, we create objects which represent various strategies and 
	 * a context object whose behavior varies as per its strategy object. 
	 * The strategy object changes the executing algorithm of the context object.*/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	      Context context = new Context(new OperationAdd());		
	      System.out.println("10 + 5 = " + context.executeStrategy(10, 5));

	      context = new Context(new OperationSubstract());		
	      System.out.println("10 - 5 = " + context.executeStrategy(10, 5));

	      context = new Context(new OperationMultiply());		
	      System.out.println("10 * 5 = " + context.executeStrategy(10, 5));
	}

}
