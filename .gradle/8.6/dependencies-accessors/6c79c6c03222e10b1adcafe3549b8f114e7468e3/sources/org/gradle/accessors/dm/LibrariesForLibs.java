package org.gradle.accessors.dm;

import org.gradle.api.NonNullApi;
import org.gradle.api.artifacts.MinimalExternalModuleDependency;
import org.gradle.plugin.use.PluginDependency;
import org.gradle.api.artifacts.ExternalModuleDependencyBundle;
import org.gradle.api.artifacts.MutableVersionConstraint;
import org.gradle.api.provider.Provider;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.ProviderFactory;
import org.gradle.api.internal.catalog.AbstractExternalDependencyFactory;
import org.gradle.api.internal.catalog.DefaultVersionCatalog;
import java.util.Map;
import org.gradle.api.internal.attributes.ImmutableAttributesFactory;
import org.gradle.api.internal.artifacts.dsl.CapabilityNotationParser;
import javax.inject.Inject;

/**
 * A catalog of dependencies accessible via the {@code libs} extension.
 */
@NonNullApi
public class LibrariesForLibs extends AbstractExternalDependencyFactory {

    private final AbstractExternalDependencyFactory owner = this;
    private final QuarkusLibraryAccessors laccForQuarkusLibraryAccessors = new QuarkusLibraryAccessors(owner);
    private final VersionAccessors vaccForVersionAccessors = new VersionAccessors(providers, config);
    private final BundleAccessors baccForBundleAccessors = new BundleAccessors(objects, providers, config, attributesFactory, capabilityNotationParser);
    private final PluginAccessors paccForPluginAccessors = new PluginAccessors(providers, config);

    @Inject
    public LibrariesForLibs(DefaultVersionCatalog config, ProviderFactory providers, ObjectFactory objects, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) {
        super(config, providers, objects, attributesFactory, capabilityNotationParser);
    }

    /**
     * Group of libraries at <b>quarkus</b>
     */
    public QuarkusLibraryAccessors getQuarkus() {
        return laccForQuarkusLibraryAccessors;
    }

    /**
     * Group of versions at <b>versions</b>
     */
    public VersionAccessors getVersions() {
        return vaccForVersionAccessors;
    }

    /**
     * Group of bundles at <b>bundles</b>
     */
    public BundleAccessors getBundles() {
        return baccForBundleAccessors;
    }

    /**
     * Group of plugins at <b>plugins</b>
     */
    public PluginAccessors getPlugins() {
        return paccForPluginAccessors;
    }

    public static class QuarkusLibraryAccessors extends SubDependencyFactory {
        private final QuarkusHibernateLibraryAccessors laccForQuarkusHibernateLibraryAccessors = new QuarkusHibernateLibraryAccessors(owner);
        private final QuarkusJdbcLibraryAccessors laccForQuarkusJdbcLibraryAccessors = new QuarkusJdbcLibraryAccessors(owner);
        private final QuarkusRestLibraryAccessors laccForQuarkusRestLibraryAccessors = new QuarkusRestLibraryAccessors(owner);

        public QuarkusLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>arc</b> with <b>io.quarkus:quarkus-arc</b> coordinates and
         * with version reference <b>quarkus</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getArc() {
            return create("quarkus.arc");
        }

        /**
         * Dependency provider for <b>flyway</b> with <b>io.quarkus:quarkus-flyway</b> coordinates and
         * with version reference <b>quarkus</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getFlyway() {
            return create("quarkus.flyway");
        }

        /**
         * Group of libraries at <b>quarkus.hibernate</b>
         */
        public QuarkusHibernateLibraryAccessors getHibernate() {
            return laccForQuarkusHibernateLibraryAccessors;
        }

        /**
         * Group of libraries at <b>quarkus.jdbc</b>
         */
        public QuarkusJdbcLibraryAccessors getJdbc() {
            return laccForQuarkusJdbcLibraryAccessors;
        }

        /**
         * Group of libraries at <b>quarkus.rest</b>
         */
        public QuarkusRestLibraryAccessors getRest() {
            return laccForQuarkusRestLibraryAccessors;
        }

    }

    public static class QuarkusHibernateLibraryAccessors extends SubDependencyFactory {
        private final QuarkusHibernateOrmLibraryAccessors laccForQuarkusHibernateOrmLibraryAccessors = new QuarkusHibernateOrmLibraryAccessors(owner);

        public QuarkusHibernateLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>quarkus.hibernate.orm</b>
         */
        public QuarkusHibernateOrmLibraryAccessors getOrm() {
            return laccForQuarkusHibernateOrmLibraryAccessors;
        }

    }

    public static class QuarkusHibernateOrmLibraryAccessors extends SubDependencyFactory {

        public QuarkusHibernateOrmLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>panache</b> with <b>io.quarkus:quarkus-hibernate-orm-panache</b> coordinates and
         * with version reference <b>quarkus</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getPanache() {
            return create("quarkus.hibernate.orm.panache");
        }

    }

    public static class QuarkusJdbcLibraryAccessors extends SubDependencyFactory {

        public QuarkusJdbcLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>postgresql</b> with <b>io.quarkus:quarkus-jdbc-postgresql</b> coordinates and
         * with version reference <b>quarkus</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getPostgresql() {
            return create("quarkus.jdbc.postgresql");
        }

    }

    public static class QuarkusRestLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public QuarkusRestLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>rest</b> with <b>io.quarkus:quarkus-rest</b> coordinates and
         * with version reference <b>quarkus</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> asProvider() {
            return create("quarkus.rest");
        }

        /**
         * Dependency provider for <b>jackson</b> with <b>io.quarkus:quarkus-rest-jackson</b> coordinates and
         * with version reference <b>quarkus</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJackson() {
            return create("quarkus.rest.jackson");
        }

    }

    public static class VersionAccessors extends VersionFactory  {

        public VersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>dockerClient</b> with value <b>3.4.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getDockerClient() { return getVersion("dockerClient"); }

        /**
         * Version alias <b>flyway</b> with value <b>11.3.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getFlyway() { return getVersion("flyway"); }

        /**
         * Version alias <b>jackson</b> with value <b>2.17.3</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getJackson() { return getVersion("jackson"); }

        /**
         * Version alias <b>kotlin</b> with value <b>2.0.20</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getKotlin() { return getVersion("kotlin"); }

        /**
         * Version alias <b>mapstruct</b> with value <b>1.6.3</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getMapstruct() { return getVersion("mapstruct"); }

        /**
         * Version alias <b>quarkus</b> with value <b>3.18.2</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getQuarkus() { return getVersion("quarkus"); }

    }

    public static class BundleAccessors extends BundleFactory {

        public BundleAccessors(ObjectFactory objects, ProviderFactory providers, DefaultVersionCatalog config, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) { super(objects, providers, config, attributesFactory, capabilityNotationParser); }

        /**
         * Dependency bundle provider for <b>quarkus</b> which contains the following dependencies:
         * <ul>
         *    <li>io.quarkus:quarkus-rest</li>
         *    <li>io.quarkus:quarkus-rest-jackson</li>
         *    <li>io.quarkus:quarkus-arc</li>
         *    <li>io.quarkus:quarkus-jdbc-postgresql</li>
         *    <li>io.quarkus:quarkus-hibernate-orm-panache</li>
         *    <li>io.quarkus:quarkus-flyway</li>
         * </ul>
         * <p>
         * This bundle was declared in catalog libs.versions.toml
         */
        public Provider<ExternalModuleDependencyBundle> getQuarkus() {
            return createBundle("quarkus");
        }

    }

    public static class PluginAccessors extends PluginFactory {

        public PluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

    }

}
