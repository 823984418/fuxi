/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuxi;

import java.lang.ref.WeakReference;
import java.util.Iterator;

/**
 * 一个弱引用链
 * @author 82398
 * @param <E> 类型
 */
public final class WeakLinks<E> implements Iterable<E> {

    private static void link(RefNode left, RefNode right) {
        left.rightRef = right;
        right.leftRef = left;
    }

    public WeakLinks() {
        link(headRefNode, endRefNode);
    }

    private int refNodeCount = 0;
    private final RefNode headRefNode = new RefNode(null);
    private final RefNode endRefNode = new RefNode(null);

    /**
     * 在头部添加引用
     * @param object 对象
     */
    public void addHead(E object) {
        refNodeCount++;
        RefNode refNode = new RefNode(object);
        link(refNode, headRefNode.rightRef);
        link(headRefNode, refNode);
    }

    /**
     * 在尾部添加引用
     * @param object 对象
     */
    public void add(E object) {
        refNodeCount++;
        RefNode refNode = new RefNode(object);
        link(endRefNode.leftRef, refNode);
        link(refNode, endRefNode);
    }

    /**
     * 获取当前引用计数，注意：此计数大于等于实际引用的数目
     * @return 一个大于有效引用的当前结点数
     */
    public int getRefNodeCount() {
        return refNodeCount;
    }

    /**
     * 顺序迭代引用链，并移除已经回收的引用
     * @return 迭代者
     */
    @Override
    public Iterator<E> iterator() {
        return new RefIterator(headRefNode, endRefNode) {
            @Override
            RefNode<E> next(RefNode<E> ref) {
                return ref.rightRef;
            }
        };
    }

    /**
     * 反序迭代引用链，并移除已经回收的引用
     * @return 迭代者
     */
    public Iterator<E> contraryIterator() {
        return new RefIterator(endRefNode, headRefNode) {
            @Override
            RefNode next(RefNode ref) {
                return ref.leftRef;
            }
        };
    }
    
    /**
     * 在头部追加引用链，并移除追加使用的内容
     * @param add 使用后清空的引用链
     * @return 本身
     */
    public WeakLinks<E> appendHead(WeakLinks<? extends E> add) {
        if (add.refNodeCount == 0) {
            return this;
        }
        refNodeCount += add.refNodeCount;
        add.refNodeCount = 0;
        link(headRefNode.rightRef,add.endRefNode.leftRef);
        link(headRefNode,add.headRefNode.rightRef);
        link(add.headRefNode, add.endRefNode);
        return this;
    }

    /**
     * 在尾部追加引用链，并移除追加使用的内容
     * @param add 使用后清空的引用链
     * @return 本身
     */
    public WeakLinks<E> append(WeakLinks<? extends E> add) {
        if (add.refNodeCount == 0) {
            return this;
        }
        refNodeCount += add.refNodeCount;
        add.refNodeCount = 0;
        link(endRefNode.leftRef, add.headRefNode.rightRef);
        link(add.endRefNode.leftRef, endRefNode);
        link(add.headRefNode, add.endRefNode);
        return this;
    }

    private abstract class RefIterator implements Iterator<E> {

        RefIterator(RefNode<E> beginRef, RefNode<E> endRef) {
            ref = next(beginRef);
            end = endRef;
            load();
        }

        private RefNode<E> ref;
        private final RefNode end;
        private E node;

        abstract RefNode<E> next(RefNode<E> ref);

        private void load() {
            while (ref != end) {
                E n = ref.get();
                if (n != null) {
                    node = n;
                    ref = next(ref);
                    return;
                }
                refNodeCount--;
                link(ref.leftRef, ref.rightRef);
                ref = next(ref);
            }
            node = null;
        }

        @Override
        public boolean hasNext() {
            return node != null;
        }

        @Override
        public E next() {
            E n = node;
            load();
            return n;
        }

    }

    private static class RefNode<E> extends WeakReference<E> {

        RefNode(E node) {
            super(node);
        }

        RefNode<E> leftRef;
        RefNode<E> rightRef;

    }

}
