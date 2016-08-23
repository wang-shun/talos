package me.ele.bpm.talos.index.exception;

import me.ele.contract.exception.ServiceException;

/**
 * Created by jeffor on 16/1/18.
 */
public class TalosException extends ServiceException{
	private static final long serialVersionUID = 1L;

	public TalosException(ExceptionCode code){
        super(code.getCode(), code.getMessage());
    }

}
