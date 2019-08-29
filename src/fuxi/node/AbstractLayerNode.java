/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuxi.node;

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
    
    public void want(float[] v) {
        if(v.length != size()) {
            throw new RuntimeException();
        }
        for(int i = 0,l = size();i < l;i++) {
            trend(i, v[i] - value(i));
        }
    }
    public int max() {
        int p = -1;
        float v = Float.NEGATIVE_INFINITY;
        for(int i = 0,l = size();i < l;i++) {
            float o = value(i);
            if(o > v) {
                v = o;
                p = i;
            }
        }
        return p;
    }
}
