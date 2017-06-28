package fxScalaLab;

// Fig. 21.26: PrimeCalculatorTask.java
// Calculates the first n primes, publishing them as they are found.
import java.util.Arrays;
import javafx.concurrent.Task;

public class fxExecTask  extends Task<Integer> {
   private final String codeToExecute;

   // constructor
   public fxExecTask(String code) {
      codeToExecute = code;
   } 

   
    @Override
    protected Integer call() throws Exception {
         scalaExec.Interpreter.GlobalValues.globalInterpreter.interpret(codeToExecute);
         return 0;
         
}
} 
