package edu.ncku.eddy.game.component;

public class Field {
	
	//�Ҧ���l	
	private Block[][] blocks;
	
	//��l�ƹC����
	public Field() {
		
	}
		
	public Block[][] getblocks(){
		return blocks;
	}
	
	public void lockPiece(Piece piece){
		//TODO:�NPiece����blocks�g�J��Field
	}
	
	public void checkLineClear(){
		
	}

	public void reset() {
		// TODO Auto-generated method stub
		
	}
}
