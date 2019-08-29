/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuxi.node;

import fuxi.Context;

/**
 *
 * @author 82398
 */
public abstract class InputLayerNode extends AbstractLayerNode implements LayerNode {

    public InputLayerNode() {

    }

    public InputLayerNode(int size) {
        super(size);
    }

    @Override
    public void updata(Context context) {
        super.updata(context);
        input(context, data);
    }

    protected abstract void input(Context context, float[] value);
    
}
