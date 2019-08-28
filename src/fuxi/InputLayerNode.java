/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuxi;

/**
 *
 * @author 82398
 */
public abstract class InputLayerNode extends AbstractNode implements LayerNode {

    public InputLayerNode() {

    }

    public InputLayerNode(int length) {
        value = new float[length];
    }
    private float[] value;

    @Override
    public float value(int i) {
        return value[i];
    }

    @Override
    public void updata(Context context) {
        super.updata(context);
        input(context, value);
    }

    @Override
    public int size() {
        return value.length;
    }

    protected abstract void input(Context context, float[] value);
}
