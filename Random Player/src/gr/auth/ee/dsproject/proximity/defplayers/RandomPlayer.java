/*	Random Player
*	
*	AUTHORS: Konstantinos Keramaris, Vassilis Karapatsias
*	
*/
package gr.auth.ee.dsproject.proximity.defplayers;

import gr.auth.ee.dsproject.proximity.board.Board;
import gr.auth.ee.dsproject.proximity.board.ProximityUtilities;
import gr.auth.ee.dsproject.proximity.board.Tile;

public class RandomPlayer implements AbstractPlayer
{
	private int id;
	private String name;
	private int score;
	private int numOfTiles;
	
	//constructors
	
	public RandomPlayer(){}
	
	public RandomPlayer(Integer pid){
		this.id = pid;
	}
	public RandomPlayer(Integer pid,String pName,Integer pScore,Integer pNumOfTiles){
		this.id = pid;
		this.name = pName;
		this.score = pScore;
		this.numOfTiles = pNumOfTiles;
	}
	// getters-setters
	
	public void setId(int id) {
		this.id=id;
	}

	public int getId() {
		
		return this.id;
	}

	public void setName(String name) {
		this.name = name;
		
	}

	public String getName() {
		return this.name;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getScore() {
		return this.score;
	}

	public void setNumOfTiles(int tiles) {
		this.numOfTiles = tiles ; 
	}

	public int getNumOfTiles() {
		return this.numOfTiles;
	}
	//next move prediction 
	
	public int[] getNextMove (Board board) {
		int[] coords=new int[2];
		int flag=1;
		do{
			//y for rows x for columns(always inside the board after using the two static variables)
			coords[0]=(int) (Math.random()*ProximityUtilities.NUMBER_OF_COLUMNS);
			coords[1]=(int) (Math.random()*ProximityUtilities.NUMBER_OF_ROWS);
			if(board.isInsideBoard(coords[0],coords[1])){	
				Tile temp=board.getTile(coords[0],coords[1]);
				flag=temp.getPlayerId();		//check if the cell is empty
			}
		}while(flag!=0);
		return coords;	
	}
	// locate the neighbors
	
	public static int[][] getNeighborsCoordinates(Board board,int x, int y){
		int[][] neighbors=new int[6][2];
		for(int i=0; i<6; i++){
			neighbors[i][0]=-1;
			neighbors[i][1]=-1;
		}
		//separate situations for even(first) or odd(second) number of rows
		
		if((y%2)==0){
			//check if inside the board
			if(board.isInsideBoard(x+1,y)){ 
				neighbors[0][0]=x+1;
				neighbors[0][1]=y;
			}
			//check if inside the board
			if(board.isInsideBoard(x,y+1)){
				neighbors[1][0]=x;
				neighbors[1][1]=y+1;
			}
			//check if inside the board
			if(board.isInsideBoard(x-1,y+1)){
				neighbors[2][0]=x-1;
				neighbors[2][1]=y+1;
			}
			//check if inside the board
			if(board.isInsideBoard(x-1,y)){
				neighbors[3][0]=x-1;
				neighbors[3][1]=y;
			}
			//check if inside the board
			if(board.isInsideBoard(x-1,y-1)){
				neighbors[4][0]=x-1;
				neighbors[4][1]=y-1;
			}
			//check if inside the board
			if(board.isInsideBoard(x,y-1)){
				neighbors[5][0]=x;
				neighbors[5][1]=y-1;
			}
		}else{
			//check if inside the board
			if(board.isInsideBoard(x+1,y)){
				neighbors[0][0]=x+1;
				neighbors[0][1]=y;
			}
			//check if inside the board
			if(board.isInsideBoard(x+1,y+1)){
				neighbors[1][0]=x+1;
				neighbors[1][1]=y+1;
			}
			//check if inside the board
			if(board.isInsideBoard(x,y+1)){
				neighbors[2][0]=x;
				neighbors[2][1]=y+1;
			}
			//check if inside the board
			if(board.isInsideBoard(x-1,y)){
				neighbors[3][0]=x-1;
				neighbors[3][1]=y;
			}
			//check if inside the board
			if(board.isInsideBoard(x,y-1)){
				neighbors[4][0]=x;
				neighbors[4][1]=y-1;
			}
			//check if inside the board
			if(board.isInsideBoard(x+1,y-1)){
				neighbors[5][0]=x+1;
				neighbors[5][1]=y-1;
			}
		}
		return neighbors;		
	}

}
