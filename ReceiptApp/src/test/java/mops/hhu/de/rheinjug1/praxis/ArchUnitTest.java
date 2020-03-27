package mops.hhu.de.rheinjug1.praxis;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import mops.PraxisApplication;

@SuppressWarnings({"PMD.ClassNamingConventions", "PMD.FieldNamingConventions"})
@AnalyzeClasses(
    packagesOf = PraxisApplication.class,
    importOptions = {ImportOption.DoNotIncludeTests.class})
public class ArchUnitTest {

  @ArchTest
  static final ArchRule hexArch =
      layeredArchitecture()
          .layer("domain")
          .definedBy("..domain..")
          .layer("web")
          .definedBy("..adapters.web..")
          .layer("db")
          .definedBy("..adapters.database..")
          .layer("auth")
          .definedBy("..adapters.auth..")
          .layer("encryption")
          .definedBy("..adapters.encryption..")
          .layer("mail")
          .definedBy("..adapters.mail..")
          .layer("meetup")
          .definedBy("..adapters.meetup..")
          .layer("minio")
          .definedBy("..adapters.minio..")
          .layer("time")
          .definedBy("..adapters.time..")
          .whereLayer("web")
          .mayNotBeAccessedByAnyLayer()
          .whereLayer("db")
          .mayNotBeAccessedByAnyLayer()
          .whereLayer("auth")
          .mayNotBeAccessedByAnyLayer()
          .whereLayer("encryption")
          .mayNotBeAccessedByAnyLayer()
          .whereLayer("mail")
          .mayNotBeAccessedByAnyLayer()
          .whereLayer("meetup")
          .mayNotBeAccessedByAnyLayer()
          .whereLayer("minio")
          .mayNotBeAccessedByAnyLayer()
          .whereLayer("time")
          .mayNotBeAccessedByAnyLayer();
}
