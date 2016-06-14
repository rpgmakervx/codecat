package org.code4j.codecat;/**
 * Description : 
 * Created by YangZH on 16-6-14
 *  上午1:16
 */

import java.io.*;
import java.net.Socket;

/**
 * Description :
 * Created by YangZH on 16-6-14
 * 上午1:16
 */

public class Client {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost",8000);
        PrintStream ps = new PrintStream(socket.getOutputStream());
        ps.println("hello world");
        BufferedReader br = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        String response = br.readLine();
        System.out.println("reponse: "+response);
        socket.close();
    }
}
