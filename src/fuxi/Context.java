/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuxi;

import fuxi.node.Node;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 一个上下文 此类不直接使用，由子类继承
 *
 * @author 82398
 * @param <T> 此上下文限制的结点类型
 */
public class Context<T extends Node> {

    /**
     * 新建一个上下文 设置类型限制
     *
     * @param type 限制类型
     */
    public Context(Class<T> type) {
        typeCheck = type;
    }

    /**
     * 反序列化一个上下文 检查类型限制是否相同，不同抛出异常
     *
     * @param type 类型限制
     * @param input 输入流
     * @throws IOException 输入流异常
     */
    public Context(Class<T> type, DataInput input) throws IOException {
        typeCheck = type;
        if (!type.getName().equals(input.readUTF())) {
            throw new RuntimeException();
        }
    }

    private final Class<T> typeCheck;

    /**
     * 获取类型限制
     *
     * @return 限制的类型
     */
    public final Class<T> getTypeCheck() {
        return typeCheck;
    }

    /**
     * 对结点进行类型检查 如果不在限制范围内，抛出异常
     *
     * @param node 结点
     * @return 结点
     */
    public final T checkType(Node node) {
        if (!typeCheck.isInstance(node)) {
            throw new RuntimeException();
        }
        return (T) node;
    }

    /**
     * 试图加载一个结点类 如果没有找到或者不在限制范围内，抛出异常
     *
     * @param typeName 名称
     * @return 加载类
     */
    public Class<? extends T> load(String typeName) {
        try {
            Class<?> type = Class.forName(typeName);
            if (typeCheck.isAssignableFrom(type)) {
                return (Class<? extends T>) type;
            }
        } catch (ClassNotFoundException ex) {
        }
        throw new RuntimeException();
    }

    /**
     * 将结点输出为数组并分配id 注意分配的id是从1开始的 即array[node.getId() - 1] == node
     *
     * @param array 输出
     * @return 实际结点数
     */
    public int toArrayAndLoadId(Node[] array) {
        return 0;
    }

    /**
     * 尝试更新一次全部结点
     */
    public void updata() {

    }

    /**
     * 尝试序列化这个上下文 此过程还可能会调用{@link  #toArrayAndLoadId(Node[])}(由子类实现)
     *
     * @param output 输出流
     * @throws IOException 输出流异常
     */
    public void save(DataOutput output) throws IOException {
        output.writeUTF(typeCheck.getName());
    }

}
