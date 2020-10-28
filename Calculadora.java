import java.io.CharArrayReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Calculadora{
    public static void main(String[] args){

        Scanner scan = new Scanner(System.in);
        boolean a = false; //verificador para o loop do dialogo inicial do algoritmo
        boolean b = false; //verificador para o loop do bloco de escolha das operacoes



        while(a == false){
            System.out.println("Deseja realizar operacoes com [i]nteiros, [f]loats ou [s]air do programa?(i/f/s) ");
            String tipo = scan.next();


            //Bloco de processamento de inteiro

            if (tipo.equals("i")){
                a = true;

                System.out.println("Informe o numero de bits com o bit de sinal incluso: ");
                int bits = scan.nextInt();

                System.out.println("Informe o primeiro numero: ");
                char[] numero1 = scan.next().toCharArray();

                System.out.println("Informe o segundo numero: ");
                char[] numero2 = scan.next().toCharArray();

                //preparacao das variaveis necessarias para as operacoes
                char[] num1 = igualaCasas(bits, numero1);
                char[] num2 = igualaCasas(bits, numero2);
                char[] resultado = new char[bits];


                //recebendo operacao
                while(b == false){
                    System.out.println("Digite o operador da operacao desejada: ");
                    String op = scan.next();

                    //adicao
                    if(op.equals("+")){
                        b = true;
                        resultado = adicao(bits, num1, num2);
                        if (temOverflow(num1, num2, resultado)){
                            System.out.println("Overflow da conta, favor testar outros numeros");
                        }
                    }

                    //subtracao
                    //checar se foi arrumado
                    else if(op.equals("-")){
                        b = true;
                        resultado = subtracao(bits, num1, num2);
                        

                    }

                    //multiplicacao
                    else if(op.equals("*")){
                        b = true;
                        resultado = MultipInteiros.multip(num1, num2);

                        

                    }

                    //divisao
                    else if(op.equals("/")){
                        b = true;
                        if (verificaZerado(num2) == false) {
                            resultado = dividir(num1, num2);
                        } else {
                            System.out.println("O Divisor nao pode ser 0");
                            for (int i = 0; i < resultado.length; i++) {
                                resultado[i] = 'x';
                            }
                        }
                    }

                    //tratamento de excessoes
                    else {
                        System.out.println("Favor utilizar somente os simbolos '+' '-' '*' '/' para informar o operador desejado.");
                    }
                }

                //resultado
                System.out.print("O resultado da operacao eh: ");
                for (int h=0; h<resultado.length; h++){
                    System.out.print(resultado[h]);
                }

                System.out.println();


                System.out.println("Deseja continuar (s/n)? ");

                String continuar = scan.next();

                if (continuar.equals("s")){
                    a = false;
                    b = false;
                }
            }


            //Bloco de processamento de float

            else if(tipo.equals("f")){
                a = true;
                PonFlut num1      = new PonFlut();
                PonFlut num2      = new PonFlut();
                PonFlut resultado = new PonFlut();


                System.out.println("Digite o sinal do primeiro numero: ");
                num1.sinal = scan.next().charAt(0);

                System.out.println("Digite a mantissa do primeiro numero: ");
                num1.mantissa = scan.next().toCharArray();

                System.out.println("Digite o expoente do primeiro numero: ");
                num1.expoente = scan.next();


                System.out.println("Digite o sinal do segundo numero: ");
                num2.sinal = scan.next().charAt(0);

                System.out.println("Digite a mantissa do segundo numero: ");
                num2.mantissa = scan.next().toCharArray();

                System.out.println("Digite o expoente do segundo numero: ");
                num2.expoente = scan.next();


                while(b == false){
                    System.out.println("Digite o operador da operacao desejada: ");
                    String op = scan.next();

                    //adicao
                    if(op.equals("+")){
                        b = true;
                        resultado = somaflut(num1, num2);
                    }

                    //subtracao
                    else if(op.equals("-")){
                        b = true;
                        num2.sinal = (num2.sinal == '+') ? '-':'+';
                        resultado = somaflut(num1, num2);
                    }

                    //multiplicacao
                    else if(op.equals("*")){
                        b = true;
                        //resultado.mantissa = multipFlut(num1, num2);
                        resultado = novamulti(num1, num2);
                    }

                    //divisao
                    else if(op.equals("/")){
                        b = true;
                        resultado = dividirFloat(num1, num2);

                    }

                    //tratamento de excessoes
                    else {
                        System.out.println("Favor utilizar somente os simbolos '+' '-' '*' '/' para informar o operador desejado.");
                    }


                    //saida padrao transformando em ieee
                    //else{
                        String saida = new String("");
                        if (resultado.sinal == '+'){
                            saida+="0";
                        }else{
                            saida+="1";
                        }
                        saida += resultado.expoente;
                        for (int i = 0; i <resultado.mantissa.length ; i++) {
                            saida += resultado.mantissa[i];
                        }
                        System.out.println(saida);
                    //}
                }


                System.out.println("Deseja continuar (s/n)? ");

                String continuar = scan.next();

                if (continuar.equals("s")){
                    a = false;
                    b = false;
                }
                else if(continuar.equals("n")){
                    a = true;
                    b = true;
                }
            }

            //saida no inicio do programa
            else if (tipo.equals("s")){
                a = true;

            }


            //Tratamento de excessoes
            else{
                System.out.println("Entrada incorreta, utilize apenas as letras 'i', 'f' ou 's'");

            }
        }
    }

    //metodo que checa se aconteceu overflow
    private static boolean temOverflow(char[] num1, char[] num2, char[] resultado) {
        if (num1[0] == num2[0]){
            if (num1[0] != resultado[0]) {
                return true;
            }
        }
        return false;


    }


    //metodo que iguala o numero de algarismos dos numeros informados
    //e utiliza o complemento de 2 se necessario
    public static char[] igualaCasas(int bits, char[] original0){

        //cria uma copia do array passado como parametro
        //para facilitar a manipulacao
        char[] original = new char [original0.length];
        for (int c = 0; c<original0.length ; c++){
            original[c] = original0[c];
        }

        //cria e preenche um array com o numero certo de algarismos
        char[] novo = new char [bits];
        for (int c = 0; c<bits ; c++){
            novo[c] = '0';
        }

        //confere se o numero eh negativo para usar o complemento de 2
        if((original[0]+"").equals("-")){
            original[0] = '0';
            int y = novo.length-1;
            for (int c = original.length-1; c>=0 ; c--){
                novo[y] = original[c];
                y--;
            }
            novo = complemento2(novo, 0);
        }

        //apenas transfere o numero informado para o array criado no inicio
        else {
            int y = novo.length-1;
            for (int c = original.length-1; c>=0 ; c--){
                novo[y] = original[c];
                y--;
            }
        }

        return novo;
    }



    //metodo que inverte todos os bits do numero
    public static char[] inverteBits(char[] num){

        //cria uma copia do array passado como parametro
        char[] n = new char[num.length];
        for(int u = num.length-1; u>=0; u--){
            n[u] = num[u];
        }

        //inversao dos bits
        for(int i=0; i<num.length; i++){
            if(n[i]=='0'){
                n[i] = '1';
            }
            else if(n[i] == '1'){
                n[i] = '0';
            }
        }
        return n;
    }



    //metodo que aplica o complemento de 2
    public static char[] complemento2(char[]n, int m){


        //cria uma copia do array passado como parametro
        char[] num = new char[n.length];
        for(int u = n.length-1; u>=0; u--){
            num[u] = n[u];
        }

        //confere se o numero eh negativo para usar o complemento de 2 
        //**utilizado apenas pela multiplicacao */
        if(m == 1){
            if((num[0]+"").equals("-")){
                num[0] = '0';
            }
            else {
                return num;
            }
        }
        

            //inversao dos valores dos bits
            num = inverteBits(num);

            //soma no bit mais significativo a direita
            if(num[num.length-1] == '1'){
                //num = somaRecursiva(num, num.length-1);
                char[] add = new char [num.length];
                for(int b = 0; b<num.length; b++){
                    add[b] = '0';
                }
                add[num.length-1] = '1';
                num = adicao(num.length, num, add);
            }
            else{
                num[num.length-1] = '1';
            }
        


        return num;
    }


	public static boolean verificaZerado(char[] numeroVerificar){
        for (int i = 0; i < numeroVerificar.length; i++) {
            if (numeroVerificar[i] != '0'){
                return false;
            }
        }
        return true;
    }
	
	//metodo que verifica se o dividendo é maior que o divisor
    public static String comparaMaior(char[] dividendo, char[] divisor){
	    char[] dividendoAuxiliar = dividendo;
	    char[] divisorAuxiliar   = divisor;

	    if(dividendo[0] == '1') dividendoAuxiliar = complemento2(dividendo  , dividendo.length);
	    if(divisor[0]   == '1') divisorAuxiliar   = complemento2(divisor    , divisor.length);

        for (int i = 0; i <dividendo.length ; i++) {
            if (dividendoAuxiliar[i] == '1' && divisorAuxiliar[i] == '0') {
                return "dividendo";
            }
            if (dividendoAuxiliar[i] == '0' && divisorAuxiliar[i] == '1') {
                return "divisor";
            }

        }
        return "iguais";
    }

    //realiza a soma de dois numeros binarios
    public static char[] adicao(int bits, char[] num1, char[] num2){


        //cria uma copia do array passado como parametro
        //para facilitar a manipulacao
        char[] n1 = new char[num1.length];
        for(int u = num1.length-1; u>=0; u--){
            n1[u] = num1[u];
        }
        char[] n2 = new char[num2.length];
        for(int u = num2.length-1; u>=0; u--){
            n2[u] = num2[u];
        }


        char[] resultado = new char[bits];
        char sobra = '0'; //variavel que armazena o "sobe um" da adicao


        //inicio das operacoes bit a bit
        for(int x=bits-1; x>=0; x--){

            if(n1[x] == '1'){

                if(n2[x] == '0'){
                    if(sobra == '1'){
                        resultado[x] = '0';
                        sobra = '1';
                    }
                    else if(sobra == '0'){
                        resultado[x] = '1';
                    }
                }

                else if(n2[x] == '1'){
                    if(sobra == '1'){
                        resultado[x] = '1';
                        sobra = '1';
                    }
                    if(sobra == '0'){
                        resultado[x] = '0';
                        sobra = '1';
                    }
                }
            }


            else if(n1[x] == '0'){
                if(n2[x] == '0'){
                    if(sobra == '1'){
                        resultado[x] = '1';
                        sobra = '0';
                    }
                    else if(sobra == '0'){
                        resultado[x] = '0';
                    }
                }

                else if(n2[x] == '1'){
                    if(sobra == '1'){
                        resultado[x] = '0';
                        sobra = '1';
                    }
                    if(sobra == '0'){
                        resultado[x] = '1';
                    }
                }
            }

        }

        return resultado;


    }


    //realiza a subtracao de dois numeros binarios
    public static char[] subtracao(int bits, char[] num1, char[]num2){

        //cria uma copia do array passado como parametro
        char[] n1 = new char[num1.length];
        for(int u = num1.length-1; u>=0; u--){
            n1[u] = num1[u];
        }
        char[] n2 = new char[num2.length];
        for(int u = num2.length-1; u>=0; u--){
            n2[u] = num2[u];
        }

        char[] resultado = new char[bits];

        //checa se os numeros sao iguais para retornar zero
        boolean diferentes = false;
        for(int i = 0; i<bits; i++){
            if(n1[i] != n2[i]){
                diferentes = true;
            }
        }
        if (diferentes == false){

            
            for(int i = 0; i<resultado.length; i++){

                resultado[i] = '0';

            }

            return resultado;
            

        }

        

        n2 = complemento2(n2, 0);
        resultado = adicao(bits, n1, n2);
        if (temOverflow(num1, num2, resultado)) {
            System.out.println("Overflow da conta, favor testar outros numeros");
        }
        return resultado;
        

    }

    //realiza a divisao de dois numeros binarios
    public static char[] dividir(char[] dividendo, char[] divisor){
        String maior = "";
        char[] resultado = new char[dividendo.length];
        boolean dividendoZerado = verificaZerado(dividendo);
        boolean bitSinalNegativo = (dividendo[0] != divisor[0]) ? true : false;


        //crio um array com o mesmo tamanho que o divisor e o dividendo contendo apenas o numero 1 para somar ao resultado
        char[] numero1 = new char[dividendo.length];
        for (int i = 0; i <dividendo.length-1; i++) {
            numero1[i] = '0';
        }
        numero1[numero1.length-1] = '1';

        for (int i = 0; i <resultado.length ; i++) {
            resultado[i] = '0';
        }

        //compara os dois pra ver se o dividendo é menor
        maior = comparaMaior(dividendo, divisor);

        if (maior == "divisor"){
            return resultado;
        }
        if(maior == "iguais"){
            return numero1;
        }

        //para fazer a divisão em si vou usar o método do complemento. Faço a subtração até não ser mais possível.
        //não é mais possivel quando o dividendo for 0 ou o divisor for maior que o dividendo
        //
        if(bitSinalNegativo){
            if (dividendo[0] == '1'){
                dividendo = complemento2(dividendo, dividendo.length);
            }
            if(divisor[0] == '1'){
                divisor = complemento2(divisor, divisor.length);
            }
        }
        while (maior == "dividendo" && dividendoZerado == false){
            dividendo = subtracao(dividendo.length, dividendo, divisor);
            resultado = adicao(dividendo.length, resultado, numero1);
            maior = comparaMaior(dividendo, divisor);
            dividendoZerado = verificaZerado(dividendo);
        }

        //Faço uma última verificação para ver se o dividendo é igual ao divisor.
        //Se for precisamos fazer a divisão uma última vez.
        for (int i = 0; i <dividendo.length ; i++) {
            if (dividendo[i] != divisor[i]){
                if (bitSinalNegativo){
                    resultado = complemento2(resultado, resultado.length);
                }
                return resultado;
            }
        }

        dividendo = subtracao(dividendo.length, dividendo, divisor);
        resultado = adicao(dividendo.length, resultado, numero1);

        // botar o bit de sinal correto no resultado:

        if(bitSinalNegativo){
            resultado = complemento2(resultado,resultado.length);
        }


        return resultado;
    }




   /*
    Método que realiza a soma de dois números em ponto flutuante seguindo o fluxograma do livro do Stallings
    1. Verificar se o primeiro ou o segundo número são zero, se algum for, devolver o outro como resposta. (ok)
    2.Verificar se os expoentes são iguais. Se forem diferentes, devemos aumentar o expoente menor e mover seu significando(parte fracionária da mantissa) pra direita.
    3.Soma os significandos (vamos usar a função de soma de inteiros já implementada)
    4.Se a soma dos significandos der 0, devolvemos 0 como resposta.
    5.Se a soma for diferente de 0, verificamos se houve overflow do significando
    6.Se tiver overflow do significando, movemos ele para a direita e aumentamos o expoente
    7.Verificamos se teve overflow do expoente
    8.Se tiver notificamos overflow e retorna nulo
    9.Se não tiver overflow do expoente verificamos se o resultado está normalizado (com o bit mais significativo na primeira posição)
    10.Se o resultado não estiver normalizado movemos o bit mais significativo para a esquerda, e a cada movimento decrescemos o expoente em 1
    11.Verificamos se houve underflow do expoente.
    12.Se tiver underflow do expoente notificamos e retorna nulo.
    13.Retorna o resultado.
     */

    public static PonFlut somaflut (PonFlut num1, PonFlut num2){

        PonFlut resultado = new PonFlut();

        //1.verificar zeros, se um dos números da soma for 0, devolvo o outro


        if (ponFlutZerado(num1)){
            num2.expoente = transformaExpoenteI3e(num2.expoente);
            num2.mantissa = transformaMantissaI3e(num2.mantissa);
            return num2;
        }

        if (ponFlutZerado(num2)){
            num1.expoente = transformaExpoenteI3e(num1.expoente);
            num1.mantissa = transformaMantissaI3e(num1.mantissa);
            return num1;
        }

        //preciso pegar o 1. e mover o ponto expoente casas pra direita, diminuindo o expoente a cada transferência

        //Auxiliares para trabalharmos com os expoentes.
        int expDec1 = Integer.parseInt(num1.expoente, 2);
        int expDec2 = Integer.parseInt(num2.expoente, 2);
        char expoenteMenor = ' ';
        int expResposta;

        //Transformo as mantissas em um ArrayList para poder trabalhar com as posições dos indices.
        List<Character> mantissa1 = new ArrayList<Character>();
        List<Character> mantissa2 = new ArrayList<Character>();
        for (int i = 0; i <num1.mantissa.length && i < 23 ; i++) {
            mantissa1.add(num1.mantissa[i]);
        }
        for (int i = 0; i <num2.mantissa.length && i < 23 ; i++) {
            mantissa2.add(num2.mantissa[i]);
        }

        mantissa1.remove(0);
        mantissa2.remove(0);
        mantissa1.set(0, '1');
        mantissa2.set(0, '1');


        //2.Se os expoentes forem diferentes devemos primeiro igualar os dois e transferir a alteração para o significando que teve o menor número
        while(expDec2 != expDec1){
            if(expDec2>expDec1){
                mantissa1.add(0,'0');
                expDec1++;
                if (mantissa1.size() > 23) mantissa1.remove(mantissa1.size()-1);
                expoenteMenor = '1';
            }
            if(expDec2<expDec1){
                mantissa2.add(0,'0');
                expDec2++;
                if (mantissa2.size() > 23) mantissa2.remove(mantissa2.size()- 1);
                expoenteMenor = '2';
            }
        }

        expResposta = expDec1;

        //Deixo as duas mantissas com o mesmo tamanho
        while (mantissa1.size() != mantissa2.size()){
            if (mantissa1.size() < mantissa2.size()){
                if (expoenteMenor == '1'){
                    mantissa1.add(0,'0');
                }
                else{
                    mantissa1.add('0');
                }
            }
            if (mantissa2.size() < mantissa1.size()){
                if (expoenteMenor == '2'){
                    mantissa2.add(0,'0');
                }
                else{
                    mantissa2.add('0');
                }
            }
        }

        //coloco o bit de sinal
        mantissa1.add(0,'0');
        mantissa2.add(0,'0');

        //crio variáveis auxiliares para usar a função de soma já implementada
        char[] arrayPraSoma1 = new char[mantissa1.size()];
        char[] arrayPraSoma2 = new char[mantissa2.size()];
        for (int i = 0; i < mantissa1.size() ; i++) {
            arrayPraSoma1[i] = (char)mantissa1.get(i);
            arrayPraSoma2[i] = (char)mantissa2.get(i);
        }

        //Verifico o sinal do número inserido pelo usuário, para em sinal '-' chamar o complemento de 2 antes da soma.
        arrayPraSoma1 = (num1.sinal == '-') ? complemento2(arrayPraSoma1, arrayPraSoma1.length) : arrayPraSoma1;
        arrayPraSoma2 = (num2.sinal == '-') ? complemento2(arrayPraSoma2, arrayPraSoma2.length) : arrayPraSoma2;

        //4.Chamamos a função de soma para inteiros. Verificamos se veio zerado, se vier retornamos 0 para a chamada.
        resultado.mantissa = adicao(mantissa1.size(), arrayPraSoma1, arrayPraSoma2);

        if(verificaZerado(resultado.mantissa)){
            resultado.sinal = '+';
            resultado.expoente = "00000000";
            return resultado;
        }

        //5.Se tiver overflow da soma vamos deslocar o significando à direita e incrementar o expoente
        //6.além de verificar o overflow vamos movimentar o significante pra direita e aumentar o expoente.
        List<Character> mantissaResultado = new ArrayList<Character>();
        for (int i = 0; i < resultado.mantissa.length; i++) {
            mantissaResultado.add(resultado.mantissa[i]);
        }
        if (temOverflow(arrayPraSoma1, arrayPraSoma2, resultado.mantissa)){
            mantissaResultado.remove(0);
            mantissaResultado.add(0,'1');
            mantissaResultado.remove(mantissaResultado.size()-1);
            expResposta++;
        }else{
            resultado.mantissa = (resultado.mantissa[0] == '0') ? resultado.mantissa : complemento2(resultado.mantissa, resultado.mantissa.length);
            for (int i = 0; i <resultado.mantissa.length ; i++) {
                mantissaResultado.set(i, resultado.mantissa[i]);
            }
        }

        //7. verificar se teve overflow do expoente (expoente  + 127 > 255)
        //8. Se tiver overflow notificamos e retornamos null
        if(expResposta + 127 > 255){
            System.out.println("Overflow do expoente");
            return null;
        }

        //9. Trazer o bit mais significativo para a esquerda, diminuirdo o expoente a cada vez (já verificamos retorno 0 na mantissa, então não tem como dar loop aqui.)
        resultado.mantissa = new char[23];

        while(mantissaResultado.get(0) != '1'){
            mantissaResultado.remove(0);
            mantissaResultado.add(mantissaResultado.size(),'0');
            expResposta--;
        }

        //10.Verificamos se houve underflow do expoente

        if (expResposta + 127 < 0){
            System.out.println("Underflow do expoente");
            return null;
        }

        //Quando o bit mais significativo estiver à esquerda, removemos ele e adicionamos 0s à direita até 23 bits para adequar ao padrão IEEE
        mantissaResultado.remove(0);
        mantissaResultado.add(mantissaResultado.size(), '0');
        while(mantissaResultado.size() < 23){
            mantissaResultado.add('0');
        }

        for (int i = 0; i < mantissaResultado.size(); i++) {
            resultado.mantissa[i] = mantissaResultado.get(i);
        }

        resultado.expoente = Integer.toBinaryString(expResposta + 127);
        while(resultado.expoente.length() < 8){
            resultado.expoente = "0" + resultado.expoente;
        }
        resultado.sinal = (expoenteMenor == '1') ? num2.sinal : num1.sinal;
        return resultado;

    }

    
    //Função que transforma a mantissa para o formato IEEE para o caso de um dos floats vir zerado
    private static char[] transformaMantissaI3e(char[] mantissa){

        char[] resultado = new char[23];

        //tamanho original do vetor para facilitar a adição de elementos para formatar no final
        int tamanhoOriginal = mantissa.length -2;

        //ArrayList para facilitar a manipulação
        List<Character> mantissaResultado = new ArrayList<Character>();
        for (int i = 0; i < mantissa.length; i++) {
            mantissaResultado.add(mantissa[i]);
        }

        //Removo os dois primeiros termos ('1' e '.')
        mantissaResultado.remove(0);
        mantissaResultado.remove(0);

        //Devolvo o ArrayList pro vetor resultado
        for (int i = 0; i < mantissaResultado.size(); i++) {
            resultado[i] = mantissaResultado.get(i);
        }

        //Completo os zeros para o vetor ter 23 bits
        for (int i = tamanhoOriginal; i < 23; i++) {
            resultado[i] = '0';

        }

        return resultado;
    }

    private static String transformaExpoenteI3e(String expoente){

        while(expoente.length() < 8){
            expoente =  expoente + "0";
        }

        return expoente;
    }

    //funcao que realiza a multiplicacao de floats
    public static PonFlut novamulti (PonFlut num1, PonFlut num2){

        //cria e zera o resultado
        PonFlut resultado = new PonFlut();
        resultado.mantissa = ("0.0").toCharArray();
        resultado.expoente = "0";
        resultado.sinal = '+';

        //verificar zeros, se um dos números da conta for 0, devolvo 0
        boolean zerado1 = true;
        for (int i = 0; i < num1.mantissa.length; i++) {
            if (num1.mantissa[i] == '1') zerado1 = false;
        }
        boolean zerado2 = true;
        for (int i = 0; i < num2.mantissa.length; i++) {
            if (num2.mantissa[i] == '1') zerado2 = false;
        }

        if (zerado1 || zerado2){
            resultado.expoente = transformaExpoenteI3e(resultado.expoente);
            resultado.mantissa = transformaMantissaI3e(resultado.mantissa);
            return resultado;
        }


        //calculando o sinal do resultado
        if(num1.sinal == '-' && num2.sinal == '-'){
            resultado.sinal = '+';
        }
        else if(num1.sinal == '-' ^ num2.sinal == '-'){
            resultado.sinal = '-';
        }


        //igualar os expoentes
        int expDec1 = Integer.parseInt(num1.expoente, 2);
        int expDec2 = Integer.parseInt(num2.expoente, 2);
        int shift = 0;
        char expoenteMenor = ' ';
        while(expDec2 != expDec1){
            if(expDec2>expDec1){
                expDec2--;
                shift++;
                expoenteMenor = '1';
            }
            if(expDec2<expDec1){
                expDec2++;
                shift++;
                expoenteMenor = '2';
            }
        }



        //Transformo as mantissas em um ArrayList para poder remover o atributo na segunda posicao com facilidade
        List<Character> mantissa1 = new ArrayList<Character>();
        List<Character> mantissa2 = new ArrayList<Character>();
        for (int i = 0; i <num1.mantissa.length ; i++) {
            mantissa1.add(num1.mantissa[i]);
        }
        for (int i = 0; i <num2.mantissa.length ; i++) {
            mantissa2.add(num2.mantissa[i]);
        }

        //remover o ponto da mantissa
        mantissa1.remove(1);
        mantissa2.remove(1);

        //igualar o tamanho das mantissas para o ponto estar na mesma posição em ambas

        while(mantissa1.size() != mantissa2.size()){
            if(mantissa1.size() < mantissa2.size()){
                mantissa1.add('0');
            }
            else{
                mantissa2.add('0');
            }
        }

        //transferir a alteração do expoente para a mantissa e caso haja underflow vamos remover o digito menos significativo
        if(expoenteMenor == '1'){
            for (int i = 0; i < shift; i++) {
                mantissa1.add(0,'0');
                if (mantissa1.size() > 23){
                    mantissa1.remove(mantissa1.size()-1);
                }
            }
        }else if(expoenteMenor == '2'){
            for (int i = 0; i < shift; i++) {
                mantissa2.add(0,'0');
                if (mantissa2.size() > 23){
                    mantissa2.remove(mantissa2.size()-1);
                }
            }
        }


        //transformar as duas em binarios de 23 bits para fazer a operacao
        while(mantissa1.size() < 23){
            mantissa1.add(0,'0');
        }
        while (mantissa2.size() < 23){
            mantissa2.add(0,'0');
        }




        //crio variáveis auxiliares para usar a função de multiplicacao já implementada
        char[] arrayPraMultiplicar1 = new char[mantissa1.size()];
        char[] arrayPraMultiplicar2 = new char[mantissa2.size()];
        for (int i = 0; i < mantissa1.size() ; i++) {
            arrayPraMultiplicar1[i] = (char)mantissa1.get(i);
            arrayPraMultiplicar2[i] = (char)mantissa2.get(i);
        }

        if(num1.mantissa[0] == '-'){
            arrayPraMultiplicar1 = complemento2(arrayPraMultiplicar1, 0);
        }
        if(num2.mantissa[0] == '-'){
            arrayPraMultiplicar2 = complemento2(arrayPraMultiplicar2, 0);
        }

        resultado.mantissa = MultipInteiros.multip(arrayPraMultiplicar1, arrayPraMultiplicar2);
        /*if (resultado.mantissa[0] == '0'){
            resultado.sinal = '+';
        }else{
            resultado.sinal = '-';
        }*/


        //Se tiver overflow da conta vamos deslocar o significando à direita e incrementar o expoente
        List<Character> mantissaResultado = new ArrayList<Character>();
        for (int i = 0; i < resultado.mantissa.length; i++) {
            mantissaResultado.add(resultado.mantissa[i]);
        }

        if (temOverflow(arrayPraMultiplicar1, arrayPraMultiplicar2, resultado.mantissa)){
            mantissaResultado.remove(0);
            mantissaResultado.add(0,'1');
            mantissaResultado.remove(mantissaResultado.size());
            expDec1++;
        }
        else{
            mantissaResultado.remove(0);
        }

        
        //preparando a mantissa para o padrao ieee
        resultado.mantissa = new char[23];

        while(mantissaResultado.get(0) != '1'){
            mantissaResultado.remove(0);
            mantissaResultado.add(mantissaResultado.size(),'0');
        }

        mantissaResultado.remove(0);
        mantissaResultado.add(mantissaResultado.size(), '0');

        for (int i = 0; i < mantissaResultado.size(); i++) {
            resultado.mantissa[i] = mantissaResultado.get(i);
        }



        //passar o expoente pro padrão IEEE
        

        expDec1 = expDec1 + expDec2 + 127;
        if (expDec1 < 0){
            System.out.println("Underflow do expoente");
            return null;
        }
        if(expDec1 >= 225){
            System.out.println("Overflow do expoente");
            return null;
        }

        resultado.expoente = Integer.toBinaryString(expDec1);

        return resultado;

    }



    /*
        Função que faz a divisão de dois floats seguindo os passos do livro de Stallings.
        Primeiro verificamos se a mantissa de um dos dois números é 0, se for devolvemos um aviso e 0
        Segundo subtrair o expoente do dividendo pelo do divisor
        Terceiro somar a polarização, se der overflow ou underflow devolver um aviso
        Finalmente dividir os coeficientes e devolver
     */
    public static PonFlut dividirFloat(PonFlut num1, PonFlut num2){
        boolean divisorZerado = true;
        boolean dividendoZerado = true;
        int expResultado;

        //cria e zera o resultado
        PonFlut resultado = new PonFlut();
        resultado.mantissa = ("0.0").toCharArray();
        resultado.expoente = "0";
        resultado.sinal = '+';

        if (ponFlutZerado(num1)){
            System.out.println("Dividendo Zerado");
            resultado.expoente = transformaExpoenteI3e(resultado.expoente);
            resultado.mantissa = transformaMantissaI3e(resultado.mantissa);
            return resultado;
        }
        if (ponFlutZerado(num2)){
            System.out.println("Divisor Zerado");
            resultado.expoente = transformaExpoenteI3e(resultado.expoente);
            resultado.mantissa = transformaMantissaI3e(resultado.mantissa);
            return resultado;
        }

        //subtrair o expoente do dividendo do expoente do divisor

        int expDec1 = Integer.parseInt(num1.expoente, 2);
        int expDec2 = Integer.parseInt(num2.expoente, 2);
        expResultado = expDec1 - expDec2;

        //somar a polarização (+127) e verificar se deu overflow ou underflow

        expResultado += 127;
        if (expResultado < 0){
            System.out.println("Underflow do expoente");
            return null;
        }
        if(expResultado >= 225){
            System.out.println("Overflow do expoente");
            return null;
        }

        //fazer a divisão das mantissas
        //pra fazer a divisão precisamos de um número de bits definido
        //Transformo as mantissas em um ArrayList para poder remover o atributo na posição 1 com facilidade
        List<Character> mantissa1 = new ArrayList<Character>();
        List<Character> mantissa2 = new ArrayList<Character>();
        for (int i = 0; i <num1.mantissa.length ; i++) {
            mantissa1.add(num1.mantissa[i]);
        }
        for (int i = 0; i <num2.mantissa.length ; i++) {
            mantissa2.add(num2.mantissa[i]);
        }

        //remover o ponto da mantissa, assim quando igualarmos as casas fica apenas a soma a fazer
        mantissa1.remove(1);
        mantissa2.remove(1);

        //igualar o tamanho das mantissas para o ponto estar na mesma posição em ambas

        while(mantissa1.size() != mantissa2.size()){
            if(mantissa1.size() < mantissa2.size()){
                mantissa1.add('0');
            }
            else{
                mantissa2.add('0');
            }
        }
        char[] divisor   = new char[mantissa1.size()];
        char[] dividendo = new char[mantissa1.size()];
        for (int i = 0; i < mantissa1.size(); i++) {
            divisor[i]   = mantissa2.get(i);
            dividendo[i] = mantissa1.get(i);
        }

        //passar a mantissa para o formato IEEE com 23 bits
        char[] mantissaResultado = new char[23];
        resultado.mantissa = dividir(dividendo, divisor);
        for (int i = 0; i < 23; i++) {
            mantissaResultado[i] = (i < resultado.mantissa.length) ? resultado.mantissa[i] : '0';
        }


        resultado.mantissa = mantissaResultado;
        resultado.sinal = (num1.sinal != num2.sinal) ? '-':  '+';
        resultado.expoente = Integer.toBinaryString(expResultado);

        return resultado;

    }

    public static boolean ponFlutZerado(PonFlut verificar){
        boolean zerado = true;
        for (int i = 1; i < verificar.mantissa.length; i++) {
            if (verificar.mantissa[i] == '1') zerado = false;
        }
        for (int i = 0; i < verificar.expoente.length() ; i++) {
            if (verificar.expoente.charAt(i) == '1') zerado = false;
        }
        return zerado;
    }

}
