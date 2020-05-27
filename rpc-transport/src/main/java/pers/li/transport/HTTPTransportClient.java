package pers.li.transport;

import org.apache.commons.io.IOUtils;
import pers.li.rpc.Peer;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPTransportClient implements TransportClient {

    private String uri;

    @Override
    public void connect(Peer peer) {
        this.uri = "http://" + peer.getHost() + ":" + peer.getPort();
    }

    @Override
    public InputStream write(InputStream inputStream) {
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) new URL(uri).openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setRequestMethod("POST");

            urlConnection.connect();
            IOUtils.copy(inputStream, urlConnection.getOutputStream());

            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return urlConnection.getInputStream();
            } else {
                return urlConnection.getErrorStream();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void close() {

    }
}
