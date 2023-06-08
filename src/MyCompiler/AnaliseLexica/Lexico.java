/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyCompiler.AnaliseLexica;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.RuntimeErrorException;

/**
 *
 * @author tarci
 */
public class Lexico {
    private char[] conteudo;
    private int indiceConteudo;

    public Lexico(String caminhoCodigoFonte) {
        try {
            String conteudoStr;
            conteudoStr = new String(Files.readAllBytes(Paths.get(caminhoCodigoFonte)));
            this.conteudo = conteudoStr.toCharArray();
            this.indiceConteudo = 0;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Retorna próximo char
    private char nextChar() {
        return this.conteudo[this.indiceConteudo++];
    }

    // Verifica existe próximo char ou chegou ao final do código fonte
    private boolean hasNextChar() {
        return indiceConteudo < this.conteudo.length;
    }

    // Retrocede o índice que aponta para o "char da vez" em uma unidade
    private void back() {
        this.indiceConteudo--;
    }

    // Identificar se char é letra minúscula
    private boolean isLetra(char c) {
        return (c >= 'a') && (c <= 'z') || (c >= 'A') && (c <= 'Z');
    }

    // Identificar se char é dígito
    private boolean isDigito(char c) {
        return (c >= '0') && (c <= '9');
    }

    // Método retorna próximo token válido ou retorna mensagem de erro.
    public Token nextToken() {
        Token token = null;
        char c;
        int estado = 0;

        StringBuffer lexema = new StringBuffer();
        while (this.hasNextChar()) {
            c = this.nextChar();
            switch (estado) {
                case 0:
                    if (c == ' ' || c == '\t' || c == '\n' || c == '\r') { // caracteres de espaço em branco ASCII
                                                                           // tradicionais
                        estado = 0;
                    } else if (this.isLetra(c) || c == '_') {
                        lexema.append(c);
                        estado = 1;
                    } else if (this.isDigito(c)) {
                        lexema.append(c);
                        estado = 2;
                    } else if (c == ')' ||
                            c == '(' ||
                            c == '{' ||
                            c == '}' ||
                            c == ',' ||
                            c == ';') {
                        lexema.append(c);
                        estado = 5;

                    } else if (c == '\'') {
                        lexema.append(c);
                        estado = 6;
                    } else if (isOperand(c)) {
                        lexema.append(c);
                        estado = 9;
                    } else if (c == '=') {
                        lexema.append(c);
                        estado = 10;
                    } else if (c == '<') {
                        lexema.append(c);
                        estado = 11;
                    } else if (c == '>') {
                        lexema.append(c);
                        estado = 12;
                    } else if (c == ':') {
                        lexema.append(c);
                        estado = 14;
                    } else if (c == '$') {
                        lexema.append(c);
                        estado = 99;
                        this.back();
                    } else if (c == '~') {
                        lexema.append(c);
                        estado = 18;
                    } else if (isRusso(c)) {
                        lexema.append(c);
                        estado = 20;
                    } else {
                        lexema.append(c);
                        throw new RuntimeException("Erro: token inválido \"" + lexema.toString() + "\"");
                    }
                    break;
                case 1:
                    if (this.isLetra(c) || this.isDigito(c) || c == '_') {
                        lexema.append(c);
                        estado = 1;
                    } else {
                        if (isRusso(c)) {
                            this.back();
                            estado = 15;
                        } else if (isKeyWord(lexema)) {
                            this.back();
                            estado = 17;
                        } else if (isAccessModifier(lexema)) {
                            this.back();
                            estado = 22;
                        } else {
                            this.back();
                            return new Token(lexema.toString(), Token.TIPO_IDENTIFICADOR);
                        }
                    }
                    break;
                case 2:
                    if (this.isDigito(c)) {
                        lexema.append(c);
                        estado = 2;
                    } else if (c == '.') {
                        lexema.append(c);
                        estado = 3;
                    } else {
                        this.back();
                        return new Token(lexema.toString(), Token.TIPO_INTEIRO);
                    }
                    break;
                case 3:
                    if (this.isDigito(c)) {
                        lexema.append(c);
                        estado = 4;
                    } else {
                        throw new RuntimeException("Erro: número float inválido \"" + lexema.toString() + "\"");
                    }
                    break;
                case 4:
                    if (this.isDigito(c)) {
                        lexema.append(c);
                        estado = 4;
                    } else {
                        this.back();
                        return new Token(lexema.toString(), Token.TIPO_REAL);
                    }
                    break;
                case 5:
                    this.back();
                    return new Token(lexema.toString(), Token.TIPO_CARACTER_ESPECIAL);

                case 6:
                    if (isLetra(c) || isDigito(c)) {
                        lexema.append(c);
                        estado = 7;
                    } else {
                        lexema.append(c);
                        throw new RuntimeException("Erro: char mal formatado \"" + lexema.toString() + "\"");
                    }
                    break;
                case 7:
                    if (c == '\'') {
                        lexema.append(c);
                        estado = 8;
                    } else {
                        lexema.append(c);
                        throw new RuntimeException("Erro: char mal formatado \"" + lexema.toString() + "\"");
                    }
                    break;
                case 8:
                    this.back();
                    return new Token(lexema.toString(), Token.TIPO_CHAR);
                case 9:
                    this.back();
                    return new Token(lexema.toString(), Token.TIPO_OPERADOR_ARITMETICO);
                case 10:
                    if (c == '=') {
                        lexema.append(c);
                        estado = 13;
                    } else {
                        this.back();
                        return new Token(lexema.toString(), Token.TIPO_ATRIBUICAO);
                    }
                    break;
                case 11:
                    if (c == '>' || c == '=') {
                        lexema.append(c);
                        estado = 13;

                    } else {
                        this.back();
                        return new Token(lexema.toString(), Token.TIPO_OPERADOR_RELACIONAL);
                    }
                    break;
                case 12:
                    if (c == '=') {
                        lexema.append(c);
                        estado = 13;
                    } else {
                        this.back();
                        return new Token(lexema.toString(), Token.TIPO_OPERADOR_RELACIONAL);
                    }
                    break;
                case 13:
                    this.back();
                    return new Token(lexema.toString(), Token.TIPO_OPERADOR_RELACIONAL);

                case 14:
                    if (isBoca(c)) {
                        lexema.append(c);
                        estado = 16;
                    } else if (c == '\'') {
                        lexema.append(c);
                        estado = 15;

                    } else {
                        lexema.append(c);
                        throw new RuntimeException("Erro: não é um emoji \"" + lexema.toString() + "\"");
                    }
                    break;
                case 15:
                    if (c == ')' || c == '(') {
                        lexema.append(c);
                        estado = 16;

                    } else {
                        lexema.append(c);
                        throw new RuntimeException("Erro: não é um emoji \"" + lexema.toString() + "\"");
                    }
                    break;
                case 16:
                    this.back();
                    return new Token(lexema.toString(), Token.TIPO_EMOJI);
                case 17:
                    this.back();
                    return new Token(lexema.toString(), Token.TIPO_PALAVRA_RESERVADA);
                case 18:
                    if (isLeet(c)) {
                        lexema.append(c);
                        estado = 18;
                    } else if (c == '~') {
                        lexema.append(c);
                        estado = 19;
                    } else {
                        lexema.append(c);
                        throw new RuntimeException("Erro: \"" + lexema.toString() + "\" não é um LEET");
                    }
                    break;
                case 19:
                    this.back();
                    return new Token(lexema.toString(), Token.TIPO_LEET);
                case 20:
                    if (isLetra(c) || isDigito(c)) {
                        lexema.append(c);
                        estado = 21;
                    } else {
                        lexema.append(c);
                        throw new RuntimeException("Erro: RUSSO mal formatado \"" + lexema.toString() + "\"");

                    }
                    break;

                case 21:
                    if (isLetra(c) || isDigito(c)) {
                        lexema.append(c);
                        estado = 21;
                    } else {
                        this.back();
                        return new Token(lexema.toString(), Token.TIPO_RUSSO);
                    }
                    break;
                case 22:
                    this.back();
                    return new Token(lexema.toString(), Token.TIPO_MODIFICADOR_DE_ACESSO);

                case 99:
                    return new Token(lexema.toString(), Token.TIPO_FIM_CODIGO);
            }
        }
        return token;

    }

    private boolean isKeyWord(StringBuffer lexema) {
        String myLexema = lexema.toString();
        return (myLexema.equals("int") || myLexema.equals("float") || myLexema.equals("char")
                || myLexema.equals("while")
                || myLexema.equals("if") || myLexema.equals("main") || myLexema.equals("else")
                || myLexema.equals("double") || myLexema.equals("static") || myLexema.equals("void")
                || myLexema.equals("return"));
    }

    private boolean isAccessModifier(StringBuffer lexema) {
        String myLexema = lexema.toString();
        return (myLexema.equals("public")) || (myLexema.equals("private")) || (myLexema.equals("protected"));
    }

    private boolean isOperand(char c) {
        return (c == '*' || c == '+' || c == '-' || c == '/' || c == '%');
    }

    private boolean isBoca(char c) {
        if (c == ')' || c == '(' || c == 'v' || c == 'O' || c == '|' || c == 'p')
            return true;
        return false;
    }

    private boolean isRusso(char c) {
        return c == '@';
    }

    private boolean isLeet(char c) {
        return (c == '4') || (c == '6') || (c == '8') || (c == 'c') || (c == 'C') || (c == 'd') || (c == 'D')
                || (c == '3') || (c == 'f') || (c == 'F') || (c == 'g') || (c == 'G') || (c == 'h') || (c == 'H')
                || (c == '1') || (c == 'j') || (c == 'J') || (c == 'k') || (c == 'K') || (c == 'l') || (c == 'L')
                || (c == 'm') || (c == 'M') || (c == 'n') || (c == 'N') || (c == '0') || (c == '9') || (c == 'q')
                || (c == 'Q') || (c == 'r') || (c == 'R') || (c == '5') || (c == '7') || (c == 'u') || (c == 'U')
                || (c == 'v') || (c == 'V') || (c == 'w') || (c == 'W') || (c == 'x') || (c == 'X') || (c == 'y')
                || (c == 'Y') || (c == 'z') || (c == 'Z');
    }

}
