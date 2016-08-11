/* 
Copyright Paul James Mutton, 2001-2004, http://www.jibble.org/

This file is part of Mini Wegb Server / SimpleWebServer.

This software is dual-licensed, allowing you to choose between the GNU
General Public License (GPL) and the www.jibble.org Commercial License.
Since the GPL may be too restrictive for use in a proprietary application,
a commercial license is also provided. Full license information can be
found at http://www.jibble.org/licenses/
*/

package org.jibble.simplewebserver;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright Paul Mutton
 * http://www.jibble.org/
 * This version is available at https://github.com/rafaelsteil/simple-webserver
 */
public class SimpleWebServer extends Thread {
    private File rootDir;
    private ServerSocket serverSocket;
    private boolean _running = true;

    public static final String VERSION = "SimpleWebServer  http://www.jibble.org/";
    public static final Map<String, String> MIME_TYPES = new HashMap<String, String>();
    
    static {
        String image = "image/";
        MIME_TYPES.put(".gif", image + "gif");
        MIME_TYPES.put(".jpg", image + "jpeg");
        MIME_TYPES.put(".jpeg", image + "jpeg");
        MIME_TYPES.put(".png", image + "png");
        
        String text = "text/";
        MIME_TYPES.put(".html", text + "html");
        MIME_TYPES.put(".htm", text + "html");
        MIME_TYPES.put(".txt", text + "plain");
    }
    
    /**
     * Starts the webserver
     * @param rootDir The root directory where the server should run
     * @param port the port to listen at
     * @throws IOException
     */
    public SimpleWebServer(File rootDir, int port) throws IOException {
        this.rootDir = rootDir.getCanonicalFile();
        
        if (!this.rootDir.isDirectory()) {
            throw new IOException("Not a directory.");
        }
        
        serverSocket = new ServerSocket(port);
        start();
    }
    
    /**
     * Changes the root directory where the server runs.
     * This method can be called while the server is running, and it will take effect right 
     * in the next request.  
     * @param rootDir the new directory.
     */
    public void setRootDir(File rootDir) {
    	this.rootDir = rootDir;
    }
    
    /**
     * Stops the webserver
     */
    public void stopServer() {
    	try {
    		serverSocket.close();
    		interrupt();
    	}
    	catch (Exception e) {}
    }
    
    public void run() {
        while (_running) {
            try {
                Socket socket = serverSocket.accept();
                RequestThread requestThread = new RequestThread(socket, rootDir);
                requestThread.start();
            }
            catch (Exception e) {
                System.exit(1);
            }
        }
    }
    
    public static void main(String[] args) {
        try {
            new SimpleWebServer(new File("./"), 80);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}