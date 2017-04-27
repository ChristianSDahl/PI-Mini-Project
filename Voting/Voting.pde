//initial values
int NumberOfLines = 20;
float HeightPerLine = height / NumberOfLines;

//Import data
String[] Lines = loadStrings ("server.data.txt"); //Will be saved in the sketch's data folder
float [] CloseArray = new float [NumberOfLines];
for (int i = 1; i < NumberOfLines+i; i++) {
  String Close = split(Lines[i], ',' )[6]; //Takes the 7th element of a line
  float CloseFloat = float(Close.substring(1));
  CloseArray[i-1] = CloseFloat;
}

//Create visualization
fullScreen(); //Window size
background(255); //White
float MaxNum = max(CloseArray);
for (int i = 0; i < CloseArray.length; i++) {
  float Ratio = CloseArray[i] / MaxNum;
  fill(Ratio * 255, 0, 0); // red green blue
  rect(0, i*HeightPerLine, Ratio*width, HeightPerLine);
}