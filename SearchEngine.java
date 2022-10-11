import java.net.URI;
import java.util.*;
import java.io.IOException;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    List<String> list=new  ArrayList<String>();


    public String handleRequest(URI url) {
        System.out.println(url);
        if (url.getPath().equals("/add")) {
             String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    list.add(parameters[1]);
                    return String.format(" %s is added to the list.", parameters[1]);
                }
        }  else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                   List<String> filteredlist=new  ArrayList<String>();
                   Iterator<String> it=list.iterator();
                   while(it.hasNext()) {
                    if (it.next().contains(parameters[1]))
                        filteredlist.add(it.next());
                   }

                    return String.format(String.join(",", filteredlist));
                }
            }
          
        }
                 return "404 Not Found!"; 
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        if(args[0].equals("22")){
            System.out.println("Did you know that port 22 is the one used by ssh? We can't use it for a web server.");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
