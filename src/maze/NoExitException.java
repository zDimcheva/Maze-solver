package maze;

/**
 * Class represents an NoExitException.
 * @author Zlatomira Dimcheva
 */

public class NoExitException extends InvalidMazeException{

    /**
     * Constructs a new exception with "Maze with no exit. Invalid!" as its detail message.
     */
    public NoExitException(){
        super("Maze with no exit. Invalid!");
    }

}
