import tdemo.tdes;
import tdemo.DesProgram;
import javazoom.jl.player.Player;
import java.awt.EventQueue;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.ColorUIResource;
import javax.swing.text.AttributeSet.ColorAttribute;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.*; 
import javax.swing.*; 
import java.io.IOException; 
import java.nio.file.Path; 
import java.nio.file.Paths; 
public class FtpClient3 implements ActionListener {  
private JFrame frame;
JLabel songName;
private JTextField textField,textField1;
JButton play,pause, stop, resume;
JButton btnBrowse = new JButton("Browse");
static Socket socket;
JButton btnSend=new JButton("Send");
JFileChooser fileChooser;
long totalLength, pauseLength;
FileInputStream fileInputStream;
BufferedInputStream bufferedInputStream;
Player player;
Thread playThread, resumeThread;
String str,ss,str1,filename,filePath;
File myFile=null;
static InputStream in ;
static OutputStream out;
public static void main(String[] args) throws UnknownHostException, IOException {    
EventQueue.invokeLater(new Runnable() {
public void run() {
try {
FtpClient3 window = new FtpClient3();
window.frame.setVisible(true);
} catch (Exception e) {
e.printStackTrace();}}});
socket = new Socket("127.0.0.1",5000);
}
public FtpClient3() {
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
background.setBounds(100,100,707,425);
frame.setContentPane(background);
JLabel lbc = new JLabel("CLIENT");
JLabel b=new JLabel("for audio book sharing-Sender");
b.setForeground(new Color(250,250,250));
lbc.setForeground(new Color(255, 255, 255));
lbc.setFont(new Font("Khmer OS Content", Font.BOLD, 50));
lbc.setBounds(216, 0, 230, 98);
frame.getContentPane().add(lbc);
b.setFont(new Font("Gadugi", Font.ITALIC, 15));
b.setBounds(200, 40, 300, 98);
frame.getContentPane().add(b);
JLabel lblNewLabel = new JLabel("Select file : ");
lblNewLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
lblNewLabel.setBounds(12, 142, 120, 33);
frame.getContentPane().add(lblNewLabel);
JLabel lblNewLabel1 = new JLabel("Key         : ");
lblNewLabel1.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
lblNewLabel1.setBounds(12, 190, 120, 33);
frame.getContentPane().add(lblNewLabel1);
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
playerPanel.setBackground(Color.ORANGE);
controlPanel.setBackground(Color.ORANGE);
controlPanel.add(play);
controlPanel.add(pause);
controlPanel.add(resume);
controlPanel.add(stop);
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
btnSend.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
btnSend.setForeground(Color.ORANGE);
btnSend.setBackground(Color.DARK_GRAY);
pause.setBackground(Color.ORANGE);
play.setBackground(Color.ORANGE);
resume.setBackground(Color.ORANGE);
stop.setBackground(Color.ORANGE);
btnSend.setBounds(255, 371, 117, 48);
frame.getContentPane().add(btnSend);
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
    btnSend.addActionListener(this);
}
public void actionPerformed(ActionEvent e) {
    if (e.getSource().equals(btnBrowse)) {
    	 fileChooser = new JFileChooser();
    	fileChooser.setCurrentDirectory(new File("C:\\Audiobooks"));
    	fileChooser.setDialogTitle("Select Mp3");
    	fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    	fileChooser.setFileFilter(new FileNameExtensionFilter("Mp3 files", "mp3"));
        if (fileChooser.showOpenDialog(btnBrowse) == JFileChooser.APPROVE_OPTION) {
        	str = fileChooser.getSelectedFile().getAbsolutePath();
        	textField.setText(str);
        	str1=str;
            myFile = fileChooser.getSelectedFile();
            filename = fileChooser.getSelectedFile().getName();
            filePath = fileChooser.getSelectedFile().getPath();
        }
        songName.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        songName.setText("File Selected : " + filename);
        System.out.println("Myfile"+myFile);
        System.out.println("str:"+str);
        System.out.println("filepath:"+filePath);
    }
    if(e.getSource().equals(btnSend))
    {
    	DesProgram d=new DesProgram();
    	 ss=textField1.getText();
    	System.out.println(ss);
    	d.fun(str,ss);
    	 Path path = Paths.get(str); 
    	 Path fileName = path.getFileName();
    	System.out.println("FileName: "+ fileName.toString());
    	 str="C:\\Final\\Encrypt"+"\\"+fileName.toString();
    	File file = new File(str);
    	long l=file.length();
    	System.out.println("Size is "+l);
    	byte [] b = new byte[171*1024];
    	try {
    	in = new FileInputStream(file);
    	} catch (FileNotFoundException e1) {
    	// TODO Auto-generated catch block
    	e1.printStackTrace();
    	} 
    	try {
    	out = socket.getOutputStream();
    	} catch (IOException e1) {
    	// TODO Auto-generated catch block
    	e1.printStackTrace();}
    	try {
    	int count ;
    	DataOutputStream dd=new DataOutputStream(out);
    	String s=fileName.toString()+"-"+l+"-"+"sender";
    	System.out.println("The value of s is:"+s);
    	//****dd.writeUTF(fileName.toString());****/
    	dd.writeUTF(s);
    	while ((count =in.read(b))!=-1) {
    	out.write(b, 0, count);
    	System.out.println("Client-count"+count);
    	}
    	System.out.println("Client-Bye");}catch (IOException e1) {
    	// TODO Auto-generated catch block
    	e1.printStackTrace();}
    }  
    if (e.getSource().equals(play)) {
        //starting play thread
        if (filename != null) {
            playThread.start();
            songName.setText("Now playing : " + filename);
        } else {
            songName.setText("No File was selected!");
        }
    }
    if (e.getSource().equals(pause)) {
        //code for pause button
        if (player != null && filename != null) {
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
        if (filename != null) {
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
            fileInputStream = new FileInputStream(myFile);
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
            fileInputStream = new FileInputStream(myFile);
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