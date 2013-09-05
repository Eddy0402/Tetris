package edu.ncku.eddy.game.component.mino;

import edu.ncku.eddy.game.component.Piece;
import edu.ncku.eddy.game.component.Piece.BlockMovingPosition;
import edu.ncku.eddy.game.component.Piece.RotationMethod;
import edu.ncku.eddy.game.component.Piece.RotationState;

public class Z extends Piece {

	public Z(int positionX, int positionY) {
		super(positionX, positionY);
	}

	@Override
	public boolean rotatePiece(RotationMethod rotationMethod) {

		return rotatePieceJLSTZ(rotationMethod);

	}

	@Override
	public Type getType() {
		return Type.Z;
	}

	public BlockMovingPosition[] getBlocks(int positionLine, int positionCol, RotationState rotationState) {

		BlockMovingPosition blockMovingPosition1 = null, blockMovingPosition2 = null, blockMovingPosition3 = null, blockMovingPosition4 = null;

		// + 0是為了修改方便
		switch (rotationState) {
		case Default:
			blockMovingPosition1 = new BlockMovingPosition(positionLine + 0, positionCol + 0);
			blockMovingPosition2 = new BlockMovingPosition(positionLine + 1, positionCol + 0);
			blockMovingPosition3 = new BlockMovingPosition(positionLine + 1, positionCol - 1);
			blockMovingPosition4 = new BlockMovingPosition(positionLine + 0, positionCol + 1);
			break;
		case Left:
			blockMovingPosition1 = new BlockMovingPosition(positionLine + 0, positionCol + 0);
			blockMovingPosition2 = new BlockMovingPosition(positionLine + 1, positionCol + 0);
			blockMovingPosition3 = new BlockMovingPosition(positionLine + 0, positionCol - 1);
			blockMovingPosition4 = new BlockMovingPosition(positionLine - 1, positionCol - 1);
			break;
		case Right:
			blockMovingPosition1 = new BlockMovingPosition(positionLine + 0, positionCol + 0);
			blockMovingPosition2 = new BlockMovingPosition(positionLine + 1, positionCol + 1);
			blockMovingPosition3 = new BlockMovingPosition(positionLine + 0, positionCol + 1);
			blockMovingPosition4 = new BlockMovingPosition(positionLine - 1, positionCol + 0);
			break;
		case UpsideDown:
			blockMovingPosition1 = new BlockMovingPosition(positionLine + 0, positionCol + 0);
			blockMovingPosition2 = new BlockMovingPosition(positionLine + 0, positionCol - 1);
			blockMovingPosition3 = new BlockMovingPosition(positionLine - 1, positionCol + 0);
			blockMovingPosition4 = new BlockMovingPosition(positionLine - 1, positionCol + 1);
			break;
		}

		BlockMovingPosition[] blockList = new BlockMovingPosition[] {
				blockMovingPosition1, blockMovingPosition2,
				blockMovingPosition3, blockMovingPosition4 };

		return blockList;
	}

}
