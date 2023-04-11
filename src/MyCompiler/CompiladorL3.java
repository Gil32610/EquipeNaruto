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
        Lexico lexico = new Lexico("C:\\Users\\aluno\\Desktop\\EquipeNaruto\\src\\codigo.txt");
        Token t = null;
        while((t = lexico.nextToken()) != null){
            System.out.println(t);
        }
    }
    
}
