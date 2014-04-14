
import controller.CoreController;
import model.CoreModel;
import view.CoreView;





public class MainApp {
 
	//Backlog: Wiki summary bugs \" \u08080 etc (unicode?)
	public static void main (String [] args)
	{
		CoreModel model = new CoreModel();
		CoreView view = new CoreView();
		CoreController controller = new CoreController(model,view);
		controller.launch("data.txt");
		view.setVisible(true);
	}
}
