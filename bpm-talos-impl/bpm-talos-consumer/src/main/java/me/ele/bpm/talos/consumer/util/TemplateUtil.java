package me.ele.bpm.talos.consumer.util;

import me.ele.bpm.talos.index.exception.TalosException;
import me.ele.bpm.talos.index.loader.IndexTemplateCenter;
import me.ele.bpm.talos.index.template.SearchTemplate;

import org.springframework.stereotype.Component;

@Component
public class TemplateUtil {

    private static IndexTemplateCenter templates = new IndexTemplateCenter();	// 模板控制中心
    
    public static SearchTemplate get(String templateCode) throws TalosException {
    	return templates.get(templateCode);
    }
}
