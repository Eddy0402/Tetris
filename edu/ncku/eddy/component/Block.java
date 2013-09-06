package edu.ncku.eddy.component;

public class Block {
	
	private BlockType blockType;
	
	public Block(BlockType blockType){
		this.blockType = blockType;
	}	
	
	public BlockType getBlockType(){
		return this.blockType;		
	}
		
	public enum BlockType{
		I, O, S, Z, L, J ,T , None , garbage
	}
	


}
