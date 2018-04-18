package fuzzy;

/**
 *
 * @author Fadel
 */
public class Myu {

    private String name;
    private final double[] range;
    private double myu;
    private int rule;

    public Myu(double[] range) {
        this.range = range;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMyu(boolean bottom, boolean top, double value) {
        
        if (bottom) {
            if (value < this.range[0]) {
                this.myu = 1;
            } else if (value >= this.range[0] && value <= this.range[1]) {
                this.myu = (this.range[1] - value) / (this.range[1] - this.range[0]);
            } else {
                this.myu = 0;
            }
        } else if (top) {
            if (value >= this.range[0] && value <= this.range[1]) {
                this.myu = (value - this.range[0]) / (this.range[1] - this.range[0]);
            } else if (value > this.range[1]) {
                this.myu = 1;
            } else {
                this.myu = 0;
            }
        } else {
            if (range.length > 3) {
                //System.out.println(this.range[1] +" "+ this.range[2]+" "+value);
                //Range Pertama
                if (value >= this.range[0] && value <= this.range[1]) {
                    this.myu = (value - this.range[0]) / (this.range[1] - this.range[0]);
                }
                //Range Kedua
                else if (value >= this.range[1] && value <= this.range[2]) {
                    this.myu = 1;
                }//Range Ketiga
                else if (value >= this.range[2] && value <= this.range[3]) {
                    this.myu = (this.range[3] - value) / (this.range[3] - this.range[2]);
                } else {
                    this.myu = 0;
                }
            } else {
                //Range Pertama
                if (value >= this.range[0] && value <= this.range[1]) {
                    this.myu = (value - this.range[0]) / (this.range[1] - this.range[0]);
                }//Range Kedua
                else if (value >= this.range[1] && value <= this.range[2]) {
                    this.myu = (this.range[2] - value) / (this.range[2] - this.range[1]);
                } else {
                    this.myu = 0;
                }
            }
        }
    }
    
    public String getName() {
        return this.name;
    }

    public void setRule(int rule) {
        this.rule = rule;
    }

    public int getRule() {
        return this.rule;
    }

    public double getMyu() {
        return this.myu;
    }
}
