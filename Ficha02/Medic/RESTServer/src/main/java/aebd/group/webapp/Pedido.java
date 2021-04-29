package aebd.group.webapp;

public class Pedido {

    private String horaExame;
    private String tipo;
    private String telefone;
    private String siglaExame;
    private String dataExame;
    private String numUtente;
    private String nome;
    private String id_exame;
    private String id_pedido;
    private String id_doente;
    private String morada;
    private String descricao;

    public Pedido(String horaExame, String tipo, String telefone, String siglaExame,
                  String dataExame, String numUtente, String nome, String id_exame,
                  String id_pedido, String id_doente, String morada, String descricao) {
        this.horaExame = horaExame;
        this.tipo = tipo;
        this.telefone = telefone;
        this.siglaExame = siglaExame;
        this.dataExame = dataExame;
        this.numUtente = numUtente;
        this.nome = nome;
        this.id_exame = id_exame;
        this.id_pedido = id_pedido;
        this.id_doente = id_doente;
        this.morada = morada;
        this.descricao = descricao;
    }

    public String getHoraExame() {
        return this.horaExame;
    }

    public String getTipo() {
        return this.tipo;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public String getSiglaExame() {
        return this.siglaExame;
    }

    public String getDataExame() {
        return this.dataExame;
    }

    public String getNumUtente() {
        return this.numUtente;
    }

    public String getNome() {
        return this.nome;
    }

    public String getId_exame() {
        return this.id_exame;
    }

    public String getId_pedido() {
        return this.id_pedido;
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

    public void setHoraExame(String horaExame) {
        this.horaExame = horaExame;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setSiglaExame(String siglaExame) {
        this.siglaExame = siglaExame;
    }

    public void setDataExame(String dataExame) {
        this.dataExame = dataExame;
    }

    public void setNumUtente(String numUtente) {
        this.numUtente = numUtente;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setId_exame(String id_exame) {
        this.id_exame = id_exame;
    }

    public void setId_pedido(String id_pedido) {
        this.id_pedido = id_pedido;
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
