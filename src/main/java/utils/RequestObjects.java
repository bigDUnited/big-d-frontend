package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RequestObjects {

    private final String USER_AGENT = "Mozilla/5.0";

    public <T> T get(String url) {
        System.out.println("Frontend RequestObjects DEBUG: # 'GET' request for "
                + "URL : " + url);

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            //add request header
            con.setRequestProperty("User-Agent", USER_AGENT);

            int responseCode = con.getResponseCode();
            System.out.println("Frontend RequestObjects DEBUG: # Response Code : "
                    + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();
            System.out.println("Frontend RequestObjects DEBUG: # Response content : "
                    + response.toString());
            return (T) (String) response.toString();
        } catch (MalformedURLException ex) {
            System.out.println("Frontend RequestObjects DEBUG: # 'GET' - ERROR - Exception : " + ex);
        } catch (IOException ex) {
            System.out.println("Frontend RequestObjects DEBUG: # 'GET' - ERROR - Exception : " + ex);
        }
        return (T) (Boolean) false;
    }

    public <T> T post(String url, String params) {
        System.out.println("Frontend RequestObjects DEBUG: # 'POST' request for "
                + "URL : " + url + ", parameters : " + params);

        try {
            URL obj = new URL(url);
            URLConnection conn = obj.openConnection();

            //Set to true so that we can send (output) a request body,
            conn.setDoOutput(true);

            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write(params);

            writer.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();

            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }

            writer.close();
            reader.close();
            System.out.println("Frontend RequestObjects DEBUG: # Response content : " + response.toString());
            return (T) (String) response.toString();
        } catch (Exception ex) {
            System.out.println("Frontend RequestObjects DEBUG: # 'POST' - ERROR - Exception : " + ex);
        }
        return (T) (Boolean) false;
    }
}
