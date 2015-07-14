package com.voayger.main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PCService {

	/**
	 * 监听端口
	 */
	private static final int PORT = 30000;
	/**
	 * 服务端数据接收流
	 */
	private static DataInputStream dataInputStream;
	/**
	 * 服务端数据输出流
	 */
	private static DataOutputStream dataOutputStream;
	/**
	 * 服务端通信socket
	 */
	private static Socket socket = null;
	/**
	 * 返回信息
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
					System.out.println("收到的信息：" + inputMsg);
					if (!inputMsg.isEmpty()) {
						Runtime.getRuntime().exec(inputMsg);
						System.out.println("exec:" + inputMsg);
						outMsg = "执行成功！";
					}
				} catch (Exception e) {
				} // 关键部分，catch到但不处理，继续循环操作
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
