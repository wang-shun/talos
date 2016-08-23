package me.ele.bpm.talos.index.register;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by jeffor on 16/1/18.
 * 逻辑注册器（具体注册数据类型未定）
 */
public class AbstractRegister<T extends AbstractRegister.IRegistable> {

    private Map<String, T> routers = new TreeMap<>();


    public AbstractRegister(){
    }

    /** 注册路由器 */
    public AbstractRegister(List<T> elemeList){
        for (T elem : elemeList){
            routers.put(elem.getNameForRegist(), elem);
        }
    }

    /** 获得路由器 */
    public T getRouter(String name){
        return routers.get(name);
    }


    /** 注册器迭代器 */
    public Iterator<T> iterator(){
        return new RegisterIterator();
    }


    /** 注册器迭代器类 */
    private class RegisterIterator implements Iterator<T>{

        Iterator<Map.Entry<String, T>> itr = null;

        public RegisterIterator(){
            itr = routers.entrySet().iterator();
        }

        @Override
        public T next() {
            return itr.next().getValue();
        }

        @Override
        public boolean hasNext() {
            return itr.hasNext();
        }
    }

    /** 可注册接口 */
    public interface IRegistable {

        /** 获得注册名称 */
        public String getNameForRegist();
    }
}
