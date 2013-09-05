package edu.ncku.eddy.game.component.mino;

import edu.ncku.eddy.game.component.Piece;

public class O extends Piece {

	public O(int positionX, int positionY) {
		super(positionX, positionY);
	}


	@Override
	public boolean rotatePiece(RotationMethod rotationMethod) {
		return false;
		// TODO Auto-generated method stub
		
	}


	
	@Override
	public Type getType() {
		return Type.O;
	}

	@Override
	public BlockMovingPosition[] getBlocks(int positionLine, int positionCol, RotationState rotationState) {
		// TODO Auto-generated method stub
		return null;
	}


}
