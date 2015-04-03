import java.util.ArrayList;



public class Blackboard {
	
	ArrayList<String> message_list;
	int step =0;
	
	private Blackboard()
	{
		message_list = new ArrayList<String>();
	}

	private static Blackboard INSTANCE = null;

	public static Blackboard getInstance()
	{	
		if (INSTANCE == null)
		{ 
			INSTANCE = new Blackboard();	
		}
		return INSTANCE;
	}
	
	public void write(String message){
		message_list.add(message);
	}
	
	public ArrayList<String> read(){
		return message_list;
	}

}
