package maze;
import java.io.Serializable;

/**
 * Class represents a tile.
 * @author Zlatomira Dimcheva
 */

public class Tile implements Serializable{

    private Type type;

    /**
     * Creates a Tile.
     * @param typeIn the type of the Tile.
     */
    private Tile(Type typeIn){
        type = typeIn;
    }

    /**
     * Enum representing types we can use .
     * @link CORRIDOR
     * @link ENTRANCE
     * @link EXIT
     * @link WALL
     */
    public enum Type {
        CORRIDOR,ENTRANCE,EXIT,WALL;
    }

   /**
    * Creates a new Tile from its text representation (supplied as a char). 
    * @return Returns a Tile represented by the char.
    * @param charIn the char representing the type.
    * @throws InvalidMazeException indicates problem with charIn. 
    */
    protected static Tile fromChar(char charIn) throws InvalidMazeException{
        switch(charIn){
            case '.':
                return new Tile(Type.CORRIDOR);
            case 'e':
                return new Tile(Type.ENTRANCE);
            case 'x':
                return new Tile(Type.EXIT);
            case '#':
                return new Tile(Type.WALL);
            default:
                throw new InvalidMazeException();                
        }        
    }
 
   /**
    * Returns the type.
    * @return Returns the Type of the Tile.
    */
    public Type getType(){
        return type;
    }

   /**
    * Checks if Tile is navigable.
    * @return Returns false if Type is WALL, true otherwise.
    */
    public boolean isNavigable(){
        
        if(type == Type.WALL){
            return false;
        }

        return true;
    }

   /**
    * Returns String representation used in text files.
    * @return Returns String representation of a Tile.
    */
    public String toString(){
        switch(type){
            case CORRIDOR:
                return ".";
            case ENTRANCE:
                return "e";
            case EXIT:
                return "x";
            case WALL:
                return "#";
        }

        return null;
    }

}
