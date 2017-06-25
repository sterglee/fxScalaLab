package fxScalaLab;

import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.Vector;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.HTMLEditor;
import javax.swing.JTextArea;
import scala.tools.nsc.Settings;

import scalaExec.Interpreter.GlobalValues;

  

public class fxScalaLabController {
  
    public static PrintStream consoleStream;

     
   // common imports used independently of the Matrix type that ScalaLab uses
    static public String commonImports = 
           "import _root_.java.awt.Color ; \n" +   // Java standard UI and graphics support
            "import scalaExec.Interpreter.importHelper._;\n"+
            "import java.lang.Math._; \n"+
            
            "import scala.concurrent.Future\n" +
            "import scala.concurrent.ExecutionContext.Implicits.global\n"+

            "import _root_.scalaSci.NROps._\n"+
            "import _root_.scalaSci.ILapack._ \n"+
 
             "import _root_.scalaSci.MTJ.BandMat\n"+
            
            "import _root_.scalaSci.math.array.DoubleArray._ \n"+

            "import _root_.scalaExec.Interpreter.MatlabConnection._ \n"+
            "import _root_.scalaExec.Interpreter.MatlabComplex \n"+
            
            "import _root_.scalaExec.Interpreter.SciLabConnection._ \n"+
            
            
          "import javax.swing._; \n"+   // Java standard UI and graphics support
         "import javax.swing.event._; \n"+
         
            "import java.text.DecimalFormat \n"+
            "import System.out._ \n"+
            
            "import _root_.cfor.CforSyntax._\n"+
             "import _root_.cifor.CiforSyntax._\n"+
          
            "import _root_.Do._ \n"+
            
            "import _root_.scalaSci.math.plot.canvas._; \n"+
            "import _root_.scalaSci.math.plot.plotObjects._; \n"+
            "import _root_.scalaSci.math.plot.plots._; \n"+
            "import _root_.scalaSci.math.plot.plot._;\n "+     // plotting routines
            "import _root_.scalaSci.math.plot.plotTypes._;\n "+     // plotting routines
            "import _root_.scalaSci.math.plot.PlotController;\n"+

     //       "import  scalaSci.math.plot.SyntaxHelper._; \n"+
            
            "import _root_.scalaSci.math.plot.ComplexArrayPlots._; \n"+
          
            "import _root_.scalaSci.Complex;\n"+
            "import _root_.scalaSci.Complex._; \n"+
            
            
            "import _root_.scalaSci.ComplexMatrix;\n"+
            "import _root_.scalaSci.ComplexMatrix._; \n"+
            
            "import _root_.scalaSci.math.plot.namedPlotsObj._;\n"+
            "import _root_.scalaSci.Vec ; \n "+
            "import _root_.scalaSci.Vec._ ; \n "+
            "import _root_.scalaSci.Matrix ; \n"+
            "import _root_.scalaSci.Matrix._ ; \n"+
            "import _root_.scalaSci.Sparse; \n"+
            "import _root_.scalaSci.Sparse._ ; \n"+
            "import _root_.scalaSci.CCMatrix ; \n"+
            "import _root_.scalaSci.CCMatrix._ ; \n"+
            "import _root_.java.util.Random; \n"+

            "import _root_.scalaSci.RichNumber; \n "+
            "import _root_.scalaSci.RichDouble2DArray; \n "+
            "import _root_.scalaSci.RichDouble2DArray._; \n "+
            "import _root_.scalaSci.RichDouble1DArray ; \n "+
            "import _root_.scalaSci.RichDouble1DArray._ ; \n "+
            
            "import _root_.scalaSci.RD2D \n "+
            "import _root_.scalaSci.RD1D \n "+
            "import _root_.scalaSci.D1D \n "+
            "import _root_.scalaSci.D2D \n "+
            "import _root_.scalaSci.RD2D \n "+
            
            "import _root_.scalaSci.MatlabRange._\n"+
            
            "import _root_.scalaSci.StaticScalaSciGlobalExt ; \n "+
            
            "import  _root_.scalaSciCommands.BasicCommands \n  "+// + // support for ScalaSci's console commands
            "import  _root_.scalaSciCommands.BasicCommands._; \n  "+// + // support for ScalaSci's console commands
            
            "import _root_.scalaSciCommands.FileOps \n"+
            "import _root_.scalaSciCommands.FileOps._ \n"+
            "import _root_.scalaSci.math.io.MatIO._ ; \n"+  // Matlab .mat compatibility
            "import _root_.scalaSci.math.io.ioUtils._ ; \n"+  //   load / save ASCII files etc.
            
             "import  _root_.JFplot._;\n "+
             "import _root_.JFplot.jFigure._;\n"+
             
