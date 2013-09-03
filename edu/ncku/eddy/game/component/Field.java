package edu.ncku.eddy.game.component;

public class Field {
	
	//所有格子	
	private Block[][] blocks;
	
	//初始化遊戲區
	public Field() {
		
	}
		
	public Block[][] getblocks(){
		return blocks;
	}
	
	public void lockPiece(Piece piece){
		//TODO:將Piece內的blocks寫入此Field
	}
	
	public void checkLineClear(){
		
	}

	public void reset() {
		// TODO Auto-generated method stub
		
	}
}
