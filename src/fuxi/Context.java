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
 * 一个上下文
 *
 * @author 82398
 * @param <T> 此上下文限制的结点类型
 */
public class Context<T extends Node> {

    public Context(Class<T> type) {
        typeCheck = type;
    }

    public Context(Class<T> type, DataInput input) throws IOException {
        typeCheck = type;
        if (!type.getName().equals(input.readUTF())) {
            throw new RuntimeException();
        }
    }

    private final Class<T> typeCheck;

    public final Class<T> getTypeCheck() {
        return typeCheck;
    }

    public final T checkType(Node node) {
        if (!typeCheck.isInstance(node)) {
            throw new RuntimeException();
        }
        return (T) node;
    }

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
