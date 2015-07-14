package com.voayger.main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PCService {

	/**
	 * �����˿�
	 */
	private static final int PORT = 30000;
	/**
	 * ��������ݽ�����
	 */
	private static DataInputStream dataInputStream;
	/**
	 * ��������������
	 */
	private static DataOutputStream dataOutputStream;
	/**
	 * �����ͨ��socket
	 */
	private static Socket socket = null;
	/**
	 * ������Ϣ
	 */
	private static String outMsg = null;

	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(PORT);
			System.out.println("Listening " + PORT + " port...");
			while (true) {
				socket = serverSocket.accept();
				outMsg = "";
				try {
					dataInputStream = new DataInputStream(
							socket.getInputStream());
					dataOutputStream = new DataOutputStream(
							socket.getOutputStream());
					String inputMsg = dataInputStream.readUTF();
					System.out.println("�յ�����Ϣ��" + inputMsg);
					if (!inputMsg.isEmpty()) {
						Runtime.getRuntime().exec(inputMsg);
						System.out.println("exec:" + inputMsg);
						outMsg = "ִ�гɹ���";
					}
				} catch (Exception e) {
				} // �ؼ����֣�catch��������������ѭ������
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (dataOutputStream != null) {
					dataOutputStream.writeUTF(outMsg);
					dataOutputStream.close();
				}
				if (dataInputStream != null) {
					dataInputStream.close();
				}
				if (socket != null) {
					socket = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
