package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.request;

public class BicicletaRetirarDaRedeRequest {

    private Integer idTranca;
    private Integer idBicicleta;
    private Integer idFuncionario;
    private String statusAcaoReparador;

    public Integer getIdTranca(){
        return idTranca;
    }

    public void setIdTranca(Integer idTranca){
        this.idTranca = idTranca;
    }
    
    public Integer getIdBicicleta() {
        return idBicicleta;
    }

    public void setIdBicicleta(Integer idBicicleta) {
        this.idBicicleta = idBicicleta;
    }

    public Integer getIdFuncionario(){
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario){
        this.idFuncionario = idFuncionario;
    }

    public String getStatusAcaoReparador(){
        return statusAcaoReparador;
    }

    public void setStatusAcaoReparador(String statusAcaoReparador){
        this.statusAcaoReparador = statusAcaoReparador;
    }
    
}
