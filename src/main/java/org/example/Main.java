package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    JFrame frame;
    public Main(){
        frame=new JFrame("org.example.Steganographer");
        frame.setVisible(true);
        frame.setSize(700,700);
        main(frame);
    }
    public void main(JFrame f){
        f.getContentPane().removeAll();
        f.repaint();
        f.setLayout(null);
        f.setTitle("org.example.Steganographer");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JButton b1=new JButton("Encode");
        b1.setBounds(275,305,100,30);
        f.add(b1);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encoder(f);
            }
        });
        JButton b2=new JButton("Decode");
        b2.setBounds(275,345,100,30);
        f.add(b2);
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decoder(f);
            }
        });
    }
    public void encoder(JFrame f){
        f.getContentPane().removeAll();
        f.repaint();
        f.setLayout(null);
        f.setTitle("Encode");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setBackground(new java.awt.Color(242, 242, 242));

        JLabel label=new JLabel("Encode");
        label.setForeground(new java.awt.Color(197, 22, 22));
        label.setFont(new java.awt.Font("sansserif", 55, 36));
        label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label.setBounds(255,50,150,40);
        f.add(label);

        JLabel photolabel=new JLabel();
        photolabel.setBounds(350,150,350,240);
        f.add(photolabel);
        photolabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.addChoosableFileFilter(new MyFilter());
                    fileChooser.setAcceptAllFileFilterUsed(false);

                    int option = fileChooser.showOpenDialog(frame);
                    if(option == JFileChooser.APPROVE_OPTION){
                        File file = fileChooser.getSelectedFile();
                        System.out.println("File Selected: " + file.getName());

                        photolabel.setIcon(new ImageIcon(resize(file,photolabel.getWidth(),photolabel.getHeight())));

                    }else{
                        System.out.println("Open command canceled");
                    }
                }
            }
        });

        JLabel Encodedphotolabel=new JLabel();
        Encodedphotolabel.setBounds(350,400,350,240);
        f.add(Encodedphotolabel);

        Image dimg=getImage("src/main/java/org/example/image.png",photolabel.getWidth(),photolabel.getHeight());
        photolabel.setIcon(new ImageIcon(dimg));
        Encodedphotolabel.setIcon(new ImageIcon(dimg));

    }
    public Image getImage(String link,int Width,int Height){
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(link));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg = img.getScaledInstance(Width, Height, Image.SCALE_SMOOTH);
        return dimg;
    }
    public Image resize(File f,int Width,int Height){
        BufferedImage img = null;
        try {
            img = ImageIO.read(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg = img.getScaledInstance(Width, Height, Image.SCALE_SMOOTH);
        return dimg;
    }
    public void decoder(JFrame f){
        f.getContentPane().removeAll();
        f.repaint();
        f.setLayout(null);
        f.setTitle("Decode");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JLabel label=new JLabel("Decode");

        label.setForeground(new java.awt.Color(197, 22, 22));
        label.setFont(new java.awt.Font("sansserif", 55, 36));
        label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label.setBounds(255,50,150,40);
        f.add(label);

        JButton b1=new JButton("Encode");
        b1.setBounds(275,305,100,30);
        f.add(b1);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encoder(f);
            }
        });
        JButton b2=new JButton("Decode");
        b2.setBounds(275,345,100,30);
        f.add(b2);
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decoder(f);
            }
        });
    }

    public static void main(String[] args) {
        new Main();
    }
}
class MyFilter extends FileFilter {
    final static String jpg = "jpg";
    final static String gif = "gif";
    final static String tiff = "tiff";

    // Accept all directories and (gif ||
    // jpg || tiff) files.
    public boolean accept(File f) {
        if(f.isDirectory()) {
            return true;
        }
        String s = f.getName();
        int i = s.lastIndexOf('.');
        if(i > 0 &&  i < s.length() - 1) {
            String extension =
                    s.substring(i+1).toLowerCase();
            if (tiff.equals(extension)
                    || gif.equals(extension)
                    || jpg.equals(extension)) {
                return true;
            } else {
                return false;
            }
        };
        return false;
    }

    // The description of this filter
    public String getDescription() {
        return "Just Images (*.jpg, *.gif, *.tiff)";
    }
}
class Utils {
    public final static String jpeg = "jpeg";
    public final static String jpg = "jpg";
    public final static String gif = "gif";
    public final static String tiff = "tiff";
    public final static String tif = "tif";
    public final static String png = "png";
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
}