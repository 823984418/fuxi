/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuxi.node;

import fuxi.Context;
import fuxi.Kits;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.PrintStream;

/**
 *
 * @author 82398
 */
public abstract class AbstractNode implements Node {
    
    public AbstractNode() {
        
    }
    
    private transient int id;

    @Override
    public void load(Node[] pool, DataInput input) {
        
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void save(DataOutput output) {
        
    }

    @Override
    public void updata(Context<? extends Node> context) {
        
    }
    
    protected float activation(float x) {
        return (float) Math.tanh(x);
    }
    
    protected float derivative(float x) {
        float a = (float) Math.tanh(x);
        return 1 - a*a;
    }

    @Override
    public void printDebug(PrintStream print) {
        print.println(Kits.asNode(this));
    }

}
