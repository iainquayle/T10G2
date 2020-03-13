# T10G2
CalcNet
TextNet for the purpose of training a net to convert img to text. TextNet is starting single threaded, but will hopefully be multi to help facilitate better training.
CalcNet will be the merger of TextNet and a Calculator 

CalcNet
packages:
 textNet
 calculator
 gui


How to run using an IDE:
1- Open CalculatorGUI.java in your preferred IDE (Example: Eclipse)
2- Compile and run the code on your preferred IDE*
3- Follow on-screen instructions

*If you're not sure how to run code on your IDE you can use the following google search "How to run and compile java code using PreferredIDE" replace PreferredIDE with the name of your IDE.

How to run the calculator using the command line:

1- Open your command line
2- Use the command "cd location of CalculatorGUI.java"* to move to the directory where you saved the code
3- Compile the code using the command "javac CalculatorGUI.java"
4- Run the code by using the command "java CalculatorGUI"
5- Follow on-screen instructions

*Do not simply write "location of Calculator.java", write the directory of your Calculator.java file.


How to train the Neural Netwrok using an IDE*:
1- Save the training data file and the split training data file inside the folder TextNet (training data can't be uploaded to github due to file size)

2- Move the folder NetArch2* to inside the folder TextNet
3- Open TextNet.Java in your preferred IDE
4- Follow on screen instructions

*If you're not sure how to run code on your IDE you can use the following google search "How to run and compile java code using PreferredIDE" replace PreferredIDE with the name of your IDE.

*NetArch2 contains the config files neccessary to run the neural network architecture


How to train the Neural Netwrok using the command line:
1- Save the training data file and the split training data file inside the folder TextNet (training data can't be uploaded to github due to file size)

2- Move the folder NetArch2* to inside the folder TextNet
3- Use the command "cd location"* to move to the directory TextNet
3- Compile the code using the command "javac *.java".

4- Run the code using "java engine.TextNet"

*Do not simply write location, write the directory that will put you inside the folder TextNet

