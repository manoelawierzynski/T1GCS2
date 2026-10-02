import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class App {
    private Scanner entrada;
    private Funcionario usuarioLogado;
    private List<Departamento> departamentos = new ArrayList<>();
    private ArrayList<Pedido> pedidos = new ArrayList<>();
    private List<Funcionario> funcionarios = new ArrayList<>();
    private int proximoIdPedido = 1;

    public App() {
        entrada = new Scanner(System.in);
        departamentos = Mock.carregarDepartamentos();
        funcionarios = Mock.carregarFuncionarios(departamentos);
    }

    public void mudarUsuarioPorId() {
        if (funcionarios == null || funcionarios.isEmpty()) {
            System.out.println("Lista de funcionários não carregada.");
            return;
        }

        System.out.println("Digite o ID: ");
        int id = entrada.nextInt();
        entrada.nextLine();
        for (Funcionario f : funcionarios) {
            if (f.getId() == id) {
                usuarioLogado = f;
                System.out.println("Usuário atual: " + f.getNome());
                return;
            }
        }
        System.out.println("Usuário com ID não encontrado.");
    }

    public void registrarPedido() {
        if (usuarioLogado == null) {
            System.out.println("Não há usuário logado.");
            return;
        }
        List<ItemPedido> itensDoPedido = new ArrayList<>();
        System.out.print("Quantos itens deseja adicionar ao pedido? ");
        int quantidadeItens = entrada.nextInt();
        entrada.nextLine();
        for (int i = 1; i <= quantidadeItens; i++) {
            System.out.println("Item " + i + ": ");
            System.out.print("Descrição do item/produto: ");
            String descricaoItem = entrada.nextLine();
            System.out.print("Valor unitário: ");
            double valorUnitario = entrada.nextDouble();
            System.out.print("Quantidade: ");
            int quantidade = entrada.nextInt();
            entrada.nextLine();

            itensDoPedido.add(new ItemPedido(descricaoItem, valorUnitario, quantidade));
        }

        double custoGlobalPedido = itensDoPedido.stream().mapToDouble(ItemPedido::getValorTotalItem).sum();
        double tetoOrcamento = usuarioLogado.getDepartamento().getLimiteMaximoPedido();

        if (custoGlobalPedido > tetoOrcamento) {
            System.out.printf("Operação negada: O montante de R$ %.2f ultrapassa o teto do departamento (R$ %.2f).\n",
                    custoGlobalPedido, tetoOrcamento);
            return;
        }

        Pedido pedidoCriado = new Pedido(proximoIdPedido, usuarioLogado, itensDoPedido);
        pedidos.add(pedidoCriado);

        System.out.println("Pedido gerado com sucesso!");
        proximoIdPedido++;
    }

    public void avaliarPedido() {
        if (!isUsuarioAdministrador())
            return;
        System.out.println("== Pedidos Abertos ou Pendentes ==");

        boolean encontrou = false;
        for (Pedido pedido : pedidos) {
            if (pedido.getStatus() == StatusPedido.PENDENTE) {
                System.out.println(pedido);
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhum pedido pendente encontrado.");
            return;
        }

        System.out.print("Digite o ID do pedido que deseja avaliar: ");
        int id = entrada.nextInt();
        entrada.nextLine();

        Pedido pedidoSelecionado = null;
        for (Pedido pedido1 : pedidos) {
            if (pedido1.getId() == id) {
                pedidoSelecionado = pedido1;
                break;
            }
        }

        if (pedidoSelecionado == null) {
            System.out.println("Pedido não encontrado.");
            return;
        }

        if (pedidoSelecionado.getStatus() != StatusPedido.PENDENTE) {
            System.out.println("Este pedido não está pendente e não pode ser avaliado.");
            return;
        }

        System.out.println("Detalhes do Pedido:" + pedidoSelecionado.getId());
        System.out.println("Pedido solicitado por: " + pedidoSelecionado.getSolicitante().getNome());
        System.out.println("Itens do Pedido:");
        for (ItemPedido item : pedidoSelecionado.getItens()) {
            System.out.println(item);
        }

        System.out.println("Total do Pedido: R$ " + pedidoSelecionado.getValorTotalPedido());

        System.out.println("Escolha uma ação: ");
        System.out.println("[1] Aprovar Pedido");
        System.out.println("[2] Reprovar Pedido");
        System.out.print("[3] Concluir Pedido: ");
        int opcao = entrada.nextInt();
        entrada.nextLine();

        if (opcao == 1) {
            pedidoSelecionado.setStatus(StatusPedido.APROVADO);
            System.out.println("Pedido aprovado com sucesso.");

        } else if (opcao == 2) {
            pedidoSelecionado.setStatus(StatusPedido.REPROVADO);
            System.out.println("Pedido reprovado com sucesso.");

        } else if (opcao == 3) {
            pedidoSelecionado.concluirPedido();
            System.out.println("Pedido concluído com sucesso.");

        } else {
            System.out.println("Opção inválida.");
        }
    }

    private boolean isUsuarioAdministrador() {
        if (usuarioLogado == null) {
            System.out.println("Nenhum usuário logado.");
            return false;
        }
        if (usuarioLogado.getTipo() != TipoFuncionario.ADMINISTRADOR) {
            System.out.println("Apenas administradores podem realizar esta ação.");
            return false;
        }
        return true;
    }

    private void menu() {
        System.out.println("Opcoes: ");
        System.out.println("[0] Sair");
        System.out.println("[1] Mudar de usuario por ID");
        System.out.println("[2] Registrar um novo pedido de aquisicao");
        System.out.println("[3] Excluir pedido de aquisicao");
        System.out.println("[4] Avaliar pedido de aquisicao");
        System.out.println("[5] Buscar pedidos por periodo");
        System.out.println("[6] Buscar pedidos por funcionario");
        System.out.println("[7] Buscar pedidos por descrição do item");
        System.out.println("[8] Exibir painel gerencial (Estatísticas)");
    }

    public void executar() {
        int opcao;
        do {
            System.out.println("\nSISTEMA DE PEDIDOS");
            menu();
            System.out.print("Digite a opcao desejada: ");
            opcao = entrada.nextInt();
            entrada.nextLine();
            switch (opcao) {
                case 0:
                    break;
                case 1:
                    mudarUsuarioPorId();
                    break;
                case 2:
                    registrarPedido();
                    break;
                case 3:
                    excluirPedido();
                    break;
                case 4:
                    avaliarPedido();
                    break;
                case 5:
                    buscarPedidoPorPeriodo();
                    break;
                case 6:
                    buscarPedidosPorFuncionario();
                    break;
                case 7:
                    buscarPedidosPorItem();
                    break;
                case 8:
                    exibirPainelGerencial();
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, tente novamente.");
            }
        } while (opcao != 0);
    }

    public void buscarPedidoPorPeriodo() {
        if (!isUsuarioAdministrador())
            return;

        System.out.print("Digite a data inicial (DD-MM-AAAA): ");
        LocalDate inicio = LocalDate.parse(entrada.nextLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        System.out.print("Digite a data final (DD-MM-AAAA): ");
        LocalDate fim = LocalDate.parse(entrada.nextLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        System.out.println("Pedidos entre " + inicio + " e " + fim + ":");
        for (Pedido pedido : pedidos) {
            if (!pedido.getDataPedido().isBefore(inicio) && !pedido.getDataPedido().isAfter(fim)) {
                System.out.println(pedido);
            }
        }
    }

    public void buscarPedidosPorFuncionario() {
        if (!isUsuarioAdministrador())
            return;

        System.out.print("Digite o nome ou parte do nome do funcionário: ");
        String termo = entrada.nextLine().toLowerCase();

        System.out.println("Pedidos do funcionário contendo " + termo + ":");
        for (Pedido pedido : pedidos) {
            if (pedido.getSolicitante().getNome().toLowerCase().contains(termo)) {
                System.out.println(pedido);
            }
        }
    }

    public void buscarPedidosPorItem() {
        if (!isUsuarioAdministrador())
            return;

        System.out.print("Digite o termo de busca para o item: ");
        String termo = entrada.nextLine().toLowerCase();

        System.out.println("Pedidos contendo itens com o termo '" + termo + "':");
        for (Pedido pedido : pedidos) {
            boolean contemItem = pedido.getItens().stream()
                    .anyMatch(item -> item.getDescricao().toLowerCase().contains(termo));

            if (contemItem) {
                System.out.println(pedido);
            }
        }
    }

    public void excluirPedido() {
        if (usuarioLogado == null) {
            System.out.println("Não há usuário logado.");
            return;
        }

        System.out.print("Digite o ID do pedido que quer excluir: ");
        int idProcurado = entrada.nextInt();
        entrada.nextLine();

        Iterator<Pedido> iterator = pedidos.iterator();
        while (iterator.hasNext()) {
            Pedido pedido = iterator.next();
            if (pedido.getId() == idProcurado) {
                if (pedido.getSolicitante().getId() == usuarioLogado.getId()) {
                    iterator.remove();
                    System.out.println("Pedido excluído com sucesso.");
                    return;
                } else {
                    System.out.println("Apenas o funcionário que criou o pedido pode excluir.");
                    return;
                }
            }
        }
        System.out.println("ID do pedido não encontrado.");
    }

    private void exibirPainelGerencial() {
        if (usuarioLogado == null || usuarioLogado.getTipo() != TipoFuncionario.ADMINISTRADOR) {
            System.out.println("Apenas administradores podem acessar o painel gerencial.");
            return;
        }
        System.out.print(EstatisticasPedidos.gerarPainel(pedidos));
    }

    public List<Pedido> getPedidos() {
        return Collections.unmodifiableList(pedidos);
    }
}