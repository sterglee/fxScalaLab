package fxScalaLab;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static org.fxmisc.richtext.demo.JavaKeywords.*;
import org.fxmisc.richtext.demo.JavaKeywordsAsync;
import scalaExec.Interpreter.GlobalValues;



public class fxScalaLab extends Application {
    
   @Override
   public void start(Stage stage) throws Exception {
    //   String prefix = "/home/sterg/JavaFX/JavaFXSceneBuilder2.0/app/ScalaLab/";
      Parent root = 
         FXMLLoader.load(getClass().getResource("fxScalaLabui.fxml"));

      Scene scene = new Scene(root); // attach scene graph to scene
         scene.getStylesheets().add(JavaKeywordsAsync.class.getResource("java-keywords.css").toExternalForm());
   
        String TITLE= buildTitle();
 
      stage.setTitle(TITLE); // displayed in window's title bar
      stage.setScene(scene); // attach scene to stage
      
            
      stage.show(); // display the stage
       
   }

 static public String buildTitle() {
     String buildDate = "4 July 2017"; 
      String mainFrameTitle =        "fxScalaLab:    Scala "+scala.tools.nsc.Properties.versionString()+",  library type: "+ GlobalValues.interpreterType()+ 
                   ",   "+System.getProperty("java.vm.name", "").toLowerCase()+",  "+ System.getProperty("os.name", "").toLowerCase()+
                   "  "+ System.getProperty("os.arch", "").toLowerCase()+" ,   "+ buildDate;
                   
      return mainFrameTitle;
}

   public static void main(String[] args) {

      launch(args); 
      
   }
}

