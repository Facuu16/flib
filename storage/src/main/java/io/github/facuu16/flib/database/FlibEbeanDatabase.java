package io.github.facuu16.flib.database;

import io.github.facuu16.flib.annotation.Attached;
import io.github.facuu16.flib.annotation.Component;
import io.github.facuu16.flib.common.Lifecycle;
import io.github.facuu16.flib.configuration.StorageConfigurationContainer;
import team.unnamed.inject.Inject;

@Component
@Attached(target = Lifecycle.class)
public class FlibEbeanDatabase extends EbeanDatabase {

    @Inject
    private FlibEbeanDatabase(StorageConfigurationContainer container) {
        super(container.asDatabaseConfiguration());
    }

}
