package ChainOfResponsibility;

public class ChainPatternDemo {

	/*As the name suggests, the chain of responsibility pattern creates a chain of receiver objects for a request. 
	 * This pattern decouples sender and receiver of a request based on type of request.*/
	
		   private static AbstractLogger getChainOfLoggers(){

			      AbstractLogger errorLogger = new ErrorLogger(AbstractLogger.ERROR);
			      AbstractLogger fileLogger = new FileLogger(AbstractLogger.DEBUG);
			      AbstractLogger consoleLogger = new ConsoleLogger(AbstractLogger.INFO);

			      errorLogger.setNextLogger(fileLogger);
			      fileLogger.setNextLogger(consoleLogger);

			      return errorLogger;	
			   }

			   public static void main(String[] args) {
			      AbstractLogger loggerChain = getChainOfLoggers();

			      loggerChain.logMessage(AbstractLogger.INFO, 
			         "This is an information.");

			      loggerChain.logMessage(AbstractLogger.DEBUG, 
			         "This is an debug level information.");

			      loggerChain.logMessage(AbstractLogger.ERROR, 
			         "This is an error information.");
			   }

}
