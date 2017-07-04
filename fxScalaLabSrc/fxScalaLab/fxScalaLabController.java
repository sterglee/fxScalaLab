package fxScalaLab;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

import static org.fxmisc.richtext.demo.JavaKeywords.*;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import scala.tools.nsc.Settings;


import scalaExec.Interpreter.GlobalValues;


public class fxScalaLabController {
    scalaExec.gui.scalalabConsole global_sc=null;
    
    Settings scalaSettings;
    
    @FXML
    private MenuItem openFile;

    
    @FXML
    private MenuItem saveFile;

           
    @FXML
    private CodeArea fxeditor;

    
    @FXML
    private TextArea outputTextArea;

    
    @FXML
    private MenuItem resetInterpreter;

    @FXML
    void initInterpreter(ActionEvent event) {
      initEJMLInterpreter();
 
    }

    
    @FXML
    void MTJInterpreter(ActionEvent event) {
  
     scalaExec.Interpreter.GlobalValues.globalInterpreter =  new  scala.tools.nsc.interpreter.IMain(global_sc.scalaSettings, new PrintWriter(outconsoleStream));
 
     scalaExec.Interpreter.GlobalValues.globalInterpreter.interpret(basicImportsMTJScala);   // interpret the basic imports

        
     System.out.println("MTJ  Interpreter created");
    }
    
    
    
    @FXML
    void ApacheCommonsInterpreter(ActionEvent event) {
  
     scalaExec.Interpreter.GlobalValues.globalInterpreter =  new  scala.tools.nsc.interpreter.IMain(global_sc.scalaSettings, new PrintWriter(outconsoleStream));
 
     scalaExec.Interpreter.GlobalValues.globalInterpreter.interpret(basicImportsCommonMathsScala);   // interpret the basic imports

     
     System.out.println("Apache Commons  Interpreter created");
    }
    
    
    @FXML
    void JBLASInterpreter(ActionEvent event) {
  
     scalaExec.Interpreter.GlobalValues.globalInterpreter =  new  scala.tools.nsc.interpreter.IMain(global_sc.scalaSettings, new PrintWriter(outconsoleStream));
 
     scalaExec.Interpreter.GlobalValues.globalInterpreter.interpret(basicImportsJBLASScala);   // interpret the basic imports

     System.out.println("JBLAS  Interpreter created");
    }
    
    void initEJMLInterpreter() {
        
     scalaExec.gui.scalalabConsole sc =     new  scalaExec.gui.scalalabConsole();
   
     GlobalValues.jarFilePath =  jarPathOfClass("scalaExec.Interpreter.GlobalValues").toString().replace("file:/", "/");
    
  
     sc.mkPaths();
             
       String fxScalaLabLibPath = GlobalValues.jarFilePath;
                  // remove jar file name from the path name
                 int dotIndex = fxScalaLabLibPath.indexOf(".");
                 int lastPos = dotIndex;
                 while (fxScalaLabLibPath.charAt(lastPos)!='/' && fxScalaLabLibPath.charAt(lastPos)!='\\'  && lastPos>0)
                             lastPos--;
                fxScalaLabLibPath = fxScalaLabLibPath.substring(0, lastPos);
      
       //  any .jar file in the defaultToolboxes folder is automatically appended to classpath 
          String defaultToolboxesFolder = fxScalaLabLibPath.replace("lib", "defaultToolboxes");
          System.out.println("appending toolboxes of DefaultToolboxes folder:  "+defaultToolboxesFolder);
     
              File [] toolboxesFolderFiles = (new java.io.File(defaultToolboxesFolder)).listFiles();  // get the list of files at the default toolboxes folder
          if (toolboxesFolderFiles!=null) {  // DefaultToolboxes folder not empty
           int numFiles = toolboxesFolderFiles.length; 
           for (int f=0; f < numFiles;  f++) {
               String currentFileName = toolboxesFolderFiles[f].getAbsolutePath();
           
                  if (currentFileName.endsWith(".jar")) {
               sc.scalaSettings.classpath().append(currentFileName);
              }  // endsWith("jar")
            }   // for all files of then DefaultToolboxes folder
          }   // DefaultToolboxes folder not empty
          
            
     scalaExec.Interpreter.GlobalValues.globalInterpreter =  new  scala.tools.nsc.interpreter.IMain(sc.scalaSettings); // new PrintWriter(outconsoleStream));
 
     scalaExec.Interpreter.GlobalValues.globalInterpreter.interpret(basicImportsEJMLScala);   // interpret the basic imports

     if (global_sc==null)  global_sc=sc;
    
      
    
        outconsoleStream = new OutputStream () {
            @Override
                public void write(int b)  {}   // never called
                public void write(  byte []  b, int off, int len )
                {
                    String outStr = new String(b, off, len);
                    outputTextArea.appendText(outStr);   // append the output to the text area
                }
            };

 
     System.out.println("EJML Interpreter created");
    
            
    }
    
      @FXML
    void onFileOpen(ActionEvent event) {
          FileChooser fileChooser = new FileChooser();
          fileChooser.setTitle("Select File to open");
          int bufSize = 1000;
          char [] buf = new char[bufSize];
          fileChooser.setInitialDirectory(new File("."));
          StringBuilder sb = new StringBuilder();
          File file = fileChooser.showOpenDialog(fxeditor.getScene().getWindow());
          
          if (file != null)  {
              
               FileReader fr = null;
            
                int nrread;
                       try {
                           fr = new FileReader(file);
                       } catch (FileNotFoundException ex) {
                           Logger.getLogger(fxScalaLabController.class.getName()).log(Level.SEVERE, null, ex);
                       }
              try {
                  while ( (nrread = fr.read(buf)) != -1)
                  {
                      sb.append(buf);
                  }
              } catch (IOException ex) {
                  Logger.getLogger(fxScalaLabController.class.getName()).log(Level.SEVERE, null, ex);
              }
              fxeditor.replaceText(sb.toString());
 
      }
    }
    

