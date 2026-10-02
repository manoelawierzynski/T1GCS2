public class ItemPedido {
    private String descricao;
    private double valorUnitario;
    private int quantidade;

    public ItemPedido(String descricao, double valorUnitario, int quantidade) {
        this.descricao = descricao;
        this.valorUnitario = valorUnitario;
        this.quantidade = quantidade;
    }

    public double getValorTotalItem() {
        return this.valorUnitario * this.quantidade;
    }

    public String getDescricao() { 
        return descricao;
    }

    public double getValorUnitario() { 
        return valorUnitario;
    }

    public int getQuantidade() { 
        return quantidade; 
    }

    @Override
    public String toString() {
        return quantidade + "x " + descricao + " (R$ " + valorUnitario + " un) = R$ " + getValorTotalItem();
    }
}