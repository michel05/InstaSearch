package br.com.interfaces;

import java.util.List;

public interface IRepository<IDominioPersistente, String> {

    void salvar(IDominioPersistente dominio);
    List<IDominioPersistente> listarTodos();
    IDominioPersistente buscarPorId(String id);
    void atualizar(IDominioPersistente dominio);
    Object getNextSequence(); 

}
