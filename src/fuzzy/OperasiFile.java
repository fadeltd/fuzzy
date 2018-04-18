package fuzzy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;

/* -------------------
 * Operasi File.java
 * -------------------
 * (C) Copyright 2014
 * @author Fadel Trivandi Dipantara.
 *
 */
public class OperasiFile {

    /**
     * Fungsi untuk memasukkan variabel pada class variabel yang dibaca dari
     * sebuah file txt. file txt dapat di pilih oleh user melalui tab setting
     * pada user interface
     *
     * @param path - Directory file variabel
     * @param pathRule - Directory file rule
     * @param input - Variabel yang akan di hitung myu nya
     * @return data table hasil perhitungan myu, alpha predikat, z, serta alpha*z yang akan di tampilkan
     */
    @SuppressWarnings({"UnusedAssignment", "empty-statement"})
    public String[][] inputVariabel(String path, String pathRule, double[] input) {
        try (BufferedReader in = new BufferedReader(new FileReader(path))) {
            Variabel[] var = getVariabel(path);
            int jumlahBaris = getLineNumber(path), jumlahRule = getLineNumber(pathRule) - 1;
            /**
             * Menghitung myu dari setiap fuzzy
             */
            Myu[][] myu = new Myu[var.length - 1][var[0].getMember().length];
            for (int counterMyu = 0; counterMyu < myu.length; counterMyu++) {
                for (int j = 0; j < myu[counterMyu].length; j++) {
                    myu[counterMyu][j] = new Myu(var[counterMyu].getMemberRange(j));
                    myu[counterMyu][j].setName(var[counterMyu].getMemberName(j));
                    myu[counterMyu][j].setMyu(var[counterMyu].getMemberBottom(j), var[counterMyu].getMemberTop(j), input[counterMyu]);
                }
            }
            
            String[][] allRules = inputRule(pathRule);
            int jumlahKolomRule = inputRule(pathRule)[0].length - 1;
            Rule[] rule = new Rule[jumlahRule];
            String[] outputRule = new String[jumlahRule];
            String[][] inputRule = new String[jumlahRule][jumlahKolomRule];
            double[][] inputMyu = new double[jumlahRule][jumlahKolomRule];
            
            for (int i = 0; i < allRules.length; i++) {
                System.arraycopy(allRules[i], 0, inputRule[i], 0, allRules[i].length - 1);
                outputRule[i] = allRules[i][jumlahKolomRule];
            }
            
            for (int i = 0; i < inputRule.length; i++) {
                for (int j = 0; j < inputRule[i].length; j++) {
                    for (Myu[] myu1 : myu) {
                        for (Myu myu11 : myu1) {
                            if (myu11.getName().equals(inputRule[i][j])) {
                                inputMyu[i][j] = myu11.getMyu();
                            }
                        }
                    }
                }
            }
            
            for (int i = 0; i < inputRule.length; i++) {
                rule[i] = new Rule(outputRule[i], inputRule[i], inputMyu[i]);
            }
            
            Z[] z = new Z[jumlahRule];
            for (int i = 0; i < jumlahRule; i++) {
                for (int j = 0; j < var[jumlahBaris - 1].getMember().length; j++) {
                    if (rule[i].getName().equals(var[jumlahBaris - 1].getMemberName(j))) {
                        z[i] = new Z(rule[i].getName(), rule[i].getRuleValue(), var[jumlahBaris - 1].getMemberRange(j));
                        z[i].setZ(var[jumlahBaris - 1].getMemberBottom(j), var[jumlahBaris - 1].getMemberTop(j));
                    }
                }
            }
            
            String[][] tableContent = new String[jumlahRule][jumlahKolomRule + 4];
            for (int i = 0; i < jumlahRule; i++) {
                System.arraycopy(rule[i].getString(), 0, tableContent[i], 0, tableContent[i].length - 2);
                tableContent[i][jumlahKolomRule + 2] = String.format("%.2f", z[i].getZ());
                double alphaZ = rule[i].getRuleValue() * z[i].getZ();
                tableContent[i][jumlahKolomRule + 3] = String.format("%.2f", alphaZ);;
            }
            return tableContent;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File Variabel masih kosong");
        } catch (ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null, "File Variabel yang diinputkan salah");
        }
        return null;
    }
    
    public Variabel[] getVariabel(String path) {
        try (BufferedReader in = new BufferedReader(new FileReader(path))) {
            String temp;
            int jumlahBaris = getLineNumber(path);
            Variabel[] var = new Variabel[jumlahBaris];
            int counter = 0;
            /**
             * Perintah untuk mengambil data variabel dan member dari txt dimana
             * setiap variabel akan ditulis dalam satu baris yang sama dan tab
             * (\t) member berserta rangenya
             */
            while ((temp = in.readLine()) != null) {
                String[] variabel = temp.split("[\t]");
                String[] allMember = variabel[1].split("[|]");
                var[counter] = new Variabel(variabel[0], allMember.length);
                /**
                 * Perintah untuk mengambil data member dari txt yang berisi
                 * nama member, dan range data dari setiap membernya. Dimana
                 * setiap member akan di pisahkan dengan koma (,) dan data range
                 * dari setiap membernya akan di pisahkan dengan titik dua (|)
                 */
                for (int j = 0; j < allMember.length; j++) {
                    String[] member = allMember[j].split("[,]");
                    double[] range = new double[member.length - 1];
                    int counterMember = 1;
                    for (int k = 0; k < range.length; k++) {
                        range[k] = Double.valueOf(member[counterMember]);
                        counterMember++;
                    }
                    var[counter].setMember(j, member[0], range);
                }
                counter++;
            }
            in.close();
            return var;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File Variabel masih kosong");
        }
        return null;
    }

    /**
     * Fungsi untuk memasukkan rule pada class rule yang dibaca dari sebuah file
     * txt. File txt dapat di pilih oleh user melalui tab setting pada user
     * interface
     *
     * @param path
     * @return 
     */
    public String[][] inputRule(String path) {
        try (BufferedReader in = new BufferedReader(new FileReader(path))) {
            // Variabel untuk menyimpan data dari dalam txt
            String temp;
            int jumlahBaris = getLineNumber(path) - 1, jumlahKolom = getColumnNumber(path);
            String[] outputRule = new String[jumlahBaris];
            String[][] inputRule = new String[jumlahBaris][jumlahKolom - 1];
            /**
             * Perintah untuk mengambil data variabel dan member dari txt dimana
             * setiap variabel akan ditulis dalam satu baris yang sama dan comma
             * (,) member berserta rangenya
             */
            
            temp = in.readLine(); //SKIP FIRST LINE
            int outRuleCounter = 0;
            while ((temp = in.readLine()) != null) {
                String[] rule = temp.split("[,]");
                outputRule[outRuleCounter] = rule[jumlahKolom - 1];
                int inRuleCounter = 0;
                for (int j = 0; j < rule.length - 1; j++) {
                    inputRule[outRuleCounter][j] = rule[inRuleCounter++];
                }
                outRuleCounter++;
            }
            
            String[][] tableContent = new String[jumlahBaris][jumlahKolom];
            for (int i = 0; i < jumlahBaris; i++) {
                System.arraycopy(inputRule[i], 0, tableContent[i], 0, jumlahKolom - 1);
                tableContent[i][jumlahKolom - 1] = outputRule[i];
            }
            in.close();
            return tableContent;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File Rule masih kosong");
        } catch (ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null, "File Rule yang diinputkan salah");
        }
        return null;
    }

    /**
     * Fungsi untuk menghitung jumlah baris yang terdapat pada file
     * yang di inputkan
     * @param path
     * @return Jumlah Baris dari sebuah File
     */
    public int getLineNumber(String path) {
        try (BufferedReader in = new BufferedReader(new FileReader(path))) {
            String temp;
            int lineNumber = 0;
            while ((temp = in.readLine()) != null) {
                lineNumber++;
            }
            return lineNumber;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return 0;
        }
    }

    /**
     * Fungsi untuk menghitung jumlah baris yang terdapat pada file yang di
     * inputkan
     * @param path
     * @return Jumlah Kolom pada baris pertama pada file yang dipisahkan
     * dengan comma
     */
    public int getColumnNumber(String path) {
        try (BufferedReader in = new BufferedReader(new FileReader(path))) {
            String temp = in.readLine();
            String[] column = temp.split("[,]");
            return column.length;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return 0;
        }
    }

    /**
     * Fungsi untuk mendapatkan baris pertama yang terdapat pada file yang di
     * inputkan
     *
     * @param path - Direktori dari file yang di inginkan
     * @return - Isi dari baris pertama pada file Rule
     */
    public String[] getColumnNames(String path) {
        try (BufferedReader in = new BufferedReader(new FileReader(path))) {
            String temp = in.readLine();
            String[] column = temp.split("[,]");
            return column;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return null;
        }
    }
}