            "import _root_.scalaSci.math.plot.PlotFunctional._ \n"+
            "import _root_.scalaSci.math.plot.PlotAdaptiveFunctional._ \n";  
    
    
       
    static public String warmUpGSLScript = 
            " org.bytedeco.javacpp.gsl.gsl_ieee_env_setup ";    // this dummy code snippet warms up GSL interface properly
  
    /*
              "import _root_.scalaSci.math.plot.plots._; \n"+
            "import _root_.scalaSci.math.plot.plot._;\n "+     // plotting routines
  
    */
    // these are intended for standalone applications that make use of the ScalaLab libraries
    static public String standAloneImports = 
           "import _root_.java.awt.Color ; \n" +   // Java standard UI and graphics support
            "import scalaExec.Interpreter.importHelper._;\n"+
            "import java.lang.Math._; \n"+

            "import _root_.scalaSci.NROps._\n"+
            "import _root_.scalaSci.ILapack._ \n"+
 
             "import _root_.scalaSci.MTJ.BandMat\n"+

          "import javax.swing._; \n"+   // Java standard UI and graphics support
         "import javax.swing.event._; \n"+
         
            "import java.text.DecimalFormat \n"+
            "import System.out._ \n"+
            
            "import _root_.scalaSci.math.plot.canvas._; \n"+
            "import _root_.scalaSci.math.plot.plotObjects._; \n"+
            "import _root_.scalaSci.math.plot.plotTypes._;\n "+     // plotting routines
            "import _root_.scalaSci.math.plot.PlotFunctional._;\n"+
            "import _root_.scalaSci.math.plot.PlotAdaptiveFunctional._;\n"+
            "import _root_.scalaSci.math.plot.PlotController;\n"+

            "import  scalaSci.math.plot.SyntaxHelper._; \n"+
            
            "import _root_.scalaSci.math.plot.ComplexArrayPlots._; \n"+
          
           
            "import _root_.scalaSci.Complex;\n"+
            "import _root_.scalaSci.Complex._; \n"+
            
            "import _root_.scalaSci.ComplexMatrix;\n"+
            "import _root_.scalaSci.ComplexMatrix._; \n"+
            
            "import _root_.scalaSci.math.plot.namedPlotsObj._;\n"+
            "import _root_.scalaSci.Vec ; \n "+
            "import _root_.scalaSci.Vec._ ; \n "+
            "import _root_.scalaSci.Matrix ; \n"+
            "import _root_.scalaSci.Matrix._ ; \n"+
            "import _root_.scalaSci.Sparse; \n"+
            "import _root_.scalaSci.Sparse._ ; \n"+
            "import _root_.scalaSci.CCMatrix ; \n"+
            "import _root_.scalaSci.CCMatrix._ ; \n"+
            "import _root_.java.util.Random; \n"+

            "import _root_.scalaSci.RichNumber; \n "+
            "import _root_.scalaSci.RichDouble2DArray; \n "+
            "import _root_.scalaSci.RichDouble2DArray._; \n "+
            "import _root_.scalaSci.RichDouble1DArray ; \n "+
            "import _root_.scalaSci.RichDouble1DArray._ ; \n "+
            
            "import _root_.scalaSci.RD2D \n "+
            "import _root_.scalaSci.RD1D \n "+
            "import _root_.scalaSci.D1D \n "+
            "import _root_.scalaSci.D2D \n "+
            "import _root_.scalaSci.RD2D \n "+
            
            "import _root_.scalaSci.MatlabRange._\n"+
            
            "import _root_.scalaSci.StaticScalaSciGlobalExt ; \n "+
            
            "import  _root_.scalaSciCommands.BasicCommands \n  "+// + // support for ScalaSci's console commands
            "import  _root_.scalaSciCommands.BasicCommands._; \n  "+// + // support for ScalaSci's console commands
            
            "import _root_.scalaSciCommands.FileOps \n"+
            "import _root_.scalaSciCommands.FileOps._ \n"+
            "import _root_.scalaSci.math.io.MatIO._ ; \n"+  // Matlab .mat compatibility
            "import _root_.scalaSci.math.io.ioUtils._ ; \n"+  //   load / save ASCII files etc.
            
             "import  _root_.JFplot._;\n "+
             "import _root_.JFplot.jFigure._;\n"+
             
             "import scala.util.control.Breaks._; \n"+

    //        "import scalaSci.JBLAS.JBLASNativeJavaInterface._\n"+
            "import  _root_.scalaSci.Bench.bench \n"+
            
            
            "import _root_.scalaSci.Mat ; \n"+ 
            "import _root_.scalaSci.Mat._ ; \n"+ 
            "import _root_.scalaSci.StaticMaths._ ; \n";
            
            
          
