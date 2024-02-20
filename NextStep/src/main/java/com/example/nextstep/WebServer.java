package com.example.nextstep;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * WebServer 클래스는 웹서버를 시작하고 사용자 요청이 있을 때까지 대기 상태에 있다가
 * 사용자 요청이 있을 경우
 * 사용자의 요청을 RequestHandler 클래스에 위임하는 역할을 한다
 */
@Slf4j
public class WebServer {
//    private static final Logger log = (Logger) LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    public static void main(String args[]) throws Exception{
        int port = 0;
        if(args == null || args.length ==0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        //서버 소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다
        try(ServerSocket listenSocket = new ServerSocket(port)){
            log.info("web Application server started ={}" +port);
            //클라이언트가 연결될 때까지 대기한다
            Socket connection;
            while((connection = listenSocket.accept()) != null){
                //socket을 RequestHandler에 전달하면서 새로운 스레드를 실행하는 방식으로 멀티스레드 프로그래밍을 지원한다
                RequestHandler requestHandler =
                        new RequestHandler(connection);

                requestHandler.start();

            }


        }
    }



}
