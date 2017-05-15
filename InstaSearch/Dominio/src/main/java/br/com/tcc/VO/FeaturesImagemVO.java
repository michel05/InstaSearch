package br.com.tcc.VO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.tcc.Interfaces.IDominioPersistente;

//@Entity
@Table(name = "features_imagem")
public class FeaturesImagemVO implements IDominioPersistente<Integer>{

	private static final long serialVersionUID = -3575062986323002799L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToOne
	private PostagemVO postagem;

	@Column(name = "quantidade_texto")
	private int quantidadeTexto;

	@Column(name = "food")
	private boolean food;

	@Column(name = "dish")
	private boolean dish;

	@Column(name = "cuisine")
	private boolean cuisine;

	@Column(name = "meal")
	private boolean meal;

	@Column(name = "dessert")
	private boolean dessert;

	@Column(name = "produce")
	private boolean produce;

	@Column(name = "meat")
	private boolean meat;

	@Column(name = "breakfast")
	private boolean breakfast;

	@Column(name = "grilling")
	private boolean grilling;

	@Column(name = "asian_food")
	private boolean asianFood;

	@Column(name = "cake")
	private boolean cake;

	@Column(name = "ice_cream")
	private boolean iceCream;

	@Column(name = "steak")
	private boolean steak;

	@Column(name = "fish")
	private boolean fish;

	@Column(name = "plant")
	private boolean plant;

	@Column(name = "seafood")
	private boolean seafood;

	@Column(name = "fruit")
	private boolean fruit;

	@Column(name = "fast_food")
	private boolean fastFood;

	@Column(name = "drink")
	private boolean drink;

	@Column(name = "vegetable")
	private boolean vegetable;

	@Column(name = "salad")
	private boolean salad;

	@Column(name = "italian_food")
	private boolean italianFood;
	
	@Column(name = "outros")
	private boolean outros;

	public PostagemVO getPostagem() {
		return postagem;
	}

	public void setPostagem(PostagemVO postagem) {
		this.postagem = postagem;
	}

	public int getQuantidadeTexto() {
		return quantidadeTexto;
	}

	public void setQuantidadeTexto(int quantidadeTexto) {
		this.quantidadeTexto = quantidadeTexto;
	}

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isFood() {
		return food;
	}

	public boolean isDish() {
		return dish;
	}

	public boolean isCuisine() {
		return cuisine;
	}

	public boolean isMeal() {
		return meal;
	}

	public boolean isDessert() {
		return dessert;
	}

	public boolean isProduce() {
		return produce;
	}

	public boolean isMeat() {
		return meat;
	}

	public boolean isBreakfast() {
		return breakfast;
	}

	public boolean isGrilling() {
		return grilling;
	}

	public boolean isAsianFood() {
		return asianFood;
	}

	public boolean isCake() {
		return cake;
	}

	public boolean isIceCream() {
		return iceCream;
	}

	public boolean isSteak() {
		return steak;
	}

	public boolean isFish() {
		return fish;
	}

	public boolean isPlant() {
		return plant;
	}

	public boolean isSeafood() {
		return seafood;
	}

	public boolean isFruit() {
		return fruit;
	}

	public boolean isFastFood() {
		return fastFood;
	}

	public boolean isDrink() {
		return drink;
	}

	public boolean isVegetable() {
		return vegetable;
	}

	public boolean isSalad() {
		return salad;
	}

	public boolean isItalianFood() {
		return italianFood;
	}

	public void setFood(boolean food) {
		this.food = food;
	}

	public boolean isOutros() {
		return outros;
	}

	public void setOutros(boolean outros) {
		this.outros = outros;
	}

	public void setDish(boolean dish) {
		this.dish = dish;
	}

	public void setCuisine(boolean cuisine) {
		this.cuisine = cuisine;
	}

	public void setMeal(boolean meal) {
		this.meal = meal;
	}

	public void setDessert(boolean dessert) {
		this.dessert = dessert;
	}

	public void setProduce(boolean produce) {
		this.produce = produce;
	}

	public void setMeat(boolean meat) {
		this.meat = meat;
	}

	public void setBreakfast(boolean breakfast) {
		this.breakfast = breakfast;
	}

	public void setGrilling(boolean grilling) {
		this.grilling = grilling;
	}

	public void setAsianFood(boolean asianFood) {
		this.asianFood = asianFood;
	}

	public void setCake(boolean cake) {
		this.cake = cake;
	}

	public void setIceCream(boolean iceCream) {
		this.iceCream = iceCream;
	}

	public void setSteak(boolean steak) {
		this.steak = steak;
	}

	public void setFish(boolean fish) {
		this.fish = fish;
	}

	public void setPlant(boolean plant) {
		this.plant = plant;
	}

	public void setSeafood(boolean seafood) {
		this.seafood = seafood;
	}

	public void setFruit(boolean fruit) {
		this.fruit = fruit;
	}

	public void setFastFood(boolean fastFood) {
		this.fastFood = fastFood;
	}

	public void setDrink(boolean drink) {
		this.drink = drink;
	}

	public void setVegetable(boolean vegetable) {
		this.vegetable = vegetable;
	}

	public void setSalad(boolean salad) {
		this.salad = salad;
	}

	public void setItalianFood(boolean italianFood) {
		this.italianFood = italianFood;
	}

}
