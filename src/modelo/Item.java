package modelo;

public abstract class Item {
    private String nome;
    private String cor;
    private String tamanho;
    private String loja;
    private EstadoConservacao conservacao;
    private int contadorLavagem = 0;
    private int totalUsos = 0;
    private boolean isLimpo;
    private int id;
    private boolean isEmprestado;
    private String paraQuemEstaEmprestado;

    public Item (String nome, String cor, String tamanho, String loja, EstadoConservacao conservacao){
        this.nome = nome;
        this.cor = cor;
        this.tamanho = tamanho;
        this.loja = loja;
        this.conservacao = conservacao;
        this.isLimpo = true;
        this.isEmprestado = false;
        this.paraQuemEstaEmprestado = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConservacao() {
        return conservacao.getDescricao();
    }

    public void setConservacao(EstadoConservacao conservacao) {
        this.conservacao = conservacao;
    }
    
    public EstadoConservacao getConservacaoEnum() {
        return this.conservacao;
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
    
    public int getTotalUsos() {
        return totalUsos;
    }
    
    public void setTotalUsos(int totalUsos) {
        this.totalUsos = totalUsos;
    }
    
    public void registrarUso() {
        this.totalUsos++;
        this.isLimpo = false;
    }

    public int getContadorLavagem(){
        return contadorLavagem;
    }
    
    public void setContadorLavagem(int contadorLavagem) {
        this.contadorLavagem = contadorLavagem;
    }
    
    public void registrarLavagem() {
        this.contadorLavagem++;
        this.isLimpo = true;
    }

    public boolean isLimpo() {
        return isLimpo;
    }
    
    public void setLimpo(boolean limpo) {
        this.isLimpo = limpo;
    }
    
    public boolean isEmprestado() {
        return this.isEmprestado;
    }

    public void setEmprestado(boolean emprestado) {
        this.isEmprestado = emprestado;
    }

    public String getParaQuemEstaEmprestado() {
        return this.paraQuemEstaEmprestado;
    }
    
    public void setParaQuemEstaEmprestado(String paraQuemEstaEmprestado) {
        this.paraQuemEstaEmprestado = paraQuemEstaEmprestado;
    }

    public void registrarEmprestimo(String paraQuem) {
        if (!(this instanceof modelo.subclasses.RoupaIntima)) {
            this.isEmprestado = true;
            this.paraQuemEstaEmprestado = paraQuem;
        }
    }

    public void registrarDevolucao() {
        this.isEmprestado = false;
        this.paraQuemEstaEmprestado = null;
    }
}