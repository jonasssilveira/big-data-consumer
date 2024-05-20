package com.example.bigdataconsumer.processor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UdpProcessor {

    public DatagramSocket udpServerSocket() throws SocketException {
        int port = 9876;
        DatagramSocket serverSocket = new DatagramSocket(port);

        new Thread(() -> {
            try {
                System.out.println("Servidor UDP iniciado. Aguardando mensagens...");

                while (true) {
                    byte[] receiveData = new byte[1024]; // Tamanho máximo do buffer
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    serverSocket.receive(receivePacket);

                    // Converter os bytes recebidos em uma String
                    String mensagem = new String(receivePacket.getData(), 0, receivePacket.getLength());

                    System.out.println("Mensagem recebida do cliente: " + mensagem);

                    // Enviar uma resposta ao cliente (opcional)
                    // Você pode usar o mesmo DatagramSocket para enviar a resposta
                    // Basta criar um novo DatagramPacket com os dados de resposta e o endereço/porta do cliente
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        return serverSocket;
    }
}
