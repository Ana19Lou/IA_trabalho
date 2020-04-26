import java.util.*;
//import java.lang.*;

public class game {
    public static Scanner in;
    public static final int tamanho = 4;// tamanho = número de linha = número de colunas (matriz n*n)
    public static int[][] confInicial = new int[tamanho][tamanho];//conficuração inicial
    public static int[][] confFinal = new int[tamanho][tamanho];//conficuração final
    public static int[] auxInicial = new int[tamanho*tamanho];//guarda a configuração inicial em linha
    public static int[] auxFinal = new int[tamanho*tamanho];//guarda a configuração final em linha
    public static int blankRowInicial;//linha da celula em branco (configuaração inicial)
    public static int blankRowFinal;//linha da celula em branco (configuaração final)
    public static Tabuleiro game;
    
    public static void main(String[] args) {
	logo();

	in = new Scanner(System.in);

	System.out.println("Configuração inicial:");
	//------------leitura da configuaração inicial-----------
	for (int i = 0; i < tamanho; i++) {
	    for (int j = 0; j < tamanho; j++) {
		int valor = in.nextInt();
		auxInicial[i*tamanho+j] = valor;
		if (valor == 0) {
		    blankRowInicial = i;
		}
		confInicial[i][j] = valor;
	    }
	}

	System.out.println("Configuração final:");
	//------------leitura da configuaração final-----------	
	for (int i = 0; i < tamanho; i++) {
	    for (int j = 0; j < tamanho; j++) {
		int valor = in.nextInt();
		auxFinal[i*tamanho+j] = valor;
		if (valor == 0) {
		    blankRowFinal = i;
		}
		confFinal[i][j] = valor;
	    }
	}


	//----chama a função háSolucao e escreve se há ou não----		
	if(haSolucao()){
	    System.out.printf("\nHá soluções!\n\n");
	    game = new Tabuleiro (confInicial, confFinal);
	    Search ();//chama a função que vai escolher o tipo de busca
	}
	else
	    System.out.printf("\nNão há soluções!\n\n");	
    }//end of main

    //--------verifica se há solução--------------------
    public static boolean haSolucao(){
	boolean condIn = (inversoes(auxInicial) % 2 == 0) == ( blankRowInicial % 2 == 1);
	boolean condFi = (inversoes(auxFinal) % 2 == 0) == ( blankRowFinal % 2 == 1);
       	return condIn == condFi;
    }// end of haSolucao
    
    public static int inversoes(int[] vet) {//conta o número de inversões
	int inversoes = 0;
	for(int i=0;i<vet.length;i++){
	    for(int j=i+1;j<vet.length;j++){
		if(vet [i] > vet [j] && vet [j] != 0)
		    inversoes++;
	    }
	}
	return inversoes;
    }//end of inversoes*/
    
    public static void Search (){ //tipo de busca a escolher
	in = new Scanner(System.in);
	
	System.out.println("*********************************");
	System.out.println("*    Escolher tipo de Busca:    *");
	System.out.println("*********************************");
	System.out.println("1- DFS");
	System.out.println("2- BFS");
	System.out.println("3- DFS Iterativo");
	System.out.println("4- Greedy");
	System.out.println("5- A*");
	System.out.println("\n0- Terminar");
	System.out.print(" > ");
       
	int s= in.nextInt();
	if (s==0){System.exit(0);}
	if (s==1){System.out.println();game.DFS();}
	if (s==2){System.out.println();game.BFS();}
	if (s==3){System.out.println();game.IDFS();}
	if (s==4){System.out.println();game.Greedy();}
	if (s==5){System.out.println();game.AStar();}
	if (s>=6){System.out.println("Opção inválida!\n");Search();}
    }//end of Search

    static void logo(){//-------------
	System.out.println("***********************************");
	System.out.println("***********************************");
	System.out.println("**          JOGO DOS 15          **");
	System.out.println("**                               **");
	System.out.println("**  Carlos Santos   Marcia Dias  **");
	System.out.println("**    201607406      201704466   **");
	System.out.println("***********************************");
	System.out.println("***********************************");
	System.out.println();
    }//end of logo
}
