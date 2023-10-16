package maze.routing;
import maze.Maze;
import maze.Tile;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * Class provides the core logic for solving the given Maze.
 * @author Zlatomira Dimcheva
 */

public class RouteFinder implements Serializable{


    private Maze maze;
    private Stack<Tile> route;
    private boolean finished;
    private List<Tile> allWay; 

    /**
     * Creates a RouteFinder.
     * @param mazeIn the maze we will solve.
     */
    public RouteFinder(Maze mazeIn){
        maze = mazeIn;
        route = new Stack<Tile>();
        allWay = new ArrayList<Tile>();
        finished = false;
    }

   /**
    * Makes exactly one step through the maze and updates the Stack
    * @return Returns boolean indicating if the maze is complete.
    * @throws NoRouteFoundException indicates problem with finding valid path (none found).
    */
    public boolean step() throws NoRouteFoundException{

        if(route.size() == 0){
            route.push(maze.getEntrance());
            allWay.add(maze.getEntrance());
            return false;
        }  

        if(isFinished()){
            return true;            
        } 

        int falseRoute = route.size();  
      
        Tile tile;

        tile = route.peek();
        Tile north = maze.getAdjacentTile(tile, Maze.Direction.NORTH);
        Tile south = maze.getAdjacentTile(tile, Maze.Direction.SOUTH);
        Tile east = maze.getAdjacentTile(tile, Maze.Direction.EAST);
        Tile west = maze.getAdjacentTile(tile, Maze.Direction.WEST);

        if(east != null && east.isNavigable() && !(allWay.indexOf(east) != -1)){
            allWay.add(east);
            route.push(east);
            finished = (maze.getExit() == east);
        }
        else if(south != null && south.isNavigable() && !(allWay.indexOf(south) != -1)){
            allWay.add(south);
            route.push(south);
            finished = (maze.getExit() == south);
        } 
        else if(north != null && north.isNavigable() && !(allWay.indexOf(north) != -1)){
            allWay.add(north);
            route.push(north);
            finished = (maze.getExit() == north);
        }
        else if(west != null && west.isNavigable() && !(allWay.indexOf(west) != -1)){
            allWay.add(west);
            route.push(west);
            finished = (maze.getExit() == west);
        } 
        else{
            route.pop(); 
        }   

        if(0 == route.size()){
            throw new NoRouteFoundException();
        }    


        return false;
    }

   /**
    * Returns the maze.
    * @return Returns the maze object.
    */
    public Maze getMaze(){
        return maze;
    }

   /**
    * Returns true once the stack contains the complete path from entrance to exit.
    * @return Returns true if maze is solved, false otherwise.
    */
    public boolean isFinished(){  
        return finished;
    }

   /**
    * Visualises the entire maze and route-solving state. 
    * @return Returns String representation of the current state of the maze.
    */
    public String toString(){
        String result = "";

        for(List<Tile> tileLine : maze.getTiles()){
            for(Tile tile: tileLine){
                if(allWay.indexOf(tile) != -1){                                                           
                    result += ((route.contains(tile)) ? "*" : "-");
                }
                else{
                    result += tile.toString();
                }
            }
            result = result + "\n";
        }

        return result;
    }

   /**
    * Saves the current route-solving state to a file.
    * @param fileSave the name of the file we are saving to.
    * @throws java.io.IOException indicates problem with fileSave.
    */
    public void save(String fileSave) throws IOException{

        FileOutputStream fileOut = new FileOutputStream(fileSave);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);

        out.writeObject(this);
        out.close();
        fileOut.close();
    }

   /**
    * Loads route-solving state from a file.
    * @param fileSave the name of the file we are reading from.
    * @throws java.io.IOException indicates problem with fileSave.
    * @throws ClassNotFoundException indicates problem with fileSave.
    */
    public static RouteFinder load(String fileSave) throws IOException, ClassNotFoundException{

        RouteFinder maze = null;

        FileInputStream fileIn = new FileInputStream(fileSave);
        ObjectInputStream in = new ObjectInputStream(fileIn);

        maze = (RouteFinder) in.readObject();
        in.close();
        fileIn.close();
        return maze;    
    }

   /**
    * Returns the route from start to end as it is currently.
    * @return Returns List of Tiles representing the current route.
    */
    public List<Tile> getRoute(){

        List<Tile> tilesList = new ArrayList<Tile>();

        for(Tile tile : route){
            tilesList.add(tile);
        }
       
        return tilesList;
    }
}
