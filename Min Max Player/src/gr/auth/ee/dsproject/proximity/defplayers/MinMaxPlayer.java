/*	Min Max Player
*	
*	AUTHORS: Konstantinos Keramaris, Vassilis Karapatsias
*	
*/
package gr.auth.ee.dsproject.proximity.defplayers;

import java.util.ArrayList;
import java.util.HashMap;

import gr.auth.ee.dsproject.proximity.board.Board;
import gr.auth.ee.dsproject.proximity.board.ProximityUtilities;
import gr.auth.ee.dsproject.proximity.board.Tile;

public class MinMaxPlayer implements AbstractPlayer
{

  int score;
  int id;
  String name;
  int numOfTiles;

  public MinMaxPlayer (Integer pid)
  {
    id = pid;
    name = "MinMaxPlayer";
  }

  public String getName ()
  {

    return name;

  }

  public int getNumOfTiles ()
  {
    return numOfTiles;
  }

  public void setNumOfTiles (int tiles)
  {
    numOfTiles = tiles;
  }

  public int getId ()
  {
    return id;
  }

  public void setScore (int score)
  {
    this.score = score;
  }

  public int getScore ()
  {
    return score;
  }

  public void setId (int id)
  {

    this.id = id;

  }

  public void setName (String name)
  {
    this.name = name;
  }


  public int[] getNextMove (Board board, int randomNumber)
  {
	  //Create the root of our MinMax tree
	  Node81558159 root= new Node81558159(board,0);
	  //create my subtree
	  createMySubTree(root,1);
	  //apply and return the result of min max algorithm
	  return chooseMinMaxMove(root,2);
  }

  public void createMySubTree (Node81558159 parent, int depth)
  {
	  //get the 2 following numbers
	  int number1;
	  Board currBoard = parent.getBoard();
	  number1 = Board.getNextTenNumbersToBePlayed()[depth - 1];
	  int number2;
	  number2 = Board.getNextTenNumbersToBePlayed()[depth];

	  //find the empty slots
	  for (int x=0;x<ProximityUtilities.NUMBER_OF_COLUMNS;x++){
		  for (int y=0;y<ProximityUtilities.NUMBER_OF_ROWS;y++){
			  if(currBoard.getTile(x, y).getPlayerId()==0){
				  
				  /*
				   * create a board that simulates every possible 
				   * move at this stage of the tree
				  */
				  Board simBoard=new Board();
				  simBoard=ProximityUtilities.boardAfterMove(this.id,currBoard,x,y,number1);
				  int[] move=new int[3];
				  move[0]=x;
				  move[1]=y;
				  move[2]=number1;
				  
				  //create the clone node for this tile and number1
				  Node81558159 clone=new Node81558159(parent, simBoard,depth,move);
				  
				  //add root's subtree
				  parent.addChild(clone);
				  
				  createOpponentSubtree(clone,depth+1,number2);
			  }
		  }
	  }

  }

  public void createOpponentSubtree (Node81558159 parent, int depth,int number2)
  {
	  Board currBoard=new Board();
	  currBoard=parent.getBoard();
	  
	  //find the empty slots
	  for (int x=0;x<ProximityUtilities.NUMBER_OF_COLUMNS;x++){
		  for (int y=0;y<ProximityUtilities.NUMBER_OF_ROWS;y++){
			  if(currBoard.getTile(x, y).getPlayerId()==0){
				  
				  //find opponent's id
				  int oppId=1;
				  if(this.id==1)oppId=2;
				 
				  /*
				   * create a board that simulates every possible 
				   * move at this stage of the tree
				  */				 
				  Board simBoard=new Board();
				  simBoard=ProximityUtilities.boardAfterMove(oppId,currBoard,x,y,number2);
				  int[] move=new int[3];
				  move[0]=x;
				  move[1]=y;
				  move[2]=number2;

				  //create the clone node for this tile and number2
				  Node81558159 clone=new Node81558159(parent, simBoard,depth,move);

				  double eval;
				  eval=clone.evaluate(this.id);
				  clone.setEval(eval);
				  parent.addChild(clone);
			  }
		  }
	  }

  }  public int[] chooseMinMaxMove(Node81558159 root,int depth){
	  
	  //create an arraylist for the root's children
	  ArrayList <Node81558159> child=new ArrayList<Node81558159>();
	  child=root.getChildren();
	  
	  /*
	   * worst case scenario for us (opponent's move)
	   * for every child check its subtrees
	  */
	  
	  /*
	   * in case we play as player two we need to check if it is the last move
	   * if it is we shouldn't check for our opponent's worst case for us move
	   * as the ArrayList is going to be out of bounds
	  */
	  if(root.getSizeOfChildren()!=1){
		  for(int i=0;i<root.getSizeOfChildren();i++){
			  
			  //create an arraylist for the second level children
			  ArrayList <Node81558159> secondChild=new ArrayList<Node81558159>();
			  secondChild=child.get(i).getChildren();
			  
			  //set as min value our first evaluation
			  double min=secondChild.get(0).getEvaluation(); 
			  
			  //find the min evaluation
			  for(int j=1;j<child.get(i).getSizeOfChildren();j++){
				  if (secondChild.get(j).getEvaluation()<min){
					  min=secondChild.get(j).getEvaluation();
				  }
			  }
			  //send the minimum evaluation to the upper level of our tree
			  child.get(i).setEval(min);
		  }
	  }
	  //best case scenario for us (mine move)
	  int[] move=new int[3];
	  
	  //set as max value our first evaluation
	  double max = child.get(0).getEvaluation();
	  move = child.get(0).getNodeMove();
	  
	  //find the max evaluation
	  for(int i=1;i<root.getSizeOfChildren();i++){
		  if (child.get(i).getEvaluation()>max){
			  max=child.get(i).getEvaluation();
			  move=child.get(i).getNodeMove();
		  }
	  }
	  // return the best move for us based to min max algorithm
	  return move;
  }
}