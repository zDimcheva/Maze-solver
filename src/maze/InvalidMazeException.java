package maze;

/**
 * Class represents an InvalidMazeException.
 * @author Zlatomira Dimcheva
 */

public class InvalidMazeException extends RuntimeException{

    /**
     * Constructs a new exception with the specified detail message.
     * @param message the detail message.
     */
    public InvalidMazeException(String message){
        super(message);
    }

    /**
     * Constructs a new exception with "Maze with wrong character. Invalid!" as its detail message.
     */
    public InvalidMazeException(){
        super("Maze with wrong character. Invalid!");
    }

}
