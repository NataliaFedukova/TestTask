package com.fedukova.task.business;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadByURL {

    /**downloading file by URL and save it in path
     * @param url - URL
     * @param path - PATH
     * @return true if OK, and false if not
     */
    public static void loadFileOnSD(String url, String path) throws ConnectionFaildExeption, IOException {
        {

            URL url_ = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) url_.openConnection();
            try {
                connection.connect();
            }
            catch (IOException e) {
                throw new ConnectionFaildExeption();
            }
            InputStream inputStream = null;
            FileOutputStream fileOutputStream = null;
            try {
                inputStream = new BufferedInputStream(url_.openStream(), 1024);
                fileOutputStream = new FileOutputStream(path);
                byte buffer[] = new byte[1024];
                int bytesRead = -1;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, bytesRead);
                }
                connection.disconnect();
            }
            finally {
                if(fileOutputStream != null)
                {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
                if(inputStream != null)
                    inputStream.close();
            }
        }
    }
}
