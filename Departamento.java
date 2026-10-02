public class Departamento {
    private int id;
    private String nome;
    private double limiteMaximoPedido;

    public Departamento(int id, String nome, double limiteMaximoPedido) {
        this.id = id;
        this.nome = nome;
        this.limiteMaximoPedido = limiteMaximoPedido;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getLimiteMaximoPedido() {
        return limiteMaximoPedido;
    }

    @Override
    public String toString() {
        return "Departamento: " + nome + " (Limite por pedido: R$ " + limiteMaximoPedido + ")";
    }
}