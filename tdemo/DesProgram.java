package tdemo;
import java.io.FileInputStream; 
import java.io.FileOutputStream; 
import java.io.IOException; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.security.InvalidAlgorithmParameterException; 
import java.security.InvalidKeyException; 
import java.security.NoSuchAlgorithmException; 
import java.security.spec.AlgorithmParameterSpec; 
import javax.crypto.Cipher; 
import javax.crypto.CipherInputStream; 
import javax.crypto.CipherOutputStream; 
import javax.crypto.KeyGenerator; 
import javax.crypto.NoSuchPaddingException; 
import javax.crypto.SecretKey; 
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;
import java.io.IOException; 
import java.nio.file.Path; 
import java.nio.file.Paths;
public class DesProgram
{
Cipher encrypt;
Cipher decrypt;
String Key;
byte[] initialization_vector = { 22, 33, 11, 44, 55, 99, 66, 77 };

//ENCRYPTION
public void fun(String s,String k)
{
Key=k;
String textFile = s; 
System.out.println(Key);
Path path = Paths.get(s); 
Path fileName = path.getFileName();
System.out.println("FileName:"+ fileName.toString());
File f1=new File("C:\\Final\\Encrypt"+"\\"+fileName.toString());
try 
{ 
//generating keys by using the KeyGenerator class 
SecretKey scrtkey=new SecretKeySpec(Key.getBytes(),"TripleDES");
AlgorithmParameterSpec aps = new IvParameterSpec(initialization_vector); 
//setting encryption mode 
 encrypt = Cipher.getInstance("TripleDES/CBC/PKCS5Padding");
encrypt.init(Cipher.ENCRYPT_MODE, scrtkey, aps); 
//setting decryption mode 
encryption(new FileInputStream(textFile), new FileOutputStream(f1)); }
catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | 
InvalidAlgorithmParameterException | IOException e) 
{ 
//prints the message (if any) related to exceptions 
e.printStackTrace(); }}

//DECRYPTION
public void fun1(String s,String k)
{
Key=k;
System.out.println(s);
//path of the decrypted file that we get as output 
Path path1 = Paths.get(s); 
Path fileName1 = path1.getFileName();
System.out.println("FileName: "+ fileName1.toString());
File f2=new File("D://Final//Decrypt"+"//"+fileName1.toString());
Path path = Paths.get(s); 
Path fileName = path.getFileName();
System.out.println("FileName: " + fileName.toString());
File f1=new File("D://Final//Client2"+"//"+fileName.toString());
try 
{ 
//generating keys by using the KeyGenerator class 
SecretKey scrtkey=new SecretKeySpec(Key.getBytes(),"TripleDES");
AlgorithmParameterSpec aps = new IvParameterSpec(initialization_vector); 
//setting decryption mode 
decrypt = Cipher.getInstance("TripleDES/CBC/PKCS5Padding"); 
decrypt.init(Cipher.DECRYPT_MODE, scrtkey, aps); 
decryption(new FileInputStream(f1), new FileOutputStream(f2)); }
catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | 
InvalidAlgorithmParameterException | IOException e) { 
//prints the message (if any) related to exceptions 
e.printStackTrace(); }}
void encryption(InputStream input, OutputStream output) 
throws IOException
{ 
output = new CipherOutputStream(output, encrypt); 
//calling the writeBytes() method to write the encrypted bytes to the file 
writeBytes(input, output); 
} 
void decryption(InputStream input, OutputStream output) throws IOException
{ 
input = new CipherInputStream(input, decrypt); 
//calling the writeBytes() method to write the decrypted bytes to the file 
writeBytes(input, output); 
} 
void writeBytes(InputStream input, OutputStream output) throws IOException
{ 
byte[] writeBuffer = new byte[512]; 
int readBytes = 0; 
while ((readBytes = input.read(writeBuffer)) >= 0) 
{ 
output.write(writeBuffer, 0, readBytes); 
} 
//closing the output stream 
output.close(); 
//closing the input stream 
input.close(); }}