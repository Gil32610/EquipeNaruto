package MyCompiler;

public class Sintatico3 {
    private Lexico lexico;
    private Token token;

    public Sintatico3(Lexico lexico) {
        this.lexico = lexico;
    }

    public void S() {
        token = lexico.nextToken();
        if (!(token.getLexema().equals("main")))
            throw new RuntimeException("Orochimaru roubou o Jutsu Secreto \"main\"");
        token = lexico.nextToken();
        if (!(token.getLexema().equals("(")))
            throw new RuntimeException();
        token = lexico.nextToken();
        if (!(token.getLexema().equals(")")))
            throw new RuntimeException();
        token = lexico.nextToken();
        this.B();
        if (token.getTipo() == Token.TIPO_FIM_CODIGO) {
            System.out.println("Você já está preparado para ser um Hokage!");
        } else
            System.out.println("Inclua $ para sair do Tsukuyomi Infinito");
    }

    private void B() {// NOVO BLOCO
        if (!(token.getLexema().equals("{")))
            throw new RuntimeException();
        token = lexico.nextToken();
        this.CS();
        if (!(token.getLexema().equals("}")))
            throw new RuntimeException("chave");
        token = lexico.nextToken();

    }

    private void CS() {
        if ((token.getTipo() == Token.TIPO_IDENTIFICADOR) || token.getLexema().equals("float") ||
                token.getLexema().equals("int") || token.getLexema().equals("if")
                || token.getLexema().equals("while")) {
            this.C();
            this.CS();
        }
    }

    private void C() {
        if (token.getLexema().equals("int") || token.getLexema().equals("float") ||
                token.getLexema().equals("char"))
            this.declaracao();
        if (token.getTipo() == Token.TIPO_IDENTIFICADOR || token.getLexema().equals("while") ||
                token.getLexema().equals("if") || token.getLexema().equals("{"))
            this.COMANDO();
    }

    private void COMANDO() {
        if (token.getTipo() == Token.TIPO_IDENTIFICADOR || token.getLexema().equals("{")) {
            this.COMANDO_BASICO();
        } else if (token.getLexema().equals("while")) {
            this.iteracao();
        }
    }

    private void COMANDO_BASICO() {
        if (token.getTipo() == Token.TIPO_IDENTIFICADOR)
            this.atribuicao();
        else if (token.getLexema().equals("{"))
            this.B();
    }

    private void atribuicao() {// E SIGINIFICA EXPRESSÃO
        if (token.getTipo() != Token.TIPO_IDENTIFICADOR)
            throw new RuntimeException();

        token = lexico.nextToken();

        if (token.getTipo() != token.TIPO_ATRIBUICAO)
            throw new RuntimeException();
        token = lexico.nextToken();
        this.E();// EXPRESSAO
        if (!(token.getLexema().equalsIgnoreCase(";"))){
            throw new RuntimeException();
        }
        token = lexico.nextToken();
    }

    private void E() {
        this.T();
        this.El();

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

    private void El() {
        if (this.token.getTipo() == Token.TIPO_OPERADOR_ARITMETICO) {
            this.OP();
            this.T();
            this.El();
        } else {
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

    private void declaracao() {
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

    private void iteracao() {
        if (!(token.getLexema().equals("while")))
            throw new RuntimeException();
        token = lexico.nextToken();
        if (!(token.getLexema().equals("(")))
            throw new RuntimeException();
        token = lexico.nextToken();
        this.R();
        if (!(token.getLexema().equals(")")))
            throw new RuntimeException();
        token = lexico.nextToken();
        this.COMANDO();
    }

    private void R() {
        if (!(token.getTipo() == Token.TIPO_IDENTIFICADOR || token.getTipo() == Token.TIPO_REAL
                || token.getTipo() == Token.TIPO_INTEIRO))
            throw new RuntimeException();
        this.E();
        if (token.getTipo() != Token.TIPO_OPERADOR_RELACIONAL)
            throw new RuntimeException();
            token = lexico.nextToken();
        this.E();
    }

}
