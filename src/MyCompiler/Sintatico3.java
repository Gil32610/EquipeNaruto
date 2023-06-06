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
    }

    private void B() {// NOVO BLOCO
        if (!(token.getLexema().equals("{")))
            throw new RuntimeException();
        token = lexico.nextToken();
        this.CS();
        if (!(token.getLexema().equals("}")))
            throw new RuntimeException();

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
        if(token.getTipo() == Token.TIPO_IDENTIFICADOR)this.atribuicao();
        else if(token.getLexema().equals("{"))this.B();
    }

    private void atribuicao() {// E SIGINIFICA EXPRESSÃO
        if (token.getTipo() != Token.TIPO_IDENTIFICADOR)
            throw new RuntimeException();

        token = lexico.nextToken();

        if (token.getTipo() != token.TIPO_ATRIBUICAO)
            throw new RuntimeException();
        token = lexico.nextToken();
        this.E();// EXPRESSAO
        token = lexico.nextToken();
        if (!(token.getLexema().equalsIgnoreCase(";")))
            throw new RuntimeException();
        token = lexico.nextToken();
    }

    private void E() {
        this.T();
        this.El();

    }

    private void T() {

    }

    private void El() {

    }

    private void declaracao() {

    }

    private void iteracao() {

    }

}
