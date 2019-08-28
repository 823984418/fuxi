/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuxi;

import java.io.DataInput;

/**
 *
 * @author 82398
 */
public class UnitLayerNode extends AbstractLayerNode implements LayerNode {

    public UnitLayerNode() {

    }

    public UnitLayerNode(LayerNode from, int size) {
        super(size);
        fromNode = from;
        weight = new float[from.size() * size];
        add = new float[size];
    }

    private LayerNode fromNode;
    private float[] weight;
    private float[] add;
    private transient float[] trends;

    @Override
    public void load(Node[] pool, DataInput input) {
        super.load(pool, input);
        //
    }

    @Override
    public void updata(Context context) {
        super.updata(context);
        int l = data.length;
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
    public void back(Context context) {
        super.back(context);
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
                fromNode.trend(j, ds * weight[i*s + j]);
            }
        }
    }
    @Override
    public void trend(int i, float d) {
        trends[i] += d;
    }

}
