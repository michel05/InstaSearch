package br.com.tcc.Dominio;

import br.com.tcc.VO.FeaturesImagemVO;
import br.com.tcc.VO.PostagemVO;

public class FeaturesImagem {

	private FeaturesImagemVO vo;

	public FeaturesImagem() {
		vo = new FeaturesImagemVO();
	}

	public FeaturesImagem(FeaturesImagemVO featuresImagem) {
		vo = featuresImagem;
	}


	public void populeFeaturesImagem(PostagemVO postagem) {

		vo.setPostagem(postagem);
		populeFeaturesOCR();
		classifiqueCategoriaPostagem();
	}

	private void classifiqueCategoriaPostagem() {
		this.getVo().getPostagem().getListaAnaliseImageVO().forEach(x -> {

			switch (x.getCategoriaImagem().getNome()) {
			case "food":
				this.getVo().setFood(true);
				break;
			case "dish":
				this.getVo().setDish(true);
				break;
			case "asian food":
				this.getVo().setAsianFood(true);
				break;
			case "breakfast":
				this.getVo().setBreakfast(true);
				break;
			case "cake":
				this.getVo().setCake(true);
				break;
			case "cuisine":
				this.getVo().setCuisine(true);
				break;
			case "dessert":
				this.getVo().setDessert(true);
				break;
			case "drink":
				this.getVo().setDrink(true);
				break;
			case "fast food":
				this.getVo().setBreakfast(true);
				break;
			case "fish":
				this.getVo().setFish(true);
				break;
			case "fruit":
				this.getVo().setFruit(true);
				break;
			case "grilling":
				this.getVo().setGrilling(true);
				break;
			case "ice cream":
				this.getVo().setIceCream(true);
				break;
			case "italian food":
				this.getVo().setItalianFood(true);
				break;
			case "meal":
				this.getVo().setMeat(true);
				break;
			case "meat":
				this.getVo().setMeat(true);
				break;
			case "plant":
				this.getVo().setPlant(true);
				break;
			case "produce":
				this.getVo().setProduce(true);
				break;
			case "salad":
				this.getVo().setSalad(true);
				break;
			case "seafood":
				this.getVo().setSeafood(true);
				break;
			case "steak":
				this.getVo().setSteak(true);
				break;
			case "vegetable":
				this.getVo().setVegetable(true);
				break;

			default:
				this.getVo().setOutros(true);
				break;
			}

		});

	}

	private void populeFeaturesOCR() {

		if (this.getVo().getPostagem().getOcrAnaliseImageVO() != null) {
			vo.setQuantidadeTexto(this.getVo().getPostagem().getOcrAnaliseImageVO().getMensagem().length());
		}
	}

	public FeaturesImagemVO getVo() {
		return vo;
	}

	public void setVo(FeaturesImagemVO vo) {
		this.vo = vo;
	}
}
