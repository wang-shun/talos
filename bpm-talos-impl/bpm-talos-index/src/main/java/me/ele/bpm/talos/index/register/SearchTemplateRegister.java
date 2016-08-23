package me.ele.bpm.talos.index.register;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import me.ele.bpm.talos.index.exception.ExceptionCode;
import me.ele.bpm.talos.index.exception.TalosException;
import me.ele.bpm.talos.index.template.SearchTemplateContainer;
import me.ele.elog.Log;
import me.ele.elog.LogFactory;

/**
 * Created by jeffor on 16/1/20.
 * 仅仅提供模板的初始化加载方式
 */
public class SearchTemplateRegister {

    private static SearchTemplateRegister register;

    private static Log log = LogFactory.getLog(SearchTemplateRegister.class);

    private SearchTemplateRegister(){}

    /**
     *  加载文件模板
     *  模板路径为: classpath:template
     *  */
    @SuppressWarnings("resource")
	public static SearchTemplateRegister loadTemplatesFromDirectory() throws TalosException {
        String rootPath = System.getProperty("user.dir");
        File file = new File(rootPath + "/template");
        File[] tempList = file.listFiles();
        
        List<String> templates = new ArrayList<>();
        if (tempList != null) {
        	int count;
        	byte[] bytes = new byte[1024];
            for (int i = 0; i < tempList.length; i++) {
                log.info("TempFile[{}] add!", tempList[i].getName());
                if (tempList[i].isFile() && tempList[i].getName().endsWith(".json")) {
                    try {
                        InputStream stream = new FileInputStream(tempList[i]);
                        StringBuffer buffer = new StringBuffer();
                        while ((count = stream.read(bytes, 0, 1024)) > 0){
                            buffer.append(new String(bytes, 0, count));
                        }
                        templates.add(buffer.toString());
                    }catch (IOException e){
                        throw new TalosException(ExceptionCode.TEMPLATE_READ_EXCEPTION);
                    }
                }
            }
        }
        SearchTemplateContainer.loadTemplateContainer(templates);
        if (register == null){
            register = new SearchTemplateRegister();
        }
        return register;
    }

}
