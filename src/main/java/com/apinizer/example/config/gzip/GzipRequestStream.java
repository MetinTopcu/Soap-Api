package com.apinizer.example.config.gzip;


import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

public class GzipRequestStream extends ServletInputStream {

    private ServletInputStream inStream = null;
    private GZIPInputStream in = null;

    public GzipRequestStream(HttpServletRequest request) throws IOException {
        this.inStream = request.getInputStream();
        this.in = new GZIPInputStream(this.inStream);
    }

    public int read() throws IOException {
        return this.in.read();
    }

    public int read(byte[] b) throws IOException {
        return this.in.read(b);
    }

    public int read(byte[] b, int off, int len) throws IOException {
        return this.in.read(b, off, len);
    }

    public void close() throws IOException {
        this.in.close();
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {

    }
}