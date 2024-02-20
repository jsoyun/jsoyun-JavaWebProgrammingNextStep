package com.example.nextstep;

import lombok.extern.slf4j.Slf4j;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * RequestHandler 클래스는 사용자의 요청에 대한 처리와 응답에 대한 처리를 담당하는 가장 중심이 되는 클래스다
 */

@Slf4j
public class RequestHandler extends Thread {
    private Socket connection;
    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    @Override
    public void run() {
        log.debug("New Client Connect ! Connected Ip: {}, Port: {}" +connection.getInetAddress(), connection.getPort() );

        //in 클라이언트(웹 브라우저)에서 서버로 요청보낼때 전달되는 데이터 담당하는 스트림
        //out 서버에서 클라이언트로 응답 보낼때 전달되는 데이터 담당하는 스트림
        try(InputStream in = connection.getInputStream();
            OutputStream out = connection.getOutputStream()){
            //TODO: 사용자 요청에 대한 처리는 이곳에 구현하면 된다.
            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = "hello world".getBytes();
            response200Header(dos, body.length);
            responseBody(dos,body);

        } catch (IOException e){
            log.error(e.getMessage());
        }

    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try{
            dos.write(body,0,body.length);
            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e){
            log.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try{
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Context-Type: text/html;charset=utf-8 \r\n");
            dos.writeBytes("Content-Length: "+ lengthOfBodyContent+"\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e){
            log.error(e.getMessage());
        }
    }

    public void start() {
    }
}
