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

public class Sintatico2 {
    private Lexico lexico;
    private Token token;

    public Sintatico2(Lexico lexico) {
        this.lexico = lexico;
    }

    public void S() {// S determina estado inicial
        this.token = this.lexico.nextToken();
        if (!token.getLexema().equals("main")) {
            throw new RuntimeException("Oxe, cadê main?");
        }

        this.token = this.lexico.nextToken();
        if (!token.getLexema().equals("(")) {
            throw new RuntimeException("Abre o parêntese do main cabra!");
        }

        this.token = this.lexico.nextToken();
        if (!token.getLexema().equals(")")) {
            throw new RuntimeException("Fechar o parêntese do main cabra!");
        }
        this.token = this.lexico.nextToken();

        this.B();
        if (this.token.getTipo() == Token.TIPO_FIM_CODIGO) {
            System.out.println("O Código tá massa! Arretado! Tu botou pra torar!");
        } else {
            throw new RuntimeException("Oxe, eu deu bronca preto do fim do programa.");
        }
    }

    private void B() {
        if (!this.token.getLexema().equals("{")) {
            throw new RuntimeException("Oxe, tave esperando um \"{\" pertinho de " + this.token.getLexema());
        }
        this.token = this.lexico.nextToken();
        this.CS();
        if (!this.token.getLexema().equals("}")) {
            throw new RuntimeException("Oxe, tava esperando um \"}\" pertinho de " + this.token.getLexema());
        }
        this.token = this.lexico.nextToken();
    }

    private void CS() {
        if ((this.token.getTipo() == Token.TIPO_IDENTIFICADOR) ||
                this.token.getLexema().equals("int") ||
                this.token.getLexema().equals("float")) {

            this.C();
            this.CS();
        } else {

        }
    }

    private void C() {
        if (this.token.getTipo() == Token.TIPO_IDENTIFICADOR) {
            this.ATRIBUICAO();
        } else if (this.token.getLexema().equals("int") ||
                this.token.getLexema().equals("float")) {
            this.DECLARACAO();
        } else {
            throw new RuntimeException("Oxe, eu tava esperando tu "
                    + "declarar um comando pertinho de :" + this.token.getLexema());
        }
    }

    private void DECLARACAO() {
        if (!(this.token.getLexema().equals("int") ||
                this.token.getLexema().equals("float"))) {
            throw new RuntimeException("Tu vacilou na delcaração de variável. "
                    + "Pertinho de: " + this.token.getLexema());
        }
        this.token = this.lexico.nextToken();
        if (this.token.getTipo() != Token.TIPO_IDENTIFICADOR) {
            throw new RuntimeException("Tu vacilou na delcaração de variável. "
                    + "Pertinho de: " + this.token.getLexema());
        }
        this.token = this.lexico.nextToken();
        if (!this.token.getLexema().equalsIgnoreCase(";")) {
            throw new RuntimeException("Tu vacilou  na delcaração de variável. "
                    + "Pertinho de: " + this.token.getLexema());
        }
        this.token = this.lexico.nextToken();
    }

    private void ATRIBUICAO() {
        if (this.token.getTipo() != Token.TIPO_IDENTIFICADOR) {
            throw new RuntimeException("Erro na atribuição. Pertinho de: " + this.token.getLexema());
        }
        this.token = this.lexico.nextToken();
        if (this.token.getTipo() != Token.TIPO_ATRIBUICAO) {
            throw new RuntimeException("Erro na atribuição. Pertinho de: " + this.token.getLexema());
        }
        this.token = this.lexico.nextToken();
        this.E();
        if (!this.token.getLexema().equals(";")) {
            throw new RuntimeException("Erro na atribuição. Pertinho de: " + this.token.getLexema());
        }
        this.token = this.lexico.nextToken();
    }

    private void E() {
        this.T();
        this.El();
    }

    private void El() {
        if (this.token.getTipo() == Token.TIPO_OPERADOR_ARITMETICO) {
            this.OP();
            this.T();
            this.El();
        } else {
        }
    }

    private void T() {
        if (this.token.getTipo() == Token.TIPO_IDENTIFICADOR ||
                this.token.getTipo() == Token.TIPO_INTEIRO ||
                this.token.getTipo() == Token.TIPO_REAL) {
            this.token = this.lexico.nextToken();
        } else {
            throw new RuntimeException("Oxe, era para ser um identificador "
                    + "ou número pertinho de " + this.token.getLexema());
        }
    }

    private void OP() {
        if (this.token.getTipo() == Token.TIPO_OPERADOR_ARITMETICO) {
            this.token = this.lexico.nextToken();
        } else {
            throw new RuntimeException("Oxe, era para ser um operador "
                    + "aritmético (+,-,/,*) pertinho de " +
                    this.token.getLexema());
        }
    }

}
