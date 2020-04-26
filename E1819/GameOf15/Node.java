import java.util.*;
import java.lang.*;

public class Node implements Comparable<Node> {
    int configuracao[][] = new int[4][4];
    int depth = 0;
    int cost = 0;
    Node pai;
    char path = ' ';

    Node(int n[][]){		
	configuracao = n;
	this.pai = pai;
    }
    
    @Override
    public int compareTo(Node n){
	if(this.cost == n.cost )
	    return 0;
	else if(this.cost > n.cost ) 
	    return 1;
	return -1;
	}
}