    static public    String  basicImportsEJMLScala = 
            commonImports+"\n"+
            "import _root_.scalaSci.EJML.Mat ; \n"+ 
            "import _root_.scalaSci.EJML.BMat ; \n"+ 
            "import _root_.scalaSci.EJML.Mat._ ; \n"+ 
            "import _root_.scalaSci.EJML.BMat._ ; \n"+ 
             "import _root_.scalaSci.EJML.StaticMathsEJML._ ; \n";
     
  
     URL  jarPathOfClass(String className) {
        try {
            return Class.forName(className).getProtectionDomain().getCodeSource().getLocation();
        } catch (ClassNotFoundException ex) {
           System.out.println("error in jarPathOfClass"+className+")");
           ex.printStackTrace();
           return null;
        }
}
      
    public fxScalaLabController() {

     scalaExec.gui.scalalabConsole sc =     new  scalaExec.gui.scalalabConsole();
   
     GlobalValues.jarFilePath =  jarPathOfClass("scalaExec.Interpreter.GlobalValues").toString().replace("file:/", "/");
    
     sc.mkPaths();
             
     scalaExec.Interpreter.GlobalValues.globalInterpreter =  new  scala.tools.nsc.interpreter.IMain(sc.scalaSettings);
 
     scalaExec.Interpreter.GlobalValues.globalInterpreter.interpret(basicImportsEJMLScala);   // interpret the basic imports

     System.out.println("EJML Interpreter created");
            
        
       
                
    }

    public void initialize() {
      

       
         // define a PrintStream that sends its bytes to the output text area
         consoleStream = new PrintStream( new OutputStream () {
            @Override
                public void write(int b)  {}   // never called
                public void write(  byte []  b, int off, int len )
                {
                    String outStr = new String(b, off, len);
                    outputTextArea.appendText(outStr);   // append the output to the text area
                }
            });

         // set both System.out and System.err to that stream
 System.setOut(consoleStream);
 System.setErr(consoleStream);
            
 
 
 System.out.println("// Type your ScalaLab code, then double click on the upper text area to execute it \n-- Only this operation is supported in this first version of fxScalaLab! \n\n");
 
 System.out.println("\n// F6 executes selected text or current line \n\n");
 

     
      
    }

    
    
      public  void  append(  String str  )  {
        outputTextArea.appendText(str);
        
      }
        
    @FXML
    private TextArea fxeditor;

    
    @FXML
    private TextArea outputTextArea;

    
    @FXML
    private MenuItem ejmlInterpreter;

    @FXML
    void initEJMLInterpreter(ActionEvent event) {
        
    }

    
// get the selected text if a selection exists, else the current line
   public  String   getSelectedTextOrCurrentLine() {
       String selectedTextOrCurrentLine = fxeditor.getSelectedText();
       if (selectedTextOrCurrentLine==null)
           selectedTextOrCurrentLine = getCurrentLine();
       else
           if (selectedTextOrCurrentLine.length()==0)
           selectedTextOrCurrentLine = getCurrentLine();

       return selectedTextOrCurrentLine;
   }

   
 // get the text of the current line (the line over which the caret is placed)
   public  String  getCurrentLine() {
       
       int caretpos = fxeditor.getCaretPosition()-1;       // the caret's current position
       String text = fxeditor.getText();
       
       if (text.charAt(caretpos) == '\n' && caretpos > 0)
           caretpos--;
       
       int startpos = caretpos;
       
       while (text.charAt(startpos)!='\n' && startpos > 0)
           startpos--;
       
       int textlen=text.length();
       int endpos = caretpos;
       while (text.charAt(endpos)!='\n' && endpos < textlen-1)
           endpos++;
       
       //System.out.println("startpos = "+startpos+", endpos = "+endpos);
       String currentLineStr = fxeditor.getText(startpos, endpos);
       
       return currentLineStr;   // return the string of the current line
   }



    @FXML
    void editorKeyPressed(KeyEvent event) {

        if (event.getCode()== KeyCode.F6) {
            String selectedTextOrCurrentLine = getSelectedTextOrCurrentLine();
            System.out.println(selectedTextOrCurrentLine);
             System.out.println(GlobalValues.globalInterpreter.interpret(selectedTextOrCurrentLine));
     
        }
    }
    
    @FXML
    void editorMouseClicked(MouseEvent event) {
        if (event.getClickCount() == 2) {
            String currentLine = fxeditor.getText();
            System.out.println(GlobalValues.globalInterpreter.interpret(currentLine));
        }
 //System.out.println(GlobalValues.globalInterpreter.interpret("var a = 7; var b=10; println(\"a+b\")").toString());
   
    }

}
