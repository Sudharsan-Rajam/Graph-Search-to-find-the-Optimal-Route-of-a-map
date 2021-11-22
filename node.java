import java.util.*;

class Node{
    
    int heuristic_cost;  
    String city;    
    Node con_city;    
    int path_cost;  

    Node(String city, Node con_city, int path_cost)   //for uninformed search
    {
        this.path_cost = path_cost;
        this.con_city = con_city;
        this.city = city;
    }
    Node(String city, Node con_city, int path_cost, int heuristicCost)    //for informed search
    {
        this.path_cost = path_cost;
        this.con_city = con_city;
        this.city = city;
        this.heuristic_cost = heuristicCost;
    }
    
}

//class for sorting in desccending order of the fringe on path cost/

class hComp implements Comparator<Node>{

    @Override
    public int compare(Node n1, Node n2) {

        if (n1.heuristic_cost>n2.heuristic_cost) return 1;
        else  if (n1.heuristic_cost<n2.heuristic_cost) return -1;
        return 0;
    }
}

class nComp implements Comparator<Node>{
    public int compare(Node n1,Node n2)
    {
        if (n1.path_cost>n2.path_cost) return 1;
        else if (n1.path_cost<n2.path_cost) return -1;
        else return 0;
    }
}