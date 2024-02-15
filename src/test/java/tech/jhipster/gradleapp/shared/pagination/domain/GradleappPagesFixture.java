package tech.jhipster.gradleapp.shared.pagination.domain;

import java.util.List;

import tech.jhipster.gradleapp.shared.pagination.domain.GradleappPage.GradleappPageBuilder;

public final class GradleappPagesFixture {

  private GradleappPagesFixture() {}

  public static GradleappPage<String> page() {
    return pageBuilder().build();
  }

  public static GradleappPageBuilder<String> pageBuilder() {
    return GradleappPage.builder(List.of("test")).currentPage(2).pageSize(10).totalElementsCount(21);
  }
}
