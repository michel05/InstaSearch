package br.com.tcc.VO;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.tcc.Enums.EficienciadaPostagemEnum;
import br.com.tcc.Enums.PeriodoDoDiaEnum;
import br.com.tcc.Interfaces.IDominioPersistente;

@Entity
@Table(name = "features")
public class FeaturesVO implements IDominioPersistente<Integer> {

	private static final long serialVersionUID = 8375435727883420550L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToOne
	@JoinColumn(name = "id_postagem")
	private PostagemVO postagem;

	@Column(name = "num_curtidas")
	private int numCurtidas;

	@Column(name = "num_comentarios")
	private int numComentarios;
	
	@Column(name = "horario_postagem")
	private Calendar horarioDaPostagem;

	@Enumerated(EnumType.STRING)
	@Column(name = "periodo_dia")
	private PeriodoDoDiaEnum periodoDoDia;

	@Enumerated(EnumType.STRING)
	@Column(name = "eficiencia_postagem")
	private EficienciadaPostagemEnum eficienciaDaPostagemCurtida;

	@Enumerated(EnumType.STRING)
	@Column(name = "eficiencia_postagem_Comentario")
	private EficienciadaPostagemEnum eficienciaDaPostagemComentario;

	@Column(name = "tamanho_descricao")
	private int tamanhoDescricao;

	@Column(name = "num_pico_curtida")
	private int numPicoDeCurtida;

	@Column(name = "horario_pico_curtida")
	private Calendar horarioPicoDeCurtida;

	@Column(name = "tempo_ate_pico_curtida")
	private double tempoAtePicoDeCurtida;

	@Column(name = "velocidade_queda")
	private double velocidadeDaQueda; // Curtidas por min

	@Column(name = "ativo")
	private boolean ativo;
	
	@Column(name = "ativo_comentario", columnDefinition = "Boolean default true")
	private boolean ativoComentario;

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PostagemVO getPostagem() {
		return postagem;
	}

	public void setPostagem(PostagemVO postagem) {
		this.postagem = postagem;
	}

	public int getNumCurtidas() {
		return numCurtidas;
	}

	public void setNumCurtidas(int numCurtidas) {
		this.numCurtidas = numCurtidas;
	}

	public int getNumComentarios() {
		return numComentarios;
	}

	public void setNumComentarios(int numComentarios) {
		this.numComentarios = numComentarios;
	}

	public PeriodoDoDiaEnum getPeriodoDoDia() {
		return periodoDoDia;
	}

	public void setPeriodoDoDia(PeriodoDoDiaEnum periodoDoDia) {
		this.periodoDoDia = periodoDoDia;
	}

	public int getTamanhoDescricao() {
		return tamanhoDescricao;
	}

	public void setTamanhoDescricao(int tamanhoDescricao) {
		this.tamanhoDescricao = tamanhoDescricao;
	}

	public int getNumPicoDeCurtida() {
		return numPicoDeCurtida;
	}

	public void setNumPicoDeCurtida(int numPicoDeCurtida) {
		this.numPicoDeCurtida = numPicoDeCurtida;
	}

	public Calendar getHorarioPicoDeCurtida() {
		return horarioPicoDeCurtida;
	}

	public void setHorarioPicoDeCurtida(Calendar horarioPicoDeCurtida) {
		this.horarioPicoDeCurtida = horarioPicoDeCurtida;
	}

	public double getTempoAtePicoDeCurtida() {
		return tempoAtePicoDeCurtida;
	}

	public void setTempoAtePicoDeCurtida(double tempoAtePicoDeCurtida) {
		this.tempoAtePicoDeCurtida = tempoAtePicoDeCurtida;
	}

	public double getVelocidadeDaQueda() {
		return velocidadeDaQueda;
	}

	public void setVelocidadeDaQueda(double velocidadeDaQueda) {
		this.velocidadeDaQueda = velocidadeDaQueda;
	}

	public Calendar getHorarioDaPostagem() {
		return horarioDaPostagem;
	}

	public void setHorarioDaPostagem(Calendar horarioDaPostagem) {
		this.horarioDaPostagem = horarioDaPostagem;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public EficienciadaPostagemEnum getEficienciaDaPostagemCurtida() {
		return eficienciaDaPostagemCurtida;
	}

	public EficienciadaPostagemEnum getEficienciaDaPostagemComentario() {
		return eficienciaDaPostagemComentario;
	}

	public void setEficienciaDaPostagemCurtida(EficienciadaPostagemEnum eficienciaDaPostagemCurtida) {
		this.eficienciaDaPostagemCurtida = eficienciaDaPostagemCurtida;
	}

	public void setEficienciaDaPostagemComentario(EficienciadaPostagemEnum eficienciaDaPostagemComentario) {
		this.eficienciaDaPostagemComentario = eficienciaDaPostagemComentario;
	}

	public boolean isAtivoComentario() {
		return ativoComentario;
	}

	public void setAtivoComentario(boolean ativoComentario) {
		this.ativoComentario = ativoComentario;
	}

}
