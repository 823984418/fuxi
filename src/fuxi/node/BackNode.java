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
public interface BackNode extends Node {
    
    public void back(Context<? extends BackNode> context);
    
}
