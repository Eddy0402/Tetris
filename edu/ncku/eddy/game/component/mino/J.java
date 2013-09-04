package edu.ncku.eddy.game.component.mino;

import java.util.ArrayList;

import edu.ncku.eddy.game.component.Piece;

public class J extends Piece {

	public J(int positionX, int positionY) {
		super(positionX, positionY);
		
	}

	@Override
	public ArrayList<BlockMovingPosition> getBlocks() {
		BlockMovingPosition blockMovingPosition1 = new BlockMovingPosition(positionLine,positionCol);
		BlockMovingPosition blockMovingPosition2 = new BlockMovingPosition(positionLine,positionCol - 1);
		BlockMovingPosition blockMovingPosition3 = new BlockMovingPosition(positionLine,positionCol + 1);
		BlockMovingPosition blockMovingPosition4 = new BlockMovingPosition(positionLine + 1,positionCol + 1);
		
		ArrayList<BlockMovingPosition> blockList = new ArrayList<>();
		
		blockList.add(blockMovingPosition1);
		blockList.add(blockMovingPosition2);
		blockList.add(blockMovingPosition3);
		blockList.add(blockMovingPosition4);
		
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
		return false;
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Type getType() {
		return Type.J;
	}


}
