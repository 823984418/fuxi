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
public interface ValueNode extends Node {
    public float value();
    public void trend(float d);
}
