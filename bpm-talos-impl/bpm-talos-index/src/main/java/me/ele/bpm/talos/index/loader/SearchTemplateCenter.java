package me.ele.bpm.talos.index.loader;

import me.ele.bpm.talos.index.exception.TalosException;
import me.ele.bpm.talos.index.template.SearchTemplate;
import me.ele.bpm.talos.index.util.HuskarUtil;

public class SearchTemplateCenter extends HuskarLoader<SearchTemplate> {

    public SearchTemplateCenter(){
        super(HuskarUtil.SEARCH_TEMPLATES_VERSION, HuskarUtil.SEARCH_TEMPLATES);
    }

    @Override
    public SearchTemplate createHotLoaderElem(String key, String conf) throws TalosException {
        return new SearchTemplate(conf);
    }
}
