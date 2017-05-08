package br.com.tcc.Interfaces;

import java.io.Serializable;

public interface IDominioPersistente<PK> extends Serializable {

	PK getId();
}
