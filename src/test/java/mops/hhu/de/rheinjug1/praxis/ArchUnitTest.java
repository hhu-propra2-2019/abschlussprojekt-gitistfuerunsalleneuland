package mops.hhu.de.rheinjug1.praxis;

import com.sun.tools.javac.Main;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import mops.PraxisApplication;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;

@AnalyzeClasses(packagesOf = PraxisApplication.class)
public class ArchUnitTest {

    @ArchTest
    static final ArchRule hexArch = layeredArchitecture()
            .layer("domain").definedBy("..domain..")
            .layer("web").definedBy("..adapters.web..")
            .layer("db").definedBy("..adapters.database..")
            .layer("auth").definedBy("..adapters.auth..")
            .layer("encryption").definedBy("..adapters.encryption..")
            .layer("mail").definedBy("..adapters.mail..")
            .layer("meetup").definedBy("..adapters.meetup..")
            .layer("minio").definedBy("..adapters.minio..")
            .layer("time").definedBy("..adapters.time..")

            .whereLayer("web").mayNotBeAccessedByAnyLayer()
            .whereLayer("db").mayNotBeAccessedByAnyLayer()
            .whereLayer("auth").mayNotBeAccessedByAnyLayer()
            .whereLayer("encryption").mayNotBeAccessedByAnyLayer()
            .whereLayer("mail").mayNotBeAccessedByAnyLayer()
            .whereLayer("meetup").mayNotBeAccessedByAnyLayer()
            .whereLayer("minio").mayNotBeAccessedByAnyLayer()
            .whereLayer("time").mayNotBeAccessedByAnyLayer();
}
