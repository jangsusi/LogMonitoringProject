package com.logmonitoring.main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.logmonitoring.common.Util;

public class ServerFileStorage {
	String url;
	String id;
	String password;
	ChannelSftp channelSftp;

	public ServerFileStorage() {
		this.url = Util.url;
		this.id = Util.id;
		this.password = Util.password;
	}

	private void connectServer() {
		JSch jsch = new JSch();
		try {
			Session session = jsch.getSession(id, url, 22);
			session.setPassword(password);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			Channel channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp) channel;
		} catch (JSchException e) {
			System.out.println("세션 접속 정보 확인 요망 \n Jsch작동 안됨. : " + e.getMessage());
		}
	}

	public void downloadFilesToLocal() {
		connectServer();
		new File(Util.ACCESS_FILE_DIR).mkdirs();
		try {
			channelSftp.cd(Util.SERVER_DIR);
			Vector<ChannelSftp.LsEntry> fileList = channelSftp.ls("*.txt");
			for (ChannelSftp.LsEntry file : fileList) {
				try (InputStream in = channelSftp.get(file.getFilename());
						BufferedInputStream bin = new BufferedInputStream(in);) {
					File localFile = new File(Util.ACCESS_FILE_DIR + file.getFilename());
					BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(localFile));
					int data;
					byte[] buffer = new byte[1024 * 8];
					while ((data = bin.read(buffer, 0, 1024 * 8)) != -1) {
						fos.write(buffer, 0, data);
					}
					fos.close();
				} catch (IOException e) {
					System.out.println(file.getFilename() + "파일 존재하지 않음 \n" + e.getMessage());
					e.printStackTrace();
				}
			}
		} catch (SftpException e1) {
			System.out.println("올바르지 않은 경로 목록\n" + e1.getMessage());
			e1.printStackTrace();
		}
	}

	public void downloadFileToLocal() {
		try {
			channelSftp.cd(Util.SERVER_DIR);
			ChannelSftp.LsEntry file = (ChannelSftp.LsEntry)channelSftp.ls("*.txt").lastElement();
			try (InputStream in = channelSftp.get(file.getFilename());
					BufferedInputStream bin = new BufferedInputStream(in);) {
				File localFile = new File(Util.ACCESS_FILE_DIR + file.getFilename());
				BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(localFile));
				int data;
				byte[] buffer = new byte[1024 * 8];
				while ((data = bin.read(buffer, 0, 1024 * 8)) != -1) {
					fos.write(buffer, 0, data);
				}
				fos.close();
			} catch (IOException e) {
				System.out.println(file.getFilename() + "파일 존재하지 않음 \n" + e.getMessage());
				e.printStackTrace();
			}
		} catch (SftpException e1) {
			System.out.println("올바르지 않은 경로 목록\n" + e1.getMessage());
			e1.printStackTrace();
		}
	}

}
