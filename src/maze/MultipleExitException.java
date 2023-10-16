package maze;

/**
 * Class represents an MultipleExitException.
 * @author Zlatomira Dimcheva
 */

public class MultipleExitException extends InvalidMazeException{

    /**
     * Constructs a new exception with "Maze with multiple exits. Invalid!" as its detail message.
     */
    public MultipleExitException(){
        super("Maze with multiple exits. Invalid!");
    }

}
