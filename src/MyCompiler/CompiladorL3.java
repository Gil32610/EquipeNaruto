/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyCompiler;

/**
 *
 * @author tarci
 */
public class CompiladorL3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Lexico lexico = new Lexico("D:\\STUDY\\COMPILADORES\\EquipeNaruto\\src\\codigo.txt");
        Sintatico3 sintatico3 = new Sintatico3(lexico);
        sintatico3.S();

    }

}
