import java.awt.EventQueue;
import tdemo.tdes;
import tdemo.DesProgram;
import javazoom.jl.player.Player;
import java.net.Socket;
import java.awt.Font;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.ServerSocket;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;
import java.awt.event.*; 
import javax.swing.*;
import java.nio.file.Path; 
import java.nio.file.Paths; 
import javax.swing.filechooser.FileNameExtensionFilter;
public class FtpClient4 implements ActionListener{
private JFrame frame;
JLabel songName,name;
JButton play,pause, stop, resume,btnBrowse, btnReceive;
JFileChooser fileChooser;
long totalLength, pauseLength,l;
FileInputStream fileInputStream;
BufferedInputStream bufferedInputStream;
Player player;
Thread playThread, resumeThread;
private JTextField textField,textField1;
static Socket socket;
static InputStream in,in1;
static OutputStream out,out1;
static FileOutputStream oStream;
static BufferedOutputStream bos=new BufferedOutputStream(oStream);
String str,ss,str1,filename,filePath,dec="";
File myFile=null;
File myFile1=null;
public static void main(String[] args) throws UnknownHostException, IOException {
EventQueue.invokeLater(new Runnable() {
public void run() {
try {
FtpClient4 window = new FtpClient4();
window.frame.setVisible(true);
} catch (Exception e) {
e.printStackTrace();}}});
 socket = new Socket("127.0.0.1", 5000);
}
public FtpClient4() {
	initialize();
	addActionEvents();
	//Calling Threads
	playThread = new Thread(runnablePlay);
	resumeThread = new Thread(runnableResume);
}
private void initialize() {
frame = new JFrame();
songName = new JLabel("", SwingConstants.CENTER);

frame.getContentPane().setBackground(UIManager.getColor("Button.highlight"));
frame.setBackground(UIManager.getColor("Button.highlight"));
frame.setBounds(100, 100, 707, 535);
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.setLayout(new BorderLayout());
JLabel background=new JLabel(new ImageIcon("C:\\Final\\Photos\\finall3.jpg"));
background.setBounds(100,100,707,535);
frame.setContentPane(background);
JLabel lbc = new JLabel("CLIENT");
JLabel b=new JLabel("for audio book sharing-Receiver");
b.setForeground(new Color(250,250,250));
lbc.setForeground(new Color(255, 255, 255));
lbc.setFont(new Font("Khmer OS Content", Font.BOLD, 50));
lbc.setBounds(216, 0, 230, 98);
frame.getContentPane().add(lbc);
b.setFont(new Font("Gadugi", Font.ITALIC, 15));
b.setBounds(196, 40, 300, 98);
frame.getContentPane().add(b);
JLabel lblNewLabel = new JLabel("Select file : ");
lblNewLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
lblNewLabel.setBounds(12, 142, 120, 33);
frame.getContentPane().add(lblNewLabel);
JLabel lblNewLabel1 = new JLabel("Key         :");
lblNewLabel1.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
lblNewLabel1.setBounds(12, 190, 120, 33);
frame.getContentPane().add(lblNewLabel1);
 btnBrowse = new JButton("Browse");
JPanel playerPanel = new JPanel(); //Music Selection Panel
JPanel controlPanel = new JPanel();
Icon iconPlay = new ImageIcon("C:\\Users\\dell\\Downloads\\play-button.png");
Icon iconPause = new ImageIcon("C:\\Users\\dell\\Downloads\\pause-button.png");
Icon iconResume = new ImageIcon("C:\\Users\\dell\\Downloads\\resume-button.png");
Icon iconStop = new ImageIcon("C:\\Users\\dell\\Downloads\\stop-button.png");
 play = new JButton(iconPlay);
 pause = new JButton(iconPause);
 resume = new JButton(iconResume);
 stop = new JButton(iconStop);
 playerPanel.add(songName);
 controlPanel.setBounds(127, 310, 376, 43);
 playerPanel.setBounds(127, 250, 376, 43);
 controlPanel.add(play);
 controlPanel.add(pause);
 controlPanel.add(resume);
 controlPanel.add(stop);
 controlPanel.setBackground(Color.ORANGE);
 playerPanel.setBackground(Color.ORANGE);
 frame.getContentPane().add(controlPanel,BorderLayout.SOUTH);
 frame.getContentPane().add(playerPanel,BorderLayout.NORTH);
btnBrowse.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
btnBrowse.setForeground(Color.ORANGE);
btnBrowse.setBackground(Color.DARK_GRAY);
btnBrowse.setBounds(510, 142, 105, 33);
frame.getContentPane().add(btnBrowse);
textField = new JTextField();
textField.setFont(new Font("Caladea", Font.PLAIN, 18));
textField.setBounds(127, 142, 376, 33);
frame.getContentPane().add(textField);
textField.setColumns(10);
textField1 = new JTextField();
textField1.setFont(new Font("Caladea", Font.PLAIN, 18));
textField1.setBounds(127, 190, 376, 33);
frame.getContentPane().add(textField1);
textField1.setColumns(10);
name=new JLabel();
btnReceive=new JButton("Receive");
btnReceive.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
btnReceive.setForeground(Color.ORANGE);
btnReceive.setBackground(Color.DARK_GRAY);
pause.setBackground(Color.ORANGE);
play.setBackground(Color.ORANGE);
resume.setBackground(Color.ORANGE);
stop.setBackground(Color.ORANGE);
btnReceive.setBounds(255, 371, 117, 48);
frame.getContentPane().add(btnReceive);
JLabel na=new JLabel("Designed by Pragadeesh B");
na.setForeground(new Color(240,240,240));
na.setFont(new Font("Khmer OS Content", Font.ITALIC, 15));
na.setBounds(110, 430, 260, 98);
frame.getContentPane().add(na);
}
public void addActionEvents() {
    //registering action listener to buttons
	btnBrowse.addActionListener(this);
    play.addActionListener(this);
    pause.addActionListener(this);
    resume.addActionListener(this);
    stop.addActionListener(this);
    btnReceive.addActionListener(this);
}
public void actionPerformed(ActionEvent e) {
    if (e.getSource().equals(btnBrowse)) {
    	 fileChooser = new JFileChooser();
    	fileChooser.setCurrentDirectory(new File("C:\\Final\\Server"));
    	fileChooser.setDialogTitle("Select Mp3");
    	fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    	fileChooser.setFileFilter(new FileNameExtensionFilter("Mp3 files", "mp3"));
    	//fileChooser.showOpenDialog(btnBrowse);  	
        if (fileChooser.showOpenDialog(btnBrowse) == JFileChooser.APPROVE_OPTION) {
        	str = fileChooser.getSelectedFile().getAbsolutePath();
        	textField.setText(str);
        	System.out.println("the value of str:"+str);
        	str1=str;
             myFile = fileChooser.getSelectedFile();
             l=myFile.length();
             System.out.println("Length of the requested file is:"+l);
            filename = fileChooser.getSelectedFile().getName();
            filePath = fileChooser.getSelectedFile().getPath();
            dec="C:\\Final\\Decrypt"+"\\"+filename;
            myFile1=new File(dec);
            System.out.println("Selected file:"+dec);
            
        }
        songName.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        songName.setText("File Selected : " + filename);
    }
    if(e.getSource().equals(btnReceive))
    {
    	DesProgram d1=new DesProgram();
    	System.out.println("Hello");
    	try {
    		in = socket.getInputStream();
    		out = socket.getOutputStream();
    	} catch (IOException e2) {
    		// TODO: handle exception
    		e2.printStackTrace();
    	}
    	try {
    		DataOutputStream dd=new DataOutputStream(out);
    		String s1=filename+"-"+ l +"-"+"receiver";
    		System.out.println("The value of s1 in c4 is:"+s1);
    		dd.writeUTF(s1);	
    		oStream=new FileOutputStream("C:\\Final\\Client2"+"\\"+filename);//should be filename instead of str
    		bos=new BufferedOutputStream(oStream);
    		byte [] b = new byte[171*1024];
    		 int count1=0 ;
    		 int i=0;
    		 while ((count1 = in.read(b)) != -1) {
    		bos.write(b, 0, count1);
    		System.out.println(count1);
    		i=i+count1;//Total bytes read so far
    		if(i==l)
    		{
    			System.out.println("File read successfully");
    			break;
    			
    		}

    		System.out.println("received"+i);


    		 }
    		 ss=textField1.getText(); 
    		 String stz="C:\\Final\\Client2"+"\\"+filename;
    		 d1.fun1(stz,ss);
    		
    	} catch (Exception e2) {
    		// TODO: handle exception
    	}  	
    }
    if (e.getSource().equals(play)) {
        //starting play thread
        if (dec != null) {
            playThread.start();
            songName.setText("Now playing : " + filename);
        } else {
            songName.setText("No File was selected!");
        }
    }
    if (e.getSource().equals(pause)) {
        //code for pause button
        if (player != null && dec != null) {
            try {
                pauseLength = fileInputStream.available();
                player.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    if (e.getSource().equals(resume)) {
        //starting resume thread
        if (dec != null) {
            resumeThread.start();
        } else {
            songName.setText("No File was selected!");
        }
    }
    if (e.getSource().equals(stop)) {
        //code for stop button
        if (player != null) {
            player.close();
            songName.setText("");
        }
    }
}
Runnable runnablePlay = new Runnable() {
    @Override
    public void run() {
        try {
            //code for play button
            fileInputStream = new FileInputStream(myFile1);
            bufferedInputStream = new BufferedInputStream(fileInputStream);
            player = new Player(bufferedInputStream);
            totalLength = fileInputStream.available();
            player.play();//starting music
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
};

Runnable runnableResume = new Runnable() {
    @Override
    public void run() {
        try {
            //code for resume button
            fileInputStream = new FileInputStream(myFile1);
            bufferedInputStream = new BufferedInputStream(fileInputStream);
            player = new Player(bufferedInputStream);
            fileInputStream.skip(totalLength - pauseLength);
            player.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
};
}