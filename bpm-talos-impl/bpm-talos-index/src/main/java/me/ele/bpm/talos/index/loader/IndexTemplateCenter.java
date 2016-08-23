package me.ele.bpm.talos.index.loader;

import me.ele.bpm.talos.index.exception.TalosException;
import me.ele.bpm.talos.index.template.SearchTemplate;
import me.ele.bpm.talos.index.util.HuskarUtil;

public class IndexTemplateCenter extends HuskarLoader<SearchTemplate> {

    public IndexTemplateCenter(){
        super(HuskarUtil.INDEX_TEMPLATES_VERSION, HuskarUtil.INDEX_TEMPLATES);
    }

    @Override
    public SearchTemplate createHotLoaderElem(String key, String conf) throws TalosException {
        return new SearchTemplate(conf);
    }
}
