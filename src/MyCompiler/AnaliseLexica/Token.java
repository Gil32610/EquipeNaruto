/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyCompiler.AnaliseLexica;

/**
 *
 * @author tarci
 */
public class Token {
    public static int TIPO_INTEIRO = 0;
    public static int TIPO_REAL = 1;
    public static int TIPO_CHAR = 2;
    public static int TIPO_IDENTIFICADOR = 3;
    public static int TIPO_OPERADOR_RELACIONAL = 4;
    public static int TIPO_OPERADOR_ARITMETICO = 5;
    public static int TIPO_CARACTER_ESPECIAL = 6;
    public static int TIPO_PALAVRA_RESERVADA = 7;
    public static int TIPO_ATRIBUICAO = 8;
    public static int TIPO_EMOJI = 9;
    public static int TIPO_RUSSO = 10;
    public static int TIPO_LEET = 11;
    public static final int TIPO_MODIFICADOR_DE_ACESSO = 12;
    public static int TIPO_FIM_CODIGO = 99;

    private int tipo; // tipo do token
    private String lexema; // conteúdo do token

    public Token(String lexema, int tipo) {
        this.lexema = lexema;
        this.tipo = tipo;
    }

    public String getLexema() {
        return this.lexema;
    }

    public int getTipo() {
        return this.tipo;
    }

    @Override
    public String toString() {
        switch (this.tipo) {
            case 0:
                return this.lexema + " - INTEIRO";
            case 1:
                return this.lexema + " - REAL";
            case 2:
                return this.lexema + " - CHAR";
            case 3:
                return this.lexema + " - IDENTIFICADOR";
            case 4:
                return this.lexema + " - OPERADOR_RELACIONAL";
            case 5:
                return this.lexema + " - OPERADOR_ARITMETICO";
            case 6:
                return this.lexema + " - CARACTER_ESPECIAL";
            case 7:
                return this.lexema + " - PALAVRA_RESERVADA";
            case 8:
                return this.lexema + " - OPERADOR_ATRIBUICAO";
            case 9:
                return this.lexema + " - EMOJI";
            case 10:
                return this.lexema + " - RUSSO";
            case 11:
                return this.lexema + " - L337";
            case 12:
                return this.lexema + " - MODIFICADOR_DE_ACESSO";
            case 99:
                return this.lexema + " - FIM_CODIGO";
        }
        return "";
    }

}
