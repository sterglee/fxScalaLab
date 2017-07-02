package fxScalaLab;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static org.fxmisc.richtext.demo.JavaKeywords.*;
import org.fxmisc.richtext.demo.JavaKeywordsAsync;



public class fxScalaLab extends Application {
   @Override
   public void start(Stage stage) throws Exception {
    //   String prefix = "/home/sterg/JavaFX/JavaFXSceneBuilder2.0/app/ScalaLab/";
      Parent root = 
         FXMLLoader.load(getClass().getResource("fxScalaLabui.fxml"));

      Scene scene = new Scene(root); // attach scene graph to scene
         scene.getStylesheets().add(JavaKeywordsAsync.class.getResource("java-keywords.css").toExternalForm());
   
      stage.setTitle("ScalaLab"); // displayed in window's title bar
      stage.setScene(scene); // attach scene to stage
      
            
      stage.show(); // display the stage
       
   }

   public static void main(String[] args) {

      launch(args); 
      
   }
}

