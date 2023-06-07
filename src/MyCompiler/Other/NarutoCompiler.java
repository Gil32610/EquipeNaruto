package MyCompiler;

public class NarutoCompiler {
    private Lexico lexico;
    private Token current;

    public NarutoCompiler(Lexico lexico) {
        this.lexico = lexico;
    }

    public void S(){//S determina estado inicial
        this.current = this.lexico.nextToken();
        if(!current.getLexema().equals("main")){
            throw new RuntimeException("Oxe, cadê main?");
        }
        
        this.current = this.lexico.nextToken();
        if(!current.getLexema().equals("(")){
            throw new RuntimeException("Abre o parêntese do main cabra!");
        }
        
        this.current = this.lexico.nextToken();
        if(!current.getLexema().equals(")")){
            throw new RuntimeException("Fechar o parêntese do main cabra!");
        }        
        this.current = this.lexico.nextToken();
        
        this.B();
        if(this.current.getTipo() == Token.TIPO_FIM_CODIGO){
            System.out.println("O Código tá massa! Arretado! Tu botou pra torar!");        
        }else{
            throw new RuntimeException("Oxe, eu deu bronca preto do fim do programa.");
        }
    }
    
    private void B(){
        if(!this.current.getLexema().equals("{")){
            throw new RuntimeException("Oxe, tave esperando um \"{\" pertinho de " + this.current.getLexema());
        }
        this.current = this.lexico.nextToken();
        this.CS();
        if(!this.current.getLexema().equals("}")){
            throw new RuntimeException("Oxe, tava esperando um \"}\" pertinho de " + this.current.getLexema());
        }        
        this.current = this.lexico.nextToken();        
    }
    
    private void CS(){
        if((this.current.getTipo() == Token.TIPO_IDENTIFICADOR) ||
            this.current.getLexema().equals("int") ||
            this.current.getLexema().equals("float")){
            
            this.C();
            this.CS();
        }else{
        
        }       
    }
    
    private void C(){
        if(this.current.getTipo() == Token.TIPO_IDENTIFICADOR){
            this.ATRIBUICAO();            
        }else if(this.current.getLexema().equals("int") ||
                this.current.getLexema().equals("float")){
            this.DECLARACAO();
        }else{
            throw new RuntimeException("Oxe, eu tava esperando tu "
                    + "declarar um comando pertinho de :" + this.current.getLexema());
        }
    }
        
    private void DECLARACAO(){
        if(!(this.current.getLexema().equals("int") ||
                this.current.getLexema().equals("float"))){
            throw new RuntimeException("Tu vacilou na delcaração de variável. "
                    + "Pertinho de: " + current.getLexema());
        }
        this.current = this.lexico.nextToken();
        if(this.current.getTipo() != Token.TIPO_IDENTIFICADOR){
            throw new RuntimeException("Tu vacilou na delcaração de variável. "
                    + "Pertinho de: " + this.current.getLexema());
        }
        this.current = this.lexico.nextToken();
        if(!this.current.getLexema().equalsIgnoreCase(";")){
            throw new RuntimeException("Tu vacilou  na delcaração de variável. "
                    + "Pertinho de: " + this.current.getLexema());
        }
        this.current = this.lexico.nextToken();        
    }
    
    private void ATRIBUICAO(){
        if(this.current.getTipo() != Token.TIPO_IDENTIFICADOR){
            throw new RuntimeException("Erro na atribuição. Pertinho de: " + this.current.getLexema());
        }
        this.current = this.lexico.nextToken();
        if(this.current.getTipo() != Token.TIPO_ATRIBUICAO){
            throw new RuntimeException("Erro na atribuição. Pertinho de: " + this.current.getLexema());
        }
        this.current = this.lexico.nextToken();
        this.E();
        if(!this.current.getLexema().equals(";")){
            throw new RuntimeException("Erro na atribuição. Pertinho de: " + this.current.getLexema());
        }
        this.current = this.lexico.nextToken();                
    }
    
    private void E(){
        this.T();
        this.El();
    }
    
    private void El(){
        if(this.current.getTipo() == Token.TIPO_OPERADOR_ARITMETICO){
            this.OP();
            this.T();
            this.El();
        }else{        
        }
    }
    
    private void T(){
        if(this.current.getTipo() == Token.TIPO_IDENTIFICADOR || 
                this.current.getTipo() == Token.TIPO_INTEIRO ||
                this.current.getTipo() == Token.TIPO_REAL){
            this.current = this.lexico.nextToken();
        }else{
            throw new RuntimeException("Oxe, era para ser um identificador "
                    + "ou número pertinho de " + this.current.getLexema());
        }
    }
    
    private void OP(){
        if(this.current.getTipo() == Token.TIPO_OPERADOR_ARITMETICO){
            this.current = this.lexico.nextToken();
        }else{
            throw new RuntimeException("Oxe, era para ser um operador "
                    + "aritmético (+,-,/,*) pertinho de "  + 
                    this.current.getLexema());
        }
    }

}