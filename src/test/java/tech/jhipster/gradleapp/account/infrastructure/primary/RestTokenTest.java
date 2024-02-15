package tech.jhipster.gradleapp.account.infrastructure.primary;

import static tech.jhipster.gradleapp.account.domain.TokensFixture.*;
import static org.assertj.core.api.Assertions.*;

import tech.jhipster.gradleapp.JsonHelper;
import tech.jhipster.gradleapp.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class RestTokenTest {

  @Test
  void shouldConvertFromDomain() {
    assertThat(JsonHelper.writeAsString(RestToken.from(token()))).isEqualTo(json());
  }

  private String json() {
    return "{\"id_token\":\"token\"}";
  }
}
