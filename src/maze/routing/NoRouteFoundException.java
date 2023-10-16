package maze.routing;

/**
 * Class represents an NoRouteFoundException.
 * @author Zlatomira Dimcheva
 */

public class NoRouteFoundException extends RuntimeException{

    /**
     * Constructs a new exception with "No route found. Invalid route!" as its detail message.
     */
    public NoRouteFoundException(){
        super("No route found. Invalid route!");
    }

}
