package project;

import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;

public class C_onetooneClient extends JFrame{

	private JButton jb;//Send Button
	private  JTextArea input=new JTextArea();//input field
	private JTextArea textArea;//Output text Area
	//Internet Connect
	private Socket socket;
	private PrintWriter writer;
    private BufferedReader reader;
	
    public C_onetooneClient() {
		// TODO Auto-generated constructor stub
		paint();
	}
	
	private void paint() {
		
		//Configuring the main Panel
		setTitle("简通信Client端");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setBounds(100,100,400,360);
		setResizable(false);
		
		//Set the JTextArea
		textArea=new JTextArea();
		JScrollPane jscrollPane=new JScrollPane(textArea);
		jscrollPane.setBounds(5,5,375,275);
		textArea.setEditable(false);
		add(jscrollPane);
		
		//Set input field
		JLabel inputJlabel=new JLabel("客户端发送消息:");
		inputJlabel.setHorizontalAlignment(SwingConstants.LEFT);
		inputJlabel.setFont(new Font("楷体",Font.BOLD,14));
		inputJlabel.setBounds(10,275,150,50);
		add(inputJlabel);
		
		//Set JtextField
		JScrollPane js=new JScrollPane(input);
		js.setBounds(130,280,180,43);
		add(js);
		
		//Set the Button
		jb=new JButton("发送");
		jb.setBounds(320,285,60,30);
		add(jb);
		
		//Action listener of the Button
		input.setFocusable(true);
		input.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.isShiftDown()&&e.getKeyChar()==KeyEvent.VK_ENTER) {
					input.append("\n");
				}
				else if(e.getKeyChar()==KeyEvent.VK_ENTER) {
					e.consume();
					if(input.getText().equals("")) {
						return;
					}else {
						writer.println(input.getText());
						textArea.append("Client:\n"+input.getText()+"\n");
					}
					input.setText("");
				}
			}
		});

		jb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(input.getText().equals("")) {
					return;
				}else {
					writer.println(input.getText());
					textArea.append("Client:\n"+input.getText()+"\n");
					input.setText("");
				}
				jb.setFocusable(false);
			}
		});
	}

	//Connect to Server
	private void send() {
		try {
			socket=new Socket("169.254.128.61",8999);
			while(true) {
				writer = new PrintWriter(socket.getOutputStream(),true);
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				textArea.append("客户端已连接\n");
				getServerInfo();
			}//Repeatable connection
		}catch(IOException e) {
			e.printStackTrace();
		}		
	}
	
	//Get information from the Server
	private void getServerInfo() {
		try {
			while(true){
				String line=reader.readLine();
				textArea.append("Server:\n"+line+"\n");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				reader.close();
				socket.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		C_onetooneClient cl=new C_onetooneClient();
        cl.setVisible(true);   
        cl.send();
	}
}
