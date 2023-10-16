import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import java.io.FileInputStream;
import java.io.IOException;
import maze.Maze;
import maze.Tile;
import java.io.File;
import java.io.FileNotFoundException;
import maze.routing.RouteFinder;
import maze.InvalidMazeException;
import maze.routing.NoRouteFoundException;

/**
 * Class that allows users to interact with the application and displays the menu and maze to the user.
 * @author Zlatomira Dimcheva
 */

public class MazeApplication extends Application {

    private static RouteFinder finder = null;
    private static VBox mazeBox = new VBox(1);
    private static boolean canStep = false;
    private static Label label = new Label("");
    private static FileChooser fileChooser;

    /**
     * Main entry point for the application. Visualizes the application features.
     * @param stage the primary stage for the application, where the scene can be set.
     * @Override 
     */
    @Override
    public void start(Stage stage){

        label.setTextFill(Color.RED);

        Button bt = new Button("Load Map");
        Button bt2 = new Button("Load Route");
        Button bt3 = new Button("Save Route");
        Button bt4 = new Button("Step");

        VBox root = new VBox(25);
        root.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        HBox placeHolder = new HBox(0);
        placeHolder.setAlignment(Pos.CENTER);
        mazeBox.setAlignment(Pos.CENTER);

        VBox firstButtonBox = new VBox(0);
        firstButtonBox.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        firstButtonBox.setAlignment(Pos.CENTER);

        HBox secondButtonBox = new HBox(0);
        secondButtonBox.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        secondButtonBox.setAlignment(Pos.CENTER);

        firstButtonBox.getChildren().addAll(bt,bt2);
        placeHolder.getChildren().addAll(mazeBox);
        root.getChildren().addAll(firstButtonBox, placeHolder, secondButtonBox, label);
        root.setAlignment(Pos.CENTER);

        EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() {
            
            public void handle(MouseEvent e){
                boolean throwedException = true;

                try{
                    placeHolder.getChildren().remove(mazeBox); 
                    fileChooser = new FileChooser();  
                    fileChooser.setTitle("Open Resource File");
                    fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt")); 
  
                    File file = fileChooser.showOpenDialog(stage);
                    String name = file.getPath();
                    Maze maze = Maze.fromTxt(name);
                    mazeBox = createMazeBox(maze.toString());
                    placeHolder.getChildren().addAll(mazeBox);
                    finder = new RouteFinder(maze);

                    if(!canStep){
                        firstButtonBox.getChildren().addAll(bt3);
                        secondButtonBox.getChildren().addAll(bt4);
                        canStep = true;
                    }

                    label.setText("");
                    throwedException = false;
                }     
                catch(Exception ex){
                    String message = ex.getMessage();                   
                    label.setText(((ex instanceof InvalidMazeException || 
                                        ex instanceof NoRouteFoundException) ? 
                                            message : "") + " Load valid file.");        
                }  
                finally{
                    if(canStep && throwedException){
                        firstButtonBox.getChildren().remove(bt3);
                        secondButtonBox.getChildren().remove(bt4);
                        canStep = false;
                    }
                }
            }
        };

        bt.addEventFilter(MouseEvent.MOUSE_CLICKED, event);

        EventHandler<MouseEvent> event1 = new EventHandler<MouseEvent>() {
            
            public void handle(MouseEvent e){
    
                boolean throwedException = true;
    
                try{
                    placeHolder.getChildren().remove(mazeBox);   
                    fileChooser = new FileChooser();
                    fileChooser.setTitle("Open Resource File"); 
         
                    File file = fileChooser.showOpenDialog(stage);
                    String name = file.getPath();            
                    finder = RouteFinder.load(name); 
                    mazeBox = createMazeBox(finder.toString());
                    placeHolder.getChildren().addAll(mazeBox); 

                    if(!canStep){
                        firstButtonBox.getChildren().addAll(bt3);
                        secondButtonBox.getChildren().addAll(bt4);
                        canStep = true;
                    } 

                    label.setText(""); 
                    throwedException = false;   
                }
                catch(Exception ex){
                    String message = ex.getMessage();                   
                    label.setText(((ex instanceof InvalidMazeException || 
                                        ex instanceof NoRouteFoundException) ? 
                                            message : "") + " Load valid file.");         
                } 
                finally{
                    if(canStep && throwedException){
                        firstButtonBox.getChildren().remove(bt3);
                        secondButtonBox.getChildren().remove(bt4);
                        canStep = false;
                    }
                }
            }
        };

        bt2.addEventFilter(MouseEvent.MOUSE_CLICKED, event1);

        EventHandler<MouseEvent> event2 = new EventHandler<MouseEvent>() {
            
            public void handle(MouseEvent e){
                try{
                    fileChooser = new FileChooser();
                    fileChooser.setTitle("Save File");

                    File file = fileChooser.showSaveDialog(stage);
                    String name = file.getPath();
                    finder.save(name);
                }
                catch(Exception ex){
                    String message = ex.getMessage();                   
                    label.setText((message == null) ? "" : message);         
                }
            }
        };

        bt3.addEventFilter(MouseEvent.MOUSE_CLICKED, event2);

        EventHandler<MouseEvent> event3 = new EventHandler<MouseEvent>() {
            
            public void handle(MouseEvent e){
                try{
                    finder.step();
                    placeHolder.getChildren().remove(mazeBox);
                    mazeBox = createMazeBox(finder.toString());
                    placeHolder.getChildren().addAll(mazeBox);
                }
                catch(Exception ex){
                    String message = ex.getMessage();                   
                    label.setText((message == null) ? "" : message);        
                }
            }
        };

        bt4.addEventFilter(MouseEvent.MOUSE_CLICKED, event3);

        Scene scene = new Scene(root, 450, 500);
        stage.setScene(scene);
        stage.setTitle("Maze");
        stage.show();      
        
    }

