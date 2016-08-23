package me.ele.bpm.talos.index.register;

import java.util.List;

import me.ele.bpm.talos.index.router.IRouter;

/**
 * Created by jeffor on 16/1/18.
 */
public class RouterRegister extends AbstractRegister<IRouter> {

    private static RouterRegister register;

    public static RouterRegister getRegister(){
        return register;
    }

    public RouterRegister(){
        super();
        register = this;
    }

    public RouterRegister(List<IRouter> elemList){
        super(elemList);
        register = this;
    }
}
