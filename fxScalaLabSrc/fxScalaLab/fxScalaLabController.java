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
import javafx.stage.FileChooser;

import static org.fxmisc.richtext.demo.JavaKeywords.*;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import scala.tools.nsc.Settings;


import static fxScalaLab.InterpreterImports.*;
import static fxScalaLab.Globals.*;


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
  
     globalInterpreter =  new  scala.tools.nsc.interpreter.IMain(global_sc.scalaSettings, new PrintWriter(outconsoleStream));
 
     globalInterpreter.interpret(basicImportsMTJScala);   // interpret the basic imports

        
     System.out.println("MTJ  Interpreter created");
    }
    
    
    
    @FXML
    void ApacheCommonsInterpreter(ActionEvent event) {
  
     globalInterpreter =  new  scala.tools.nsc.interpreter.IMain(global_sc.scalaSettings, new PrintWriter(outconsoleStream));
 
     globalInterpreter.interpret(basicImportsCommonMathsScala);   // interpret the basic imports

     
     System.out.println("Apache Commons  Interpreter created");
    }
    
    
    @FXML
    void JBLASInterpreter(ActionEvent event) {
  
     globalInterpreter =  new  scala.tools.nsc.interpreter.IMain(global_sc.scalaSettings, new PrintWriter(outconsoleStream));
 
     globalInterpreter.interpret(basicImportsJBLASScala);   // interpret the basic imports

     System.out.println("JBLAS  Interpreter created");
    }
    
    void initEJMLInterpreter() {
        
        boolean      hostIsUnix = true;
         if (File.pathSeparatorChar==';')
              hostIsUnix = false;  // Windows host
          
             if (hostIsUnix) {    

     jarFilePath =  jarPathOfClass("scalaExec.Interpreter.GlobalValues").toString().replace("file:/", "/");
    
                }
             else  {
                   jarFilePath =  jarPathOfClass("scalaExec.Interpreter.GlobalValues").toString().replace("file:/", "");
 
             }
                 
      scalaExec.gui.scalalabConsole sc =     new  scalaExec.gui.scalalabConsole();

     sc.mkPaths();
             
       String fxScalaLabLibPath = jarFilePath;
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
          
            
     globalInterpreter =  new  scala.tools.nsc.interpreter.IMain(sc.scalaSettings); // new PrintWriter(outconsoleStream));
 
     globalInterpreter.interpret(basicImportsEJMLScala);   // interpret the basic imports

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
            
  globalInterpreter =  new  scala.tools.nsc.interpreter.IMain(global_sc.scalaSettings, new PrintWriter(outconsoleStream));
  globalInterpreter.interpret(basicImportsEJMLScala);   // interpret the basic imports
 
 
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
            execService.execute(fxtask);
        }
        else if (eventKeyCode == KeyCode.F5) {
            outputTextArea.clear();
        }
    }
    
    @FXML
    void editorMouseClicked(MouseEvent event) {
        if (event.getClickCount() == 2) {
            String currentLine = fxeditor.getText();
            System.out.println(globalInterpreter.interpret(currentLine));
        }
        
 //System.out.println(GlobalValues.globalInterpreter.interpret("var a = 7; var b=10; println(\"a+b\")").toString());
   
    }

}
