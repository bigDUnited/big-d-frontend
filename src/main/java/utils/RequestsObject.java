package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.net.www.http.HttpClient;

public class RequestsObject {

    private final String USER_AGENT = "Mozilla/5.0";

    public <T> T get(String url) {

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            //add request header
            con.setRequestProperty("User-Agent", USER_AGENT);
            
            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            System.out.println("Response content : " + response.toString());
            return (T) (String) response.toString();
        } catch (MalformedURLException ex) {
            Logger.getLogger(RequestsObject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RequestsObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (T) (Boolean) false;
    }

    public String post(String url, String params) {
        try {
            URL urlObj = new URL(url);
            URLConnection conn = urlObj.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write(params);
            writer.flush();
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = reader.readLine()) != null) {
                System.out.println("POST REPLY : " + line);
                return line;
            }
            writer.close();
            reader.close();
        } catch (Exception ex) {
            Logger.getLogger(RequestsObject.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
        return "";
    }
}
