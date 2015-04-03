import java.util.ArrayList;
import java.util.HashMap;

public class Noeud {
	
	ArrayList<Noeud> children;
	ArrayList<Noeud> parents;
	float probability;
	float probabilityParents;
	String name;
	String description;
	
	public Noeud(String n, String desc, float prob){
		name = n;
		description = desc;
		probability = prob;
		probabilityParents = 0.0f;
		children = new ArrayList<Noeud>();
		parents = new ArrayList<Noeud>();
	}
	
	public Object clone() {
		return children.clone();
	}


	public ArrayList<Noeud> getChildren() {
		return children;
	}


	public ArrayList<Noeud> getParents() {
		return parents;
	}

	public void addParent(Noeud n){
		parents.add(n);
		probabilityParents = 1.0f /(float)parents.size();
	}
	
	public float p(String id){
		for(Noeud n : parents){
			if(n.getName().equals(id))
				return probabilityParents;
		}
		return 0.0f;
	}
	
	public void addChild(Noeud n){
		children.add(n);
	}
	
	public boolean isHypothesis(String id){
		for(Noeud n : children){
			if(n.getName().equals(id))
				return true;
		}
		return false;
	}

	public float getProbability() {
		return probability;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
	
	@Override
	public boolean equals(Object o){
	    if (o == null) {
	        return false;
	    }
	    if (getClass() != o.getClass()) {
	        return false;
	    }
	    final Noeud other = (Noeud) o;
	    if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
	        return false;
	    }
	    if (this.description != other.description) {
	        return false;
	    }
	    return true;
	}

}
