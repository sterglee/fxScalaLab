
package fxScalaLab;

import edu.emory.mathcs.utils.ConcurrencyUtils;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import scala.tools.nsc.interpreter.IMain;


public class Globals {
  static  String buildDate = "4-July-2017"; 
    
  static    IMain globalInterpreter;  // the Scala Interpreter
  static String jarFilePath;  // Swing ScalaLab jar file path
  
   // a thread pool for tasks as parallel multiplication, multithreaded computations, asynchronous future based computations etc.
   static public ExecutorService execService =   Executors.newFixedThreadPool(ConcurrencyUtils.getNumberOfProcessors()*4);

   
 static public String buildTitle() {
       String mainFrameTitle =        "fxScalaLab:    Scala "+scala.tools.nsc.Properties.versionString()+",   "+
               System.getProperty("java.vm.name", "").toLowerCase()+",  "+ System.getProperty("os.name", "").toLowerCase()+
                   "  "+ System.getProperty("os.arch", "").toLowerCase()+" ,   "+ buildDate;
                   
      return mainFrameTitle;
}
}
