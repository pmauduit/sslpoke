import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.Certificate;
import java.io.*;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

public class HttpsClient{

  public static void main(String[] args)
  {
    if (args.length != 1) {
      System.out.println("Usage: HttpsClient <host>");
      return;
    }
    new HttpsClient().testIt(args[0]);
  }

  private void testIt(String https_url){

    URL url;
    try {

      url = new URL(https_url);
      HttpsURLConnection con = (HttpsURLConnection)url.openConnection();

      //dumpl all cert info
      print_https_cert(con);

      //dump all the content
      //print_content(con);

    } catch (Throwable e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  private void print_https_cert(HttpsURLConnection con) throws Exception {

    if(con!=null){

      try {

        System.out.println("Response Code : " + con.getResponseCode());
        System.out.println("Cipher Suite : " + con.getCipherSuite());
        System.out.println("\n");

        Certificate[] certs = con.getServerCertificates();
        for(Certificate cert : certs){
          System.out.println("Cert Type : " + cert.getType());
          System.out.println("Cert Hash Code : " + cert.hashCode());
          System.out.println("Cert Public Key Algorithm : " 
              + cert.getPublicKey().getAlgorithm());
          System.out.println("Cert Public Key Format : " 
              + cert.getPublicKey().getFormat());
          System.out.println("\n");
        }

      } catch (SSLPeerUnverifiedException e) {
        e.printStackTrace();
        throw e;
      } catch (IOException e){
        e.printStackTrace();
        throw e;
      }

    }

  }

  private void print_content(HttpsURLConnection con) throws Exception {
    if(con!=null) {

      try {

        System.out.println("****** Content of the URL ********");
        BufferedReader br =
          new BufferedReader(
              new InputStreamReader(con.getInputStream()));

        String input;

        while ((input = br.readLine()) != null){
          System.out.println(input);
        }
        br.close();

      } catch (IOException e) {
        e.printStackTrace();
        throw e;
      }

    }

  }

}
