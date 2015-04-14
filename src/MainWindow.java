
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;



/*****/
public class MainWindow extends JFrame{
	
	ArrayList<Noeud> plans;
	ArrayList<Noeud> actions;
	
	observerAgent obs;
	operatorAgent op;
	
	static JTextArea operatorText;
	static JTextArea observatorText;
	
	public MainWindow(){
		
		this.setTitle("Système Multi-Agents : Observer / Operator");
	    this.setSize(600, 600);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);
	   
	    JPanel blueprint = new JPanel();
	    blueprint.setPreferredSize(new Dimension(550, 150));
	    ImagePanel imgpan = new ImagePanel();
	    blueprint.setLayout(new BorderLayout());
	    blueprint.add(imgpan, BorderLayout.CENTER);
	    
	    operatorText = new JTextArea();
	    JScrollPane scrollPaneOp = new JScrollPane( operatorText );
	    scrollPaneOp.setPreferredSize(new Dimension(550,150));
	    
	    JButton button = new JButton("Next Step");
	    button.setMinimumSize(new Dimension(300, 100));
	    button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				next();
			}
	    });
	    
	    observatorText = new JTextArea();
	    JScrollPane scrollPaneObs = new JScrollPane( observatorText );
	    scrollPaneObs.setPreferredSize(new Dimension(550,150));
	    
	    JPanel content = new JPanel();
	    content.setPreferredSize(new Dimension(600, 600));
	    content.setLayout(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	        
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.gridheight = 3;
	    gbc.gridwidth = 12;
	    content.add(blueprint, gbc);
	    
	    gbc.gridy = 3;
	    content.add(scrollPaneOp, gbc);
	    
	    gbc.gridy = 9;
	    content.add(button, gbc);
	    
	    gbc.gridy = 6;
	    content.add(scrollPaneObs, gbc);
	    
	    this.setContentPane(content);
	    this.setVisible(true); 
	    
	    
		Noeud h1 = new Noeud("h1","Travailler",.3f);
		Noeud h2 = new Noeud("h2","Sortir",.15f);
		Noeud h3 = new Noeud("h3","Dormir",.25f);
		Noeud h4 = new Noeud("h4","Jouer a la Console",.1f);
		Noeud h5 = new Noeud("h5","Regarder un film",.2f);
		
		Noeud e1 = new Noeud("e1","Allumer l'ordinateur portable",.2f);
		Noeud e2 = new Noeud("e2","Eteindre la lumière",.2f);
		Noeud e3 = new Noeud("e3","Aller dans le lit",.2f);
		Noeud e4 = new Noeud("e4","Allumer la Console",.2f);
		Noeud e5 = new Noeud("e5","Allumer l'Ecran",.2f);
		
		/**/
		link(h1,e1);
		
		link(h2,e2);
		
		link(h3,e2);
		link(h3,e3);
		
		link(h4,e4);
		link(h4,e5);
		
		link(h5,e1);
		link(h5,e3);
		link(h5,e5);
		
		/**/
		plans = new ArrayList<Noeud>();
		plans.add(h1);
		plans.add(h2);
		plans.add(h3);
		plans.add(h4);
		plans.add(h5);
		
		actions = new ArrayList<Noeud>();
		actions.add(e1);
		actions.add(e2);
		actions.add(e3);
		actions.add(e4);
		actions.add(e5);
		
		obs = new observerAgent(plans,actions);
		op = new operatorAgent(plans,actions);
		
	}
	
	public void next(){
		Blackboard.getInstance().step ++;
		op.proceedNext();
		obs.actionPerformed();
	}
	
	public void link(Noeud parent, Noeud child){
		parent.addChild(child);
		child.addParent(parent);
	}
	
    static public void main(String args[]) {
    	MainWindow mw = new MainWindow();
    }
    
    public class ImagePanel extends JPanel {
    	  public void paintComponent(Graphics g){
    	    try {
    	      Image img = ImageIO.read(new File("src/schema.png"));
    	      g.drawImage(img, 0, 0, this);
    	      //Pour une image de fond
    	      //g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
    	    } catch (IOException e) {
    	      e.printStackTrace();
    	    }                
    	  }               
    	}
    
}
