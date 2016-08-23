package me.ele.bpm.talos.index.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.ele.bpm.talos.index.exception.TalosException;

/**
 * Created by jeffor on 16/1/20.
 */
public class SearchTemplateContainer {

    private static Map<Integer, SearchTemplate> templates = new HashMap<>();

    public static void loadTemplateContainer(List<String> templateConfigs)
            throws TalosException {
        for (String templateConfig : templateConfigs){
            addTemplate(templateConfig);
        }
    }

    public static SearchTemplate getTemplate(int id){
        return templates.get(id);
    }

    public static void addTemplate(String templateConfig)
            throws TalosException {
        SearchTemplate template = new SearchTemplate(templateConfig);
        SearchTemplateContainer.templates.put(template.getId(), template);
    }

}
