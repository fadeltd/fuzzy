package fuzzy;

/**
 * Class untuk menyimpan member dari variabel
 * Contoh variabel = Suhu
 * Member = Dingin, Sedang, Hangat
 * @author Fadel
 */
public class Member {

    private final String name;
    private final double[] range;
    private boolean top = false, bottom;

    /**
     * Menginisialisasi nama dari member, dan
     * range data yang dimiliki 
     * @param name Dingin
     * @param range {5, 10, 15}
     */
    public Member(String name, double[] range) {
        this.name = name;
        this.range = range;
    }

    /**
     * Menginisialisasi apakah member termasuk
     * data tebawah, sehingga jika nilai kurang dari
     * range pertama, grafik menunjukkan
     * garis lurus bernilai 1 sehingga myu akan
     * bernilai 1
     * @param top true/false
     */
    public void setTop(boolean top) {
        this.top = top;
    }

    /**
     * Menginisialisasi apakah member termasuk
     * data teratas, sehingga jika nilai melebihi
     * range terakhir, grafik menunjukkan
     * garis lurus bernilai 1 sehingga myu akan
     * bernilai 1
     * @param bottom true/false
     */
    public void setBottom(boolean bottom) {
        this.bottom = bottom;
    }

    /**
     * Mengembalikan nama yang dimiliki member
     * @return nama dari member (Dingin, dsb)
     */
    public String getName() {
        return this.name;
    }

    /**
     * Mengembalikan range yang dimiliki member
     * @return double[]range
     */
    public double[] getRange() {
        return this.range;
    }

    /**
     * Mengembalikan suatu nilai yang dimiliki range
     * @param index index nilai dari range
     * @return mengembalikan suatu nilai yang dimiliki range
     */
    public double getValue(int index) {
        return this.range[index];
    }

    public boolean getTop() {
        return this.top;
    }

    public boolean getBottom() {
        return this.bottom;
    }
}
