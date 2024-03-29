/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuxi.node;

import fuxi.Context;
import fuxi.Kits;
import java.io.DataInput;
import java.io.PrintStream;

/**
 *
 * @author 82398
 */
public class UnitLayerNode extends AbstractLayerNode implements LayerNode, BackNode {
    private static void random(float[] array) {
        for(int i = 0,l = array.length;i < l;i++) {
            array[i] = (float) Math.random();
        }
    }

    public UnitLayerNode() {

    }

    public UnitLayerNode(LayerNode from, int size) {
        super(size);
        fromNode = from;
        weight = new float[from.size() * size];
        add = new float[size];
        trends = new float[size];
        random();
    }

    private LayerNode fromNode;
    private float[] weight;
    private float[] add;
    private transient float[] trends;
    
    public void random() {
        random(weight);
        random(add);
    }

    @Override
    public void load(Node[] pool, DataInput input) {
        super.load(pool, input);
        //
    }

    @Override
    public void updata(Context<? extends Node> context) {
        super.updata(context);
        int l = size();
        for (int i = 0; i < l; i++) {
            data[i] = activation(sum(i));
        }
    }
    
    private float sum(int i) {
        float x = add[i];
        for (int j = 0, s = fromNode.size(); j < s; j++) {
            x += fromNode.value(j) * weight[i * s + j];
        }
        return x;
    }

    @Override
    public void back(Context<? extends BackNode> context) {
        int l = data.length;
        for(int i = 0;i < l;i++) {
            float dx = sum(i);
            float ds = trends[i] / derivative(dx);
            float ws = add[i];
            for(int j = 0,s = fromNode.size();j < s;j++) {
                ws += weight[i*s + j];
            }
            ds /= ws;
            for(int j = 0,s = fromNode.size();j < s;j++) {
                weight[i*s + j] += 0.2 * weight[i*s + j]*ds;
                //fromNode.trend(j, ds * weight[i*s + j]);
            }
        }
    }
    
    @Override
    public void trend(int i, float d) {
        trends[i] += d;
    }

    @Override
    public void printDebug(PrintStream print) {
        super.printDebug(print);
        print.print("\tfromNode:>>");
        print.print(Kits.asNode(fromNode));
    }

}
