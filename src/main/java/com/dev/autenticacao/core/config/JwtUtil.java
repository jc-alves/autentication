package com.dev.autenticacao.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class JwtUtil {

    @Value("${jwt.public.key}")
    private RSAPublicKey publicKey;
    @Value("${jwt.private.key}")
    private RSAPrivateKey privateKey;

    public String decodeToken(String token) {
        try {
            // Cria um decodificador de token JWT usando a chave pública
            NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withPublicKey(publicKey).build();

            // Decodifica o token JWT
            Jwt jwt = jwtDecoder.decode(token);

            // Retorna o conteúdo do token decodificado (neste exemplo, retorna os claims do token como JSON)
            return jwt.getClaims().toString();
        } catch (Exception e) {
            // Trata exceções de decodificação de token JWT
            e.printStackTrace(); // Trate conforme necessário
            return null; // Retorna null ou uma mensagem de erro, dependendo da sua lógica
        }
    }



    // Método para extrair o idEmpresa do token JWT
    public String extractIdEmpresa(String token) {
        try {
            // Cria um decodificador de token JWT usando a chave pública
            NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withPublicKey(publicKey).build();

            // Decodifica o token JWT
            Jwt jwt = jwtDecoder.decode(token);
            String cl = jwt.getClaims().toString();

            int startIndex = cl.indexOf("idEmpresa=") + "idEmpresa=".length();
            int endIndex = cl.indexOf(",", startIndex);

            // Extrair o valor do idEmpresa
            String idEmpresa = cl.substring(startIndex, endIndex);
            // Remover os caracteres de `{}` para obter um JSON válido
            // String jsonStr = jwt.getClaims().toString().replaceAll("\\{", "").replaceAll("\\}", "");
            // Parsear a string JSON para um objeto JSONObject
            //    JSONObject jsonObject = new JSONObject(jsonStr);



            // Retorna o valor do claim "idEmpresa"
            return jwt.getClaim("idEmpresa").toString();
        } catch (Exception e) {
            // Trata exceções de decodificação de token JWT
            e.printStackTrace(); // Trate conforme necessário
            return null; // Retorna null ou uma mensagem de erro, dependendo da sua lógica
        }
    }
}
