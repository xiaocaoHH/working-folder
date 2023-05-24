package Facade;

public class FacadePatternDemo {

	/*Facade pattern hides the complexities of the system 
	 * and provides an interface to the client using which the client can access the system.
     * This type of design pattern comes under structural pattern as this pattern adds an interface to existing system to hide its complexities.*/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	      ShapeMaker shapeMaker = new ShapeMaker();

	      shapeMaker.drawCircle();
	      shapeMaker.drawRectangle();
	      shapeMaker.drawSquare();	
	}

}
