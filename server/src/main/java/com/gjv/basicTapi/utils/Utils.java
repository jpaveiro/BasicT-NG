package com.gjv.basicTapi.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;

public class Utils {

  public static String checkName(String name) {
    String regex = "^[\\p{L} .'-áéíóúÁÉÍÓÚâêîôÂÊÎÔãõÃÕçÇ]+$";

    if (name.matches(regex)) {
      return name.toUpperCase();
    }

    return null;
  }

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

  public static String generateToken() {
    String id = UUID.randomUUID().toString();
    return id.replaceAll("-", "").substring(0, 15);
  }

  /**
   * 
   * @param phone Espera um telefone com ou sem formatação
   * @return retorna um telefone sem formatação
   */
  public static String formatPhone(String phone) {
        phone = phone.replaceAll("[^0-9]", "").replaceAll("\\s", "");
        
        if (phone.length() > 11 || phone.length() < 10) {
            return null;
        }
        
        return phone;
    }

  /**
   * @param cpf Espera um cpf com ou sem formatação.
   * @return retorna um cpf sem formatação no tipo String apenas se o tamanho dele sem formatação for igual a 11.
   */
  public static String checkCpf(String cpf) {
    cpf = cpf.replaceAll("[^0-9]", "");

    if (cpf.length() != 11) {
      return null;
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

  public static Date checkBirthDate(String birthDate) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    try {
      LocalDate parsedDate = LocalDate.parse(birthDate, formatter);

      LocalDate now = LocalDate.now();
      if(parsedDate.isAfter(now)) {
        return null;
      }

      int years_old = Period.between(parsedDate, now).getYears();

      if (years_old < 18) {
        return null;
      }

      return Date.valueOf(parsedDate);
    } catch (DateTimeParseException e) {
      return null;
    }
  }
}
