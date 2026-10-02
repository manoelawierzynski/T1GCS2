import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public final class EstatisticasPedidos {
    private EstatisticasPedidos() { }

    public static int totalPedidos(List<Pedido> pedidos) {
        return pedidos == null ? 0 : pedidos.size();
    }

    public static int quantidadeAprovados(List<Pedido> pedidos) {
        return contarPorStatus(pedidos, StatusPedido.APROVADO);
    }

    public static int quantidadeReprovados(List<Pedido> pedidos) {
        return contarPorStatus(pedidos, StatusPedido.REPROVADO);
    }

    public static double percentualAprovados(List<Pedido> pedidos) {
        return percentual(quantidadeAprovados(pedidos), totalPedidos(pedidos));
    }

    public static double percentualReprovados(List<Pedido> pedidos) {
        return percentual(quantidadeReprovados(pedidos), totalPedidos(pedidos));
    }

    public static List<Pedido> pedidosDosUltimos30Dias(List<Pedido> pedidos) {
        LocalDate hoje = LocalDate.now();
        LocalDate inicio = hoje.minusDays(30);
        return pedidos.stream()
                .filter(pedido -> pedido != null && pedido.getDataPedido() != null)
                .filter(pedido -> !pedido.getDataPedido().isBefore(inicio)
                        && !pedido.getDataPedido().isAfter(hoje))
                .collect(java.util.stream.Collectors.toList());
    }

    public static double valorMedioDosUltimos30Dias(List<Pedido> pedidos) {
        List<Pedido> recentes = pedidosDosUltimos30Dias(pedidos);
        return recentes.isEmpty() ? 0.0
                : recentes.stream().mapToDouble(Pedido::getValorTotalPedido).average().orElse(0.0);
    }

    /** Neste sistema, PENDENTE representa o pedido ainda aberto. */
    public static Optional<Pedido> pedidoAbertoDeMaiorValor(List<Pedido> pedidos) {
        return pedidos.stream()
                .filter(pedido -> pedido != null && pedido.isAberto())
                .max(Comparator.comparingDouble(Pedido::getValorTotalPedido));
    }

    public static String gerarPainel(List<Pedido> pedidos) {
        List<Pedido> recentes = pedidosDosUltimos30Dias(pedidos);
        StringBuilder painel = new StringBuilder();
        painel.append("\n===== PAINEL GERENCIAL - ESTATISTICAS =====\n");
        painel.append("Total de pedidos: ").append(totalPedidos(pedidos)).append('\n');
        painel.append(String.format(Locale.US, "Aprovados: %d (%.2f%%)%n",
                quantidadeAprovados(pedidos), percentualAprovados(pedidos)));
        painel.append(String.format(Locale.US, "Reprovados: %d (%.2f%%)%n",
                quantidadeReprovados(pedidos), percentualReprovados(pedidos)));
        painel.append("Pedidos realizados nos ultimos 30 dias: ").append(recentes.size()).append('\n');
        painel.append(String.format(Locale.US, "Valor medio dos pedidos recentes: R$ %.2f%n",
                valorMedioDosUltimos30Dias(pedidos)));
        painel.append("\nPedido aberto de maior valor:\n");
        Optional<Pedido> maior = pedidoAbertoDeMaiorValor(pedidos);
        painel.append(maior.isPresent() ? descreverPedido(maior.get())
                : "Nenhum pedido aberto encontrado.\n");
        painel.append("===========================================\n");
        return painel.toString();
    }

    private static int contarPorStatus(List<Pedido> pedidos, StatusPedido status) {
        if (pedidos == null) return 0;
        return (int) pedidos.stream()
                .filter(pedido -> pedido != null && pedido.getStatus() == status).count();
    }

    private static double percentual(int parte, int total) {
        return total == 0 ? 0.0 : (parte * 100.0) / total;
    }

    private static String descreverPedido(Pedido pedido) {
        StringBuilder descricao = new StringBuilder();
        descricao.append("ID: ").append(pedido.getId()).append('\n');
        descricao.append("Solicitante: ").append(pedido.getSolicitante().getNome()).append('\n');
        descricao.append("Departamento: ").append(pedido.getDepartamentoSolicitante().getNome()).append('\n');
        descricao.append("Data: ").append(pedido.getDataPedido()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).append('\n');
        descricao.append("Status: ").append(pedido.getStatus()).append('\n');
        descricao.append(String.format(Locale.US, "Valor total: R$ %.2f%n", pedido.getValorTotalPedido()));
        descricao.append("Itens:\n");
        for (ItemPedido item : pedido.getItens()) {
            descricao.append("  - ").append(item).append('\n');
        }
        return descricao.toString();
    }
}
