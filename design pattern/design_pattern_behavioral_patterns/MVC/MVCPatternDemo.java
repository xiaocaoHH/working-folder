package MVC;

public class MVCPatternDemo {

	/*Controller acts on both model and view. 
	 * It controls the data flow into model object and updates the view whenever data changes. 
	 * It keeps view and model separate.*/
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	      //fetch student record based on his roll no from the database
	      Student model  = retriveStudentFromDatabase();

	      //Create a view : to write student details on console
	      StudentView view = new StudentView();

	      StudentController controller = new StudentController(model, view);

	      controller.updateView();

	      //update model data
	      controller.setStudentName("John");

	      controller.updateView();
	}
	
	   private static Student retriveStudentFromDatabase()
	   {
		      Student student = new Student();
		      student.setName("Robert");
		      student.setRollNo("10");
		      return student;
		}

}
