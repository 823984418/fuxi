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
 *
 * @author 82398
 */
public class Context<T extends Node> {
    public Context(Class<T> type) {
        typeCheck = type;
        updataCount = -1;
    }
    public Context(Class<T> type, DataInput input) throws IOException {
        typeCheck = type;
        updataCount = input.readInt();
    }
    private final Class<T> typeCheck;
    public Class<T> getTypeCheck() {
        return typeCheck;
    }
    public T checkType(Node node) {
        if(!typeCheck.isInstance(node)) {
            throw new RuntimeException();
        }
        return (T) node;
    }
    public Class<? extends Node> load(String typeName) {
        try {
            Class<?> type = Class.forName(typeName);
            if(Node.class.isAssignableFrom(type)){
                return (Class<? extends Node>) type;
            }
        } catch (ClassNotFoundException ex) {
        }
         return null;
    }
    public void loadID() {
        
    }
    public void save(DataOutput output) throws IOException {
        output.writeInt(updataCount);
    }
    public int updataCount;
    public void updata() {
        updataCount++;
    }
}
