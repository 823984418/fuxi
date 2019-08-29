/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuxi;

import fuxi.node.InputLayerNode;
import fuxi.node.LayerNode;
import fuxi.node.Node;
import fuxi.node.UnitLayerNode;
import fuxi.tools.MnistImageFile;
import fuxi.tools.MnistLableFile;

/**
 *
 * @author 82398
 */
public class Main {

    public static void main(String[] args) throws Exception {
        MnistImageFile m = MnistImageFile.load();
        MnistLableFile l = MnistLableFile.load();
        WeakContext<Node> c = new WeakContext<>(Node.class);
        LayerNode ln = new InputLayerNode(10) {
            @Override
            protected void input(Context context, float[] value) {
                
            }
        };
        c.addNode(ln);
        ln = new UnitLayerNode(ln, 10);
        c.addNode(ln);
        c.applyAdd();
        c.printDebug(System.out);
    }
}
