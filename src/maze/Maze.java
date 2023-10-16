package maze;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import maze.Tile;
import java.io.Serializable;

/**
 * Class represents a maze.
 * @author Zlatomira Dimcheva
 */

public class Maze implements Serializable{

    /**
     * Creates an empty maze.
     */
    private Maze(){
    }

    /**
     * Enum representing directions we can use 
     * @link NORTH
     * @link SOUTH
     * @link EAST
     * @link WEST
     */
    public enum Direction {
        NORTH,SOUTH,EAST,WEST;
    }

    /**
     * Class representing coordinates of tiles.
     */
    public static class Coordinate{

        private int x;
        private int y;

        /**
         * Coordinate constructor.
         * @param xIn the x coordinate.
         * @param yIn the y coordinate.
         */
        public Coordinate(int xIn, int yIn){
            x = xIn;
            y = yIn;
        }

        /**
         * Gets the column number. (x coordinate)
         * @return Returns an integer representation of x coordinate.
         */
        public int getX(){
            return x;
        }

        /**
         * Gets the row number. (y coordinate)
         * @return Returns an integer representation of y coordinate.
         */
        public int getY(){
            return y;
        }

        /**
         * Returns String "(x, y)" representing the coordinates
         * @return Returns String representation of the coordinates.
         */
        public String toString(){
            return "(" + x + ", " + y + ")";
        }
    }

    private Tile entrance;
    private Tile exit;
    private List<List<Tile>> tiles;
    
   /**
    * Returns Maze object created by reading from a text file.
    * @return Returns Maze object read from .txt file.
    * @param inputFileMaze the full path of the file we are reading the Maze from.
    * @throws InvalidMazeException indicates problem with the representation of maze in inputFileMaze (constraints not met). 
    * @throws java.io.FileNotFoundException indicates problem with missing file.
    */
    public static Maze fromTxt(String inputFileMaze) throws InvalidMazeException, FileNotFoundException{

        Maze maze = new Maze();

        File fileToRead = new File(inputFileMaze);
        Scanner scanner = new Scanner(fileToRead);

        int lineLength = -1;
        
        maze.tiles = new ArrayList<List<Tile>>();

        while(scanner.hasNextLine()){
        
            String line = scanner.nextLine();
            
            if(lineLength == -1){
                lineLength = line.length();
            }
            if(lineLength != line.length()){
                throw new RaggedMazeException();
            }

            char[] chars = line.toCharArray();
            List<Tile> tilesRow = new ArrayList<Tile>();
            Tile tileType = null;

            for(int i = 0; i < chars.length; i++){
                tileType = Tile.fromChar(chars[i]);
                tilesRow.add(tileType); 

                if(chars[i] == 'e'){
                    maze.entrance = tileType;                          
                } 
                if(chars[i] == 'x'){
                    maze.exit = tileType;  
                }                  
            }

            maze.tiles.add(tilesRow);              
        }

        
        if(maze.entrance == null){
            throw new NoEntranceException();
        }
        if(maze.exit == null){
            throw new NoExitException();
        }

        return maze;
    }

   /**
    * Returns a String representation of a Maze from text file and returns Maze object.
    * @return Returns Maze object read from .txt file.
    * @param inputFileMaze the text file we are reading the Maze from.
    */
    public String toString(){

        String result = "";
        for(List<Tile> tileLine : tiles){
            for(Tile tile: tileLine){
                result += tile.toString() + getTileLocation(tile);
            }
            result = result + "\n";
        }

        return result;
        
    }

   /**
    * Returns Coordinate of a Tile given as parameter.
    * @return Returns coordinates of a tile passed as parameter.
    * @param tile the tile whose coordinates we want to find.
    */
    public Coordinate getTileLocation(Tile tile){
        
        int y = tiles.toArray().length - 1;
        int x = 0;
        Coordinate coordinate = null;

        for(List<Tile> tileLine : tiles){
            for(Tile tilePiece: tileLine){
                if(tilePiece == tile){
                    coordinate = new Coordinate(x,y);
                }
                x++;
            }
            y--;
            x = 0;
        }

        return coordinate;        
    }

   /**
    * Returns the entrance.
    * @return Returns the entrance Tile of the maze.
    */
    public Tile getEntrance(){
        return entrance;
    }

   /**
    * Returns the exit.
    * @return Returns the exit Tile of the maze.
    */
    public Tile getExit(){
        return exit;
    } 

   /**
    * Returns all tiles from the maze as a two-dimensional ArrayList.
    * @return Returns two-dimensional ArrayList representation of the tiles.
    */
    public List<List<Tile>> getTiles(){
        return tiles;
    } 

   /**
    * Sets entrance to a tile present in the maze.
    * @param exitIn the exit we wantto set.
    */
    private void setEntrance(Tile entranceIn){
        if(entranceIn == entrance){
            return;
        }
        if(entrance == null){
            for(List<Tile> tileLine : tiles){
                for(Tile tilePiece: tileLine){
                    if(tilePiece == entranceIn){                        
                        entrance = entranceIn;
                        return;
                    }
                }
            }
        }
        else{
            throw new MultipleEntranceException();
        }
    }

   /**
    * Sets exit to a tile present in the maze.
    * @param exitIn the exit we wantto set.
    */
    private void setExit(Tile exitIn){
        if(exitIn == exit){
            return;
        }
        if(exit == null){
            for(List<Tile> tileLine : tiles){
                for(Tile tilePiece: tileLine){
                    if(tilePiece == exitIn){                        
                        exit = exitIn;
                        return;
                    }
                }
            }
        }
        else{
            throw new MultipleExitException();
        }
    }

   /**
    * Returns the Tile on specified Coordinate.
    * @return Returns a Tile representation on the specified coordinates.
    * @param coordinate the coordinates on which we look for a tile,
    */
    public Tile getTileAtLocation(Coordinate coordinate){

        try{
            return tiles.get(tiles.toArray().length - 1 - coordinate.getY()).get(coordinate.getX());
        }
        catch(IndexOutOfBoundsException e){
            return null;
        }
    }

   /**
    * Returns the tile adjacent to the passed tile in the direction specified. 
    * @return Returns the adjacent tile.
    * @param tileIn tile from which we start the search for adjacent tile.
    * @param direction the direction in which we are looking for adjacent tile.
    */
    public Tile getAdjacentTile(Tile tileIn, Direction direction){
        
        Coordinate last = getTileLocation(tileIn);

        if(last == null){
            return null;
        }

        switch(direction){
            case NORTH:
                return getTileAtLocation(new Coordinate(last.getX(), last.getY() + 1));
            case SOUTH:
                return getTileAtLocation(new Coordinate(last.getX(), last.getY() - 1));
            case EAST:
                return getTileAtLocation(new Coordinate(last.getX() + 1, last.getY()));
            case WEST:
                return getTileAtLocation(new Coordinate(last.getX() - 1, last.getY()));
        }

        return null;
    }

}

