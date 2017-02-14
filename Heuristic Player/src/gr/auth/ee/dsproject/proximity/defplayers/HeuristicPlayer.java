/*	Heuristic Player
*	
*	AUTHORS: Konstantinos Keramaris, Vassilis Karapatsias
*	
*/
package gr.auth.ee.dsproject.proximity.defplayers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import gr.auth.ee.dsproject.proximity.board.Board;
import gr.auth.ee.dsproject.proximity.board.ProximityUtilities;
import gr.auth.ee.dsproject.proximity.board.Tile;

public class HeuristicPlayer implements AbstractPlayer {

	int score;
	int id;
	String name;
	int numOfTiles;

	public HeuristicPlayer (Integer pid)
	{
		id = pid;
		name = "Heuristic";
	}

	public String getName ()
	{

		return name;

	}

	public int getNumOfTiles(){
		return numOfTiles;
	}

	public void setNumOfTiles(int tiles){
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
	
	public int[] getNextMove (Board board , int randomNumber){
		int[] nextMove= new int[3]; //Pinakas epistrofis
		//Dimiourgia 2 parallilwn arraylist gia sintetagmenes kai aksiologisi
		
		ArrayList<Integer[]> arrayCoord= new ArrayList<Integer[]>();
		ArrayList<Double> arrayEval= new ArrayList<Double>();
		//Prwti kinisi
		if(board.getOpponentsLastMove()[1]==-1){// elegxos an o antipalos epaikse prwtos
			nextMove[0]=(int) (Math.random()*(ProximityUtilities.NUMBER_OF_COLUMNS - 2)+1);//x=[1,10]
			nextMove[1]=(int) (Math.random()*(ProximityUtilities.NUMBER_OF_ROWS - 2)+1);//y=[1,8]
			nextMove[2]=randomNumber;
			return nextMove;
		}
		Tile tempTile= new Tile();
		Double evaluation;//temp metablhth gia apotelesma getEvaluation
		//elegxos gia kathe plakidio
		for(int x=0; x<ProximityUtilities.NUMBER_OF_COLUMNS; x++){
			for(int y=0; y<ProximityUtilities.NUMBER_OF_ROWS; y++){
				Integer[] tempArray= new Integer[2];
				tempTile=board.getTile(x,y);
				tempArray[0]=x;
				tempArray[1]=y;
				//aksiologhsh efoson einai keno
				if(tempTile.getPlayerId()==0){
					evaluation=getEvaluation(board, randomNumber, tempTile);
					arrayCoord.add(tempArray);
					arrayEval.add(evaluation);
				}
			}
		}
		Double maxEval=-1.0;//arxikopoihsh me mia adynath timh 
		int pos=0;
		for(int i=0; i<arrayEval.size(); i++){//ebresh max evaluation
			if(arrayEval.get(i)>maxEval){
				maxEval=arrayEval.get(i);
				pos=i;//kataxwrhsh thesis max
			}
		}
		nextMove[0]=arrayCoord.get(pos)[0];
		nextMove[1]=arrayCoord.get(pos)[1];
		nextMove[2]=randomNumber;
		return nextMove;
		
	}
	
	double getEvaluation(Board board, int randomNumber, Tile tile){
		Tile[] neighArray= new Tile[6];  
		neighArray = ProximityUtilities.getNeighbors(tile.getX(), tile.getY(), board);//oi 6 geitones		
		double e1=0;
		double e2=0;
		double e3=0;
		double e4=0;
		//1o kritirio
		int numOfFriendly=0;
		for(int i=0; i<6; i++){
			if(neighArray[i]!=null){ //elegxos gia ektos oriou geitona
				if(neighArray[i].getPlayerId()==this.id){//elegxos tautotitas geitona
					numOfFriendly++;
				}
			}
		}
		if (randomNumber<15){
			if(numOfFriendly==6){
				return 100;
			}
			e1=numOfFriendly*10;
		}else{
			e1=numOfFriendly*6;
		}
		
		//2o kritirio
		int sumOfFriendly=0;
		for(int i=0; i<6; i++){	//elegxos gia kathe geitona
			if(neighArray[i]!=null){
				if(neighArray[i].getPlayerId()==this.id){ //elegxos gia diko moy plakidio
					sumOfFriendly+= neighArray[i].getScore();
				}
			}
		}
		if (randomNumber<8){
			e2=sumOfFriendly*15/111;
		}else{
			e2=sumOfFriendly*10/111;
		}
		
		//3o kritirio
		int sumOfStolen=0;
		for(int i=0; i<6; i++){
			if(neighArray[i]!=null){	//elegxos gia tile entos oriwn
				//plakidio geitona+na mporw na to klepsw
				if((neighArray[i].getPlayerId()!=this.id)&&(neighArray[i].getPlayerId()!=0)&&(randomNumber>neighArray[i].getScore())){
					sumOfStolen+= neighArray[i].getScore();
				}
			}
		}
		if (randomNumber<8){
			e3=sumOfStolen*20/33;
		}else if(randomNumber<15){
			e3=sumOfStolen*25/75;
		}else{
			e3=sumOfStolen*39/111;
		}
		//4o kritirio
		
		for(int i=0; i<6; i++){
			if(neighArray[i]!=null){
				
				//plakidio geitona+na mporw na to klepsw
				if((neighArray[i].getPlayerId()!=this.id)&&(neighArray[i].getPlayerId()!=0)&&(randomNumber>neighArray[i].getScore())){
					
					//eyresh arithmoy kenwn geitonikwn toy kathe geitona
					int numOfEmpty=0;
					int numOfNeigh=0;
					Tile[] secondNeighArray= new Tile[6]; //pinakas geitonikwn tou geitona
					
					secondNeighArray = ProximityUtilities.getNeighbors(neighArray[i].getX(),neighArray[i].getY(),board);
					for(int j=0; j<6; j++){
						//elegxos gia tile entos oriwn
						if(secondNeighArray[j]!=null){  
							if(secondNeighArray[j].getPlayerId()==0){
								numOfEmpty++;
							}
						}
					}
					numOfEmpty--;//den lambano to ypopsifio plakidio san keno
					numOfNeigh=6-numOfEmpty;//eyresh desmeymenwn geitonwn 
					e4+=numOfNeigh*15/6;
				}
			}
		}
		return(e1+e2+e3+e4);
	}
}
