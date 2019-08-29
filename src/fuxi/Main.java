/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuxi;

import fuxi.node.InputLayerNode;
import fuxi.node.UnitLayerNode;
import fuxi.node.AbstractLayerNode;
import fuxi.tools.MnistImageFile;
import fuxi.tools.MnistLableFile;
import java.util.Arrays;
import javax.swing.JFrame;

/**
 *
 * @author 82398
 */
public class Main {

    public static void main(String[] args) throws Exception {
        final MnistImageFile m = MnistImageFile.load();
        MnistLableFile l = MnistLableFile.load();
        WeakContext c = new WeakContext();
        AbstractLayerNode ln = new InputLayerNode(m.getWidth() * m.getHeight()) {
            @Override
            protected void input(Context context, float[] value) {
                int p = size();
                byte[] d = new byte[p];
                m.readData(d, context.updataCount);
                for(int i = 0;i < p;i++) {
                    value[i] = (d[i] & 0xFF)/256.0F;
                }
            }
        };
        c.addNode(ln);
        ln = new UnitLayerNode(ln, 1024);
        c.addNode(ln);
        ln = new UnitLayerNode(ln, 1024);
        c.addNode(ln);
        ln = new UnitLayerNode(ln, 10);
        c.addNode(ln);
        c.applyAdd();
        for(int i = 0;i < 0;i++) {
            c.updata();
            ln.want(l.readAsArray(c.updataCount));
            c.back();
        }
        int test = 2345;
        c.updataCount = test - 1;
        m.readImage(test);
        c.updata();
        System.out.println(ln.max());
        m.show(test).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
