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
    
}
