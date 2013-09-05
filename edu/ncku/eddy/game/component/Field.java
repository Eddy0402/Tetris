package edu.ncku.eddy.game.component;

import edu.ncku.eddy.game.component.Block.BlockType;
import edu.ncku.eddy.game.component.Piece.BlockMovingPosition;

public class Field {

	/**
	 * 30*10���C���ϡA�䤤�]���X���P�w��������m�O�_�Q���סA��ڷ|�Ψ쪺�j��24��
	 * �y�гW���аѦ�����
	 */
	private Block[][] blocks = new Block[30][10];


	public Field() {
		// ��l�ƹC����
		reset();
	}

	/**
	 * �T�{�i�_����A�Y�i�H�h����
	 */
	public int checkLineClear() {		
		int lineClearCount = 0;
		for (int line = 0; line < 30; line++) {
			
			//�O�_�����
			boolean canClear = true;
			for (int col = 0; col < 10; col++) {
				if( blocks[line][col].getBlockType() == BlockType.None)canClear=false;				
			}		
			
			if(canClear){
				//���p�O�A���W������U��
				for(int dropLine = line + 1;dropLine<30;dropLine++){
					for(int dropCol = 0;dropCol < 10;dropCol++){
						blocks[dropLine- 1][dropCol] = blocks[dropLine][dropCol];						
					}
				}
				//�b�̤W��Ф@�Ʒs�����				
				for(int newCol=0;newCol<10;newCol++){
					blocks[29][newCol] = new Block(BlockType.None);
				}		
				
				lineClearCount++;
				line--;//�ϤU���������Q�P�_
			}			
		}
		return lineClearCount;
	}
	
	/**
	 * �Ѯy�ЩT�w��@���
	 */
	public void setBlock(Block block, int x, int y) {
		this.blocks[x][y] = block;
	}
	
	/**
	 * �ǤJx,y�y�Ш��o�Ӧ�m���
	 */
	public Block getBlock(int x, int y) {
		if (x > 29 || x < 0 || y > 9 || y < 0) {
			return null;
		} else {
			return this.blocks[x][y];
		}
	}
	
	/**
	 * �ǤJBlockMovingPosition���o�Ӧ�m���
	 */
	public Block getBlock(BlockMovingPosition position){
		return getBlock(position.line, position.col);
	}
	
	public Block[][] getblocks() {
		return blocks;
	}

	/**
	 * ��l�ƹC����
	 */
	public void reset() {
		for (int line = 0; line < 30; line++) {
			for (int col = 0; col < 10; col++) {
				blocks[line][col] = new Block(BlockType.None);
			}
		}
	}
}
