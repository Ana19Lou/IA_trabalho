import java.util.*;
import java.lang.*;

public class Tabuleiro{
    Node  confInicial, confFinal;
    HashSet<String> hashTable = new HashSet<>();
    String incialKey, finalKey;
    double start; //momento do início da pesquisa

    public Tabuleiro (int MatIn[][],int MatFi[][]){
	confInicial = new Node(MatIn);
	confFinal = new Node(MatFi);
	incialKey = hash_key(MatIn);
	hashTable.add(incialKey);
	finalKey = hash_key(MatFi);
    } //end of tabuleiro
    public String hash_key(int matriz[][]){
	String key = "";
	for(int i=0;i<4;i++){
	    for(int j=0;j<4;j++){
		char a = (char)matriz[i][j];
		a += 65;	
		key = key + a;		
	    }
	}
	//System.out.println(key);
	return key;
    }//end of hash_key

    public void DFS(){//----------------------
        start = new Date().getTime();
	Stack<Node> pilha = new Stack<Node>();
	pilha.push(confInicial);
	hashTable.add(hash_key(confInicial.configuracao));
	while(!pilha.isEmpty()){
	    Node node = pilha.pop();
	    String key = hash_key(node.configuracao);
	    if(key.equals(finalKey)){
		encontrou(node);
		System.exit(0);
	    }	
	    LinkedList<Node> descendentList = new LinkedList<>();    
	    descendentList.addAll(MakeDescendents(node));
	    while(!descendentList.isEmpty())
		pilha.push(descendentList.removeLast());
	}
    }//End of DFS

    public void BFS(){//---------------------
	start = new Date().getTime();
	LinkedList<Node> lista = new LinkedList<Node>();
	lista.add(confInicial);
	while(!lista.isEmpty()){
	    Node node = lista.removeFirst();
	    String key = hash_key(node.configuracao);// Gera a chave para a hashTable
	    if(key.equals(finalKey)){//verifica se encontrou a solucao
		encontrou(node);
		System.exit(0);//termina	
	    }
	    LinkedList<Node> descendentList = new LinkedList<>();//lista para guardar os nos descendentes
	    descendentList.addAll(MakeDescendents(node));
	    lista.addAll(descendentList);// meter os nos gerados na fila
	}
    }//end of BFS
    
    public void IDFS(){//--------------------
	 start = new Date().getTime();
	Stack<Node> pilha = new Stack<Node>();
	int limite = 100;
	int profundidade = 0;
	while(profundidade <= limite){
	    pilha.push(confInicial);
	    hashTable.add(hash_key(confInicial.configuracao));
	    while(!pilha.isEmpty()){
		Node node = pilha.pop();
		String key = hash_key(node.configuracao);
		if(key.equals(finalKey)){
		    encontrou(node);
		    System.exit(0);
		}	
		if(node.depth <= profundidade){
		    LinkedList<Node> descendentList = new LinkedList<>();
		    descendentList.addAll(MakeDescendents(node));
		    while(!descendentList.isEmpty())
			pilha.push(descendentList.removeLast());
		}
	    }
	    profundidade++;
	    hashTable.clear();
	    pilha.clear();
	}
    }//end of IDFS

    public void Greedy(){//-------------
	start= new Date().getTime();
	PriorityQueue<Node> fila = new PriorityQueue<Node>();
	fila.add(confInicial);
	while(fila.peek() != null){
	    Node node = fila.poll();
	    String Key = hash_key(node.configuracao);
	    if(Key.equals(finalKey)){
		encontrou(node);
		System.exit(0);
	    }
	    PriorityQueue<Node> descendentList = new PriorityQueue<Node>();
	    descendentList.addAll(MakeDescendentsG(node));
	    while(!descendentList.isEmpty())
		fila.add(descendentList.poll());//extract
	}
    }//end of greedy

    public void AStar (){
	start= new Date().getTime();
	PriorityQueue<Node> fila = new PriorityQueue<Node>();
	fila.add(confInicial);
	while(fila.peek() != null){
	    Node node = fila.poll();
	    String Key = hash_key(node.configuracao);
	    if(Key.equals(finalKey)){
		encontrou(node);
		System.exit(0);
	    }
	    PriorityQueue<Node> descendentList = new PriorityQueue<Node>();
	    descendentList.addAll(MakeDescendentsA(node));
	    while(!descendentList.isEmpty())
		fila.add(descendentList.poll());//extract
	}
    }//end of AStar

