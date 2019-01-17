package controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

/**

 */
public class WorkerRunnable implements Runnable{

    protected Socket clientSocket = null;
    protected String serverText   = null;
    private static AtomicInteger c = new AtomicInteger(0);
  

    public WorkerRunnable(Socket clientSocket, String serverText) {
        this.clientSocket = clientSocket;
        this.serverText   = serverText;
    }

    public void run() {
        try {
        	System.out.println(c.incrementAndGet());
            InputStream input  = clientSocket.getInputStream();
           // OutputStream output = clientSocket.getOutputStream();
           // long time = System.currentTimeMillis();
           // output.write(("HTTP/1.1 200 OK\n\nWorkerRunnable: " +
           // 		this.serverText + " - " +
           // 		time +
           // 		"").getBytes());*/
            
            
           // if(c.get()%10000==0) System.out.println(c.get());
           // c.incrementAndGet();
            
            //System.out.println("value: "+input.read());
            //output.close();
            
            input.close();
        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }
}