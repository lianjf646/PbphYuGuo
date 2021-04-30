package com.pbph.yuguo.util;

/**
 * Created by Administrator on 2018/1/23.
 */

public class MissionUtils {
    public static String getMissionName(int type) {
        String name = "";
        switch (type) {
            case 0: {
                name = "关注";
            }
            break;
            case 1: {
                name = "mp直投";
            }
            break;
            case 2: {
                name = "三方直投";
            }
            break;
            case 3: {
                name = "简单任务";
            }
            break;
            case 4: {
                name = "微信阅读";
            }
            break;
            case 5: {
                name = "微信阅读点赞";
            }
            break;
            case 6: {
                name = "微信阅读评论点赞";
            }
            break;
            case 8: {
                name = "拼多多砍价";
            }
            break;
            case 9: {
                name = "拼多多拆红包";
            }
            break;
            case 10: {
                name = "微信阅读评论";
            }
            break;
            case 11: {
                name = "微博点赞";
            }
            break;
            case 12: {
                name = "微博评论";
            }
            break;
            case 13: {
                name = "微博转发";
            }
            break;
            case 14: {
                name = "微博阅读";
            }
            break;
            case 15: {
                name = "抖音关注";
            }
            break;
            case 16: {
                name = "抖音点赞";
            }
            break;
            case 17: {
                name = "抖音转发";
            }
            break;
        }
        return name;
    }

    public static String getMissionDesc(int type) {
        String name = "";
        switch (type) {
            case 0:
            case 1:
            case 2:
                name = "投票";
                break;//投票
            case 4:
                name = "阅读";
                break;//阅读
            case 5:
                name = "点赞";
                break;//点赞
            case 6:
                name = "评论点赞";
                break;//点赞
            case 8:
                name = "砍价";
                break;//点赞
            case 9:
                name = "拆红包";
                break;//点赞
            case 10:
                name = "评论";
                break;
            case 11:
                name = "微博点赞";
                break;
            case 12:
                name = "微博评论";
                break;
            case 13:
                name = "微博转发";
                break;
            case 14:
                name = "微博阅读";
                break;
            case 15:
                name = "抖音关注";
                break;
            case 16:
                name = "抖音点赞";
                break;
            case 17:
                name = "抖音转发";
                break;
            case 18:
                name = "抖音评论";
                break;
            case 19:
                name = "微视关注";
                break;
            case 20:
                name = "微视点赞";
                break;
            case 21:
                name = "微视评论";
                break;
            case 22:
                name = "猫眼砍价";
                break;
        }
        return name;
    }
}
