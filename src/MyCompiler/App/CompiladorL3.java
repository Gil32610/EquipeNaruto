/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyCompiler.App;

import MyCompiler.AnaliseLexica.Token;
import MyCompiler.AnaliseLexica.Lexico;
import MyCompiler.AnaliseSintatica.Sintatico3;

/**
 *
 * @author tarci
 */
public class CompiladorL3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Lexico lexico1 = new Lexico("D:\\STUDY\\COMPILADORES\\EquipeNaruto\\src\\lexico.txt");
        // TODO code application logic here
        Token t = null;
        while((t = lexico1.nextToken())!= null){
            System.out.println(t);
        }
        
        Lexico lexico = new Lexico("D:\\STUDY\\COMPILADORES\\EquipeNaruto\\src\\codigo.txt");
        Sintatico3 sintatico3 = new Sintatico3(lexico);
        sintatico3.S();

    }

}
