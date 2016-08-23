package me.ele.bpm.talos.index.router.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import me.ele.bpm.talos.index.exception.TalosException;
import me.ele.bpm.talos.index.router.IRouter;

import org.codehaus.jackson.JsonNode;

public class AssembleRouter implements IRouter{
	
	private static final String name = "assembleRouter";

	@Override
	public String getNameForRegist() {
		return name;
	}

	@Override
	public Map<String, String> getRequiredParams() {
		return null;
	}

	@Override
	public String[] destination(Map<String, Object> doc, JsonNode routerParams) throws TalosException {
		Iterator<JsonNode> it = routerParams.get("fields").getElements();
		List<Object> values = new ArrayList<Object>();
		while (it.hasNext()) {
			Object obj = doc.get(it.next().asText());
			if (obj == null) {
				return new String[]{};
			}
			values.add(obj);
		}
		return new String[]{String.format(routerParams.get("dataBase").asText(), values.toArray())};
	}
	
}
