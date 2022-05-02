package demo;

import java.net.*;

public class address {

	public static void main(String[] args) {
		InetAddress ip;
		try {
			ip=InetAddress.getLocalHost();
			String localname=ip.getHostName();
			String localip=ip.getHostAddress();
			System.out.println("The name of the computer is "+localname);
			System.out.println("The IP address of this computer is "+localip);
			
		}catch(UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
