/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fuzzy;

/**
 *
 * @author FadelTrivandi
 */
public class Z {
    
    private final String name;
    private final double[]range;
    private final double alpha;
    private double z;
    
    public Z(String name, double alpha, double[]range){
        this.name = name;
        this.alpha = alpha;
        this.range = range;
    }
    
    public void setZ(boolean bottom, boolean top){
        if (bottom) {
            this.z = this.range[1]-(this.alpha*(this.range[1]-this.range[0]));
        } else if (top) {
            this.z = this.range[1]+(this.alpha*(this.range[1]-this.range[0]));
        } else {
            this.z = (this.range[1]+this.range[2])/2;
        }
    }
    
    public String getName(){
        return this.name;
    }
    
    public double getZ(){
        return this.z;
    }
}