      @FXML
    void onFileSave(ActionEvent event) {
          FileChooser fileChooser = new FileChooser();
          fileChooser.setTitle("Select File to saver to");
          int bufSize = 1000;
          char [] buf = new char[bufSize];
          fileChooser.setInitialDirectory(new File("."));
          StringBuilder sb = new StringBuilder();
        
          File file = fileChooser.showSaveDialog(fxeditor.getScene().getWindow());
          
          if (file != null)  {
              
               FileWriter fw = null;
            
                   try {
                       fw = new FileWriter(file);
                   
                   } catch (IOException ex) {
                       Logger.getLogger(fxScalaLabController.class.getName()).log(Level.SEVERE, null, ex);
                   }
              try {
                  fw.write(fxeditor.getText());
                  fw.close();
              } catch (IOException ex) {
                  Logger.getLogger(fxScalaLabController.class.getName()).log(Level.SEVERE, null, ex);
              }
 
      }
    }
    

 
 
  
    public static PrintStream consoleStream;
    public static OutputStream outconsoleStream;
     
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
    
    static public    String  basicImportsMTJScala = 
            commonImports+
           "import _root_.scalaSci.MTJ.Mat ; \n"+ 
           "import _root_.scalaSci.MTJ.Mat._ ; \n"+ 
           "import _root_.scalaSci.MTJ.StaticMathsMTJ._ ; \n";
           
    static public    String  basicImportsJBLASScala = 
            commonImports+
            "import _root_.scalaSci.JBLAS.Mat; \n"+
            "import _root_.scalaSci.JBLAS.Mat._; \n"+
            "import _root_.scalaSci.JBLAS.StaticMathsJBLAS._\n";
                    
  
    static public    String  basicImportsCommonMathsScala = 
            commonImports+
           "import _root_.scalaSci.CommonMaths.Mat ; \n"+ 
           "import _root_.scalaSci.CommonMaths.Mat._ ; \n"+ 
           "import _root_.scalaSci.CommonMaths.StaticMathsCommonMaths._ ; \n";
                    
    static public    String  basicImportsEigenScala = 
            commonImports+
           "import _root_.scalaSci.jeigen.Mat ; \n"+ 
           "import _root_.scalaSci.jeigen.StaticMathsEigen._; \n";
             
    
    static public    String  basicImportsD2Das1DScala = 
            commonImports+
           "import _root_.scalaSci.D2Das1DMat ; \n"+ 
           "import _root_.scalaSci.StaticMathsD2Das1D._; \n";
             
     
  
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

     initEJMLInterpreter();
       
    }

    public void initialize() {
      
   fxeditor.setParagraphGraphicFactory(LineNumberFactory.get(fxeditor));

        fxeditor.richChanges()
                .filter(ch -> !ch.getInserted().equals(ch.getRemoved())) // XXX
                .subscribe(change -> {
                    fxeditor.setStyleSpans(0, computeHighlighting(fxeditor.getText()));
                });
       

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
            
  scalaExec.Interpreter.GlobalValues.globalInterpreter =  new  scala.tools.nsc.interpreter.IMain(global_sc.scalaSettings, new PrintWriter(outconsoleStream));
  scalaExec.Interpreter.GlobalValues.globalInterpreter.interpret(basicImportsEJMLScala);   // interpret the basic imports
 
 
 System.out.println("// Type your ScalaLab code, then double click on the upper text area to execute it ");
 System.out.println("\n// F6 executes selected text or current line ");
 System.out.println("\n// F5 clears this output area ");
 

     
      
    }

    
    
      public  void  append(  String str  )  {
        outputTextArea.appendText(str);
        
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
       
       int caretpos = fxeditor.getCaretPosition();       // the caret's current position
       String text = fxeditor.getText();
       int textlen=text.length();
    
           if (caretpos == textlen) caretpos--;
       
   //     System.out.println("caretpos = "+caretpos);
     
       if (text.charAt(caretpos) == '\n' && caretpos > 0)
           caretpos--;
       
       int startpos = caretpos;
       while (text.charAt(startpos)!='\n' && startpos > 0)
           startpos--;
       
          int endpos = caretpos;
       while (text.charAt(endpos)!= '\n' && endpos < textlen-1)
           endpos++;
    
     // System.out.println("startpos = "+startpos+", endpos = "+endpos);
       String currentLineStr = fxeditor.getText(startpos, endpos+1);
       
       return currentLineStr;   // return the string of the current line
   }



    @FXML
    void editorKeyPressed(KeyEvent event) {

        KeyCode  eventKeyCode = event.getCode();
        
        if (eventKeyCode == KeyCode.F6) {
            String selectedTextOrCurrentLine = getSelectedTextOrCurrentLine();
            fxExecTask fxtask = new fxExecTask(selectedTextOrCurrentLine);
            GlobalValues.execService.execute(fxtask);
        }
        else if (eventKeyCode == KeyCode.F5) {
            outputTextArea.clear();
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
