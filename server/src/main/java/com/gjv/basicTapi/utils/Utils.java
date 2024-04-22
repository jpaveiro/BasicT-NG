package com.gjv.basicTapi.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.UUID;

public class Utils {

  /**
   * @param password Espera uma senha para ser criptografada.
   * @return uma senha criptografada em SHA-256.
   */
  public static String hashPassword(String password) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

      StringBuilder hexString = new StringBuilder();

      for (byte b : hash) {
        String hex = Integer.toHexString(0xff & b);
        if (hex.length() == 1) {
          hexString.append(0);
        }

        hexString.append(hex);
      }

      return hexString.toString();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * @return retorna um id com 47 carácteres separados por (15 carácteres)-(15 carácteres)-(carácteres).
   */
  public static String generateId() {
    String id = UUID.randomUUID().toString();
    String formatId = id.replaceAll("-", "").substring(0, 15);
    return formatId + "-" + formatId + "-" + formatId;
  }

  /**
   * @param cpf Espera um cpf com ou sem formatação.
   * @return retorna um cpf sem formatação no tipo String apenas se o tamanho dele sem formatação for igual a 11.
   */
  public static String checkCpf(String cpf) {
    cpf = cpf.replaceAll("[^0-9]", "");

    if (cpf.length() != 11) {
      return "Muito grande/pequeno";
    }

    int sum = 0;
    for (int i = 0; i < 9; i++) {
      sum += (cpf.charAt(i) - '0') * (10 - i);
    }
    int digit1 = 11 - (sum % 11);
    if (digit1 > 9) {
      digit1 = 0;
    }

    sum = 0;
    for (int i = 0; i < 10; i++) {
      sum += (cpf.charAt(i) - '0') * (11 - i);
    }
    int digit2 = 11 - (sum % 11);
    if (digit2 > 9) {
      digit2 = 0;
    }

    boolean isValid = (cpf.charAt(9) - '0') == digit1 && (cpf.charAt(10) - '0') == digit2;
    if (isValid) {
      return cpf;
    }
    return null;
  }

  /**
   * @param rg Espera um RG com ou sem formatação
   * @return retorna um RG sem formatação no tipo String apenas se o tamanho dele for igual 9
   */
  public static String checkRg(String rg) {
    String rgFormated = rg.replaceAll("[^0-9]", "");

    if (rgFormated.length() == 9) {
      return rgFormated;
    }
    return null;
  }
}
