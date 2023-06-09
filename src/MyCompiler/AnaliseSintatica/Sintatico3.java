package MyCompiler.AnaliseSintatica;

import MyCompiler.AnaliseLexica.Token;

import java.util.ArrayList;

import MyCompiler.AnaliseLexica.Lexico;

public class Sintatico3 {
    private Lexico lexico;
    private Token token;
    private ArrayList<String> variables;
    private ArrayList<Integer> references;
    private int currentPos;
    private int scope;

    public Sintatico3(Lexico lexico) {
        this.lexico = lexico;
        variables = new ArrayList<String>();
        references = new ArrayList<>();
        currentPos = 0;
        scope = 0;
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
        variables.clear();
        references.clear();
        currentPos = 0;
        scope = 0;
        this.M();
        if (token.getTipo() == Token.TIPO_FIM_CODIGO) {
            System.out.println("Você já está preparado para ser um Hokage!");
        } else
            System.out.println("Inclua $ para sair do Tsukuyomi Infinito");
    }

    private void B() {// NOVO BLOCO
        if (!(token.getLexema().equals("{")))
            throw new RuntimeException("Faltou abrir o pergaminho(Escopo), perto de: "+token.getLexema());
        token = lexico.nextToken();
        this.CS();
        for (int i = currentPos - 1; i >= scope; i--) {
            variables.remove(i);
        }
        int im = currentPos;
        currentPos = scope;
        scope = im - scope;

        if (!(token.getLexema().equals("}")))
            throw new RuntimeException("Faltou fechar o pergaminho(Escopo), perto de: "+token.getLexema());
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
        } else if (token.getLexema().equals("if")) {
            this.COND();
        }
    }

    private void COMANDO_BASICO() {
        if (token.getTipo() == Token.TIPO_IDENTIFICADOR)
            this.atribuicao();
        else if (token.getLexema().equals("{")) {
            scope = currentPos;
            this.B();
        }

    }

    private void atribuicao() {// E SIGINIFICA EXPRESSÃO
        if (token.getTipo() != Token.TIPO_IDENTIFICADOR)
            throw new RuntimeException();
        if (!(variables.contains(token.getLexema())))
            throw new RuntimeException("O nome do jutsu(variável): " + token.getLexema() + ", não foi declarado");
        token = lexico.nextToken();

        if (token.getTipo() != token.TIPO_ATRIBUICAO)
            throw new RuntimeException();
        token = lexico.nextToken();
        this.E();// EXPRESSAO
        if (!(token.getLexema().equalsIgnoreCase(";"))) {
            throw new RuntimeException("A besta de 9 caudas foi libertada, destruiu \";\" perto de: \""
                    + token.getLexema() + "\", e atacou Konoha.");
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
            if ((token.getTipo() == Token.TIPO_IDENTIFICADOR) && !(variables.contains(token.getLexema()))) {
                throw new RuntimeException("O nome do jutsu(variável): " + token.getLexema() + ", não foi declarado");
            }
            this.token = this.lexico.nextToken();
        } else {
            throw new RuntimeException("Não informou o Identificador do Jutsu, "+"Pertinho de: "+token.getLexema());
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
            throw new RuntimeException("Oxe, era para ser um dos 4 hokages "+"aritméticos (+,-,/,*), pertinho de: "+token.getLexema());
        }
    }

    private void declaracao() {
        if (!(this.token.getLexema().equals("int") ||
                this.token.getLexema().equals("float"))) {
            throw new RuntimeException("Selo desconhecido: "+token.getLexema()+", diferente de int, char ou float");
        }
        this.token = this.lexico.nextToken();
        if (this.token.getTipo() != Token.TIPO_IDENTIFICADOR) {
            throw new RuntimeException("Não informou o Identificador do Jutsu, "+"Pertinho de: "+token.getLexema());
        }
        if (variables.contains(token.getLexema())) {
            throw new RuntimeException();
        }
        variables.add(token.getLexema());
        references.add(variables.indexOf(token.getLexema()));
        currentPos++;
        this.token = this.lexico.nextToken();
        if (!this.token.getLexema().equalsIgnoreCase(";")) {
            throw new RuntimeException("A besta de 9 caudas foi libertada, destruiu o: \";\" perto de: \""
                    + token.getLexema() + "\", e atacou Konoha.");
        }
        this.token = this.lexico.nextToken();
    }

    private void iteracao() {
        if (!(token.getLexema().equals("while")))
            throw new RuntimeException();
        token = lexico.nextToken();
        if (!(token.getLexema().equals("("))) {
            throw new RuntimeException("Akatsuki invadiu a vila da Repetição e raptou o Jinchuriki: \"(\", perto do: "+token.getLexema());
        }
        token = lexico.nextToken();
        this.R();
        if (!(token.getLexema().equals(")")))
            throw new RuntimeException("Akatsuki invadiu a vila da Repetição e raptou o Jinchuriki: \")\", perto do: "+token.getLexema());
        token = lexico.nextToken();
        this.COMANDO();
    }

    private void R() {// Expressão relacional
        if (!(token.getTipo() == Token.TIPO_IDENTIFICADOR || token.getTipo() == Token.TIPO_REAL
                || token.getTipo() == Token.TIPO_INTEIRO))
            throw new RuntimeException("Tinha que ser um ninja nível Identificador ou Tipo de Dado");
        this.E();
        if (token.getTipo() != Token.TIPO_OPERADOR_RELACIONAL)
            throw new RuntimeException("Para um genjutsu desse nível: "+token.getLexema()+", não há comparação.");
        token = lexico.nextToken();
        this.E();
    }

    public void COND() {// Estrutura condicional
        if (!(token.getLexema().equals("if")))
            throw new RuntimeException();
        token = lexico.nextToken();
        if (!(token.getLexema().equals("(")))
            throw new RuntimeException("Akatsuki invadiu a vila Condicional e raptou o Jinchuriki: \"(\", perto do: "+token.getLexema());
        token = lexico.nextToken();
        this.R();
        if (!(token.getLexema().equals(")")))
            throw new RuntimeException("Akatsuki invadiu a vila Condicional e raptou o Jinchuriki: \")\", perto do: "+token.getLexema());
        token = lexico.nextToken();
        this.COMANDO();
        if (!(token.getLexema().equals("else")))
            throw new RuntimeException("O selo else não foi realizado perto do: "+token.getLexema()+", jutsu falhou!");
        token = lexico.nextToken();
        this.COMANDO();
    }

    private void M() {

        this.METHOD();
        this.Ml();

    }

    private void METHOD() {
        if ((token.getTipo() == Token.TIPO_MODIFICADOR_DE_ACESSO)) {

            token = lexico.nextToken();
            if (token.getLexema().equals("void"))
                this.Rv();
            else
                this.Re();
            this.ID();
            if (!(token.getLexema().equals("("))) {
                throw new RuntimeException("Akatsuki invadiu a vila Método e raptou o Jinchuriki: \"(\", perto do: "+token.getLexema());
            }
            token = lexico.nextToken();
            if (!(token.getLexema().equals("int") || token.getLexema().equals("float")
                    || token.getLexema().equals("char")))
                throw new RuntimeException("Madara ressucitou e atacou a aliança do Tipo de Dado dos Métodos :"+token.getLexema());
            this.P();
            if (!(token.getLexema().equals(")"))) {
                throw new RuntimeException("Akatsuki invadiu a vila Método e raptou o Jinchuriki: \")\", perto do: "+token.getLexema());
            }
            token = lexico.nextToken();
            this.B();
        }
    }

    private void Ml() {
        if (token.getTipo() == Token.TIPO_MODIFICADOR_DE_ACESSO) {
            this.METHOD();
            this.Ml();
        } else {

        }
    }

    private void P() {
        if (token.getLexema().equals("int") || token.getLexema().equals("float")
                || token.getLexema().equals("char")) {
            this.A();
            this.Pl();
        } else
            ;
    }

    private void A() {
        this.D();
        this.ID();
    }

    private void Pl() {
        if (token.getLexema().equals(",")) {
            token = lexico.nextToken();
            this.A();
            this.Pl();
        } else
            ;
    }

    private void ID() {
        if ((token.getTipo() != Token.TIPO_IDENTIFICADOR)) {
            throw new RuntimeException("Não informou o Identificador do Jutsu, "+"Pertinho de: "+token.getLexema());
        }
        variables.add(token.getLexema());
        currentPos++;
        token = lexico.nextToken();
    }

    private void D() {//
        if (!(token.getLexema().equals("int") || token.getLexema().equals("float")
                || token.getLexema().equals("char"))) {
            throw new RuntimeException("Selo desconhecido: "+token.getLexema()+", diferente de int, char ou float");
        }
        token = lexico.nextToken();
    }

    private void Re() {
        if (!(token.getLexema().equals("int") || token.getLexema().equals("float")
                || token.getLexema().equals("char"))) {
            throw new RuntimeException("Selo desconhecido: "+token.getLexema()+", diferente de int, char ou float");
        }
        token = lexico.nextToken();
    }

    private void Rv() {
        if (!(token.getLexema().equals("void"))) {
            throw new RuntimeException("Selo desconhecido: "+token.getLexema()+", diferente de int, char ou float");
        }
        token = lexico.nextToken();
    }
}
