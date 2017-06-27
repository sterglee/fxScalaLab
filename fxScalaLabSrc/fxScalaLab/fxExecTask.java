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

/*************************************************************************
* (C) Copyright 1992-2015 by Deitel & Associates, Inc. and               *
* Pearson Education, Inc. All Rights Reserved.                           *
*                                                                        *
* DISCLAIMER: The authors and publisher of this book have used their     *
* best efforts in preparing the book. These efforts include the          *
* development, research, and testing of the theories and programs        *
* to determine their effectiveness. The authors and publisher make       *
* no warranty of any kind, expressed or implied, with regard to these    *
* programs or to the documentation contained in these books. The authors *
* and publisher shall not be liable in any event for incidental or       *
* consequential damages in connection with, or arising out of, the       *
* furnishing, performance, or use of these programs.                     *
*************************************************************************/