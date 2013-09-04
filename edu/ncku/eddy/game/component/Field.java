package edu.ncku.eddy.game.component;

import edu.ncku.eddy.game.component.Block.BlockType;

public class Field {
	
	//所有格子	
	private Block[][] blocks = new Block[20][10];
	
	//初始化遊戲區
	public Field() {
		reset();
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
		for (int line = 0;line <20 ; line++){
			for(int col=0; col<10 ;col++)
			{
				blocks[line][col]=new Block(BlockType.None);
			}
		}
	}
}
