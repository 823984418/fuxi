/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuxi;

import fuxi.node.Node;

/**
 *
 * @author 82398
 */
public class Kits {
    public static int getID(Node node) {
        if(node == null) {
            return 0;
        }
        return node.getID();
    }
}
