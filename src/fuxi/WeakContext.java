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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 82398
 */
public class WeakContext<T extends Node> extends Context<T> implements Iterable<T> {

    public WeakContext(Class<T> type) {
        super(type);
    }

    public WeakContext(Class<T> type, DataInput input) throws IOException {
        super(type, input);
        List<Class<? extends Node>> types = new ArrayList<>();
        while (true) {
            String str = input.readUTF();
            if ("void".equals(str)) {
                break;
            }
            types.add(load(str));
        }
        int len = input.readInt() + 1;
        Node[] pool = new Node[len];
        for (int i = 1; i < len; i++) {
            try {
                pool[i++] = types.get(input.readInt()).getConstructor().newInstance();
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

    public int toPool(Node[] array) {
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
        Node[] pool = new Node[nodes.getRefNodeCount() + 1];
        toPool(pool);
        Map<Class<? extends Node>, Integer> types = new HashMap<>();
        int id = 1;
        for (Node n : pool) {
            if (n != null) {
                Class<? extends Node> type = n.getClass();
                Integer i = types.get(type);
                if (i == null) {
                    i = types.size();
                    types.put(type, i);
                    output.writeUTF(type.getName());
                }
                n.setID(id++);
            }
        }
        output.writeUTF("void");
        output.writeInt(id - 1);
        for (Node n : pool) {
            if (n != null) {
                output.writeInt(types.get(n.getClass()));
                n.save(output);
            }
        }
    }

}
