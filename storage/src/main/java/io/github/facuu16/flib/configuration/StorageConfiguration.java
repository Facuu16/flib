package io.github.facuu16.flib.configuration;

import io.github.facuu16.flib.FlibApplication;
import lombok.Data;
import lombok.experimental.Accessors;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@Data
@ConfigSerializable
@Accessors(fluent = true)
public class StorageConfiguration {

    private String driver = "org.h2.Driver";
    private String url = "jdbc:h2:file:${root}/storage/repository/database;DB_CLOSE_ON_EXIT=true";
    private String username = "sa";
    private String password = "sa";
    private String platform = "update";

    public String url() {
        return url.replace("${root}", FlibApplication.folder().toAbsolutePath().toString());
    }

}