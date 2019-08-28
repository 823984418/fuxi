/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuxi;

import fuxi.tools.MnistImageFile;
import javax.swing.JFrame;

/**
 *
 * @author 82398
 */
public class Main {

    public static void main(String[] args) throws Exception {
        MnistImageFile m = MnistImageFile.load();
        JFrame f = m.show(0);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
