package modelo;

public abstract class Item {
    private String nome;
    private String cor;
    private String tamanho;
    private String loja;
    private String conservacao;
    private int totalLavagens = 0;
    private int totalUsos = 0;
    private boolean isLimpo;
    private int quantidadeLavagens = 0;




    public Item (String nome, String cor, String tamanho, String loja, String conservacao){
        this.nome = nome;
        this.cor = cor;
        this.tamanho = tamanho;
        this.loja = loja;
        this.conservacao = conservacao;
        this.isLimpo = true;
    }

  public void registrarUso() {
        this.totalUsos++;
        this.isLimpo = false; 
    }

    public void registrarLavagem() {
        this.totalLavagens++;
        this.isLimpo = true; 
    }

    public int getQuantidadeLavagens(){
        return this.quantidadeLavagens;
    }

    public abstract String getTipo();
    public String getNome() {
        return nome;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getCor() {
        return cor;
    }


    public void setCor(String cor) {
        this.cor = cor;
    }


    public String getTamanho() {
        return tamanho;
    }


    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }


    public String getLoja() {
        return loja;
    }   


    public void setLoja(String loja) {
        this.loja = loja;
    }


    public String getConservacao() {
        return conservacao;
    }


    public void setConservacao(String conservacao) {
        this.conservacao = conservacao;
    }


    public int getTotalUsos() {
        return totalUsos;
    }


        
    public int getContadorLavagem(){
        return totalLavagens;
    }


    public boolean isLimpo() {
        return isLimpo;
    }


}

