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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author 82398
 * @param <T> 限制的结点类型
 */
public class WeakContext<T extends Node> extends Context<T> implements Iterable<T> {

    /**
     * 创建一个弱引用上下文 根据给定的限制类型
     *
     * @param type 限制类型
     */
    public WeakContext(Class<T> type) {
        super(type);
    }

    /**
     * 反序列化一个弱引用上下文 检查限制类型
     *
     * @param type 限制类型
     * @param input 输入流
     * @throws IOException 输入流异常
     */
    public WeakContext(Class<T> type, DataInput input) throws IOException {
        super(type, input);
        int l = input.readInt();
        Class<? extends T>[] types = new Class[l];
        for (int i = 0; i < l; i++) {
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

    /**
     * 预添加一个结点 此节点不会直接被添加 还需要调用{@link applyAdd()}应用添加
     *
     * @param node 添加的结点
     */
    public void addNode(Node node) {
        adds.add(checkType(node));
    }

    /**
     * 应用添加
     */
    public void applyAdd() {
        nodes.append(adds);
    }

    /**
     * 顺序迭代 迭代过程中移除无效引用
     *
     * @return 迭代者
     */
    @Override
    public Iterator<T> iterator() {
        return nodes.iterator();
    }

    /**
     * 反序迭代 迭代过程中移除无效引用
     *
     * @return 迭代者
     */
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

    @Override
    public int toArrayAndLoadId(Node[] array) {
        int i = 0;
        for (Node n : this) {
            array[i++] = n;
            n.setId(i);
        }
        return i;
    }

    /**
     * 序列化此弱引用上下文 此过程会调用{@link applyAdd()}以序列化所有结点
     * 调用{@link toArrayAndLoadId(Node[])}分配id
     *
     * @param output 输出流
     * @throws IOException 输出流异常
     */
    @Override
    public void save(DataOutput output) throws IOException {
        super.save(output);
        applyAdd();
        Node[] array = new Node[nodes.getRefNodeCount()];
        int l = toArrayAndLoadId(array);
        Set<Class<? extends T>> types = new HashSet<>();
        for (int n = 0; n < l; n++) {
            types.add((Class<? extends T>) array[n].getClass());
        }

        int i = 0;
        Map<Class<? extends T>, Integer> map = new HashMap<>();
        output.writeInt(types.size());
        for (Class<? extends T> t : types) {
            map.put(t, i++);
            output.writeUTF(t.getName());
        }

        output.writeInt(l);
        for (int n = 0; n < l; n++) {
            Node nn = array[n];
            output.writeInt(map.get((Class<? extends T>) nn.getClass()));
            nn.save(output);
        }
    }

}
