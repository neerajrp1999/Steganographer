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
    Boolean isImageSelected_in_encoder=false;
    Boolean isImageSelected_in_decoder=false;
    String selectedImagePath_in_encoder=null;
    String selectedImagePath_in_decoder=null;
    public void encoder(JFrame f){
        isImageSelected_in_encoder=false;
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

        JButton selectImageFromFileSysytemButton=new JButton("Select Input Image");
        selectImageFromFileSysytemButton.setBounds(80,150,200,50);
        f.add(selectImageFromFileSysytemButton);

        JLabel label1=new JLabel("Enter Data To Hide");
        label1.setBounds(85,220,150,40);
        f.add(label1);

        JTextArea textArea=new JTextArea(100,10);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(70,250,250,100);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        f.add(scrollPane);

        JButton start_encode=new JButton("Start Encode");
        start_encode.setBounds(80,400,200,50);
        f.add(start_encode);

        JButton back=new JButton("Back");
        back.setBounds(80,500,200,50);
        f.add(back);

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main(f);
            }
        });

        JLabel photolabel=new JLabel();
        photolabel.setBounds(350,150,350,240);
        f.add(photolabel);
        photolabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    callFileChooser_Encoder(photolabel);
                }
            }
        });

        Image dimg=getImage("src/main/java/org/example/image.png",photolabel.getWidth(),photolabel.getHeight());
        photolabel.setIcon(new ImageIcon(dimg));

        selectImageFromFileSysytemButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                callFileChooser_Encoder(photolabel);
            }
        });

        start_encode.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String data=textArea.getText().toString().trim();
                if(data.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Enter a data first ...");
                }else if(!isImageSelected_in_encoder){
                    JOptionPane.showMessageDialog(null, "select image first ...");
                }else{
                    Steganographer.encode(selectedImagePath_in_encoder,data);
                }
            }
        });
    }
    public void callFileChooser_Encoder(JLabel photolabel){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new MyFilter());
        fileChooser.setAcceptAllFileFilterUsed(false);

        int option = fileChooser.showOpenDialog(frame);
        if(option == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            selectedImagePath_in_encoder=file.getAbsolutePath();
            System.out.println("File Selected: " + file.getName());
            photolabel.setIcon(new ImageIcon(resize(file,photolabel.getWidth(),photolabel.getHeight())));
            isImageSelected_in_encoder=true;
        }else{
            System.out.println("Open command canceled");
        }
    }
    public void callFileChooser_Decoder(JLabel photolabel){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new MyFilter());
        fileChooser.setAcceptAllFileFilterUsed(false);

        int option = fileChooser.showOpenDialog(frame);
        if(option == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            selectedImagePath_in_decoder=file.getAbsolutePath();
            System.out.println("File Selected: " + file.getName());
            photolabel.setIcon(new ImageIcon(resize(file,photolabel.getWidth(),photolabel.getHeight())));
            isImageSelected_in_decoder=true;
        }else{
            System.out.println("Open command canceled");
        }
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
        isImageSelected_in_decoder=false;
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

        JButton selectImageFromFileSystemButton=new JButton("Select Encoded Input Image");
        selectImageFromFileSystemButton.setBounds(80,150,200,50);
        f.add(selectImageFromFileSystemButton);

        JLabel label1=new JLabel("Decoded Message:");
        label1.setBounds(85,220,150,40);
        f.add(label1);

        JTextArea textArea=new JTextArea(100,10);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(70,250,250,100);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        f.add(scrollPane);

        JButton start_decode=new JButton("Start Decode");
        start_decode.setBounds(80,400,200,50);
        f.add(start_decode);

        JButton back=new JButton("Back");
        back.setBounds(80,500,200,50);
        f.add(back);

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main(f);
            }
        });

        JLabel photolabel=new JLabel();
        photolabel.setBounds(350,150,350,240);
        f.add(photolabel);
        photolabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    callFileChooser_Decoder(photolabel);
                }
            }
        });

        Image dimg=getImage("src/main/java/org/example/image.png",photolabel.getWidth(),photolabel.getHeight());
        photolabel.setIcon(new ImageIcon(dimg));

        selectImageFromFileSystemButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                callFileChooser_Decoder(photolabel);
            }
        });

        start_decode.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!isImageSelected_in_decoder){
                    JOptionPane.showMessageDialog(null, "select image first ...");
                }else{
                    textArea.setText(Steganographer.decode(selectedImagePath_in_decoder));
                }
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
    final static String png = "png";
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
                    || png.equals(extension)
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