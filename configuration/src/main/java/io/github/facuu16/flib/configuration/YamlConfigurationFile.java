package io.github.facuu16.flib.configuration;

import io.github.facuu16.flib.core.FlibApplication;
import io.github.facuu16.flib.core.util.Formatter;
import io.github.facuu16.flib.core.util.Paths;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.Accessors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

@Data
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = true)
public class YamlConfigurationFile extends YamlConfiguration implements ConfigurationFile {
    
    protected final String undefined = "undefined";
    protected final File file;
    
    public YamlConfigurationFile(@NonNull Path path, boolean defaults) throws IOException, InvalidConfigurationException {
        this.file = Paths.ensure(Paths.Type.FILE, path).toFile();
        this.load(file);
        defaults(defaults);
    }
    
    public YamlConfigurationFile(@NonNull Path path) throws IOException, InvalidConfigurationException {
        this(path, true);
    }
    
    @Override
    public void copyDefaults(@NonNull InputStream input) throws IOException {
        try (final InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {
            setDefaults(YamlConfiguration.loadConfiguration(reader));
            options().copyDefaults(true);
            save();
        }
    }
    
    @Override
    public boolean update() {
        try {
            load(file);
            return true;
        } catch (Exception e) {
            FlibApplication.logger().log(Level.SEVERE, "Could not reload '" + file.getName() + "' yaml file", e);
            return false;
        }
    }
    
    @Override
    public boolean save() {
        try {
            save(file);
            return true;
        } catch (Exception e) {
            FlibApplication.logger().log(Level.SEVERE, "Could not save '" + file.getName() + "' yaml file", e);
            return false;
        }
    }
    
    public <T> T getOrDefault(@NonNull String section, @NonNull String key, @NonNull T value) {
        if (!isConfigurationSection(section))
            return value;
        
        return (T) getConfigurationSection(section).get(key, value);
    }
    
    public String single(@NonNull String path, @NonNull String key) {
        if (!isConfigurationSection(path))
            return undefined;
        
        final ConfigurationSection section = getConfigurationSection(path);
        
        return section.isList(key) ? Formatter.multiline(section.getStringList(key)) : section.getString(key, undefined);
    }
    
    public String message(@NonNull String section, @NonNull String key) {
        return getOrDefault(section, key, undefined);
    }
    
    public List<String> messages(@NonNull String section, @NonNull String key) {
        return getOrDefault(section, key, Collections.singletonList(undefined));
    }
    
    public Component component(@NonNull String section, @NonNull String key) {
        return MiniMessage.miniMessage().deserialize(single(section, key));
    }
    
    public Component component(@NonNull String section, @NonNull String key, @NonNull TagResolver... resolvers) {
        return MiniMessage.miniMessage().deserialize(single(section, key), resolvers);
    }

}