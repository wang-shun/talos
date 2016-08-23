package me.ele.bpm.talos.consumer.document;

import me.ele.bpm.talos.consumer.util.TemplateUtil;
import me.ele.bpm.talos.index.document.Document;
import me.ele.bpm.talos.index.exception.TalosException;
import me.ele.bpm.talos.index.template.Template;
import me.ele.bpm.talos.mq.push.model.MessageEx;
import me.ele.elog.Log;
import me.ele.elog.LogFactory;

public class CommonDocument extends Document {
	
	private Log log = LogFactory.getLog(CommonDocument.class);
	
	private Template template;
	
	public CommonDocument() {
		super();
	}

	public CommonDocument(MessageEx messageEx) throws TalosException {
		super(messageEx.getId(), messageEx.getAction(), messageEx.getPayload(), messageEx.getVersion());
		template = TemplateUtil.get(messageEx.getTemplateCode());
	}

	@Override
	public String getCluster() {
		return template.getClusterName();
	}
	
	@Override
	public String getIndex() {
		String[] indices = null;
		try {
			indices = template.indexDestination(payload);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if (null == indices || indices.length == 0) {
			log.error("index not found!");
			return null;
		}
		return indices[0];
	}

	@Override
	public String getType() {
		String[] types = null;
		try {
			types = template.doctypeDestination(payload);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if (null == types || types.length == 0) {
			log.error("docType not found!");
			return null;
		}
		return types[0];
	}

}
