package controller;


import java.util.HashMap;




import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
/**
 * this class represents the Controllers controller :)
 * it contains a hashmap of the different screens and has the ability to pass
 * between them.
 */
public class ScreenController extends StackPane {
	
	private HashMap<String,Node> screens = new HashMap<>();
	
	/**
	 * the function receives:
	 * @param name for node
	 * @param node for a screen
	 * and add's it to the map.
	 */
	public void addScreen(String name, Node node)
	{
		screens.put(name,node);
	}
	
	/**
	 * 
	 * @param name
	 * @return the according screen if exist's in map.
	 */
	public Node getScreen(String name){
		return screens.get(name);
	}
	/**
	 * the function receives 2 strings and load them.
	 * then sets them to the HashMap.
	 * @param name
	 * @param resource
	 * @return true if successful false else.
	 */
	public boolean loadScreen(String name, String resource)
	{
		try{
			FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
			Parent loadScreen = (Parent)myLoader.load();
			ControlledScreen myScreenControl = ((ControlledScreen)myLoader.getController());
			myScreenControl.setScreenParent(this);
			addScreen(name, loadScreen);
			return true;
		}catch(Exception e){
			System.out.println(e.getMessage()+" ** ");
			return false;
		}
	}
	
	/**
	 * this function sets a Select the screen to be shown.
	 * the function also sets a fadeIn effect to the loading of a screen.
	 * @param name, the name of the screen to fetch from hashmap
	 * @return true if successful false else.
	 */
	public boolean setScreen(final String name)
	{
		if(screens.get(name)!=null){
			final DoubleProperty opacity = opacityProperty();
			if(!getChildren().isEmpty())
			{
				Timeline fade = new Timeline(
						 new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0))
						 , new KeyFrame(new Duration(1000), new EventHandler<ActionEvent>()
								 {
							 		@Override
							 		public void handle(ActionEvent t)
							 		{
							 			getChildren().remove(0);
							 			getChildren().add(0,screens.get(name));
							 			Timeline fadeIn = new Timeline(
												new KeyFrame(Duration.ZERO,new KeyValue(opacity, 0.0)),
												new KeyFrame(new Duration(800),new KeyValue(opacity,1.0)));
							 			fadeIn.play();
							 		}
								 },new KeyValue(opacity, 0.0)));
				fade.play();
				
			}else
			{
				getChildren().add(screens.get(name)); // no fadeIn to first View.
			}
			
		}else{
			System.out.println("No screen loaded big ba da beng!");
			return false;
		}
		
		return false;
	}//end of function
	
	/**
	 * remove screen from map
	 * @param name
	 * @return true if successful else false 
	 */
	public boolean unloadScreen(String name)
	{
		if(screens.remove(name) == null)
		{
			System.out.println("Screen doens't exist");
			return false;
		}else return true;
	}
}
