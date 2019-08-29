/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuxi.tools;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 * @author 82398
 */
public class MnistLableFile extends IdxFile<byte[]> {
    public static MnistLableFile load() {
        try(DataInputStream input = new DataInputStream(new FileInputStream("D:\\code\\MNIST\\train_output.idx"))) {
            return new MnistLableFile(input);
        } catch (IOException ex) {
            return null;
        }
    }
    public MnistLableFile(DataInput input) throws IOException {
        super(byte[].class, input);
    }
    public int getLableCount() {
        return dimensions[0];
    }
    public int readLable(int i) {
        byte b = data[i];
        return b & 0xFF;
    }
    public float[] readAsArray(int i) {
        float[] r = new float[10];
        r[readLable(i)] = 1.0F;
        return r;
    }
}
