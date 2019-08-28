/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuxi.tools;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author 82398
 */
public class MnistImageFile extends IdxFile<byte[]> {
    public static MnistImageFile load() {
        try {
            return new MnistImageFile(new DataInputStream(new FileInputStream("D:\\code\\MNIST\\train_input.idx")));
        } catch (IOException ex) {
            return null;
        }
    }
    public MnistImageFile(DataInput input) throws IOException {
        super(byte[].class, input);
        if(dimension() != 3) {
            throw new RuntimeException();
        }
    }
    public int getImageCount() {
        return dimensions[0];
    }
    public int getHeight() {
        return dimensions[1];
    }
    public int getWidth() {
        return dimensions[2];
    }
    public BufferedImage readImage(int index) {
        int w = getWidth();
        int h = getHeight();
        int s = w*h;
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);
        int[] da = new int[s];
        byte[] d = new byte[s];
        readData(d,index);
        for(int i = 0;i < s;i++) {
            da[i] = (d[i] & 0xFF)*0x010101;
        }
        img.setRGB(0, 0, w, h, da, 0, w);
        return img;
    }
    public JFrame show(int index) {
        int w = getWidth();
        int h = getHeight();
        BufferedImage img = readImage(index);
        JFrame f = new JFrame("in " + index);
        JLabel l = new JLabel(new ImageIcon(readImage(index).getScaledInstance(w*4, h*4, Image.SCALE_DEFAULT)));
        f.add(l);
        f.pack();
        f.setVisible(true);
        return f;
    }
}
