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
public abstract class AbstractValueNode extends AbstractNode implements ValueNode {
    public AbstractValueNode() {
        
    }
    protected float value;
    @Override
    public float value() {
        return value;
    }
    @Override
    public void trend(float d) {
        
    }
}
