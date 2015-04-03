import java.util.ArrayList;


public class operatorAgent {

	public static String NEW_ACTION = "Nouvelle action";

	ArrayList<Noeud> plans;
	ArrayList<Noeud> actions;

	ArrayList<Noeud> toPerforme;
	Noeud aim;
	boolean currentlyPerforming = false;

	public operatorAgent(ArrayList<Noeud> p, ArrayList<Noeud> a){
		plans = p;
		actions = a;
	}

	public void chooseNewPlan(){

		currentlyPerforming = true;

		float random = (float)getRandomNum(0, 100) / 100;
		for(Noeud n : plans){
			random = random - n.getProbability();
			if(random<=0){
				toPerforme = (ArrayList<Noeud>) n.getChildren().clone();
				aim = n;
				break;
			}
		}
	}

	public void proceedNext(){
		String toWrite = "";
		String stepText;
		if(currentlyPerforming){

			String desc = Blackboard.getInstance().read().get(Blackboard.getInstance().read().size()-1);
			Noeud lastAction = findFromDesc(desc);
			if(toPerforme.lastIndexOf(lastAction) != -1){
				toPerforme.remove(toPerforme.lastIndexOf(lastAction));
			}
			if(!toPerforme.isEmpty()){
				int random = getRandomNum(0, toPerforme.size()-1);

			    stepText = "Action N°" + Blackboard.getInstance().step + ": Execute action: " + toPerforme.get(random).getDescription() + "\n";
				toWrite = toPerforme.get(random).getDescription() ;
				toPerforme.remove(random);
			}
			else{
				toWrite = aim.getDescription();

				stepText = "Action N°" + Blackboard.getInstance().step + ": Execute Plan: " + aim.getDescription() +"\n";
			    stepText += "----------------------------------------------------------------------\n";
				currentlyPerforming = false;
			}
		}
		else{
			chooseNewPlan();
			toWrite = NEW_ACTION;
			stepText = "Action N°" + Blackboard.getInstance().step + ": Nouveau Plan: " + aim.getDescription() +"\n";
		}
		
		MainWindow.operatorText.append(stepText);
		Blackboard.getInstance().write(toWrite);
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

	/*Renvoi une valeur x telle que : minNim <= x <= maxNum*/
	private int getRandomNum(int minNum, int maxNum){

		int MonChiffre = maxNum + 1;

		while(MonChiffre>maxNum){

			MonChiffre = (int)(minNum + (Math.random() * (maxNum+1)));
		}
		return MonChiffre;
	}


}
