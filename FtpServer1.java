import java.awt.EventQueue;
import java.io.*;
import javax.print.attribute.Size2DSyntax;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import java.awt.SystemColor;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.zip.InflaterInputStream;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Color;
public class FtpServer1 {
private JFrame frame;
static InputStream in ;
static OutputStream out,out1,oz;
static FileOutputStream oStream;
static BufferedOutputStream bos=new BufferedOutputStream(oStream);
static ServerSocket serverSocket,serverSocket1;
static JLabel lblNewLabel_3;
static FileInputStream fiz;
static JLabel lblNewLabel_4;
static JLabel lblNewLabel_5;
 static JLabel lblNewLabel_6;
 static JLabel lblNewLabel_7;
 static JLabel lblNewLabel_8;
 static JLabel lblNewLabel_9;
 static BufferedInputStream biz;
public static void main(String[] args) throws Exception {
EventQueue.invokeLater(new Runnable() {
public void run() {
try {
FtpServer1 window = new FtpServer1();
window.frame.setVisible(true);
} catch (Exception e) {
e.printStackTrace();
}
}}); 
serverSocket = new ServerSocket(5000);
Socket socket = serverSocket.accept();
lblNewLabel_4.setText(":  New Client accepted");
lblNewLabel_4.setForeground(new Color(127, 255, 0));
in = socket.getInputStream();
DataInputStream dd1=new DataInputStream(in);
String s1=dd1.readUTF(); 
System.out.println("The value of s1 is"+s1);// Filename+length+sender/receiver
System.out.println(s1);
String[] arr=s1.split("-",3);
String str=arr[0];//Name of the file
int lenString=Integer.parseInt(arr[1]);//Size of the file
String sr=arr[2];//sender or receiver
System.out.println("The value of sr is"+sr);
//************ client1 is a SENDER. server is RECEIVER*********************//
if(sr.equals("sender"))
{
	lblNewLabel_4.setText(":  New Client(1) accepted");
	lblNewLabel_4.setForeground(new Color(127, 255, 0));
	oStream=new FileOutputStream("C:\\Final\\Server"+"\\"+str);
	System.out.println("Size"+lenString +"and string:"+str);
	bos=new BufferedOutputStream(oStream);
	byte [] b = new byte[171*1024];
	 int count=0 ;
	 int i=0;
	 while ((count = in.read(b)) != -1) {
	bos.write(b, 0, count);
	System.out.println(count);
	i=i+count;//Total bytes read so far
	if(i==lenString)
	{
		System.out.println("File read successfully");
		bos.flush();
		socket.close();
		break;
	}
	System.out.println("received"+i);
	lblNewLabel_5.setText(":  New file recieved !");
	lblNewLabel_5.setForeground(Color.ORANGE);
	 }
	System.out.println("Bye-1");
	//bos.flush();
}
//*********************client2 is receiver. Server is SENDER***********************//
else if(sr.equals("receiver"))
{
	lblNewLabel_4.setText(":  New Client(2) accepted");
	String pathz="C:\\Final\\Server"+"\\"+str;
	File ff=new File(pathz);
	byte[] b1=new byte[171*1024];
	try {
		fiz = new FileInputStream(ff);
		 biz = new BufferedInputStream(fiz);
		
	} catch (Exception e1) {
		e1.printStackTrace();
	}
	try {
		 oz = socket.getOutputStream();
		
	} catch (Exception e) {
		// TODO: handle exception
	}
	try {
		
		int countz ;
		while ((countz =biz.read(b1))!=-1) {
			oz.write(b1, 0, countz);
			System.out.println("Server2-count"+ countz);
			}
		lblNewLabel_7.setText(":  File sent");
		lblNewLabel_7.setForeground(Color.ORANGE);
			System.out.println("File sent successfully");
			lblNewLabel_7.setText("File sent!");
		
	} catch (Exception e) {
		// TODO: handle exception
	}
}
}
public FtpServer1() {
initialize();
}

//initialize();
private void initialize() {
frame = new JFrame();
frame.getContentPane().setBackground(new Color(128, 128, 128));
frame.setBounds(100, 100, 527, 440);
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.setLayout(new BorderLayout());
JLabel background=new JLabel(new ImageIcon("C:\\Final\\Photos\\server1.jpg"));
background.setBounds(100,100,527,440);
frame.setContentPane(background);
JLabel lblServer = new JLabel("SERVER");
lblServer.setForeground(new Color(90,90,90));
lblServer.setFont(new Font("Khmer OS Content", Font.BOLD, 50));
lblServer.setBounds(156, 10, 230, 98);
frame.getContentPane().add(lblServer);
JLabel b=new JLabel("for audio book sharing");
b.setForeground(new Color(50,50,50));
b.setFont(new Font("Gadugi", Font.ITALIC, 15));
b.setBounds(186, 55, 300, 98);
frame.getContentPane().add(b);
JLabel lblNewLabel = new JLabel("Status");
lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 18));
lblNewLabel.setForeground(new Color(50,50,50));
lblNewLabel.setBounds(40, 131, 208, 34);
frame.getContentPane().add(lblNewLabel);
JLabel lblNewLabel_1 = new JLabel("Client connection");
lblNewLabel_1.setForeground(new Color(50,50,50));
lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 18));
lblNewLabel_1.setBounds(40, 177, 208, 39);
frame.getContentPane().add(lblNewLabel_1);
JLabel lblNewLabel_2 = new JLabel("Recieved files");
lblNewLabel_2.setForeground(new Color(50,50,50));
lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD, 18));
lblNewLabel_2.setBounds(40, 228, 208, 33);
frame.getContentPane().add(lblNewLabel_2);
JLabel lblNewLabel_6 = new JLabel("Sent files ");
lblNewLabel_6.setForeground(new Color(50,50,50));
lblNewLabel_6.setFont(new Font("Dialog", Font.BOLD, 18));
lblNewLabel_6.setBounds(40, 278, 192, 33);
frame.getContentPane().add(lblNewLabel_6);
 lblNewLabel_3 = new JLabel(":  Running");
lblNewLabel_3.setFont(new Font("Dialog", Font.BOLD, 18));
lblNewLabel_3.setForeground(new Color(204,204,0));
lblNewLabel_3.setBounds(234, 140, 249, 22);
frame.getContentPane().add(lblNewLabel_3);
lblNewLabel_4 = new JLabel(":  No new Clients");
lblNewLabel_4.setForeground(new Color(50,50,50));
lblNewLabel_4.setFont(new Font("Dialog", Font.BOLD, 18));
lblNewLabel_4.setBounds(234, 177, 249, 39);
frame.getContentPane().add(lblNewLabel_4);
lblNewLabel_5 = new JLabel(":  No file recieved");
lblNewLabel_5.setForeground(new Color(50,50,50));
lblNewLabel_5.setFont(new Font("Dialog", Font.BOLD, 18));
lblNewLabel_5.setBounds(234, 228, 221, 33);
frame.getContentPane().add(lblNewLabel_5);
lblNewLabel_7 = new JLabel(":  No file requested");
lblNewLabel_7.setForeground(new Color(50,50,50));
lblNewLabel_7.setFont(new Font("Dialog", Font.BOLD, 18));
lblNewLabel_7.setBounds(234, 278, 249, 33);
frame.getContentPane().add(lblNewLabel_7);
JLabel na=new JLabel("Designed by Pragadeesh B");
na.setForeground(new Color(100,100,100));
na.setFont(new Font("Khmer OS Content", Font.ITALIC, 12));
na.setBounds(105, 338, 260, 98);
frame.getContentPane().add(na);
}
}
