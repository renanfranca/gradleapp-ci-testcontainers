package tech.jhipster.gradleapp.account.infrastructure.primary;

import static org.assertj.core.api.Assertions.*;

import tech.jhipster.gradleapp.JsonHelper;
import tech.jhipster.gradleapp.UnitTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@UnitTest
class RestAuthenticationQueryTest {

  @Test
  void shouldDeserializeFromJson() {
    RestAuthenticationQuery query = JsonHelper.readFromJson(json(), RestAuthenticationQuery.class);

    assertThat(query.authenticationToken()).isEqualTo(new UsernamePasswordAuthenticationToken("user", "password"));
    assertThat(query.isRememberMe()).isTrue();
  }

  private String json() {
    return """
        {
          "username": "user",
          "password": "password",
          "rememberMe": true
        }
        """;
  }
}