    public LinkedList<Node> MakeDescendents(Node no){
	LinkedList<Node> list = new LinkedList<Node>();		
	for(int i=0;i<4;i++){
	    for(int j=0;j<4;j++){
		if(no.configuracao[i][j] == 0){
		    //CIMA
		    if(i >= 1){
			int[][] m = move(no,'U',i,j);
			String chave = hash_key(m);		     
			if(!hashTable.contains(chave)){
			    Node no_filho = new Node(m);
			    no_filho.depth = no.depth + 1;
			    no_filho.pai = no;
			    no_filho.path = 'U';
			    list.addLast(no_filho);
			    hashTable.add(chave);
			}
		    }
		    //BAIXO
		    if(i <= 2){
			int[][] m = move(no,'D',i,j);
			String chave = hash_key(m);
			if(!hashTable.contains(chave)){
			    Node no_filho = new Node(m);
			    no_filho.depth = no.depth + 1;
			    no_filho.pai = no;
			    no_filho.path = 'D';
			    list.addLast(no_filho);
			    hashTable.add(chave);
			}
		    }
		    //DIREITA
		    if(j <= 2){
			int[][] m = move(no,'R',i,j);
			String chave = hash_key(m);
						
			if(!hashTable.contains(chave)){
			    Node no_filho = new Node(m);
			    no_filho.depth = no.depth + 1;
			    no_filho.pai = no;
			    no_filho.path = 'R';
			    list.addLast(no_filho);
			    hashTable.add(chave);
			}
		    }
		    //ESQUERDA
		    if(j >= 1){
			int[][] m = move(no,'L',i,j);
			String chave = hash_key(m);
			if(!hashTable.contains(chave)){
			    Node no_filho = new Node(m);
			    no_filho.depth = no.depth + 1;
			    no_filho.pai = no;
			    no_filho.path = 'L';
			    list.addLast(no_filho);
			    hashTable.add(chave);
			}
		    }
		}		
	    }
	}
	//System.out.println(list.toString());
	return list;
    }//End of MakeDescendents

    public int[][] move(Node no,char direcao,int i,int j){
	int clone[][] = new int[4][4];
	for(int x=0;x<4;x++)
	    System.arraycopy(no.configuracao[x],0,clone[x],0,4);
	switch(direcao){
	case'U':
	    clone[i][j] = clone[i-1][j];;
	    clone[i-1][j] = 0;
	    break;
	case'D':
	    clone[i][j] = clone[i+1][j];
	    clone[i+1][j] = 0;
	    break;
	case'R':
	    clone[i][j] = clone[i][j+1];
	    clone[i][j+1] = 0;
	    break;
	case'L':
	    clone[i][j] = clone[i][j-1];
	    clone[i][j-1] = 0;
	    break;
	}
	return clone;
    }//end of move
    
    public void encontrou(Node n){
	Long end = new Date().getTime();
	System.out.println("Jogadas: " + n.depth);
	System.out.print("Caminho: ");
	caminho(n);	
	System.out.println("Tempo de execução: " + ((end-start)/1000)+"s");//divide por 1000 para ser representado em segundos
	double memory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
	System.out.printf("Memoria utilizada: %.3fMB\n",memory/(1024*1024));//divide por 1024^2 para poder ser representado em MB
    }//end of encontrou
    
    public void caminho(Node n){
	System.out.println();
	char caminho[] = new char[99999999];
	int i = 0;
	while (n != null){
	    caminho[i] = n.path;
	    n = n.pai;
	    i++;
	}
	for(int j=i;j>=0;j--)
	    System.out.print(caminho[j] + " ");
	System.out.println();
    }//end of caminho

    public PriorityQueue<Node> MakeDescendentsG(Node no){//--------- 
	PriorityQueue<Node> list = new PriorityQueue<Node>();
	for(int i=0;i<4;i++){
	    for(int j=0;j<4;j++){
		if(no.configuracao[i][j] == 0){
		    //CIMA
		    if(i >= 1){
			int[][] m = move(no,'U',i,j);
			String chave = hash_key(m);
			if(!hashTable.contains(chave)){
			    Node no_filho = new Node(m);
			    no_filho.depth = no.depth + 1;	
			    no_filho.cost = heuristic(no_filho.configuracao,confFinal.configuracao);
			    no_filho.pai = no;
			    no_filho.path = 'U';
			    list.add(no_filho);
			    hashTable.add(chave);
			}
		    }
		    //BAIXO
		    if(i <= 2){
			int[][] m = move(no,'D',i,j);
			String chave = hash_key(m);
			if(!hashTable.contains(chave)){
			    Node no_filho = new Node(m);
			    no_filho.depth = no.depth + 1;
			    no_filho.cost = heuristic(no_filho.configuracao,confFinal.configuracao);
			    no_filho.pai = no;
			    no_filho.path = 'D';
			    list.add(no_filho);
			    hashTable.add(chave);
			}
		    }
		    //DIREITA
		    if(j <= 2){
			int[][] m = move(no,'R',i,j);
			String chave = hash_key(m);
			if(!hashTable.contains(chave)){
			    Node no_filho = new Node(m);
			    no_filho.depth = no.depth + 1;
			    no_filho.cost = heuristic(no_filho.configuracao,confFinal.configuracao);
			    no_filho.pai = no;
			    no_filho.path = 'R';
			    list.add(no_filho);
			    hashTable.add(chave);
			}
		    }
		    //ESQUERDA
		    if(j >= 1){
			int[][] m = move(no,'L',i,j);
			String chave = hash_key(m);
			if(!hashTable.contains(chave)){
			    Node no_filho = new Node(m);
			    no_filho.depth = no.depth + 1;
			    no_filho.cost = heuristic(no_filho.configuracao,confFinal.configuracao);
			    no_filho.pai = no;
			    no_filho.path = 'L';
			    list.add(no_filho);
			    hashTable.add(chave);
			}
		    }
		}		
	    }
	}
	return list;
    }

