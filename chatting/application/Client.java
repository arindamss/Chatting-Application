package chatting.application;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.Box;

import javax.swing.border.EmptyBorder;
import javax.swing.WindowConstants;

import java.awt.Color; 
import java.awt.Font;
import java.awt.Image;
import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.BoxLayout;

import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;


public class Client implements ActionListener{
    static JPanel a1;
    JTextField text;
    static JFrame frame=new JFrame();
    static DataOutputStream dout;
    
    static Box vertical=Box.createVerticalBox();
    
    Client(){
        frame.setSize(450,700);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocation(800,50);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setLayout(null);
        frame.setUndecorated(true);
        
        JPanel p1=new JPanel();
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0,0,450,70);
        p1.setLayout(null);
        frame.add(p1);
        
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2=i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);
        JLabel back=new JLabel(i3);
        back.setBounds(5,20,25,25);
        p1.add(back);
        
        back.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                System.exit(0);
            }
        });
        
        
        ImageIcon i4=new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));
        Image i5=i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6=new ImageIcon(i5);
        JLabel profile=new JLabel(i6);
        profile.setBounds(40,10,50,50);
        p1.add(profile);
        
        
        ImageIcon i7=new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8=i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9=new ImageIcon(i8);
        JLabel video=new JLabel(i9);
        video.setBounds(300,20,30,30);
        p1.add(video);
        
        
        ImageIcon i10=new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11=i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon i12=new ImageIcon(i11);
        JLabel phone=new JLabel(i12);
        phone.setBounds(360,20,35,30);
        p1.add(phone);
        
        ImageIcon i13=new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14=i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon i15=new ImageIcon(i14);
        JLabel morevert=new JLabel(i15);
        morevert.setBounds(420,20,10,25);
        p1.add(morevert);
        
        JLabel name=new JLabel("Bunty");
        name.setForeground(Color.WHITE);
        name.setBounds(105,22,100,18);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        p1.add(name);
        
        JLabel status=new JLabel("Active Now");
        status.setForeground(Color.WHITE);
        status.setBounds(105,42,100,18);
        status.setFont(new Font("SAN_SERIF", Font.BOLD, 10));
        p1.add(status);
        
        
        a1=new JPanel();
        a1.setBounds(5,75,440,570);
        frame.add(a1);
        
        text=new JTextField();
        text.setBounds(5,650,310,40);
        text.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        frame.add(text);
        
        
        
        JButton send=new JButton("Send");
        send.setBounds(320,650,123,40);
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.WHITE);
        send.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        send.addActionListener(this);
        frame.add(send);
        
        
        
        frame.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        try{
            String out=text.getText();
            System.out.println(out);

            a1.setLayout(new BorderLayout());

            JPanel a2=formatLabel(out);

            JPanel right=new JPanel(new BorderLayout());
            right.add(a2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            a1.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF(out);

            frame.repaint();
            frame.invalidate();
            frame.validate();
            text.setText(null);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public static JPanel formatLabel(String out){
        JPanel panel=new JPanel();
        
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JLabel output=new JLabel("<html>\n" +
                                    "<p width=\"150px\">"+out+"</p>\n" +
                                    "</html>");
        output.setFont(new Font("Tahoma", Font.PLAIN,16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));
        panel.add(output);
        
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("HH:MM");
        
        JLabel time=new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);
        
        return panel;
    }
    
    public static void main(String args[]){
        new Client();
        try{
            Socket s=new Socket("127.0.0.1",6001);
            DataInputStream din=new DataInputStream(s.getInputStream());
            dout=new DataOutputStream(s.getOutputStream());
            while(true){
                a1.setLayout(new BorderLayout());
                String msg=din.readUTF();
                    
                JPanel pane=formatLabel(msg);
                    
                JPanel left=new JPanel(new BorderLayout());
                left.add(pane, BorderLayout.LINE_START);
                vertical.add(left);
                
                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical, BorderLayout.PAGE_START);
                
                frame.validate();
            }
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
