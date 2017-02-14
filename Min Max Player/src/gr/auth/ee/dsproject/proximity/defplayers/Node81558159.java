/*
	ERGASIA TWN FOITHTWN:
	
	KARAPATSIAS VASILEIOS  AEM: 8155 email: vkarapats@auth.gr kinhto thlefwno: 6955962251
	
*	KERAMARIS KWNSTANTINOS AEM:8159  email: konskera@auth.gr  kinhto thlefwno:6982429477
*
*/
package gr.auth.ee.dsproject.proximity.defplayers;

import java.util.ArrayList;
import java.util.Arrays;

import gr.auth.ee.dsproject.proximity.board.Board;
import gr.auth.ee.dsproject.proximity.board.ProximityUtilities;
import gr.auth.ee.dsproject.proximity.board.Tile;

public class Node81558159
{
	Node81558159 parent;
	ArrayList <Node81558159> children;
	int nodeDepth;
	int[] nodeMove;
	Board nodeBoard;
	double nodeEvaluation;
	
	//constructors
	public Node81558159() {
		parent = null;
		children = null;
		nodeDepth = 0;
		nodeMove = null;
		nodeBoard = null;
		nodeEvaluation = 0;
	}	
	public Node81558159(Board board,int n){
		children= new ArrayList<Node81558159>();
		nodeDepth=n;
		nodeBoard=board;
		parent = null;
		nodeMove = null;
		nodeEvaluation = 0;
	}
	
	public Node81558159(Node81558159 parent, Board board,int n,int[] move){
		children= new ArrayList<Node81558159>();
		nodeDepth=n;
		nodeBoard=board;
		nodeMove=move;
		this.parent = parent;
		nodeEvaluation = 0;
	}
	
	
	//getters-setters
	public Board getBoard(){
		return nodeBoard;
	}
	public void setEval(double Evaluation){
		nodeEvaluation=Evaluation;
	}
	public double getEvaluation(){
		return nodeEvaluation;
	}
	public int[] getNodeMove(){
		return nodeMove;
	}
	
	public int getSizeOfChildren(){
		return children.size();
	}
	public ArrayList<Node81558159> getChildren(){
		return children;
	}
	
	//new child adder
	public void addChild(Node81558159 clone){
		children.add(clone);
	}
	//evaluation function
	public double evaluate(int myId){
		  int myScore = 0;
		  int oppScore = 0;
		  
		  //find oppponent's id
		  int oppId = 1;
		  if(oppId==myId)oppId=2;
		  
		  //check every tile of the board for every player's score
		  for (int x=0;x<ProximityUtilities.NUMBER_OF_COLUMNS;x++){
			  for (int y=0;y<ProximityUtilities.NUMBER_OF_ROWS;y++){
				  Tile tile =new Tile();
				  tile = this.nodeBoard.getTile(x, y);
				  
				  if(tile.getPlayerId()==myId){
					  myScore += tile.getScore();
				  }else if(tile.getPlayerId()==oppId){
					  oppScore += tile.getScore();
				  }
			  }
		}
		//return the difference between 2 players' scores 
		return ((double)(myScore-oppScore));
	  }
	 }
