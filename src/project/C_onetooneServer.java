package project;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.Iterator;

public class C_onetooneServer extends JFrame{

	private JButton jb;//Send Button
	private  JTextArea input;//input field
	private JTextArea textAreas;//Output text Area
	//Internet Connect
	private ServerSocket server;
	private Socket socket;
	//input and output
	private PrintWriter writer;
    private BufferedReader reader;
    
	public C_onetooneServer() {
		Paint();
	}
	
	private void Paint() {
		
		//Configuring the main Panel
		setTitle("简通信Server端");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setBounds(100,100,400,360);
		setBackground(Color.BLACK);
		setResizable(false);
		
		//Set the JTextArea
		textAreas=new JTextArea();
		JScrollPane jscrollPane=new JScrollPane(textAreas);
		jscrollPane.setBounds(5,5,375,275);
		textAreas.setEditable(false);
		add(jscrollPane);
		
		//Set input field
		JLabel inputJlabel=new JLabel("服务器发送消息:");
		inputJlabel.setHorizontalAlignment(SwingConstants.LEFT);
		inputJlabel.setFont(new Font("楷体",Font.BOLD,14));
		inputJlabel.setBounds(10,275,150,50);
		add(inputJlabel);
		
		//Set JtextField
		input=new JTextArea();
		input.setEditable(true);
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
						textAreas.append("Server:\n"+input.getText()+"\n");
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
					textAreas.append("Server:\n"+input.getText()+"\n");
					input.setText("");
				}
				jb.setFocusable(false);
				}
		});
	}
	
	//Waiting to connect Client
	private void Connect(){
		try {
			server=new ServerSocket(8999);//Enable interface 8999
			textAreas.append("服务器已准备！\n");
			while(true){
				socket=server.accept();
				textAreas.append("客户机已连接\n");
				reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer=new PrintWriter(socket.getOutputStream(),true);
				getClientInfo();
			}//Repeatable Connection
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	//Get information from the Client
	private void getClientInfo() {
		try {
			while(true){
				String line=reader.readLine();
				textAreas.append("Client:\n"+line+"\n");
			}
		}catch(Exception e) {
			textAreas.append("客户已经退出\n");
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
		C_onetooneServer c=new C_onetooneServer();
		c.setVisible(true);		
		c.Connect();
	}

}
/*两个bug，
1.回车换行键√（已解决）
2.接受流一次只能接收一行
*/



