import java.util.*;
import java.io.IOException; 
import java.io.FileReader;
import java.io.File;    
import java.io.BufferedReader;  
 


//Informed search
public class find_route {
    // City Map
    
    Hashtable<String, ArrayList<String[]>> city_map = new Hashtable<String, ArrayList<String[]>>();
   // City map for the hueristics file/
    Hashtable<String, Integer> hueristics = new Hashtable<String,Integer>();
    Hashtable<String, Object[]> route = new Hashtable<String, Object[]>();
    int exp_nodes=0;
    int nodes_popped=0;
    int generated_nodes=0;

    private void readLn(String inputFile) throws IOException
    {

        File file = new File(inputFile);    //reading the file
        BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
        String line;
        while (!(line=br.readLine()).equals("END OF INPUT"))   
        {
            String start = line.split(" ")[0];
            String end = line.split(" ")[1];
            String dist = line.split(" ")[2];
            Map_City(start, end,dist);    
            Map_City(end, start, dist);    
        }
    }


    private void readHeuFile(String h_file) throws IOException
    {

        File file = new File(h_file);
        Scanner sc = new Scanner(new FileReader(file.getPath()));
        String line;
        while (!(line = sc.nextLine()).equals("END OF INPUT"))
        {
            hueristics.put(line.split(" ")[0].toString(),Integer.parseInt(line.split(" ")[1].toString()));
        }
    }
        
    // Storing the input from the file
    private void Map_City(String start, String end, String dist)
    {
        String[] entry = {end, dist};
        if (city_map.containsKey(start))
            city_map.get(start).add(entry);
        else {
            ArrayList<String[]> temp = new ArrayList<String[]>();
            temp.add(entry);
            city_map.put(start,temp);
        }
        
    }

    
    public void storeRoute(Node node)
    {
        if (!route.containsKey(node.city) || (Integer) route.get(node.city)[1]>node.path_cost){
            Object[] valueRoute = {node.con_city!=null ? node.con_city.city : null, node.path_cost};
            route.put(node.city,valueRoute);
        }
    
    }
    
    //printing  route from node  to the goal node
    private void BackTrack_Path(String destination)
    {

        String cumulativeDistance = "infinity";
        Stack<String> finalRoute = new Stack<String>();
        if (route.containsKey(destination)){
            cumulativeDistance = route.get(destination)[1]+" km";
            String con_city = (String) route.get(destination)[0];
            while (con_city!=null){
                int dist = (Integer)route.get(destination)[1] - (Integer)route.get(con_city)[1];
                finalRoute.push(con_city+" to "+destination+ ", "+dist+" km");
                destination=con_city;
                con_city = (String)route.get(destination)[0];
            }
            generated_nodes=route.size();
        }
        StringBuffer sb = new StringBuffer();
        sb.append("Nodes generated: "+generated_nodes+"\n");
        sb.append("Nodes expanded: "+exp_nodes  +"\n");
        sb.append("Nodes popped: "+nodes_popped+"\n");
        sb.append("Distance: "+cumulativeDistance+"\n");
        sb.append("Route:\n");
        if (finalRoute.isEmpty()) sb.append("none");
        else {
            while (!finalRoute.isEmpty()){
                sb.append(finalRoute.pop());
                sb.append("\n");
            }
        }
        System.out.println(sb.toString());
    }
     //informed search
     private void Search_Route(String inputFile, String source, String destination, String h_file) throws IOException
     {   

        readHeuFile(h_file);

        readLn(inputFile);

        
         HashSet<String> visited_node = new HashSet<String>();
         PriorityQueue<Node> fringe = new PriorityQueue<Node>(1,new hComp());
         fringe.add(new Node(source,null,0,0)); 
         while (!fringe.isEmpty())
         {   
             Node currentNode = fringe.poll(); // storing the node from the fringe
           
             nodes_popped++;
             
             storeRoute(currentNode);
             
             if (currentNode.city.equals(destination)) 
                 break;
             
             if(visited_node.contains(currentNode.city))
                 continue;
             ArrayList<String[]> childrenCityInfo = city_map.get(currentNode.city);
             Iterator<String[]> cCIIterator = childrenCityInfo.iterator();
             while (cCIIterator.hasNext()){
                 String[] str = cCIIterator.next();
                 Node node = new Node(str[0],currentNode, currentNode.path_cost+Integer.parseInt(str[1]),currentNode.path_cost+Integer.parseInt(str[1])+hueristics.get(str[0]));
                 fringe.add(node);
             }
            
             visited_node.add(currentNode.city);
             exp_nodes = visited_node.size();
         }

         BackTrack_Path(destination);
         
     }

    //for uninformed search
    private void Search_Route(String inputFile, String source, String destination) throws IOException
    {
        readLn(inputFile);
        HashSet<String> visited_node = new HashSet<String>();
        PriorityQueue<Node> fringe = new PriorityQueue<Node>(1,new nComp()); 
        fringe.add(new Node(source,null,0)); 
        while (!fringe.isEmpty())
        {   
            //storing the node from fringe at index 0 to currentNode
            Node currentNode = fringe.poll();
            nodes_popped++;
           
            storeRoute(currentNode);

            //If the destination is reached then break the loop
            if (currentNode.city.equals(destination)) 
                break;
            //if the current city is in visited then skip the process
            if(visited_node.contains(currentNode.city))
                continue;
            ArrayList<String[]> childrenCityInfo = city_map.get(currentNode.city);
            Iterator<String[]> iter = childrenCityInfo.iterator();
            while (iter.hasNext()){
                String[] s = iter.next();
                Node n = new Node(s[0],currentNode,currentNode.path_cost+Integer.parseInt(s[1]));
                fringe.add(n);
            }
            //Adding the city to visited the node
            visited_node.add(currentNode.city);
            exp_nodes = visited_node.size();

          
        }
        //prints the nodes of the path
        BackTrack_Path(destination);
    }

   

    //main function
  
    
    public static void main(String[] args) throws IOException
    {
        find_route Search_Route = new find_route();
        if (args.length==4 && (args[0]!=null || args[1]!=null || args[2]!=null || args[3]!=null))
        Search_Route.Search_Route(args[0],args[1],args[2],args[3]);
        else if (args.length==3 && (args[0]!=null || args[1]!=null || args[2]!=null))
            Search_Route.Search_Route(args[0],args[1],args[2]);
        else
            System.out.println("Invalid input!");
    }
}