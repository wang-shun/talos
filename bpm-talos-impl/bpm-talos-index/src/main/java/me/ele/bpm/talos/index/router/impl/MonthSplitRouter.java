package me.ele.bpm.talos.index.router.impl;

import me.ele.bpm.talos.index.exception.ExceptionCode;
import me.ele.bpm.talos.index.exception.TalosException;
import me.ele.bpm.talos.index.router.IRouter;
import org.codehaus.jackson.JsonNode;
import org.elasticsearch.common.joda.time.LocalDate;
import org.elasticsearch.common.joda.time.format.DateTimeFormat;
import org.elasticsearch.common.joda.time.format.DateTimeFormatter;

import java.util.*;

/**
 * 纯值路由器
 */
public class MonthSplitRouter implements IRouter{

    private static final Map<String, String> requiredParams = new TreeMap<>();
    private static final String name = "MonthSplitRouter";
    private static DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    /** 初始化路由器参数映射表 */
    static {
        requiredParams.put("prifix", "路由前缀");
        requiredParams.put("count", "分片大小（天）");
        requiredParams.put("maxIndex", "最大分片号");
        requiredParams.put("maxWidth", "最长时间跨度（天）");
        requiredParams.put("searchWidth", "单次搜索的时间跨度（天）");
    }


    @Override
    public String getNameForRegist() {
        return name;
    }

    /**
     * params: {"name": "router name"}
     * */
    @Override
    public Map<String, String> getRequiredParams() {
        return requiredParams;
    }


    @Override
    public String[] destination(Map<String, Object> doc, JsonNode routerParams) throws TalosException {
        try {
            return getDocumentTypes(doc, routerParams);
        }catch (Exception e){
            throw new TalosException(ExceptionCode.PARAM_READ_EXCEPTION);
        }
    }

    /**
     * 将一个每个月份划分成多个分片
     * */
    private String[] getDocumentTypes(Map<String, Object> doc, JsonNode routerParams)  {
        Set<String> documentTypes = new HashSet<>();

        Integer searchWidth = routerParams.get("searchWidth") == null? 30: routerParams.get("searchWidth").asInt();
        LocalDate today= LocalDate.now();
        LocalDate severalDaysAgo = today.minusDays(routerParams.get("maxWidth").asInt());

        String beginDate = (String)doc.get("begin_date");
        String endDate = (String)doc.get("end_date");

        LocalDate endDay = (endDate == null) ?
                today : LocalDate.parse((endDate.length() < 12?
                endDate + " 00:00:00": endDate.replace("T", " ")), format);
        LocalDate beginDay = (beginDate == null) ?
                endDay.minusDays(7) : LocalDate.parse((beginDate.length() < 12?
                    beginDate+" 00:00:00": beginDate.replace("T", " ")), format);

        beginDay = (beginDay == null || beginDay.compareTo(severalDaysAgo) < 0 )? severalDaysAgo :beginDay ;
        endDay = (endDay == null || endDay.compareTo(today) > 0) ? today : endDay;

        if(searchWidth != null && beginDay.plusDays(searchWidth).compareTo(endDay) < 0){
            beginDay = endDay.minusDays(searchWidth);
        }


        int year;
        int month;
        int day;
        int delta;
        String documentType;
        String documentFormat = routerParams.get("prefix").asText() + "_%s_%s_%s";
        int count = routerParams.get("count").asInt();
        int maxIndex = routerParams.get("maxIndex").asInt();

        while(beginDay.compareTo(endDay) <= 0){
            year = beginDay.getYear();
            month = beginDay.getMonthOfYear();
            day = beginDay.getDayOfMonth()/ count;
            delta = (day + 1)*count - beginDay.getDayOfMonth();
            if(day > maxIndex) {
                day = maxIndex;
            }
            documentType = String.format(documentFormat, year, month, day);
            documentTypes.add(documentType);
            if(beginDay.compareTo(endDay) < 0 &&
                    beginDay.plusDays(delta).compareTo(endDay) > 0){
                beginDay = endDay;
            }else {
                beginDay = beginDay.plusDays(delta);
            }
        }
        return documentTypes.toArray(new String[]{});
    }
}
