

import java.util.Scanner;
 
//funcao que multiplica dois numeros binarios (em complemento de 2 se necessario)
//funcao adaptada do site https://www.sanfoundry.com/java-program-booth-algorithm/
public class MultipInteiros{
    public static Scanner s = new Scanner(System.in);
    public static char[] multip(char[] n1, char[] n2){

        
        

        char[] m = n1;
        char[] m1 = Calculadora.complemento2(m, 0);
        char[] r = n2;
        char[] A = new char[n1.length+n2.length+1];
        char[] S = new char[n1.length+n2.length+1];
        char[] P = new char[n1.length+n2.length+1];
        for (int u = 0; u<A.length; u++){

            A[u] = '0';
            S[u] = '0';
            P[u] = '0';

        }  
        
        
        //preenchimento das linhas da tabela
        for (int i = 0; i < n1.length; i++)
        {
            A[i] = m[i];
            S[i] = m1[i];
        }
        for (int i = 0; i < n2.length; i++)
        {
            P[i+n1.length] = r[i];
        }

 
        //funcionamento do algoritmo de booth
        for (int i = 0; i < n2.length; i++)
        {
            if (P[P.length-2] == '0' && P[P.length-1] == '0');
                // nao faz nada        
            else if (P[P.length-2] == '1' && P[P.length-1] == '0')
                P = Calculadora.adicao(P.length, P, S);                            
            else if (P[P.length-2] == '0' && P[P.length-1] == '1')
                P = Calculadora.adicao(P.length, P, A);            
            else if (P[P.length-2] == '1' && P[P.length-1] == '1');
                // nao faz nada
 
            rightShift(P);
        }

        char[] resultado = new char[P.length-1];
        for(int y = P.length-2; y >= 0; y--){

            resultado[y] = P[y];
            
        }


        char[] temp = new char[n1.length]; //vetor temporario para armazenamento do resultado
        int itera = n1.length-1; //iterador para o vetor temp

        boolean overflow = false;

        for(int i= resultado.length-1; i>=0 ; i--){
            if(i<resultado.length/2){
                if(resultado[i]=='1'){
                    overflow = true;
                }
            }
            if(i>=resultado.length/2){
                temp[itera] = resultado[i];
                itera--;
            }
        }
        resultado = temp;
        if (overflow == true){
            System.out.println("Overflow da conta, favor testar outros numeros");
        }
        


        return resultado;
        
    }

    // Funcao para deslocar os valores 1 casa para a direita
    public static void rightShift(char[] A)
    {        
        for (int i = A.length-1; i >= 1; i--)
            A[i] = A[i - 1];        
    }

    //espaco reservado para testes
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        char[] numero1 = sc.next().toCharArray();
        char[] numero2 = sc.next().toCharArray();
        System.out.println(multip(numero1, numero2));
    }
}