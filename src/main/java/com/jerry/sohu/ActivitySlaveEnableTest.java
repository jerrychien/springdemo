package com.jerry.sohu;

import org.apache.commons.lang.time.DateUtils;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author: qianzhiyong
 * @date: 2018/8/10 11:57
 * @description: 计算活动有效徒弟的信息
 */
public class ActivitySlaveEnableTest {

    /**
     * 徒弟绑定日期
     */
    static Map<String, Date> slaveBindDateMap = new HashMap<>();
    /**
     * 徒弟最后签到日期
     */
    static Map<String, Date> slaveSignDateMap = new HashMap<>();

    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) throws IOException {
        init();
        handle();
    }

    private static void init() throws IOException {
        slaveSignDateMap.clear();
        slaveBindDateMap.clear();
        loadMapFormFile("/Users/qianzhiyong/Documents/10031_salve_bind_date.txt", slaveBindDateMap);
        loadMapFormFile("/Users/qianzhiyong/Documents/10031_slave_last_sign_date.txt", slaveSignDateMap);
    }

    private static void handle() {
        if (slaveBindDateMap.size() > slaveSignDateMap.size()) {
            System.out.println("数据正常，开始分析");
            int noCount = 0;
            int succCount = 0;
            int errCount = 0;
            int totalCount = 0;
            Set<Map.Entry<String, Date>> setList = slaveBindDateMap.entrySet();
            for (Map.Entry<String, Date> entry : setList) {
                String userId = entry.getKey();
                Date bindDate = entry.getValue();
                Date signedDate = slaveSignDateMap.get(entry.getKey());
                if (signedDate == null) {
                    System.out.println("用户:" + userId + " 无签到时间");
                    noCount++;
                } else if (DateUtils.addDays(bindDate, 2).before(signedDate)) {
                    System.out.println("条件吻合,绑定时间：" + simpleDateFormat.format(bindDate) + ",签到时间：" + simpleDateFormat.format(signedDate));
                    succCount++;
                } else {
                    System.out.println("用户:" + userId + " 签到时间不在绑定时间之后2天，绑定时间：" + simpleDateFormat.format(bindDate) + ",签到时间：" + simpleDateFormat.format(signedDate));
                    errCount++;
                }
                totalCount++;
            }
            System.out.println("总数：" + totalCount);
            System.out.println("无签到时间：" + noCount);
            System.out.println("签到时间不吻合：" + errCount);
            System.out.println("正确：" + succCount);
        } else {
            System.out.println("数据异常");
        }
    }

    private static void loadMapFormFile(String filePath, Map<String, Date> targetMap) throws IOException {
        System.out.println("start loadMapFormFile from " + filePath + ", start");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(filePath)));
        String line = "";
        int count = 0;
        while ((line = bufferedReader.readLine()) != null) {
            String[] arr = line.split("\\t");
            try {
                Date d1 = simpleDateFormat.parse(arr[1]);
//                System.out.println("userId:" + arr[0] + ",date:" + simpleDateFormat.format(d1));
                targetMap.put(arr[0], d1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            count++;
        }
        System.out.println("loadMap from " + filePath + " over, count:" + count);
    }
}
