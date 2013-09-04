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
	
	public void checkLineClear(){
		//TODO
	}
	
	public void setBlock(Block block,int x,int y){
		this.blocks[x][y] = block;
	}
	
	public Block getBlock(int x,int y){
		return this.blocks[x][y];
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
