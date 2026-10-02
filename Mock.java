
import java.util.ArrayList;
import java.util.List;

public class Mock {

    public static List<Departamento> carregarDepartamentos() {
        List<Departamento> departamentos = new ArrayList<>();
        departamentos.add(new Departamento(1, "Financeiro", 5000.00));
        departamentos.add(new Departamento(2, "Recursos Humanos", 3000.00));
        departamentos.add(new Departamento(3, "Engenharia", 10000.00));
        departamentos.add(new Departamento(4, "Manutenção", 4000.00));
        departamentos.add(new Departamento(5, "Vendas", 6000.00));
        return departamentos;
    }

    public static List<Funcionario> carregarFuncionarios(List<Departamento> departamentos) {
        List<Funcionario> funcionarios = new ArrayList<>();

        Departamento financeiro = departamentos.get(0);
        Departamento rh = departamentos.get(1);
        Departamento engenharia = departamentos.get(2);
        Departamento manutencao = departamentos.get(3);
        Departamento vendas = departamentos.get(4);

        // Financeiro
        funcionarios.add(new Funcionario(1, "Ana Silva", "AS", TipoFuncionario.ADMINISTRADOR, financeiro));
        funcionarios.add(new Funcionario(2, "Carlos Souza", "CS", TipoFuncionario.FUNCIONARIO, financeiro));
        funcionarios.add(new Funcionario(3, "Beatriz Lima", "BL", TipoFuncionario.FUNCIONARIO, financeiro));

        // RH
        funcionarios.add(new Funcionario(4, "Mariana Costa", "MC", TipoFuncionario.ADMINISTRADOR, rh));
        funcionarios.add(new Funcionario(5, "Lucas Rocha", "LR", TipoFuncionario.FUNCIONARIO, rh));
        funcionarios.add(new Funcionario(6, "Juliana Mendes", "JM", TipoFuncionario.FUNCIONARIO, rh));

        // Engenharia
        funcionarios.add(new Funcionario(7, "Gabriel Alves", "GA", TipoFuncionario.ADMINISTRADOR, engenharia));
        funcionarios.add(new Funcionario(8, "Jussara Freitas", "JF", TipoFuncionario.FUNCIONARIO, engenharia));
        funcionarios.add(new Funcionario(9, "Rafael Dias", "RD", TipoFuncionario.FUNCIONARIO, engenharia));

        // Manutenção
        funcionarios.add(new Funcionario(10, "Roberto Santos", "RS", TipoFuncionario.ADMINISTRADOR, manutencao));
        funcionarios.add(new Funcionario(11, "Fernanda Lima", "FL", TipoFuncionario.FUNCIONARIO, manutencao));

        // Vendas
        funcionarios.add(new Funcionario(12, "Camila Duarte", "CD", TipoFuncionario.ADMINISTRADOR, vendas));
        funcionarios.add(new Funcionario(13, "Bruno Farias", "BF", TipoFuncionario.FUNCIONARIO, vendas));
        funcionarios.add(new Funcionario(14, "Larissa Antunes", "LA", TipoFuncionario.FUNCIONARIO, vendas));
        funcionarios.add(new Funcionario(15, "Diego Ramos", "DR", TipoFuncionario.FUNCIONARIO, vendas));

        return funcionarios;
    }
}