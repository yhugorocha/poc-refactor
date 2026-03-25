package io.github.yhugorocha.security.jwt;

import io.github.yhugorocha.domain.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    @Value("${security.jwt.expiracao}")
    private String expiracao;

    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinatura;

    public String gerarToken(Usuario usuario) {
        final long expString = Long.valueOf(expiracao);
        final LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString);
        final Date data = Date.from(dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant());

        return Jwts
                .builder()
                .setSubject(usuario.getLogin())
                .setExpiration(data)
                .signWith(SignatureAlgorithm.HS256, chaveAssinatura)
                .compact();
    }

    private Claims obterClaims(String token) throws ExpiredJwtException {
        final SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(chaveAssinatura));

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Boolean tokenValido(String token){
        try {
            final Claims claim = obterClaims(token);
            final Date dataExpiracao = claim.getExpiration();
            final LocalDateTime localDateTime = dataExpiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            return !LocalDateTime.now().isAfter(localDateTime);

        }catch (Exception e){
            return false;
        }
    }

    public String obterLoginUsuario(String token) throws ExpiredJwtException{
        return (String) obterClaims(token).getSubject();
    }
}
