package testdrawing;

/* Simple graph drawing class
Bert Huang
COMS 3137 Data Structures and Algorithms, Spring 2009

This class is really elementary, but lets you draw 
reasonably nice graphs/trees/diagrams. Feel free to 
improve upon it!
 */

import java.util.*;
import java.awt.*;
import javax.swing.*;

public class GraphDraw extends JFrame {

	
	private static final long serialVersionUID = 1L;
	int width;
    int height;

    ArrayList<Node> nodes;
    ArrayList<edge> edges;
    ArrayList<edge_tag> tags;

    public GraphDraw() { //Constructor
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		nodes = new ArrayList<Node>();
		edges = new ArrayList<edge>();
		tags = new ArrayList<edge_tag>();
		width = 30;
		height = 30;
    }

    public GraphDraw(String name) { //Construct with label
		this.setTitle(name);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		nodes = new ArrayList<Node>();
		edges = new ArrayList<edge>();
		tags = new ArrayList<edge_tag>();
		width = 30;
		height = 30;
    }

    class Node {
		int x, y;
		String name;
		
		public Node(String myName, int myX, int myY) {
		    x = myX;
		    y = myY;
		    name = myName;
		}
    }
    
    class edge {
		int i,j;
		
		public edge(int ii, int jj) {
		    i = ii;
		    j = jj;	    
		}
    }
    
    
    class edge_tag {
    	String tag;
    	int x,y;
    	
    	public edge_tag(int xx, int yy, String etag) {
    		x = xx;
    		y = yy;
    		tag = etag;
    	}
    }
    
    public void addNode(String name, int x, int y) { 
		//add a node at pixel (x,y)
		nodes.add(new Node(name,x,y));
		this.repaint();
    }
    
    public void addEdge(int i, int j) {
		//add an edge between nodes i and j
		edges.add(new edge(i,j));
		this.repaint();
    }
    
    public void addTag(int x, int y, String tag) {
    	tags.add(new edge_tag(x,y,tag));
    	this.repaint();
    }
    
    public void paint(Graphics g) { // draw the nodes and edges
		FontMetrics f = g.getFontMetrics();
		int nodeHeight = Math.max(height, f.getHeight());
	
		g.setColor(Color.black);
		for (edge e : edges) {
		    g.drawLine(nodes.get(e.i).x, nodes.get(e.i).y,
			     nodes.get(e.j).x, nodes.get(e.j).y);
		}
	
		for (Node n : nodes) 
		{
		    int nodeWidth = Math.max(width, f.stringWidth(n.name)+width/2);
		    g.setColor(Color.white);
		    g.fillOval(n.x-nodeWidth/2, n.y-nodeHeight/2, 
			       nodeWidth, nodeHeight);
		    g.setColor(Color.black);
		    g.drawOval(n.x-nodeWidth/2, n.y-nodeHeight/2, 
			       nodeWidth, nodeHeight);
		    
		    g.drawString(n.name, n.x-f.stringWidth(n.name)/2,
				 n.y+f.getHeight()/2);
		}
		
		for (edge_tag t : tags)
		{
		    g.drawString(t.tag, t.x, t.y);
		}
    }
    
    public static void main(String[] args) {
    	GraphDraw frame = new GraphDraw("Test Visualize");

    	
    	frame.setSize(500,500);
    	
    	frame.setVisible(true);

    	//2 vars
    	
//    	frame.addNode("00", 200,100); //0
//    	frame.addNode("01", 100,200); //1
//    	frame.addNode("10", 300,200); //2
//    	frame.addNode("11", 200,300); //3
//    	
//    	frame.addEdge(0,1,"1");
//    	frame.addEdge(0,2,"1");
//    	frame.addEdge(0,3,"1");
//    	
//    	frame.addEdge(1,2,"1");
//    	frame.addEdge(1,3,"1");
//    	
//    	frame.addEdge(2,3,"1");
//    	
//    	frame.addTag(100, 100, "1");
//    	frame.addTag(300, 100, "2");
//    	frame.addTag(100, 300, "3");
//    	frame.addTag(300, 300, "4");
//    	
//    	frame.addTag(150, 195, "5");
//    	frame.addTag(205, 150, "6");
    	
    	//2 vars
    	
    	//3 vars
    	frame.addNode("000", 250,50); //0
    	frame.addNode("001", 375,125); //0
    	frame.addNode("010", 450,250); //0
    	frame.addNode("011", 375,375); //0
    	frame.addNode("100", 250,450); //0
    	frame.addNode("101", 125,375); //0
    	frame.addNode("110", 50,250); //0
    	frame.addNode("111", 125,125); //0
    	
    	for (int i = 0; i < 8; i++)
    		for (int j = i+1; j < 8;j++)
    			frame.addEdge(i, j);

    	//3 vars
    }
    
}

//class testGraphDraw {
    //Here is some example syntax for the GraphDraw class
    
//}