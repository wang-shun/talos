package me.ele.bpm.talos.search.soa;

import me.ele.contract.iface.IServiceChecker;

public class SearchServiceChecker implements IServiceChecker {
    @Override
    public boolean isAvailable() {
        return true;
    }
}
