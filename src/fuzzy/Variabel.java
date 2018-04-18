package fuzzy;

/**
 *
 * @author Fadel
 */
public class Variabel {
    private String name;
    private final Member[]member;
    
    public Variabel(String name, int memberSize){
        this.name = name;
        this.member = new Member[memberSize];
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setMember(int index, String name, double[]range){
        this.member[index] = new Member(name, range);
        if(range.length<3){
            if(index > 0 && this.member[0].getValue(0) < this.member[index].getValue(1)){
                this.member[index].setTop(true);
            }else{
                this.member[index].setBottom(true);
            }
        }
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getMemberName(int index){
        return this.member[index].getName();
    }
    
    public double getMemberRangeValue(int index, int rangeIndex){
        return this.member[index].getValue(rangeIndex);
    }
    
    public double[]getMemberRange(int index){
        return this.member[index].getRange();
    }
    
    public boolean getMemberTop(int index){
        return this.member[index].getTop();
    }
    
    public boolean getMemberBottom(int index){
        return this.member[index].getBottom();
    }
    
    public Member[] getMember(){
        return this.member;
    }
    
}
