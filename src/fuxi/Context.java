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
        return null;
    }

    public void loadID() {

    }

    public void updata() {

    }

    public void save(DataOutput output) throws IOException {
        output.writeUTF(typeCheck.getName());
    }

}