   /**
    * Creates VBox contating the visualized maze in its current state.
    * @return Returns VBox containing the visual representations of the maze.
    * @param mazeAsString the maze we will visualize.
    */
    public static VBox createMazeBox(String mazeAsString){

        try{
            VBox mazeBox = new VBox(1);
            mazeBox.setAlignment(Pos.CENTER);

            Image imageWall = new Image(new FileInputStream("src/maze/visualization/1.png"));
            Image imageExit = new Image(new FileInputStream("src/maze/visualization/x.jpg"));
            Image imageRightPath = new Image(new FileInputStream("src/maze/visualization/star.png"));
            Image imageWrongPath = new Image(new FileInputStream("src/maze/visualization/minus.png"));
            Image imagePath = new Image(new FileInputStream("src/maze/visualization/white.png"));
            Image imageEntrance = new Image(new FileInputStream("src/maze/visualization/e.jpg"));

            char[] mazeAsArray = mazeAsString.toCharArray(); 
            HBox rowBox = new HBox(1);       

            for(char tile : mazeAsArray){
                switch(tile){
                    case '\n':  
                        mazeBox.getChildren().addAll(rowBox);             
                        rowBox = new HBox(1); 
                        break;
                    case '#':
                        rowBox.getChildren().addAll(new ImageView(imageWall));
                        break;
                    case '.':
                        rowBox.getChildren().addAll(new ImageView(imagePath));
                        break;
                    case '*':
                        rowBox.getChildren().addAll(new ImageView(imageRightPath));
                        break;
                    case '-':
                        rowBox.getChildren().addAll(new ImageView(imageWrongPath));
                        break; 
                    case 'e':
                        rowBox.getChildren().addAll(new ImageView(imageEntrance));
                        break;
                    case 'x':
                        rowBox.getChildren().addAll(new ImageView(imageExit));
                        break;               
                }
            }

            mazeBox.getChildren().addAll(rowBox);
            return mazeBox;
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
        return null;
    }

   /**
    * This is the main method which starts the application.
    * @return Nothing.
    * @param args Unused.
    */
    public static void main (String args[]){
       launch(args);
    }
}
