import java.time.LocalDate;
import java.util.List;

public class Pedido {
    private int id;
    private Funcionario solicitante;
    private Departamento departamentoSolicitante;
    private LocalDate dataPedido;
    private LocalDate dataConclusao;
    private StatusPedido status;
    private List<ItemPedido> itens;

    public Pedido(int id, Funcionario solicitante, List<ItemPedido> itens) {
        this.id = id;
        this.solicitante = solicitante;
        this.departamentoSolicitante = solicitante.getDepartamento();
        this.dataPedido = LocalDate.now(); 
        this.status = StatusPedido.PENDENTE;
        this.itens = itens;
    }

    public double getValorTotalPedido() {
        return itens.stream().mapToDouble(ItemPedido::getValorTotalItem).sum();
    }

    public void concluirPedido() {
        this.status = StatusPedido.CONCLUIDO;
        this.dataConclusao = LocalDate.now();
    }

    // getters e setters p ajudar vcs no resto das classes
    public int getId() { 
        return id; 
    }

    public Funcionario getSolicitante() { 
        return solicitante; 
    }

    public Departamento getDepartamentoSolicitante() { 
        return departamentoSolicitante; 
    }

    public LocalDate getDataPedido() { 
        return dataPedido; 
    }

    public LocalDate getDataConclusao() { 
        return dataConclusao; 
    }

    public void setDataConclusao(LocalDate dataConclusao) { 
        this.dataConclusao = dataConclusao; 
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }

    public StatusPedido getStatus() { 
        return status; 
    }

    public void setStatus(StatusPedido status) { 
        this.status = status; 
    }

    public List<ItemPedido> getItens() { 
        return itens; 
    }
    public boolean isAberto() {
        return status == StatusPedido.PENDENTE;
    }

    @Override
    public String toString() {
        return "Pedido #" + id + " [Solicitante: " + solicitante.getNome() + ", Status: " + status + ", Total: R$ " + getValorTotalPedido() + "]";
    }
}
