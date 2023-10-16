package maze;

/**
 * Class represents an MultipleEntranceException.
 * @author Zlatomira Dimcheva
 */

public class MultipleEntranceException extends InvalidMazeException{

    /**
     * Constructs a new exception with "Maze with multiple entrances. Invalid!" as its detail message.
     */
    public MultipleEntranceException(){
        super("Maze with multiple entrances. Invalid!");
    }

}
