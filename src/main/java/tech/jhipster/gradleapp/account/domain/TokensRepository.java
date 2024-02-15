package tech.jhipster.gradleapp.account.domain;

public interface TokensRepository {
  Token buildToken(AuthenticationQuery query);
}
