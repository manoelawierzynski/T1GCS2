public class Funcionario {
    private int id;
    private String nome;
    private String iniciais;
    private TipoFuncionario tipo;
    private Departamento departamento;

    public Funcionario(int id, String nome, String iniciais, TipoFuncionario tipo, Departamento departamento) {
        this.id = id;
        this.nome = nome;
        this.iniciais = iniciais;
        this.tipo = tipo;
        this.departamento = departamento;
    }

    public int getId() { 
        return id;
    }

    public String getNome() { 
        return nome; 
    }

    public String getIniciais() { 
        return iniciais; 
    }

    public TipoFuncionario getTipo() { 
        return tipo; 
    }

    public Departamento getDepartamento() { 
        return departamento; 
    }

    @Override
    public String toString() {
        return nome + " (" + iniciais + ") - " + tipo + " [" + departamento.getNome() + "]";
    }
}