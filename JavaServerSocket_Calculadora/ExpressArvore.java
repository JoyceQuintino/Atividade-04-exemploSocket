import java.io.Serializable;

public class ExpressArvore implements Serializable{
	protected char operacao;
	protected ExpressArvore left, right;
	protected Double o1, o2;
	protected ExpressArvore expressArvore;
	
	public ExpressArvore(char operacao) {
		this.operacao = operacao;
	}
	public ExpressArvore(char operacao, Double o1, Double o2) {
		this.operacao = operacao;
		this.o1 = o1;
		this.o2 = o2;
	}
	public ExpressArvore(char operacao, ExpressArvore expressArvore, Double o2) {
		this.operacao = operacao;
		this.left = expressArvore;
		this.o2 = o2;
	}
}
