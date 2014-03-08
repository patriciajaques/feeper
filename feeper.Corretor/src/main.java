
import feeper.Data.entity.Resposta;
import feeper.Data.service.RespostaService;
import feeper.service.Correcao;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author
 * fabioalves
 */
public class main {
    
    public static void main(String[] args) throws IOException {
        
        int n=0;
        try {
            ServerSocket server = new ServerSocket(3029); // create a new socket to listen on
            System.out.println("Codejudge compilation server running ...");
            while(true) {
                n++;
                // accept any incoming connection and process it on a new thread
                Socket s = server.accept();
                Correcao c = new Correcao(s, n);
                Thread t = new Thread(c);
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
}
