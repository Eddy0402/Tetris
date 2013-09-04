package edu.ncku.eddy.game.component.mino;

import edu.ncku.eddy.Launcher;
import edu.ncku.eddy.game.component.Block;
import edu.ncku.eddy.game.component.Piece;
import edu.ncku.eddy.game.component.Block.BlockType;

public class J extends Piece {

	public J(int positionX, int positionY) {
		super(positionX, positionY);
		
	}

	@Override
	public BlockMovingPosition[] getBlocks() {
		BlockMovingPosition blockMovingPosition1 = new BlockMovingPosition(positionLine,positionCol);
		BlockMovingPosition blockMovingPosition2 = new BlockMovingPosition(positionLine,positionCol - 1);
		BlockMovingPosition blockMovingPosition3 = new BlockMovingPosition(positionLine,positionCol + 1);
		BlockMovingPosition blockMovingPosition4 = new BlockMovingPosition(positionLine + 1,positionCol + 1);
		
		BlockMovingPosition[] blockList = new BlockMovingPosition[]{blockMovingPosition1,blockMovingPosition2,blockMovingPosition3,blockMovingPosition4};
		
		return blockList;
	}


	@Override
	public boolean rotatePiece(RotationMethod rotationMethod) {
		return false;
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isTouchingButtom() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean moveLeft() {
		return false;
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean moveRight() {
		return false;
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean canMoveLeft() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean canMoveRight() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean moveDown() {
		switch (this.rotationState) {
		case Default:
			Block testPoint01 = Launcher.gameEngine.GetField().getBlock(positionLine - 1,positionCol);
			Block testPoint02 = Launcher.gameEngine.GetField().getBlock(positionLine - 1,positionCol + 1);
			Block testPoint03 = Launcher.gameEngine.GetField().getBlock(positionLine - 1,positionCol - 1);
			if (testPoint01 == null || testPoint02 == null || testPoint03 == null)return false;
			if (testPoint01.getBlockType() == BlockType.None
					&& testPoint02.getBlockType() == BlockType.None
					&& testPoint03.getBlockType() == BlockType.None) {
				this.positionLine--;
				return true;
			}else {
				return false;
			}
		case Left:
			//TODO
			return false;
		case Right:
			//TODO
			return false;
		case UpsideDown:
			//TODO
			return false;
		default:
			return false;
		}		
	}
	
	@Override
	public Type getType() {
		return Type.J;
	}


}
