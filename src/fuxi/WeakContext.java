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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author 82398
 * @param <T> 限制的结点类型
 */
public class WeakContext<T extends Node> extends Context<T> implements Iterable<T> {

    public WeakContext(Class<T> type) {
        super(type);
    }

    public WeakContext(Class<T> type, DataInput input) throws IOException {
        super(type, input);
        int l = input.readInt();
        Class<? extends T>[] types = new Class[l];
        for(int i = 0;i < l;i++) {
            types[i] = load(input.readUTF());
        }
        int len = input.readInt() + 1;
        Node[] pool = new Node[len];
        for (int i = 1; i < len; i++) {
            try {
                pool[i++] = types[input.readInt()].getConstructor().newInstance();
            } catch (ReflectiveOperationException ex) {
                throw new RuntimeException(ex);
            }
        }
        for (Node n : pool) {
            if (n != null) {
                n.load(pool, input);
                nodes.add(checkType(n));
            }
        }
    }

    private final WeakLinks<T> nodes = new WeakLinks<>();
    private final WeakLinks<T> adds = new WeakLinks<>();

    public void addNode(Node node) {
        adds.add(checkType(node));
    }

    public void applyAdd() {
        nodes.append(adds);
    }

    @Override
    public Iterator<T> iterator() {
        return nodes.iterator();
    }

    public Iterator<T> contraryIterator() {
        return nodes.contraryIterator();
    }

    @Override
    public void updata() {
        super.updata();
        for (Node n : this) {
            if (n != null) {
                n.updata(this);
            }
        }
    }

    public int toArray(Node[] array) {
        int i = 0;
        for (Node n : this) {
            array[i++] = n;
        }
        return i;
    }

    @Override
    public void save(DataOutput output) throws IOException {
        super.save(output);
        applyAdd();
        Node[] array =  new Node[nodes.getRefNodeCount() + 1];
        toArray(array);
        Set<Class<? extends T>> types = new HashSet<>();
        int id = 0;
        for (Node n : array) {
            if (n != null) {
                n.setID(++id);
                Class<? extends T> type = (Class<? extends T>) n.getClass();
                types.add(type);
            }
        }
        
        int i = 0;
        Map<Class<? extends T>,Integer> map = new HashMap<>();
        output.writeInt(types.size());
        for(Class<? extends T> t : types) {
            map.put(t, i++);
            output.writeUTF(t.getName());
        }
        
        output.writeInt(id);
        for (Node n : array) {
            if (n != null) {
                output.writeInt(map.get((Class<? extends T>) n.getClass()));
                n.save(output);
            }
        }
    }

}
