package fuzzy;

/**
 * Class untuk menyimpan data rule, berupa member-member dari setiap variabel
 * yang digunakan, baik input maupun output, untuk mendapatkan myu atau derajat
 * keanggotaan
 *
 * @author Fadel
 */
public class Rule {

    /**
     * Atribut yang digunakan pada class rule berisi nama dari output. Isi dari
     * rule, berupa array String dari member input/
     */
    private final String name;
    private final String[] content;
    private double ruleValue;
    private final double[] myu;

    public Rule(String name, String[] content, double[] myu) {
        this.name = name;
        this.content = content;
        this.myu = myu;
        this.setRuleValue();
    }

    private void setRuleValue() {
        this.ruleValue = this.myu[0];
        for (int i = 1; i < this.myu.length; i++) {
            if (this.myu[i] < ruleValue) {
                this.ruleValue = this.myu[i];
            }
        }
    }

    public String getName() {
        return this.name;
    }

    public String[] getContent() {
        return this.content;
    }

    public String getContentName(int index) {
        return this.content[index];
    }

    public double getRuleValue() {
        return this.ruleValue;
    }

    public String[] getString() {
        String[] temp = new String[this.myu.length + 2];
        for (int i = 0; i < this.myu.length; i++) {
            temp[i] = String.format("%.2f", this.myu[i]);
        }
        temp[this.myu.length] = this.name;
        temp[this.myu.length + 1] = String.format("%.2f", this.getRuleValue());
        return temp;
    }

    @Override
    public String toString() {
        String temp = "";
        for (int i = 0; i < this.myu.length; i++) {
            temp += String.format("%.2f", this.myu[i]);
            if (i != this.myu.length - 1) {
                temp += " AND ";
            }
        }
        return this.name + " = " + temp + " = " + String.format("%.2f", this.getRuleValue());
    }
}
