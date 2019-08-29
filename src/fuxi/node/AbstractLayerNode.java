/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuxi.node;

import java.io.PrintStream;

/**
 *
 * @author 82398
 */
public abstract class AbstractLayerNode extends AbstractNode implements LayerNode {
    public AbstractLayerNode() {
        
    }
    public AbstractLayerNode(int size) {
        data = new float[size];
    }
    protected float[] data;
    @Override
    public float value(int i) {
        return data[i];
    }
    @Override
    public int size() {
        return data.length;
    }

    @Override
    public void printDebug(PrintStream print) {
        super.printDebug(print);
        print.print("\tvalue[");
        int l = size();
        print.print(l);
        print.print("]:>>");
        for(int i = 0;i < l;i++) {
            print.print("|");
            print.print(value(i));
        }
        print.println("|");
    }

}
