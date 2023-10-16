import maze.Maze;
import maze.Maze.Coordinate;
import maze.routing.RouteFinder;

public class MazeDriver {
    public static void main(String args[]) {
        Maze m = Maze.fromTxt("resources/mazes/maze2.txt");
        System.out.println(m);
        Maze.Coordinate c = new Maze.Coordinate(0,5);
        System.out.println(m.getTileAtLocation(c)); 

        RouteFinder finder = new RouteFinder(m);
        for(int i = 0; i < 70; i++){
            finder.step();
            System.out.println(finder);
        }    

    }
}
