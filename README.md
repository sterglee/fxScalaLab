# fxScalaLab

## A version of ScalaLab with a JavaFX interface ##


## Project Summary

`This project aims to provide a pure JavaFX interface to the ScalaLab project that is  based on the Swing framework. JavaFX is clearly better than Swing, offering much better  control over the overall Graphical User Interface (GUI) components, better support for configuration  and improved mulithreading. Also, is the official supported platform for the development of GUIs for the Java platfor. Therefore, the project aims to gradually implement all the original functionality of the Swing based ScalaLab project with JavaFX technology  only. `

`The ScalaLab project aims to provide an efficient scientific programming environment for the Java Virtual Machine. The scripting language is based on the Scala programming language enhanced with high level scientific operators and with an integrated environment that provides a MATLAB-like working style. Also, all the huge libraries of Java scientific code can be easily accessible (and many times with a more convenient syntax). The main potential of the ScalaLab is numerical code speed and flexibility. The statically typed Scala language can provide speeds of scripting code similar to pure Java. A major design priority of ScalaLab is its user-friendly interface. We like the user to enjoy writing scientific code, and with this objective we design the whole framework.`

`The MATLAB-like mathematical DSL of ScalaLab is termed ScalaSci , and is developed as an internal DSL, by exploiting the superb extensibility of the Scala language.`

`Toolboxes of Java scientific code can be easily installed, using a menu based installation procedure. Also, any .jar packed toolbox, can be directly available by placing it at the defaultToolboxes folder.`

`Many environment configuration options can be easily performed within the graphical user interface. Also,  code completion features and on line help support on the contents of classes, objects, libraries etc., using Java/Scala reflection, can further facilitate the programmer.`

`ScalaLab utilizes also and native C/C++ code for some important numerical operations. Although the speed  of pure Java code is generally adequate, optimized native code can provide further additional improvement. Also, the Java Native Interface (JNI) is utilized to interface with NVIDIA's CUDA technology, that provides dramatic speed improvements for many important tasks.`

## Documentation

The recently published book:

### Scientific Computing with ScalaLab at the Java Platform
### Scholars' Press (2017-01-11 )

https://www.morebooks.de/store/gb/book/scientific-computing-with-scalalab-at-the-java-platform/isbn/978-3-659-84599-4

describes ScalaLab in detail.
Also,  the Amazon link:

https://www.amazon.com/s/ref=nb_sb_noss?url=search-alias%3Dstripbooks&field-keywords=scalalab

and the Amazon smile link:

https://smile.amazon.com/Scientific-Computing-ScalaLab-Java-Platform/dp/365984599X/ref=sr_1_1?s=books&ie=UTF8&qid=1486391462&sr=1-1




## Installation

`fxScalaLab is developed with JDK8, so make sure to have JDK8 installed.`

`To install and execute fxScalaLab, download the .zip , and unzip it.  `

`The .zip download contains both the sources and all the relevant libraries to build  ScalaLab with sbt.`

`Run the fxScalaLab with: java -jar fxScalaLab.jar`

## `ScalaLab most useful commands`


`Currently you can execute the code within the textarea with double-clicking within the text area. Gradually, we will add a full set of functionality.`
