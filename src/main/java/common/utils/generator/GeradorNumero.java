package common.utils.generator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;

public class GeradorNumero {

    private static BigDecimal valor;

    public static String gerarNumero(int min, int max) {
        SecureRandom rand = new SecureRandom();
        Integer numero = rand.nextInt((max - min) + 1) + min;
        return numero.toString();
    }

    public static Integer gerarNumeroInteger(int min, int max) {
        SecureRandom rand = new SecureRandom();
        return rand.ints((max + 1)).limit(1).findFirst().getAsInt();
    }

    public static Long gerarNumeroLong(Long min, Long max) {
        SecureRandom rand = new SecureRandom();
        Long generateLong = min + (long) (rand.nextInt(10) * (max - min));
        return generateLong;
    }

    public static BigDecimal gerarValor() {
        valor = null;
        SecureRandom rand = new SecureRandom();
        BigDecimal min = new BigDecimal(1.0);
        BigDecimal max = new BigDecimal(1000.0);
        GeradorNumero.valor = min.add(new BigDecimal(rand.nextInt(10)).multiply(max.subtract(min)));
        GeradorNumero.valor = GeradorNumero.valor.setScale(2, RoundingMode.FLOOR);
        GeradorNumero.valor = GeradorNumero.valor.stripTrailingZeros();
        return GeradorNumero.valor;

    }

}
