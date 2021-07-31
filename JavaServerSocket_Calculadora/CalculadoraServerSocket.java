import java.io.BufferedReader;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class CalculadoraServerSocket {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerSocket welcomeSocket;
		DataOutputStream socketOutput = null;   	
	    DataInputStream socketInput;
	    BufferedReader socketEntrada;
	    //Instância da classe Calculadora que possui operações básicas de soma, subtração, divisão e multiplicação.
	    Calculadora calc = new Calculadora(); 
	    
		try {
		  welcomeSocket = new ServerSocket(6699);
		  int i = 0; //número de clientes
	  
	      System.out.println("Servidor no ar");
	      while(true) { 
	    	   //connectionSocket 
	           Socket connectionSocket = welcomeSocket.accept(); 
	           i++;
	           System.out.println ("Nova conexão");
	          
	           
	           //DataInputStream in = new DataInputStream(new BufferedInputStream(connectionSocket.getInputStream()));
	           ObjectInputStream socketInput2 = new ObjectInputStream(new BufferedInputStream(connectionSocket.getInputStream()));
	           //ObjectOutputStream out = new ObjectOutputStream(connectionSocket.getOutputStream());
	           
	           
	        try {
				ExpressArvore arvore = (ExpressArvore)socketInput2.readObject();
				
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        
	           
	           //Interpretando dados do servidor
	           socketEntrada = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
               String operacao= socketEntrada.readLine();
               String oper1=socketEntrada.readLine();
               String oper2=socketEntrada.readLine();
               
               //Chamando a calculadora
               String result = "";
               if(Integer.parseInt(operacao)==1) {
               		result= ""+calc.soma(Double.parseDouble(oper1),Double.parseDouble(oper2));
               }else if(Integer.parseInt(operacao)==2) {
            	    result= ""+calc.subtracao(Double.parseDouble(oper1),Double.parseDouble(oper2));
               }else if(Integer.parseInt(operacao)==3) {
           	    	result= ""+calc.divisao(Double.parseDouble(oper1),Double.parseDouble(oper2));
               }else if(Integer.parseInt(operacao)==4) {
          	    	result= ""+calc.multiplicacao(Double.parseDouble(oper1),Double.parseDouble(oper2));
               }
               
               //Enviando dados para o servidor
               socketOutput= new DataOutputStream(connectionSocket.getOutputStream());     	
	           socketOutput.writeBytes(result+ '\n');
	           System.out.println (result);	           
	           socketOutput.flush();
	           socketOutput.close();
	                    
	      }
	      
	      
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    
	}

}
