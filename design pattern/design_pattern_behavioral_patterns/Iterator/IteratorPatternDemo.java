package Iterator;

public class IteratorPatternDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	      NameRepository namesRepository = new NameRepository();

	      for(Iterator iter = namesRepository.getIterator(); iter.hasNext();){
	         String name = (String)iter.next();
	         System.out.println("Name : " + name);
	      } 	

	}

}
