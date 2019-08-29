/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuxi;

import fuxi.node.InputLayerNode;
import fuxi.node.UnitLayerNode;
import fuxi.node.AbstractLayerNode;
import fuxi.node.Node;
import fuxi.tools.MnistImageFile;
import fuxi.tools.MnistLableFile;
import java.util.Arrays;
import javax.swing.JFrame;

/**
 *
 * @author 82398
 */
public class Main {

    public static void main(String[] args) throws Exception {
        MnistImageFile m = MnistImageFile.load();
        MnistLableFile l = MnistLableFile.load();
        
    }
}
