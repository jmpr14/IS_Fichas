package aebd.group.webapp;

public class Pedido {

    private String telefone;
    private String siglaExame;
    private String numUtente;
    private String numPedido;
    private String nome;
    private String relatorio;
    private String id_exame;
    private String num_Episodio;
    private String id_doente;
    private String morada;
    private String descricao;

    public Pedido(String telefone, String siglaExame, String numUtente, String numPedido, String nome, String relatorio,
                  String id_exame, String num_Episodio, String id_doente, String morada, String descricao) {
        this.telefone = telefone;
        this.siglaExame = siglaExame;
        this.numUtente = numUtente;
        this.numPedido = numPedido;
        this.nome = nome;
        this.relatorio = relatorio;
        this.id_exame = id_exame;
        this.num_Episodio = num_Episodio;
        this.id_doente = id_doente;
        this.morada = morada;
        this.descricao = descricao;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public String getSiglaExame() {
        return this.siglaExame;
    }

    public String getNumUtente() {
        return this.numUtente;
    }

    public String getNumPedido() {
        return this.numPedido;
    }

    public String getNome() {
        return this.nome;
    }

    public String getRelatorio() {
        return this.relatorio;
    }

    public String getId_exame() {
        return this.id_exame;
    }

    public String getNum_Episodio() {
        return this.num_Episodio;
    }

    public String getId_doente() {
        return this.id_doente;
    }

    public String getMorada() {
        return this.morada;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setSiglaExame(String siglaExame) {
        this.siglaExame = siglaExame;
    }

    public void setNumUtente(String numUtente) {
        this.numUtente = numUtente;
    }

    public void setNumPedido(String numPedido) {
        this.numPedido = numPedido;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setRelatorio(String relatorio) {
        this.relatorio = relatorio;
    }

    public void setId_exame(String id_exame) {
        this.id_exame = id_exame;
    }

    public void setNum_Episodio(String num_Episodio) {
        this.num_Episodio = num_Episodio;
    }

    public void setId_doente(String id_doente) {
        this.id_doente = id_doente;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
