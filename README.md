# Graph-Search-to-find-the-Optimal-Route-of-a-map

Implemented the graph search to get the optimal route available according to the information in the file

The code structure is designed as followed :-

Firstly, A java file node.java is created which consists current city, connect city and the cost of the path 

Class for sorting in desccending order of the fringe on path cost is created which consists of the comparator function

Then the informed and uninformed search strategies are implemented

STEPS ON HOW TO RUN


1. Open Visual Studio and open file named "find_route.java" which is in the zipped folder.
3. Create object file
	"javac find_route.java"
4. Run the code
   Uninformed
	"java find_route input1.txt Bremen Kassel"
   Informed
	"java find_route input1.txt Bremen Kassel h_kassel.txt"
