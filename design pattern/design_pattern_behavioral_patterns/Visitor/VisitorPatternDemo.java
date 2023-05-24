package Visitor;

public class VisitorPatternDemo {

	/*By this way, execution algorithm of element can vary as and when visitor varies. */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	      ComputerPart computer = new Computer();
	      computer.accept(new ComputerPartDisplayVisitor());
	}

}
