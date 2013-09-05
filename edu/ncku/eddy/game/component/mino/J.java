package edu.ncku.eddy.game.component.mino;

import edu.ncku.eddy.game.component.Piece;

public class J extends Piece {

	public J(int positionX, int positionY) {
		super(positionX, positionY);
		
	}

	
	public BlockMovingPosition[] getBlocks(int positionLine , int positionCol,RotationState rotationState) {
		
		//TODO
		
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
	public Type getType() {
		return Type.J;
	}


}
