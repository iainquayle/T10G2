# T10G2
CalcNet
TextNet for the purpose of training a net to convert img to text. TextNet is starting single threaded, but will hopefully be multi to help facilitate better training.
CalcNet will be the merger of TextNet and a Calculator 

Text TODO:
 (Training data input (brings in a certain amount of training data and resizes it to required specs . this may include resizing of images(no need for smoothing these if ))(data should be in the form of 1d float arrays))
 (Dense/Conv2D - save and init)
 (Net Architecture) 
 (Multi thread - MAYBE)
Calc TODO:
 (takes in string representing equation)
 (start with dealing with equations with no unknown variables)
IMG Processing TODO:
 (img processing methods will also be used by the class that iputs training data, so any thing img processing goes here as it may be useful later)
 (img resizing - can resize a rectangular img to be square (ie stretch/compress in two directions))(newDimX, newDimY, oldDimX, oldDimY, img)
 (some function to bold edges (looks at old img pixel one by one, if coloured, colour surrounding pixels if their value is less))
 (some function to shrink edges (looks at old img pixel one by one, refill to the lowest value in a certain radius if their value is below a certain threshold))
 (img darkening function)
