package me.ele.bpm.talos.common.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SkyrimUtil {

	@SuppressWarnings("resource")
	public static OutputStream read(String file) throws IOException {
		InputStream in = new FileInputStream(file);
		OutputStream swap = null;
        int ch;
        while ((ch = in.read()) != -1) {   
            swap.write(ch);   
        }
        return swap;
	}
}
