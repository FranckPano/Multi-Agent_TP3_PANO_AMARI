import java.util.ArrayList;
import java.util.HashMap;


public class observerAgent {
	
	ArrayList<Noeud> plans;
	ArrayList<Noeud> actions;
	
	ArrayList<Noeud> performed;
	Noeud hypotesis;
	float probabilityHypotesis;
	
	public observerAgent(ArrayList<Noeud> p, ArrayList<Noeud> a){
		plans = p;
		actions = a;
		performed = new ArrayList<Noeud>();
	}
	
	public void newPlanStarted(){
		performed.clear();
	}
	
	public void actionPerformed(){
		
		String stepText;
		String desc = Blackboard.getInstance().read().get(Blackboard.getInstance().read().size()-1);

		if(desc.equals(operatorAgent.NEW_ACTION)){
			newPlanStarted();
		    MainWindow.observatorText.append("Observation N°" + Blackboard.getInstance().step + ": Nouveau plan démarré.\n");
			return ;
		}
		
		Noeud lastAction = findFromDesc(desc);
		if(isPlan(lastAction.getName())){
		    MainWindow.observatorText.append("Observation N°" + Blackboard.getInstance().step + ": Le plan prévu est terminé.\n");
		    MainWindow.observatorText.append("----------------------------------------------------------------------\n");
		    Blackboard.getInstance().step = 0;
		    return ;
		}
		performed.add(lastAction);
		probabilityHypotesis = 0.0f;
		
		float toTest = 0;
		for(Noeud h : plans){
			toTest = getNumerator(h.getDescription()) / getDenominator();
			if(probabilityHypotesis < toTest){
				hypotesis = h;
				probabilityHypotesis = toTest;
			}
		}
		
		stepText = "Observation N°" + Blackboard.getInstance().step + ": Hypothèse: " + hypotesis.getDescription()
	    		+ ", avec une probabilité de " + probabilityHypotesis*100 +"%\n";
	    MainWindow.observatorText.append(stepText);
	    
		if(probabilityHypotesis == 1.0f){
			for(Noeud n : hypotesis.getChildren()){
				if(!performed.contains(n)){
					Blackboard.getInstance().write(n.getDescription());
					stepText = "Observation N°" + Blackboard.getInstance().step + ": AIDE: effectue: " + n.getDescription() +"\n";
				    MainWindow.observatorText.append(stepText);
				}
			}
		}
	}
	
	public boolean isPlan(String name){
		for(Noeud noeud : plans){
			if(noeud.getName().equals(name))
				return true;
		}
		return false;
	}
	
	public float getNumerator(String desc){
		float toReturn = findFromDesc(desc).getProbability();
		String name = findFromDesc(desc).getName();
		for(Noeud e : performed){
			toReturn *= e.p(name);
		}
		return toReturn;
	}
	
	public float getDenominator(){
		float toReturn = 0;
		float product;
		 
		for(Noeud h : plans){
			product = 1;
			for(Noeud e : performed){
				product *= e.p(h.getName());
			}
			product *= h.getProbability();
			toReturn += product;
		}
		return toReturn;
	}
	
	public Noeud findFromDesc(String n){
		for(Noeud noeud : actions){
			if(noeud.getDescription().equals(n))
				return noeud;
		}
		for(Noeud noeud : plans){
			if(noeud.getDescription().equals(n))
				return noeud;
		}
		return null;
	}
	
}
