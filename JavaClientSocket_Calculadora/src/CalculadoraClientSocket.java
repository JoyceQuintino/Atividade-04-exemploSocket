import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;
import com.thoughtworks.xstream.XStream;

import java.math.BigDecimal;

public class CalculadoraClientSocket {

	public static void main(String[] args) {
		
		Scanner read = new Scanner(System.in);
		double oper1,oper2;
		int operacao; 
		String result="";
		String result2="";
		
		ExpressArvore arvore;
		arvore = new ExpressArvore('/');
		arvore.left = new ExpressArvore('-', 50.0, 30.0);
		arvore.right = new ExpressArvore('*', new ExpressArvore('+', 3.0,2.0), 4.0);
		
		int num=1; //inicializa com 1 apenas para o la�o executar
		
		do {
			// TODO Auto-generated method stub
			System.out.printf("Informe o primeiro valor: \n");
			oper1 = read.nextDouble();
			System.out.printf("Informe o segundo valor: \n");
			oper2 = read.nextDouble();
			
			//1-somar 2-subtrair 3-dividir 4-multiplicar
			System.out.printf("Informe a opera��o:\n "
					+ "1 - Soma\n 2 - Subtra��o\n 3 - Divis�o\n 4 - Multiplica��o\n");
			operacao = read.nextInt();
			//O programa deve solicitar novamente os dados caso do denominador igual a 0 e a opera��o de divis�o escolhida.
			if(operacao == 3 && oper2 == 0) {
				System.out.printf("Divis�o imposs�vel de ser realizada.\n");
				continue;
			}
			
	        try {
	        	//Conex�o com o Servidor
	            Socket clientSocket = new Socket("localhost", 6699);
	            
	            //Cria um fluxo de sa�da dos dados
	            //O getOutputStream() envia os dados para o servidor em bytes
	            DataOutputStream socketSaidaServer = new DataOutputStream
	            		(clientSocket.getOutputStream());
	            
	            //Enviando os dados
	            socketSaidaServer.writeBytes(operacao+"\n");
	            socketSaidaServer.writeBytes(oper1+ "\n");
	            socketSaidaServer.writeBytes( oper2+ "\n");
	            socketSaidaServer.flush(); //fo�a que os dados sejam enviados

	            //Recebendo a resposta
	            //� atrav�s do m�todo getInputStream() que o servidor consegue capturar o que o cliente est� enviado.
	            //O objeto messageFromServer recebe os dados enviados para o servidor
	            BufferedReader messageFromServer = new BufferedReader
	                    (new InputStreamReader(clientSocket.getInputStream()));
	            //Ler uma linha por vez dos dados enviados para o servidor e armazena na vari�vel result.
	            result=messageFromServer.readLine();
	            
	            if(Double.parseDouble(result)%2==0) {
	            	System.out.println("resultado= "+result);
	            }else {
	            	//Exibe os dados
		            System.out.println("resultado= "+result);
	            }
	           
	          //Conex�o com o servidor
	            ObjectOutputStream socketSaidaServer2 = new ObjectOutputStream(clientSocket.getOutputStream());
	            
	            //Enviando os dados
	            socketSaidaServer2.writeObject(arvore);
	            socketSaidaServer2.flush();
	            
	            //Recebendo a resposta
	            DataInputStream socketRespostaServer = new DataInputStream(clientSocket.getInputStream());
	            result2 = socketRespostaServer.readUTF();
	            System.out.println("Resultado NOVO="+result2);
	            
	            //Fecha a conex�o com o servidor
	            clientSocket.close();

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        System.out.printf("Deseja continuar?\n 0 - Encerrar\n 1 - Continuar\n");
			num = read.nextInt();
			if(num==0) {
				System.out.printf("Fim!\n");
			}
		}while(num==1);

	}

}
