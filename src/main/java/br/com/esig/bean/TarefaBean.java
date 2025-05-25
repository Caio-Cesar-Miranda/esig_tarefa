package br.com.esig.bean;

import br.com.esig.dao.TarefaDAO;
import br.com.esig.model.Tarefa;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@ManagedBean(name = "tarefaBean")
@ViewScoped
public class TarefaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<String> listaResponsaveis = Arrays.asList("Caio Cesar", "Clara Cerqueira", "Lucas Marques");
    private Tarefa tarefa = new Tarefa();
    private String filtroSituacao = "Em andamento";
    private List<Tarefa> lista;

    public String salvar() {
        if (tarefa.getTitulo() == null || tarefa.getTitulo().isEmpty()  ||
                tarefa.getDescricao() == null || tarefa.getDescricao().isEmpty() ||  
                tarefa.getResponsavel() == null || tarefa.getResponsavel().isEmpty() || 
                tarefa.getPrioridade() == null || tarefa.getPrioridade().isEmpty() ||
                tarefa.getDeadline() == null) {

            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Preencha todos os campos do formulário, incluindo o deadline!", ""));
            return null;
        }
        try {
            TarefaDAO dao = new TarefaDAO();
            if (tarefa.getId() == 0) {
                tarefa.setSituacao("Em andamento");
                dao.inserir(tarefa);
            } else {
                dao.atualizar(tarefa);
            }

            tarefa = new Tarefa();
            listar();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    public String listar() {
        try {
            TarefaDAO dao = new TarefaDAO();
            lista = dao.listarPorSituacao(filtroSituacao);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String remover(int id) {
        try {
            TarefaDAO dao = new TarefaDAO();
            dao.remover(id);
            listar();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String concluir(int id) {
        try {
            TarefaDAO dao = new TarefaDAO();
            dao.concluir(id);
            listar();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getListaResponsaveis() {
        return listaResponsaveis;
    }
    
    public void editar(Tarefa t) {
        this.tarefa = t;
    }

	public Tarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	public String getFiltroSituacao() {
		return filtroSituacao;
	}

	public void setFiltroSituacao(String filtroSituacao) {
		this.filtroSituacao = filtroSituacao;
	}

	public List<Tarefa> getLista() {
		return lista;
	}

	public void setLista(List<Tarefa> lista) {
		this.lista = lista;
	}
	
	@PostConstruct
	public void init() {
	    listar();
	}


    
}
