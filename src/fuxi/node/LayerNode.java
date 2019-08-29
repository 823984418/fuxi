/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuxi.node;

/**
 * 代表一个层结点
 *
 * @author 82398
 */
public interface LayerNode extends Node {

    /**
     * 读取结点值
     *
     * @param i 结点
     * @return 结点的值
     */
    public float value(int i);

    /**
     * 获取此层的大小
     *
     * @return 此层结点的结点数
     */
    public int size();
}
