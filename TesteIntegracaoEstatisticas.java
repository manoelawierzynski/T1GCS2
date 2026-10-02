import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TesteIntegracaoEstatisticas {
    public static void main(String[] args) {
        Departamento departamento = new Departamento(1, "Teste", 10000.0);
        Funcionario funcionario = new Funcionario(1, "Usuario Teste", "UT",
                TipoFuncionario.ADMINISTRADOR, departamento);

        Pedido aprovado = criarPedido(1, funcionario, 100.0);
        aprovado.setStatus(StatusPedido.APROVADO);
        Pedido reprovado = criarPedido(2, funcionario, 200.0);
        reprovado.setStatus(StatusPedido.REPROVADO);
        Pedido aberto = criarPedido(3, funcionario, 500.0);
        Pedido antigo = criarPedido(4, funcionario, 900.0);
        antigo.setStatus(StatusPedido.APROVADO);
        antigo.setDataPedido(LocalDate.now().minusDays(31));

        List<Pedido> pedidos = new ArrayList<>(Arrays.asList(aprovado, reprovado, aberto, antigo));
        verificar(EstatisticasPedidos.totalPedidos(pedidos) == 4, "total de pedidos");
        verificar(EstatisticasPedidos.quantidadeAprovados(pedidos) == 2, "aprovados");
        verificar(EstatisticasPedidos.quantidadeReprovados(pedidos) == 1, "reprovados");
        verificar(EstatisticasPedidos.pedidosDosUltimos30Dias(pedidos).size() == 3, "filtro de 30 dias");
        verificar(Math.abs(EstatisticasPedidos.valorMedioDosUltimos30Dias(pedidos) - 266.6666667) < 0.01,
                "valor medio");
        verificar(EstatisticasPedidos.pedidoAbertoDeMaiorValor(pedidos).get().getId() == 3,
                "maior pedido aberto");
        System.out.println("Teste de integracao das estatisticas: OK");
    }

    private static Pedido criarPedido(int id, Funcionario funcionario, double valor) {
        return new Pedido(id, funcionario,
                Arrays.asList(new ItemPedido("Item " + id, valor, 1)));
    }

    private static void verificar(boolean condicao, String nome) {
        if (!condicao)
            throw new AssertionError("Falha: " + nome);
    }
}
