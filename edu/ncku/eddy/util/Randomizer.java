package edu.ncku.eddy.util;

import edu.ncku.eddy.game.component.Piece;
import edu.ncku.eddy.game.component.mino.*;

public class Randomizer {
	private long seed;
	public Randomizer(long seed){
		this.seed = seed;
	}
	
	/*
    public int[] createPieceSequence(boolean[] pieceEnable, Random random, int arrayMax) {
        int[] pieceArray = new int[arrayMax];
        int pieceKind = GeneralUtil.getNumberOfPiecesCanAppear(pieceEnable);

        
        for(int i = 0; i < arrayMax / pieceKind; i++) {
                // Flags for pieces which have already appeared
                boolean[] alreadyAppeared = new boolean[Piece.PIECE_COUNT];

                // Create draws
                for(int j = 0; j < pieceKind; j++) {
                        int id = 0;

                        // Draw
                        do {
                                id = random.nextInt(Piece.PIECE_COUNT);
                        } while((pieceEnable[id] == false) || (alreadyAppeared[id] == true));

                        // Set block drawn flag to ON
                        alreadyAppeared[id] = true;

                        // Add to NEXT list
                        pieceArray[i * pieceKind + j] = id;
                }
        }

        return pieceArray;
}
*/
	
	public Piece[] getNextPieces(){
		//TODO next¥\¯à
		return null;
	}

	public Piece getNewPiece(){
		//TODO 
		return new J(4,19);
	}
}
