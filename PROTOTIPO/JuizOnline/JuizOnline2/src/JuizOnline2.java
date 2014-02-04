
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class JuizOnline2 {

    public static void main(String[] args) throws IOException {
        
        String saida = "47";
        String entrada = "";
        String mensagem = "Você deve validar números primos!";
        
        // Make connection and initialize streams
        Socket socket = new Socket("127.0.0.1", 3029);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream out = new PrintStream(socket.getOutputStream(), true);
        
        out.println("Solution.java");
        out.println("10000");
        out.println("public class Solution {$_n_$" +
                    "    public static void main(String[] args) {$_n_$" +
                    "        System.out.print(47);$_n_$" +
                    "    }$_n_$" +
                    "}");
        out.println("10$_n_$9");
        out.println("java");
        
        String status = in.readLine();
        
        String line;
        String content = "";
        while ((line = in.readLine()) != null) {
            content += line;
        }
        
        if (status.equals("0"))
        {
            System.out.println("oops! compile error");
            System.out.println(content);
        }
        else if (status.equals("1"))
        {
            if (content.trim().equals(TrataString(saida).trim()))
                System.out.println("holla! problem solved");
            else
            {
                System.out.println("duh! wrong output");
                System.out.println(mensagem);
            }
        }
        else if (status.equals("2"))
        {
            System.out.println("timeout");
        }
        
        System.exit(0);
    }
    
    private static String TrataString(String valor)
    {
        return valor.replace("\n\r", "\n").replace("\r\n", "\n").replace("\r", "");
    }
}
