package br.com.tcc.Utils;

public final class GeradorDeCodigoAutomatico {
	private static int ID = 1;

    public static int getProximoId() {
        return ID++;
    }
}
