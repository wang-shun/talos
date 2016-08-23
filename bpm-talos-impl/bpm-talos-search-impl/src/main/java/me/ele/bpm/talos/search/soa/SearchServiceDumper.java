package me.ele.bpm.talos.search.soa;

import me.ele.contract.iface.IServiceDumper;

public class SearchServiceDumper implements IServiceDumper {
    @Override
    public Object dumpInfo() {
        ServiceInfo info = new ServiceInfo();
        info.setAppId("bpm.talos.search");
        info.setWorking(true);
        return info;
    }

    public static class ServiceInfo {
        private String appId;
        private boolean isWorking;

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public boolean isWorking() {
            return isWorking;
        }

        public void setWorking(boolean isWorking) {
            this.isWorking = isWorking;
        }
    }
}
