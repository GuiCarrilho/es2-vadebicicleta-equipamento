package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.request;

public class TrancaIncluirNaRedeRequest {
    private Integer idTotem;
    private Integer idTranca;
    private Integer idFuncionario;

    public Integer getIdTotem() {
        return idTotem;
    }

    public void setIdTotem(Integer idTotem) {
        this.idTotem = idTotem;
    }

    public Integer getIdTranca() {
        return idTranca;
    }

    public void setIdTranca(Integer idTranca) {
        this.idTranca = idTranca;
    }

    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
    }
}
