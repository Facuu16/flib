package io.github.facuu16.flib.configuration;

import io.github.facuu16.flib.FlibApplication;
import io.github.facuu16.flib.annotation.Attached;
import io.github.facuu16.flib.annotation.Component;
import io.ebean.config.DatabaseConfig;
import io.ebean.datasource.DataSourceConfig;
import io.github.facuu16.flib.common.Updatable;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

@Component
@Attached(target = Updatable.class)
public class StorageConfigurationContainer extends HoconConfigurationContainer<StorageConfiguration> {

    private StorageConfigurationContainer() {
        super(HoconConfigurationLoader.builder().path(FlibApplication.folder().resolve("storage.conf")));
    }

    public DatabaseConfig asDatabaseConfiguration() {
        final StorageConfiguration configuration = get();

        final DatabaseConfig model = new DatabaseConfig();
        final DataSourceConfig source = new DataSourceConfig();

        model.setName("flib");
        model.setDefaultServer(true);
        model.setRegister(true);

        source.setDriver(configuration.driver());
        source.setUrl(configuration.url());
        source.setUsername(configuration.username());
        source.setPassword(configuration.password());
        source.setPlatform(configuration.platform());

        model.setDataSourceConfig(source);

        return model;
    }

}