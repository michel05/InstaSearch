package br.com.interfaces;

import java.util.List;

public interface IRepository<IDominioPersistente, PK> {

    void salvar(IDominioPersistente dominio);
    List<IDominioPersistente> listarTodos();
    IDominioPersistente buscarPorIdInstagram(String id);
    IDominioPersistente buscarPorId(PK id);
    void atualizar(IDominioPersistente dominio);
    Object getNextSequence(); 

}
