package com.algaworks.algafood.documentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.stereotype.Component;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.DefaultsProviderPlugin;
import springfox.documentation.spi.service.ResourceGroupingStrategy;
import springfox.documentation.spi.service.contexts.DocumentationContextBuilder;
import springfox.documentation.spring.web.SpringGroupingStrategy;
import springfox.documentation.spring.web.plugins.DefaultConfiguration;
import springfox.documentation.spring.web.plugins.DocumentationPluginsManager;

/*
 * @Primary serve para informar que esse será o bean primario, no caso já temos DocumentationPluginsManager
 * que já é do Spring, então precisou ser customizado para que não ocorra o erro com o método getPluginFor
 * que estava acontecendo depois que foi atualizada a versão do spring para 2.2.2 e o spring-plugin-core do hetoas para 2.0.0
 * */
@Primary
@Component
@SuppressWarnings("deprecation")
public class DocumentationPluginsManagerCustom extends DocumentationPluginsManager {

    @Autowired
    @Qualifier("defaultsProviderPluginRegistry")
    private PluginRegistry<DefaultsProviderPlugin, DocumentationType> defaultsProviders;

    @Autowired
    @Qualifier("resourceGroupingStrategyRegistry")
    private PluginRegistry<ResourceGroupingStrategy, DocumentationType> resourceGroupingStrategies;

    @Override
    public DocumentationContextBuilder createContextBuilder(DocumentationType documentationType, DefaultConfiguration defaultConfiguration) {
        return ((DefaultsProviderPlugin) this.defaultsProviders.getPluginOrDefaultFor(documentationType, defaultConfiguration)).create(documentationType).withResourceGroupingStrategy(this.resourceGroupingStrategy(documentationType));
    }

    @Override
    public ResourceGroupingStrategy resourceGroupingStrategy(DocumentationType documentationType) {
        return (ResourceGroupingStrategy) this.resourceGroupingStrategies.getPluginOrDefaultFor(documentationType, new SpringGroupingStrategy());
    }

}
