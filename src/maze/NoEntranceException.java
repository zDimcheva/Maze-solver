package maze;

/**
 * Class represents an NoEntranceException.
 * @author Zlatomira Dimcheva
 */

public class NoEntranceException extends InvalidMazeException{

    /**
     * Constructs a new exception with "Maze with no entrance. Invalid!" as its detail message.
     */
    public NoEntranceException(){
        super("Maze with no entrance. Invalid!");
    }

}
