package com.via.boleto.grafica.model;
/**
 * Created by Matheus Silva on 09/04/2018.
 */
public class ProdutoTO {
    private int produtoId;
    private String nome;
    private Double valorVenda;
    private String tipoProduto;

    public ProdutoTO() {
        this(0, "", 0.0, "");
    }

    public ProdutoTO(int produtoId, String nome, Double valorVenda, String tipoProduto) {
        this.produtoId = produtoId;
        this.nome = nome;
        this.valorVenda = valorVenda;
        this.tipoProduto = tipoProduto;
    }

    public int getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(int produtoId) {
        this.produtoId = produtoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(Double valorVenda) {
        this.valorVenda = valorVenda;
    }

    public String getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(String tipoProduto) {
        this.tipoProduto = tipoProduto;
    }
}
