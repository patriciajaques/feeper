/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.model;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author
 * fabioalves
 */
public class Util {
    
    public static String criptoMD5(String value)
    {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(value.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                    sb.append(Integer.toHexString((int) (b & 0xff)));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            return value;
        }
    }
    
    private static final String MAIUSCULAS = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
    private static final String MINUSCULAS = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z";
    private static final String NUMEROS = "0,1,2,3,4,5,6,7,8,9";
    private static final String SIMBOLOS = "!,@,#,?";
    
    public static String gerarSenha(int tamanhoSenha)
    {
        String senhaGerada = "";
        boolean valido = false;
        String[] permitidos = String.format("%s,%s,%s,%s", MAIUSCULAS, MINUSCULAS, NUMEROS, SIMBOLOS).split(",");

        do
        {
            Random rd = new Random();

            String temp = "";
            senhaGerada = "";

            for (int i = 0; i < tamanhoSenha; i++)
            {
                temp = permitidos[rd.nextInt(permitidos.length)];
                senhaGerada += temp;
            }

            valido = validarFormatoSenha(senhaGerada);
        } while (!valido);

        return senhaGerada;
    }

    public static boolean validarFormatoSenha(String senha)
    {
        String[] listaMaiusculas = MAIUSCULAS.split(",");
        String[] listaMinusculas = MINUSCULAS.split(",");
        String[] listaNumeros = NUMEROS.split(",");
        String[] listaSimbolos = SIMBOLOS.split(",");

        boolean temMaiuscula = false;
        boolean temMinuscula = false;
        boolean temNumero = false;
        boolean temSimbolo = false;
        
        for (int i = 0; i < listaMaiusculas.length; i++)
            if (senha.contains(listaMaiusculas[i]))
            {
                temMaiuscula = true;
                break;
            }
        for (int i = 0; i < listaMinusculas.length; i++)
            if (senha.contains(listaMinusculas[i]))
            {
                temMinuscula = true;
                break;
            }
        for (int i = 0; i < listaNumeros.length; i++)
            if (senha.contains(listaNumeros[i]))
            {
                temNumero = true;
                break;
            }
        for (int i = 0; i < listaSimbolos.length; i++)
            if (senha.contains(listaSimbolos[i]))
            {
                temSimbolo = true;
                break;
            }
        
        return (temMaiuscula && temMinuscula && temNumero && temSimbolo);
    }
    
    public static byte[] zipFiles(List<byte[]> files, List<String> filenames) throws FileNotFoundException, IOException
    {
        byte[] buf = new byte[2048];
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream out = new ZipOutputStream(baos);
        
        for (int i = 0; i < files.size(); i++) {
            InputStream stream = new ByteArrayInputStream(files.get(i));
            BufferedInputStream bis = new BufferedInputStream(stream);
            
            out.putNextEntry(new ZipEntry(filenames.get(i)));
            int bytesRead;
            
            while ((bytesRead = bis.read(buf)) != -1) {
                out.write(buf, 0, bytesRead);
            }
            
            out.closeEntry();
            bis.close();
            stream.close();
        }
        out.flush();
        baos.flush();
        out.close();
        baos.close();
        
        out.flush();
        out.close();
        
        return baos.toByteArray();
    }
    
    public static String prepareStringForSave(String text)
    {
        if (text == null || text.isEmpty()) return "";

        return removeSpecialCharacters(removeAccent(text)).trim();
    }

    public static String removeSpecialCharacters(String text)
    {
        return text.replaceAll("[^a-zA-Z 0-9]", "");
    }

    public static String removeNumbers(String text)
    {
        return text.replaceAll("[^a-zA-Z ]", "");
    }
    
    public static String removeAccent(String text)
    {
        return StringUtils.stripAccents(text);
    }
    
    public static String truncateString(String value, int length, String comp)
    {
        try
        {
            if (value == null || value.isEmpty()) return "";
            if (value.length() > length)
                return value.substring(0, length) + comp;
            else
                return value;
        }
        catch (Exception e)
        {
            return value;
        }
    }
    
    public static String formatDate(Date date, String format)
    {
        //String DATE_FORMAT_NOW = "yyyy-MM-dd";
        //Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String stringDate = sdf.format(date);
        return stringDate;
    }
    
    public static Date stringToData(String date)
    {
        try {
            
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");  
            return new Date(format.parse(date).getTime());
            
        } catch (Exception e) {
            return new Date();
        }
    }
    
    public static void saveToPNG(byte[] source, String path, int width, int height)
    {
        try
        {
            ByteArrayInputStream bis = new ByteArrayInputStream(source);
            BufferedImage original = ImageIO.read(bis);
            int type = original.getType() == 0? BufferedImage.TYPE_INT_ARGB : original.getType();
            BufferedImage resize = resizeImageWithHint(original, type, width, height);
            ImageIO.write(resize, "png", new File(path));
        }
        catch (Exception e)
        {
        }
    }
    
    public static BufferedImage resizeImage(BufferedImage originalImage, int type, int width, int height){
	BufferedImage resizedImage = new BufferedImage(width, height, type);
	Graphics2D g = resizedImage.createGraphics();
	g.drawImage(originalImage, 0, 0, width, height, null);
	g.dispose();
 
	return resizedImage;
    }
 
    public static BufferedImage resizeImageWithHint(BufferedImage originalImage, int type, int width, int height){
 
	BufferedImage resizedImage = new BufferedImage(width, height, type);
	Graphics2D g = resizedImage.createGraphics();
	g.drawImage(originalImage, 0, 0, width, height, null);
	g.dispose();	
	g.setComposite(AlphaComposite.Src);
 
	g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
	RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	g.setRenderingHint(RenderingHints.KEY_RENDERING,
	RenderingHints.VALUE_RENDER_QUALITY);
	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	RenderingHints.VALUE_ANTIALIAS_ON);
 
	return resizedImage;
    }
    
    public static boolean sendMail(String subject, String body)
    {
        return sendMail("", subject, body);
    }
    public static boolean sendMail(String to, String subject, String body)
    {
        try {
            
            body = body.replace("\n", "<br>");
            
            final String username = "feeper.box@gmail.com";
            final String password = "kx8f33p3r";

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
              new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                    }
              });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("feeper.box@gmail.com"));

            if (to.isEmpty())
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("feeper.box@gmail.com"));
            else
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            message.setSubject("[feeper] " + subject);
            message.setContent(body,"text/html");

            Transport.send(message);

            return true;
            
        } catch (MessagingException e) {
            return false;
        }
    }
    
}
