/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuxi.tools;

import java.io.DataInput;
import java.io.IOException;
import java.lang.reflect.Array;

/**
 * 一个用于解析IDX的工具类
 *
 * @author 82398
 * @param <T> 数据类型，必须是基本数组类型
 */
public class IdxFile<T> {

    /**
     * 8位无符号字节
     */
    public static final byte U_BYTE = 0x08;

    /**
     * 8位字节
     */
    public static final byte BYTE = 0x09;

    /**
     * 16位整数
     */
    public static final byte SHORT = 0x0B;

    /**
     * 32位整数
     */
    public static final byte INT = 0x0C;

    /**
     * 32位浮点数
     */
    public static final byte FLOAT = 0x0D;

    /**
     * 64位浮点数
     */
    public static final byte DOUBLE = 0x0E;

    /**
     * 读取一个文件，并且坚持类型，如果类型不符，抛出异常
     *
     * @param types 类型
     * @param input 输入
     * @throws IOException 输入流异常
     */
    public IdxFile(Class<T> types, DataInput input) throws IOException {
        if (!types.isArray()) {
            throw new RuntimeException();
        }
        input.readByte();
        input.readByte();
        type = input.readByte();
        int dimension = input.readUnsignedByte();
        int[] ds = dimensions = new int[dimension];
        int size = 1;
        for (int i = 0; i < dimension; i++) {
            size *= ds[i] = input.readInt();
        }
        boolean can = false;
        Class<?> t = types.getComponentType();
        if (t == byte.class) {
            can = type == U_BYTE || type == BYTE;
            byte[] d = new byte[size];
            input.readFully(d);
            data = (T) d;
        } else if (t == short.class) {
            can = type == SHORT;
            short[] d = new short[size];
            for (int i = 0; i < size; i++) {
                d[i] = input.readShort();
            }
            data = (T) d;
        } else if (t == int.class) {
            can = type == INT;
            int[] d = new int[size];
            for (int i = 0; i < size; i++) {
                d[i] = input.readInt();
            }
            data = (T) d;
        } else if (t == float.class) {
            can = type == FLOAT;
            float[] d = new float[size];
            for (int i = 0; i < size; i++) {
                d[i] = input.readFloat();
            }
            data = (T) d;
        } else if (t == double.class) {
            can = type == DOUBLE;
            double[] d = new double[size];
            for (int i = 0; i < size; i++) {
                d[i] = input.readDouble();
            }
            data = (T) d;
        }
        if (!can) {
            throw new RuntimeException();
        }
    }

    /**
     * 此文件的数据类型
     */
    public byte type;

    /**
     * 此文件的维度信息
     */
    public int[] dimensions;

    /**
     * 此文件的数据
     */
    public T data;

    /**
     * 获取维度数
     *
     * @return 维度数
     */
    public int dimension() {
        return dimensions.length;
    }

    /**
     * 获取某个指定的偏移量，如果输入值长度不等于维度数，抛出异常
     *
     * @param xyz 指定的维度值
     * @return 偏移
     */
    public int getIndex(int... xyz) {
        int[] dis = dimensions;
        int d = xyz.length;
        if (dimension() != xyz.length) {
            throw new RuntimeException();
        }
        int index = 0;
        for (int i = 0; i < d; i++) {
            int p = xyz[i];
            int di = dis[i];
            if (p < 0 || p >= di) {
                throw new RuntimeException();
            }
            index *= di;
            index += p;
        }
        return index;
    }

    /**
     * 获取某个不完全指定的偏移量，输入值长度不能长于维度数，否则抛出异常
     *
     * @param xyz 不完全指定的维度值
     * @return 开头偏移量
     */
    public int getPartIndex(int... xyz) {
        int[] a = new int[dimension()];
        System.arraycopy(xyz, 0, a, 0, xyz.length);
        return getIndex(a);
    }

    /**
     * 获取不完全指定时的数据长度
     *
     * @param p 指定的维度数
     * @return 数据长度
     */
    public int getPartUsed(int p) {
        int[] dis = dimensions;
        int d = dis.length;
        if (p < 0 || p > d) {
            throw new RuntimeException();
        }
        int used = 1;
        for (d--; d >= p; d--) {
            used *= dis[d];
        }
        return used;
    }

    /**
     * 读取一段数据
     *
     * @param array 接受数据的数组
     * @param xyz 不完全指定的位置
     * @return 读出的数据量
     */
    public int readData(T array, int... xyz) {
        int p = getPartUsed(xyz.length);
        int index = getPartIndex(xyz);
        System.arraycopy(data, index, array, 0, p);
        return p;
    }

}
