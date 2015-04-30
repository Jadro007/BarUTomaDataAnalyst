package cz.muni.fi.PB138.main.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

public class GetFileFromURL {

    /*public static void main(String[] args) {
        System.out.println("\nOutput: \n" + downloadFile("http://cdn3.crunchify.com/wp-content/uploads/code/json.sample.txt"));
    }*/

    public String downloadFile(String myUrl) {
        StringBuilder builder = new StringBuilder();
        URLConnection connection = null;
        InputStreamReader reader = null;

        try {
            URL url = new URL(myUrl);
            connection = url.openConnection();
            if (connection != null)
                connection.setReadTimeout(60 * 1000);
            if (connection != null && connection.getInputStream() != null) {
                reader = new InputStreamReader(connection.getInputStream(), Charset.defaultCharset());
                BufferedReader bufferedReader = new BufferedReader(reader);
                if (bufferedReader != null) {
                    int c;
                    while ((c = bufferedReader.read()) != -1)
                        builder.append((char) c);
                }
                bufferedReader.close();
            }
            reader.close();
        } catch (IOException ioe) {
            System.err.print("Exception while calling URL, " + ioe);
        }
        return builder.toString();
    }
}
