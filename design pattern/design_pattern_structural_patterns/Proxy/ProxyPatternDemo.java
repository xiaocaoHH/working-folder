package Proxy;

public class ProxyPatternDemo {

	/*In proxy pattern, a class represents functionality of another class. */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	      Image image = new ProxyImage("test_10mb.jpg");

	      //image will be loaded from disk
	      image.display(); 
	      System.out.println("");
	      
	      //image will not be loaded from disk
	      image.display(); 
	}

}
