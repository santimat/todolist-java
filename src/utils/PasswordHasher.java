package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordHasher {
  // Definimos el algoritmo de clave
  private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
  // Alto numero de iteraciones para volverlo lento y complejizar ataques de
  // fuerza bruta
  private static final int ITERATIONS = 310000;
  // longitud del hash resultante (256 bits = 32 bytes)
  private static final int KEY_LENGTH = 256;
  // longitud del salt en bytes (16 bytes es un tamaño comun y seguro)
  private static final int SALT_LENGTH = 16;

  /**
   * Genera un salt unico
   * 
   * @return Arreglo de bytes que representa el salt
   */
  public static byte[] generateSalt() {
    // Inicializa un generador de números aleatorios criptográficamente seguro.
    SecureRandom random = new SecureRandom();
    // Crea un arreglo de bytes con la longitud definida para el salt.
    byte[] salt = new byte[SALT_LENGTH];
    // Rellena el arreglo 'salt' con bytes aleatorios.
    random.nextBytes(salt);
    return salt;
  }

  /**
   * Genera el hash de la clave usando el salt y PBKDF2
   * Se usa tanto al almacenar como al verificar
   * 
   * @param password en texto plano
   * @param salt     unico de la clave
   * @return arreglo de bytes que representa el hash final
   */
  private static byte[] hash(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
    KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
    SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
    return factory.generateSecret(spec).getEncoded();
  }

  /**
   * 
   * @param plainPassword Clave en texto plano ingresada por el usuario
   * @return Arreglo con el hash codificado y el salt codificado, ambos en base 64
   * @throws Exception
   */
  public static String[] hashPassword(String plainPassword) throws Exception {
    byte[] saltBytes = generateSalt();
    byte[] hashBytes = PasswordHasher.hash(plainPassword, saltBytes);

    String encodedSalt = Base64.getEncoder().encodeToString(saltBytes);
    String encodeHash = Base64.getEncoder().encodeToString(hashBytes);

    return new String[] { encodeHash, encodedSalt };
  }

  /**
   * Verificacion para el login
   * 
   * @param enteredPassword Clave ingresada por el usuario
   * @param storedHash      Clave almacenada
   * @param storedSalt      Salt almacenado
   * @return true si la clave es correcta, sino false
   */
  public static boolean verifyPassword(String enteredPassword, String storedHash, String storedSalt)
      throws Exception {
    byte[] saltBytes = Base64.getDecoder().decode(storedSalt);
    byte[] storedHashBytes = Base64.getDecoder().decode(storedHash);

    byte[] enteredHashBytes = hash(enteredPassword, saltBytes);

    return MessageDigest.isEqual(enteredHashBytes, storedHashBytes);
  }

}
