package maze;
 
/**
 * Class represents an RaggedMazeException.
 * @author Zlatomira Dimcheva
 */

public class RaggedMazeException extends InvalidMazeException{

    /**
     * Constructs a new exception with "Maze with different rows of tiles in length. Invalid!" as its detail message.
     */
    public RaggedMazeException(){
        super("Maze with different rows of tiles in length. Invalid!");
    }

}
