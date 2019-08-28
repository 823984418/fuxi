/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuxi;

import java.io.DataInput;
import java.io.DataOutput;

/**
 * 神经网络结点 任何一个实现类都至少有一个公有无参构造器一用于反序列化
 *
 * @author 82398
 */
public interface Node {

    /**
     * 在使用无参构造器尝试反序列化时读取输入
     *
     * @param pool 所有反序列化的结点
     * @param input 输入
     */
    public void load(Node[] pool, DataInput input);

    /**
     * 设置此结点的id 通常用于序列化结点 由Context配置
     *
     * @param id id值
     */
    public void setID(int id);

    /**
     * 获取此结点的id值 通常此值既结点是在{@link load(Node[],DataInput)}的pool中的位置
     *
     * @return id值
     */
    public int getID();

    /**
     * 序列化此节点
     *
     * @param output 输出
     */
    public void save(DataOutput output);

    /**
     * 更新结点内容
     *
     * @param context 调用者
     */
    public void updata(Context context);

    /**
     * 反向更新结点内容
     *
     * @param context 调用者
     */
    public void back(Context context);

}
