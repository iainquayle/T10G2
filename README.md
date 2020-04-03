# T10G2
CalcNet
Currently CalcNet is composed of TextNet and CalculatorGUI which are going to be connected to each other and the work-in-progress image processing.

The following are insturctions on how to run the calculator and how train the neural network and run the image processing:


How to run the calculator using an IDE:
1- Open GUI.java in your preferred IDE (Example: Eclipse)
2- Compile and run the code on your preferred IDE*
3- Follow on-screen instructions

*If you're not sure how to run code on your IDE you can use the following google search "How to run and compile java code using PreferredIDE" replace PreferredIDE with the name of your IDE.

How to run the calculator using the command line:
1- Open your command line
2- Use the command "cd location of newCalc"* to move to the directory newCalc
3- Compile the code using the command "javac *.java"
4- Run the code by using the command "java GUI"
5- Follow on-screen instructions

*Do not simply write "location of newCalc", write the directory of your GUI.java file.


How to train the Neural Netwrok using an IDE*:
1- Save the training data file and the split training data file inside the folder "TextNet" (training data can't be uploaded to github due to file size)

2- Move the folder NetArch2* to inside the folder TextNet
3- Create a new folder in "TextNet" named "engine" and place everything that's inside "TextNet" into "engine"
3- Open TextNet.Java in your preferred IDE
4- Follow on screen instructions

*If you're not sure how to run code on your IDE you can use the following google search "How to run and compile java code using PreferredIDE" replace PreferredIDE with the name of your IDE.

*NetArch2 contains the config files neccessary to run the neural network architecture


How to train the Neural Netwrok using the command line:
1- Save the training data file and the split training data file inside the folder TextNet (training data can't be uploaded to github due to file size)

2- Move the folder NetArch2* to inside the folder TextNet
3- Create a new folder in "TextNet" named "engine" and place everything that's inside "TextNet" into "engine"
4- Open the command line
5- Use the command "cd location of TextNet/engine"* to move to the directory TextNet
6- Compile the code using the command "javac *.java".

7- Run the code using "java TextNet"

*Do not simply write location, write the directory that will put you inside the folder TextNet

How to run the image processing portion using an IDE:
1- Open ImgResize.java in your preferred IDE (Example: Eclipse)*
1.25- You will need to edit the string variable inImgPath to match the path of the image you want the code to work on
1.5- You will need to edit string variables outImgPath1, outImgPath2 to match the paths where you want the new processed images to be saved
2- Compile and run the code on your preferred IDE*
3- Follow on-screen instructions

*If you're not sure how to run code on your IDE you can use the following google search "How to run and compile java code using PreferredIDE" replace PreferredIDE with the name of your IDE.

How to run the image processing portion using the command line:
0- You will need to edit the string variable inImgPath to match the path of the image you want the code to work on
0.5- You will need to edit string variables outImgPath1, outImgPath2 to match the paths where you want the new processed images to be saved
1- Open your command line
2- Use the command "cd location of ImgProcess"* to move to the directory ImgProcess
3- Compile the code using the command "javac ImgResize.java"
4- Run the code by using the command "java ImgResize"

*Do not simply write "location of ImgProcess", write the directory of your ImgResize.java file.

