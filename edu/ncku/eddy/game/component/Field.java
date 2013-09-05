package edu.ncku.eddy.game.component;

import edu.ncku.eddy.game.component.Block.BlockType;
import edu.ncku.eddy.game.component.Piece.BlockMovingPosition;

public class Field {

	/**
	 * 30*10的遊戲區，其中因為出局判定為中央位置是否被阻擋，實際會用到的大概24格
	 * 座標規劃請參考轉位圖
	 */
	private Block[][] blocks = new Block[30][10];


	public Field() {
		// 初始化遊戲區
		reset();
	}

	/**
	 * 確認可否消行，若可以則消行
	 */
	public int checkLineClear() {		
		int lineClearCount = 0;
		for (int line = 0; line < 30; line++) {
			
			//是否完整行
			boolean canClear = true;
			for (int col = 0; col < 10; col++) {
				if( blocks[line][col].getBlockType() == BlockType.None)canClear=false;				
			}		
			
			if(canClear){
				//假如是，讓上面的填下來
				for(int dropLine = line + 1;dropLine<30;dropLine++){
					for(int dropCol = 0;dropCol < 10;dropCol++){
						blocks[dropLine- 1][dropCol] = blocks[dropLine][dropCol];						
					}
				}
				//在最上行創一排新的方塊				
				for(int newCol=0;newCol<10;newCol++){
					blocks[29][newCol] = new Block(BlockType.None);
				}		
				
				lineClearCount++;
				line--;//使下落的那行能被判斷
			}			
		}
		return lineClearCount;
	}
	
	/**
	 * 由座標固定單一方塊
	 */
	public void setBlock(Block block, int x, int y) {
		this.blocks[x][y] = block;
	}
	
	/**
	 * 傳入x,y座標取得該位置方塊
	 */
	public Block getBlock(int x, int y) {
		if (x > 29 || x < 0 || y > 9 || y < 0) {
			return null;
		} else {
			return this.blocks[x][y];
		}
	}
	
	/**
	 * 傳入BlockMovingPosition取得該位置方塊
	 */
	public Block getBlock(BlockMovingPosition position){
		return getBlock(position.line, position.col);
	}
	
	public Block[][] getblocks() {
		return blocks;
	}

	/**
	 * 初始化遊戲區
	 */
	public void reset() {
		for (int line = 0; line < 30; line++) {
			for (int col = 0; col < 10; col++) {
				blocks[line][col] = new Block(BlockType.None);
			}
		}
	}
}