    public int heuristic(int MatIn[][],int MatFi[][]){//heuristica usando o manhattanDistance ----------
	int heuristic = 0;
	int xIn=0,yIn=0,xFi=0,yFi=0;
	for(int i=1;i<16;i++){
	    for(int x=0;x<4;x++){
		for(int y=0;y<4;y++){
		    if(MatIn[x][y] == i){
			xIn = x;
			yIn = y;
		    }
		}
	    }
	    for(int x=0;x<4;x++){
		for(int y=0;y<4;y++){
		    if(MatFi[x][y] == i){
			xFi = x;
			yFi = y;
		    }
		}
	    }
	    int manhattanDistance = (Math.abs(xIn-xFi) + Math.abs(yIn-yFi));
	    heuristic += manhattanDistance;
	}
	return heuristic;
    }//end of heuristic

     public PriorityQueue<Node> MakeDescendentsA(Node no){//--------- 
	PriorityQueue<Node> list = new PriorityQueue<Node>();
	for(int i=0;i<4;i++){
	    for(int j=0;j<4;j++){
		if(no.configuracao[i][j] == 0){
		    //CIMA
		    if(i >= 1){
			int[][] m = move(no,'U',i,j);
			String chave = hash_key(m);
			if(!hashTable.contains(chave)){
			    Node no_filho = new Node(m);
			    no_filho.depth = no.depth + 1;	
			    no_filho.cost = heuristicA(no_filho,confFinal);
			    no_filho.pai = no;
			    no_filho.path = 'U';
			    list.add(no_filho);
			    hashTable.add(chave);
			}
		    }
		    //BAIXO
		    if(i <= 2){
			int[][] m = move(no,'D',i,j);
			String chave = hash_key(m);
			if(!hashTable.contains(chave)){
			    Node no_filho = new Node(m);
			    no_filho.depth = no.depth + 1;
			    no_filho.cost = heuristicA(no_filho,confFinal);
			    no_filho.pai = no;
			    no_filho.path = 'D';
			    list.add(no_filho);
			    hashTable.add(chave);
			}
		    }
		    //DIREITA
		    if(j <= 2){
			int[][] m = move(no,'R',i,j);
			String chave = hash_key(m);
			if(!hashTable.contains(chave)){
			    Node no_filho = new Node(m);
			    no_filho.depth = no.depth + 1;
			    no_filho.cost = heuristicA(no_filho,confFinal);
			    no_filho.pai = no;
			    no_filho.path = 'R';
			    list.add(no_filho);
			    hashTable.add(chave);
			}
		    }
		    //ESQUERDA
		    if(j >= 1){
			int[][] m = move(no,'L',i,j);
			String chave = hash_key(m);
			if(!hashTable.contains(chave)){
			    Node no_filho = new Node(m);
			    no_filho.depth = no.depth + 1;
			    no_filho.cost = heuristicA(no_filho,confFinal);
			    no_filho.pai = no;
			    no_filho.path = 'L';
			    list.add(no_filho);
			    hashTable.add(chave);
			}
		    }
		}		
	    }
	}
	return list;
    }

    public int heuristicA(Node cur, Node confFinal){//heuristica usando o manhattanDistance ----------
	int MatCur [][] = cur.configuracao;
	int MatFi [] [] = confFinal.configuracao;
	int heuristic = 0;
	int xIn=0,yIn=0,xFi=0,yFi=0;
	for(int i=1;i<16;i++){
	    for(int x=0;x<4;x++){
		for(int y=0;y<4;y++){
		    if(MatCur[x][y] == i){
			xIn = x;
			yIn = y;
		    }
		}
	    }
	    for(int x=0;x<4;x++){
		for(int y=0;y<4;y++){
		    if(MatFi[x][y] == i){
			xFi = x;
			yFi = y;
		    }
		}
	    }
	    int manhattanDistance = (Math.abs(xIn-xFi) + Math.abs(yIn-yFi));
	    heuristic += (manhattanDistance+cur.cost);
	}
	return heuristic;
    }//end of heuristicA
}
