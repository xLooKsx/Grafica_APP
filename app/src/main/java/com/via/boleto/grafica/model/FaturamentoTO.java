package com.via.boleto.grafica.model;

public class FaturamentoTO {
    private Double total;

    public FaturamentoTO(Double total) {
        this.total = total;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
